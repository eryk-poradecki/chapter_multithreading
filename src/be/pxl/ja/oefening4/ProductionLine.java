package be.pxl.ja.oefening4;

import java.util.LinkedList;
import java.util.Queue;

public class ProductionLine {
    private final Queue<Package> line;

    public ProductionLine() {
        this.line = new LinkedList<>();
    }

    public synchronized void addPackage(Package p) {
        line.add(p);
    }

    public synchronized Package getPackage() {
        if (!line.isEmpty()) {
            return line.poll();
        }
        return null;
    }
}
