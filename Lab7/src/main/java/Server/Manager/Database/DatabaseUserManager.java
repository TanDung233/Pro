package Server.Manager.Database;

import Common.Data.User;
import Server.ServerApp;
import Server.Utility.Enum.COLUMNS;
import Server.Utility.DatabaseHandler;
import Server.Utility.Enum.TABLES;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * A manager of user database.
 */
public class DatabaseUserManager {
    // USER_TABLE
    private final String SELECT_USER_BY_ID = "SELECT * FROM " + TABLES.USER +
            " WHERE " + COLUMNS.ID + " = ?";
    private final String SELECT_USER_BY_USERNAME = "SELECT * FROM " + TABLES.USER +
            " WHERE " + COLUMNS.USERNAME + " = ?";
    private final String SELECT_USER_BY_USERNAME_AND_PASSWORD = SELECT_USER_BY_USERNAME + " AND " +
            COLUMNS.PASSWORD + " = ?";
    private final String INSERT_USER = "INSERT INTO " +
            TABLES.USER + " (" +
            COLUMNS.USERNAME + ", " +
            COLUMNS.PASSWORD + ") VALUES (?, ?)";

    private DatabaseHandler databaseHandler;

    public DatabaseUserManager(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    /**
     * @param userId User id.
     * @return User by id.
     */
    public User getUserById(long userId) {
        User user = null;
        PreparedStatement preparedSelectUserByIdStatement = null;
        try{
            preparedSelectUserByIdStatement = databaseHandler.getPreparedStatement(SELECT_USER_BY_ID, false);
            preparedSelectUserByIdStatement.setLong(1, userId);
            ResultSet resultSet = preparedSelectUserByIdStatement.executeQuery();
            ServerApp.logger.log(Level.INFO, "Executed query SELECT_USER_BY_ID");
            if (resultSet.next()) {
                user = new User(
                        resultSet.getString(COLUMNS.USERNAME),
                        resultSet.getString(COLUMNS.PASSWORD)
                );
            } else throw new SQLException();
        } catch (SQLException e) {
            ServerApp.logger.log(Level.WARNING, "An error occurred while executing the SELECT_USER_BY_ID query!");
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectUserByIdStatement);
        }
        return user;
    }

    /**
     * Check user by username and password.
     *
     * @param user User.
     * @return Result set.
     */
    public boolean checkUserByUsernameAndPassword(User user){
        PreparedStatement preparedSelectUserByUsernameAndPasswordStatement = null;
        try{
            preparedSelectUserByUsernameAndPasswordStatement = databaseHandler.getPreparedStatement(SELECT_USER_BY_USERNAME_AND_PASSWORD, false);
            preparedSelectUserByUsernameAndPasswordStatement.setString(1, user.getUserName());
            preparedSelectUserByUsernameAndPasswordStatement.setString(2, user.getPassword());
            ResultSet resultSet = preparedSelectUserByUsernameAndPasswordStatement.executeQuery();
            ServerApp.logger.log(Level.INFO, "Executed query SELECT_USER_BY_USERNAME_AND_PASSWORD");
            return resultSet.next();
        } catch (SQLException e) {
            ServerApp.logger.log(Level.WARNING, "An error occurred while executing the SELECT_USER_BY_USERNAME_AND_PASSWORD query!");
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectUserByUsernameAndPasswordStatement);
        }
        return false;
    }

    /**
     * Get user id by username.
     *
     * @param user User.
     * @return User id.
     */
    public long getUserIdByUsername(User user){
        Long userId = null;
        PreparedStatement preparedSelectUserByUsernameStatement = null;
        try{
            preparedSelectUserByUsernameStatement = databaseHandler.getPreparedStatement(SELECT_USER_BY_USERNAME, false);
            preparedSelectUserByUsernameStatement.setString(1, user.getUserName());
            ResultSet resultSet = preparedSelectUserByUsernameStatement.executeQuery();
            ServerApp.logger.log(Level.INFO, "Executed query SELECT_USER_BY_USERNAME");
            if (resultSet.next()){
                userId = resultSet.getLong(COLUMNS.ID);
            } else userId = -1L;
        } catch (SQLException e) {
            ServerApp.logger.log(Level.WARNING, "An error occurred while executing the SELECT_USER_BY_USERNAME query!");
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectUserByUsernameStatement);
        }
        return userId;
    }

    /**
     * Insert user.
     * @param user User.
     * @return Status of insert.
     */

    public boolean insertUser(User user) {
        PreparedStatement preparedInsertUserStatement = null;
        try {
            if (getUserIdByUsername(user) != -1L) return false;
            preparedInsertUserStatement =
                    databaseHandler.getPreparedStatement(INSERT_USER, false);
            preparedInsertUserStatement.setString(1, user.getUserName());
            preparedInsertUserStatement.setString(2, user.getPassword());
            if (preparedInsertUserStatement.executeUpdate() == 0) throw new SQLException();
            ServerApp.logger.log(Level.INFO, "Executed INSERT_USER");
            return true;
        } catch (SQLException exception) {
            ServerApp.logger.log(Level.WARNING, "An error occurred while executing the INSERT_USER!");
        } finally {
            databaseHandler.closePreparedStatement(preparedInsertUserStatement);
        }
        return false;
    }
}