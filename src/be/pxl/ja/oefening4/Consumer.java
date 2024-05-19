package be.pxl.ja.oefening4;

public class Consumer implements Runnable {
    private final ProductionLine productionLine;
    private final int productionRate; // number of packages consumed per minute

    public Consumer(ProductionLine productionLine, int productionRate) {
        this.productionLine = productionLine;
        this.productionRate = productionRate;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Package p = productionLine.getPackage();
                Thread.sleep((60 / productionRate) * 1000);
                if (p != null) {
                    System.out.println("Consumer consumed a package: " + p);
                } else {
                    System.out.println("No packages available for consumption");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }


    public static void main(String[] args) {
        ProductionLine productionLine = new ProductionLine();
        Producer producer1 = new Producer(productionLine, 20);
        Producer producer2 = new Producer(productionLine, 15);
        Producer producer3 = new Producer(productionLine, 12);
        Producer producer4 = new Producer(productionLine, 7);

        Consumer consumer = new Consumer(productionLine, 30);

        Thread producerThread1 = new Thread(producer1);
        Thread producerThread2 = new Thread(producer2);
        Thread producerThread3 = new Thread(producer3);
        Thread producerThread4 = new Thread(producer4);
        Thread consumerThread = new Thread(consumer);

        producerThread1.start();
        producerThread2.start();
        producerThread3.start();
        producerThread4.start();
        consumerThread.start();
    }
}
