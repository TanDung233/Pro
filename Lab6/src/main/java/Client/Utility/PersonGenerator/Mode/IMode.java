/**
 * The {@code IMode} interface defines methods for executing a mode and obtaining a scanner object.
 * Implementing classes should provide implementations for these methods.
 */
package Client.Utility.PersonGenerator.Mode;

import java.util.Scanner;

public interface IMode {

    /**
     * Executes the mode-specific functionality.
     */
    String executeMode();

    /**
     * Retrieves a scanner object.
     *
     * @return a scanner object for input processing.
     */
    Scanner getScanner();
}
