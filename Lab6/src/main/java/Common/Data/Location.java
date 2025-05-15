package Common.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * The {@code Location} class represents a geographical location.
 * It encapsulates the x, y, z coordinates and a name, providing methods for retrieval,
 * equality comparison, and generating a hash code.
 */
public class Location implements Serializable, Comparable<Location>, Cloneable {
    private Integer x; // Поле не может быть null
    private double y;
    private double z;
    private String name; // Строка не может быть пустой, Поле может быть null

    /**
     * Constructs a {@code Location} object with the specified coordinates and name.
     *
     * @param x    The x-coordinate. Must not be null.
     * @param y    The y-coordinate.
     * @param z    The z-coordinate.
     * @param name The name of the location. Can be null, but if not null, must not be empty.
     * @throws IllegalArgumentException if x is null or name is an empty string.
     */
    public Location(Integer x, double y, double z, String name) {
        if (x == null) {
            throw new IllegalArgumentException("The x-coordinate cannot be null.");
        }
        if (name != null && name.isEmpty()) {
            throw new IllegalArgumentException("The name cannot be an empty string.");
        }
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    /**
     * Gets the x-coordinate.
     *
     * @return The x-coordinate.
     */
    public Integer getX() {
        return x;
    }

    /**
     * Sets the x-coordinate.
     *
     * @param x The x-coordinate. Must not be null.
     * @throws IllegalArgumentException if x is null.
     */
    public void setX(Integer x) {
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
    public double getY() {
        return y;
    }

    /**
     * Sets the y-coordinate.
     *
     * @param y The y-coordinate.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Gets the z-coordinate.
     *
     * @return The z-coordinate.
     */
    public double getZ() {
        return z;
    }

    /**
     * Sets the z-coordinate.
     *
     * @param z The z-coordinate.
     */
    public void setZ(double z) {
        this.z = z;
    }

    /**
     * Gets the name of the location.
     *
     * @return The name of the location. Can be null.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the location.
     *
     * @param name The name of the location. Can be null, but if not null, must not be empty.
     * @throws IllegalArgumentException if name is an empty string.
     */
    public void setName(String name) {
        if (name != null && name.isEmpty()) {
            throw new IllegalArgumentException("The name cannot be an empty string.");
        }
        this.name = name;
    }

    @Override
    public int compareTo(Location other) {
        int cmp = this.x.compareTo(other.x);
        if (cmp != 0) return cmp;
        cmp = Double.compare(this.y, other.y);
        if (cmp != 0) return cmp;
        return Double.compare(this.z, other.z);
    }

    @Override
    public String toString() {
        return "x = " + getX() + ", y = " + getY() + ", z = " + getZ() + ", Name = " + getName();
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Location other = (Location) obj;
        return Objects.equals(x, other.x) &&
                Double.compare(y, other.y) == 0 &&
                Double.compare(z, other.z) == 0 &&
                Objects.equals(name, other.name);
    }

    @Override
    public Location clone() {
        try {
            return (Location) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning not supported", e);
        }
    }
}