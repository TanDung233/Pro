package Common.Data.Person;

import java.io.Serializable;

/**
 * The {@code Color} enum represents different colors.
 */
public enum Color implements Serializable {
    UNKNOW,
    RED,
    BLACK,
    BLUE,
    YELLOW,
    BROWN;

    /**
     * Returns a comma-separated string of all color names.
     *
     * @return A string containing all color names, separated by commas.
     */
    public static String nameList() {
        StringBuilder nameList = new StringBuilder();
        for (Color color : values()) {
            nameList.append(color.name()).append(",");
        }
        // Remove the trailing comma
        if (nameList.length() > 0) {
            nameList.deleteCharAt(nameList.length() - 1);
        }
        return nameList.toString();
    }
}