package be.pxl.ja.exercise2;

public class DivisorCounter extends Thread {

    private final int min;
    private final int max;

    private int no_divisors = 0;

    private int found_number = 0;

    public DivisorCounter(int min, int max) {
        this.min = min;
        this.max = max;
//        long start = System.currentTimeMillis();
//        findNumberWithMostDivisors();
//        long end = System.currentTimeMillis();
//        System.out.println("Time: " + (end - start) + "ms");
    }

    @Override
    public void run() {
        findNumberWithMostDivisors();
    }


    public void findNumberWithMostDivisors() {
        for (int i = min; i <= max; i++) {
            int divisors = 0;
            for (int j = 1; j <= i; j++) {
                if (i % j == 0) {
                    divisors++;
                }
            }
            if (divisors > no_divisors) {
                no_divisors = divisors;
                found_number = i;
            }
        }
    }

    public static void main(String[] args) {
        // Single thread
//        DivisorCounter divisorCounter = new DivisorCounter(1, 50000);
//        System.out.println("Number with most divisors between 1 and 10000 is " + divisorCounter.found_number + " with " + divisorCounter.no_divisors + " divisors.");

        // 10 threads
        DivisorCounter divisorCounter1 = new DivisorCounter(1, 5000);
        DivisorCounter divisorCounter2 = new DivisorCounter(5001, 10000);
        DivisorCounter divisorCounter3 = new DivisorCounter(10001, 15000);
        DivisorCounter divisorCounter4 = new DivisorCounter(15001, 20000);
        DivisorCounter divisorCounter5 = new DivisorCounter(20001, 25000);
        DivisorCounter divisorCounter6 = new DivisorCounter(25001, 30000);
        DivisorCounter divisorCounter7 = new DivisorCounter(30001, 35000);
        DivisorCounter divisorCounter8 = new DivisorCounter(35001, 40000);
        DivisorCounter divisorCounter9 = new DivisorCounter(40001, 45000);
        DivisorCounter divisorCounter10 = new DivisorCounter(45001, 50000);
        long start = System.currentTimeMillis();
        divisorCounter1.start();
        divisorCounter2.start();
        divisorCounter3.start();
        divisorCounter4.start();
        divisorCounter5.start();
        divisorCounter6.start();
        divisorCounter7.start();
        divisorCounter8.start();
        divisorCounter9.start();
        divisorCounter10.start();
        try {
            divisorCounter1.join();
            divisorCounter2.join();
            divisorCounter3.join();
            divisorCounter4.join();
            divisorCounter5.join();
            divisorCounter6.join();
            divisorCounter7.join();
            divisorCounter8.join();
            divisorCounter9.join();
            divisorCounter10.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DivisorCounter[] divisorCounters = {divisorCounter1, divisorCounter2, divisorCounter3, divisorCounter4, divisorCounter5, divisorCounter6, divisorCounter7, divisorCounter8, divisorCounter9, divisorCounter10};
        int maxDivisors = 0;
        int number = 0;
        for (DivisorCounter divisorCounter : divisorCounters) {
            if (divisorCounter.no_divisors > maxDivisors) {
                maxDivisors = divisorCounter.no_divisors;
                number = divisorCounter.found_number;
            }
        }
        System.out.println("Number with most divisors between 1 and 50000 is " + number + " with " + maxDivisors + " divisors.");
        long end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start) + "ms");
    }
}
