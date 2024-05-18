package be.pxl.ja.exercise1;

public class Talker implements Runnable {

    private final int id;

    public Talker(int id){
        this.id = id;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(this.id);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Thread
//    public static void main(string[] args) {
//        talker talker1 = new talker(1);
//        talker talker2 = new talker(2);
//        talker talker3 = new talker(3);
//        talker talker4 = new talker(4);
//        talker1.start();
//        talker2.start();
//        talker3.start();
//        talker4.start();
//
//    }

    // Runnable
    public static void main(String[] args) {
        for (int i = 0; i < 4; i++) {
            new Thread(new Talker(i+1)).start();
        }
    }
}
