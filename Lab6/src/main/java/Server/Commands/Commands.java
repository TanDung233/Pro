package Server.Commands;

import java.util.Objects;

/**
 * The general design of the commands
 */
public abstract class Commands implements CommandInterface {
    private final String name;
    private final String description;

    public Commands( String name, String description ) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Commands commands)) return false;
        return Objects.equals(getName(), commands.getName()) && Objects.equals(getDescription(), commands.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription());
    }

    @Override
    public String toString() {
        return "Commands{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}