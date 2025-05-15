package main.java.utility.creator;

import main.java.utility.CollectionManager;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Generates unique IDs using auto-increment.
 */
public class IDGenerator {

    private final CollectionManager collectionManager;

    public IDGenerator(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }


    /**
     * Gets the current ID that will be used for the next generation
     * @return the next ID that will be generated
     */
//    public int getNextId() {
//        AtomicInteger nextId = new AtomicInteger(this.collectionManager.idmax()+1);
//        return nextId.getAndIncrement();    }

    public int getNextId() {
        return this.collectionManager.idmax() + 1;
    }
}