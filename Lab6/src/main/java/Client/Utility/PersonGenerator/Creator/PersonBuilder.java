package Client.Utility.PersonGenerator.Creator;

import Common.Data.Color;
import Common.Exception.NotDeclaredValueException;
import Common.Exception.NotInDeclaredLimitsException;
import Common.Exception.WrongInputInScriptException;
import Server.Utility.ConsolePrinter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Utility class for getting all information about the person from the user.
 */
public class PersonBuilder {

    private Scanner userScanner;
    private boolean fileMode;
    ConsolePrinter printer = new ConsolePrinter();

    /**
     * Creates a new instance of the PersonBuilder class.
     *
     * @param scanner the Scanner object used to read user input.
     */
    public PersonBuilder(Scanner scanner) {
        this.userScanner = scanner;
    }

    /**
     * Sets the Scanner object used to read user input.
     *
     * @param scanner the Scanner object to set.
     */
    public void setUserScanner(Scanner scanner) {
        this.userScanner = scanner;
    }

    /**
     * Gets the Scanner object used to read user input.
     *
     * @return the Scanner object.
     */
    public Scanner getUserScanner() {
        return userScanner;
    }

    /**
     * Sets the file mode.
     * This mode is used when reading input from a file.
     */
    public void setFileMode() {
        this.fileMode = true;
    }

    /**
     * Sets the user mode.
     * This mode is used when reading input from the user.
     */
    public void setUserMode() {
        this.fileMode = false;
    }

