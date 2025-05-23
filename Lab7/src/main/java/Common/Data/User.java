package Common.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * User class
 */
public class User implements Serializable {
    private String userName;
    private String password;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof User user)) return false;
        return Objects.equals(getUserName(), user.getUserName()) && Objects.equals(getPassword(), user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserName(), getPassword());
    }

    @Override
    public String toString() {
        return "User: " + getUserName();
    }
}