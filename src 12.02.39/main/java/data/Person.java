package main.java.data;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * The {@code Person} class represents information about a person.
 * It includes details such as ID, name, coordinates, creation date, height,
 * birthday, eye color, hair color, and location.
 */
public class Person implements Comparable<Person>, Cloneable {
    private Integer id; // Значение поля должно быть больше 0, уникальным и генерироваться автоматически
    private String name; // Поле не может быть null, строка не может быть пустой
    private Coordinates coordinates; // Поле не может быть null
    private ZonedDateTime creationDate; // Поле не может быть null, значение генерируется автоматически
    private int height; // Значение поля должно быть больше 0
    private LocalDateTime birthday; // Поле не может быть null
    private Color eyeColor; // Поле не может быть null
    private Color hairColor; // Поле может быть null
    private Location location; // Поле не может быть null

    /**
     * Constructs a person with the specified details.
     *
     * @param id           the person ID (must be greater than 0)
     * @param name         the person name (cannot be null or empty)
     * @param coordinates  the person coordinates (cannot be null)
     * @param height       the person height (must be greater than 0)
     * @param birthday     the person birthday (cannot be null)
     * @param eyeColor     the person eye color (cannot be null)
     * @param hairColor    the person hair color (can be null)
     * @param location     the person location (cannot be null)
     * @throws IllegalArgumentException if any field violates its constraints.
     */
    public Person(int id, String name, Coordinates coordinates, int height,
                  LocalDateTime birthday, Color eyeColor, Color hairColor, Location location) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be greater than 0.");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (coordinates == null) {
            throw new IllegalArgumentException("Coordinates cannot be null.");
        }
        if (height <= 0) {
            throw new IllegalArgumentException("Height must be greater than 0.");
        }
        if (birthday == null) {
            throw new IllegalArgumentException("Birthday cannot be null.");
        }
        if (eyeColor == null) {
            throw new IllegalArgumentException("Eye color cannot be null.");
        }
        if (location == null) {
            throw new IllegalArgumentException("Location cannot be null.");
        }

        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = ZonedDateTime.now(); // Автоматически генерируемое значение
        this.height = height;
        this.birthday = birthday;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.location = location;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be greater than 0.");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("Coordinates cannot be null.");
        }
        this.coordinates = coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        if (height <= 0) {
            throw new IllegalArgumentException("Height must be greater than 0.");
        }
        this.height = height;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        if (birthday == null) {
            throw new IllegalArgumentException("Birthday cannot be null.");
        }
        this.birthday = birthday;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(Color eyeColor) {
        if (eyeColor == null) {
            throw new IllegalArgumentException("Eye color cannot be null.");
        }
        this.eyeColor = eyeColor;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor; // Может быть null
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("Location cannot be null.");
        }
        this.location = location;
    }

    @Override
    public int compareTo(Person other) {
        int heightCompare = Integer.compare(this.height, other.height);

        if (heightCompare == 0) {
            return Integer.compare(this.id, other.id);
        }

        return heightCompare;
    }
    @Override
    public String toString() {
        String info = "Person № " + id ;
        info += " (added " + creationDate.toLocalDate() + " " + creationDate.toLocalTime() + ")\n";
        info += "Name: " + name + "\n";
        info += "Coordinates: " + coordinates + "\n";
        info += "Height: " + height + "\n";
        info += "Birthday: " + birthday + "\n";
        info += "EyeColor: " + eyeColor + "\n";
        info += "HairColor: " + hairColor + "\n";
        info += "Location: " + location + "\n";

        return info;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, height, birthday, eyeColor, hairColor, location);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Person other = (Person) obj;
        return id == other.id &&
                Objects.equals(name, other.name) &&
                Objects.equals(coordinates, other.coordinates) &&
                Objects.equals(creationDate, other.creationDate) &&
                height == other.height &&
                Objects.equals(birthday, other.birthday) &&
                eyeColor == other.eyeColor &&
                Objects.equals(hairColor, other.hairColor) &&
                Objects.equals(location, other.location);
    }

    @Override
    public Person clone() {
        try {
            return (Person) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning not supported", e);
        }
    }
}