package be.pxl.ja.oefening3;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class User implements Runnable {
    private final BankAccount bankAccount;
    private final int noOfTransactions;
    private final int transactionLimit;
    private final String name;
    private Random random = new Random();

    private final CountDownLatch latch;


    public User(BankAccount bankAccount, int noOfTransactions, int transactionLimit, String name, CountDownLatch latch) {
        this.bankAccount = bankAccount;
        this.noOfTransactions = noOfTransactions;
        this.transactionLimit = transactionLimit;
        this.name = name;
        this.latch = latch;
    }

    @Override
    public void run() {
        for (int i = 0; i < noOfTransactions; i++) {
            if (random.nextBoolean()) {
                bankAccount.deposit(random.nextInt(transactionLimit), name);
            } else {
                bankAccount.withdraw(random.nextInt(transactionLimit), name);
            }
        }
        latch.countDown();
    }
}
