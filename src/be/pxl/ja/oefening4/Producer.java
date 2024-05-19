package be.pxl.ja.oefening4;

public class Producer implements Runnable {
    private final ProductionLine productionLine;
    private final int productionRate; // number of packages produced per minute

    public Producer(ProductionLine productionLine, int productionRate) {
        this.productionLine = productionLine;
        this.productionRate = productionRate;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                productionLine.addPackage(new Package());
                Thread.sleep((60 / productionRate) * 1000);
                System.out.println("Producer produced a package");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
