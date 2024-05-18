package be.pxl.ja.oefening3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class BankAccount {
	private int balance;
	private String accountNumber;
	private BufferedWriter logger;

	private int transactionLimit;

	
	public BankAccount(String accountNumber, int initialBalance, BufferedWriter logger, int transactionLimit)  {
		this.accountNumber = accountNumber;
		this.balance = initialBalance;
		this.logger = logger;
		this.transactionLimit = transactionLimit;
	}
	
	public synchronized void deposit(int amount, String user) {
		if (amount > transactionLimit) {
			return;
		}
		balance += amount;
		try {
			logger.write(String.format("deposit of %d by %s, remaining: %d", amount, user, balance));
			logger.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized void withdraw(int amount, String user) {
		if (amount > transactionLimit) {
			return;
		}
		try {
			if (balance - amount < 0) {
				logger.write(String.format("withdrawal of %d by %s failed, insufficient funds", amount, user));
				logger.newLine();
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		balance -= amount;
		try {
			logger.write(String.format("withdrawal of %d by %s, remaining: %d", amount, user, getBalance()));
			logger.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getBalance() {
		return balance;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}

	public static void main(String[] args) throws IOException {
		Properties properties = new Properties();
		try (InputStream input = BankAccount.class.getClassLoader().getResourceAsStream("bank.properties")) {
			if (input == null) {
				System.out.println("unable to find the properties file");
				return;
			}
			properties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		int initialBalance = Integer.parseInt(properties.getProperty("account.balance"));
		int transactionLimit = Integer.parseInt(properties.getProperty("transaction.limit"));
		int noOfTransactions = Integer.parseInt(properties.getProperty("user.transactions"));
		int noOfUsers = Integer.parseInt(properties.getProperty("account.users"));

		BufferedWriter logger = new BufferedWriter(new FileWriter("transaction_log.txt"));
		BankAccount bankAccount = new BankAccount("123-4567890-02", initialBalance, logger, transactionLimit);

		CountDownLatch latch = new CountDownLatch(noOfUsers);

		for (int i = 0; i < noOfUsers; i++) {
			new Thread(new User(bankAccount, noOfTransactions, transactionLimit, "User " + i, latch)).start();
		}

		try {
			latch.await();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} finally {
			logger.close();
		}
	}
}
