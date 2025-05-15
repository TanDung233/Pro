package main.java.run;

import main.java.utility.Console;
import main.java.utility.ConsolePrinter;

public class CollectionApp {

    public static void main(String[] args) {

        ConsolePrinter consolePrinter = new ConsolePrinter();
        Console console = new Console();

        if (args.length == 0 || args[0].trim().isEmpty()) {
            consolePrinter.printInformation("You have not entered the name of the file from which you want to load the collection!");
            System.exit(0);
        }

        consolePrinter.printInformation("Welcome to our application!");
        consolePrinter.printInformation("Need help? Use command 'help' to see the available commands!");

        console.starter(args[0]);
    }
}
