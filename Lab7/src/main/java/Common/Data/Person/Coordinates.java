package Common.Data.Person;

import java.io.Serializable;

/**
 * The {@code Coordinates} class represents a set of geographical coordinates.
 * It encapsulates the x and y coordinates and provides methods for retrieval,
 * equality comparison, and generating a hash code.
 */
public class Coordinates implements Serializable {
    private Double x; // Поле не может быть null
    private Double y; // Значение поля должно быть больше -348, Поле не может быть null

    /**
     * Constructs a {@code Coordinates} object with the specified x and y coordinates.
     *
     * @param x The x-coordinate. Must not be null.
     * @param y The y-coordinate. Must not be null and must be greater than -348.
     * @throws IllegalArgumentException if x is null, y is null, or y is not greater than -348.
     */
    public Coordinates(Double x, Double y) {
        if (x == null) {
            throw new IllegalArgumentException("The x-coordinate cannot be null.");
        }
        if (y == null || y <= -348) {
            throw new IllegalArgumentException("The y-coordinate cannot be null and must be greater than -348.");
        }
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x-coordinate.
     *
     * @return The x-coordinate.
     */
    public Double getX() {
        return x;
    }

    /**
     * Sets the x-coordinate.
     *
     * @param x The x-coordinate. Must not be null.
     * @throws IllegalArgumentException if x is null.
     */
    public void setX(Double x) {
        if (x == null) {
            throw new IllegalArgumentException("The x-coordinate cannot be null.");
        }
        this.x = x;
    }

    /**
     * Gets the y-coordinate.
     *
     * @return The y-coordinate.
     */
    public Double getY() {
        return y;
    }

    /**
     * Sets the y-coordinate.
     *
     * @param y The y-coordinate. Must not be null and must be greater than -348.
     * @throws IllegalArgumentException if y is null or y is not greater than -348.
     */
    public void setY(Double y) {
        if (y == null || y <= -348) {
            throw new IllegalArgumentException("The y-coordinate cannot be null and must be greater than -348.");
        }
        this.y = y;
    }

    @Override
    public String toString() {
        return "x = " + getX() + ", y = " + getY();
    }

    @Override
    public int hashCode() {
        return x.hashCode() + y.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordinates that = (Coordinates) obj;
        return x.equals(that.x) && y.equals(that.y);
    }
}