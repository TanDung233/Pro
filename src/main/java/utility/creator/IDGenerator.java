package main.java.utility.creator;

import main.java.utility.CollectionManager;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Utility class for generating unique IDs.
 * Uses auto-increment for generating unique IDs.
 */
public class IDGenerator {

    private static AtomicInteger nextId = new AtomicInteger(CollectionManager.idmax()+1);

    /**
     * Generates a unique ID using auto-increment.
     *
     * @return the generated unique ID.
     */
    public static int generateID() {
        return nextId.getAndIncrement();
    }
}