    /**
     * Asks the user for the person's name.
     *
     * @return the person's name.
     * @throws WrongInputInScriptException if an error occurs while reading input in script mode.
     */
    public String nameAsker() throws WrongInputInScriptException {
        String name;
        while (true) {
            try {
                printer.printInformation("Enter person's name: ");
                printer.printInput(">");
                name = userScanner.nextLine().trim();
                if (fileMode) printer.printInformation(name);
                if (name.isEmpty()) throw new NotDeclaredValueException();
                break;
            } catch (NotDeclaredValueException exception) {
                printer.printError("The 'name' value cannot be empty! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NoSuchElementException exception) {
                printer.printError("The name isn't recognized!");
            } catch (IllegalStateException exception) {
                printer.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return name;
    }

    /**
     * Asks the user for the x coordinate.
     *
     * @return the x coordinate.
     * @throws WrongInputInScriptException if an error occurs while reading input in script mode.
     */
    public Integer xAsker() throws WrongInputInScriptException {
        String strX;
        int x;
        while (true) {
            try {
                printer.printInformation("Enter x coordinate: ");
                printer.printInput(">");
                strX = userScanner.nextLine().trim();
                if (fileMode) printer.printInformation(strX);
                x = Integer.parseInt(strX);
                if (strX.isEmpty()) throw new NotDeclaredValueException();
                break;
            } catch (NotDeclaredValueException exception) {
                printer.printError("Coordinate x cannot be empty! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NumberFormatException exception) {
                printer.printError("Coordinate x must be a number! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                printer.printError("Unexpected error!");
                System.exit(0);
            } catch (NoSuchElementException exception) {
                printer.printError("Coordinate x isn't recognized!");
                if (fileMode) throw new WrongInputInScriptException();
            }
        }
        return x;
    }

    /**
     * Asks the user for the y coordinate.
     *
     * @return the y coordinate.
     * @throws WrongInputInScriptException if an error occurs while reading input in script mode.
     */
    public Double yAsker() throws WrongInputInScriptException {
        String strY;
        double y;
        while (true) {
            try {
                printer.printInformation("Enter y coordinate: ");
                printer.printInput(">");
                strY = userScanner.nextLine().trim();
                if (fileMode) printer.printInformation(strY);
                y = Double.parseDouble(strY);
                break;
            } catch (NumberFormatException exception) {
                printer.printError("Coordinate y must be a number! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NoSuchElementException exception) {
                printer.printError("Coordinate y isn't recognized!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                printer.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return y;
    }

    public Double zAsker() throws WrongInputInScriptException {
        String strZ;
        double y;
        while (true) {
            try {
                printer.printInformation("Enter z coordinate: ");
                printer.printInput(">");
                strZ = userScanner.nextLine().trim();
                if (fileMode) printer.printInformation(strZ);
                y = Double.parseDouble(strZ);
                break;
            } catch (NumberFormatException exception) {
                printer.printError("Coordinate z must be a number! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NoSuchElementException exception) {
                printer.printError("Coordinate z isn't recognized!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                printer.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return y;
    }

    /**
     * Asks the user for the person's height.
     *
     * @return the person's height.
     * @throws WrongInputInScriptException if an error occurs while reading input in script mode.
     */
    public int heightAsker() throws WrongInputInScriptException {
        String strHeight;
        int height;
        while (true) {
            try {
                printer.printInformation("Enter height: ");
                printer.printInput(">");
                strHeight = userScanner.nextLine().trim();
                if (fileMode) printer.printInformation(strHeight);
                height = Integer.parseInt(strHeight);
                if (height <= 0) throw new NotInDeclaredLimitsException();
                break;
            } catch (NotInDeclaredLimitsException exception) {
                printer.printError("Height must be greater than 0! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NumberFormatException exception) {
                printer.printError("Height must be a number! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NoSuchElementException exception) {
                printer.printError("Height isn't recognized!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                printer.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return height;
    }

    /**
     * Asks the user for the person's birthday.
     *
     * @return the person's birthday.
     * @throws WrongInputInScriptException if an error occurs while reading input in script mode.
     */
    public LocalDateTime birthdayAsker() throws WrongInputInScriptException {
        String strBirthday;
        LocalDateTime birthday;
        while (true) {
            try {
                printer.printInformation("Enter birthday (yyyy-MM-dd HH:mm): ");
                printer.printInput(">");
                strBirthday = userScanner.nextLine().trim();
                if (fileMode) printer.printInformation(strBirthday);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                birthday = LocalDateTime.parse(strBirthday, formatter);
                break;
            } catch (DateTimeParseException exception) {
                printer.printError("Invalid date format. Please use yyyy-MM-dd HH:mm.");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NoSuchElementException exception) {
                printer.printError("Birthday isn't recognized!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                printer.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return birthday;
    }

    /**
     * Asks the user for the person's eye color.
     *
     * @return the person's eye color.
     * @throws WrongInputInScriptException if an error occurs while reading input in script mode.
     */
    public Color eyeColorAsker() throws WrongInputInScriptException {
        String strEyeColor;
        Color eyeColor;
        while (true) {
            try {
                printer.printInformation("Enter eye color (RED, BLACK, BLUE, YELLOW, BROWN): ");
                printer.printInput(">");
                strEyeColor = userScanner.nextLine().trim().toUpperCase();
                if (fileMode) printer.printInformation(strEyeColor);
                eyeColor = Color.valueOf(strEyeColor);
                break;
            } catch (IllegalArgumentException exception) {
                printer.printError("Invalid eye color. Please choose from RED, BLACK, BLUE, YELLOW, BROWN.");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NoSuchElementException exception) {
                printer.printError("Eye color isn't recognized!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                printer.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return eyeColor;
    }

    /**
     * Asks the user for the person's hair color.
     *
     * @return the person's hair color.
     * @throws WrongInputInScriptException if an error occurs while reading input in script mode.
     */
    public Color hairColorAsker() throws WrongInputInScriptException {
        String strHairColor;
        Color hairColor;
        while (true) {
            try {
                printer.printInformation("Enter hair color (RED, BLACK, BLUE, YELLOW, BROWN): ");
                printer.printInput(">");
                strHairColor = userScanner.nextLine().trim().toUpperCase();
                if (fileMode) printer.printInformation(strHairColor);
                if (strHairColor.isEmpty()) {
                    return null; // hairColor can be null
                }
                hairColor = Color.valueOf(strHairColor);
                break;
            } catch (IllegalArgumentException exception) {
                printer.printError("Invalid hair color. Please choose from RED, BLACK, BLUE, YELLOW, BROWN.");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NoSuchElementException exception) {
                printer.printError("Hair color isn't recognized!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                printer.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return hairColor;
    }

    /**
     * Asks the user for the location's name.
     *
     * @return the location's name.
     * @throws WrongInputInScriptException if an error occurs while reading input in script mode.
     */
    public String locationNameAsker() throws WrongInputInScriptException {
        String name;
        while (true) {
            try {
                printer.printInformation("Enter location name: ");
                printer.printInput(">");
                name = userScanner.nextLine().trim();
                if (fileMode) printer.printInformation(name);
                if (name.isEmpty()) throw new NotDeclaredValueException();
                break;
            } catch (NotDeclaredValueException exception) {
                printer.printError("Location name cannot be empty! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NoSuchElementException exception) {
                printer.printError("Location name isn't recognized!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                printer.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return name;
    }

    @Override
    public String toString() {
        return "PersonBuilder (helper class for user requests)";
    }
}