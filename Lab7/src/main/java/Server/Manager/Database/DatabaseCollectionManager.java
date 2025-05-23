package Server.Manager.Database;

import Common.Data.Person.Color;
import Common.Data.Person.Coordinates;
import Common.Data.Person.Location;
import Common.Data.Person.Person;
import Common.Data.User;
import Server.ServerApp;
import Server.Utility.Enum.COLUMNS;
import Server.Utility.Enum.DML;
import Server.Utility.DatabaseHandler;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.TreeSet;
import java.util.logging.Level;

public class DatabaseCollectionManager {
    private final DatabaseUserManager databaseUserManager;
    private final DatabaseHandler databaseHandler;

    public DatabaseCollectionManager(DatabaseUserManager databaseUserManager, DatabaseHandler databaseHandler) {
        this.databaseUserManager = databaseUserManager;
        this.databaseHandler = databaseHandler;
    }

    public boolean insertPerson(Person person, User user) {
        PreparedStatement personTablePreparedStatement = null;
        PreparedStatement coordinatesTablePreparedStatement = null;
        PreparedStatement locationTablePreparedStatement = null;

        try {
            databaseHandler.setCommitMode();
            databaseHandler.setSavepoint();
            LocalDateTime creationDate = LocalDateTime.now();


            // INSERT coordinates
            coordinatesTablePreparedStatement = databaseHandler.getPreparedStatement(DML.INSERT_COORDINATES, true);
            coordinatesTablePreparedStatement.setDouble(1, person.getCoordinates().getX());
            coordinatesTablePreparedStatement.setDouble(2, person.getCoordinates().getY());
            if (coordinatesTablePreparedStatement.executeUpdate() == 0) throw new SQLException();
            ResultSet coordinatesKeys = coordinatesTablePreparedStatement.getGeneratedKeys();
            long coordinatesId = coordinatesKeys.next() ? coordinatesKeys.getLong(1) : throwEx();
            ServerApp.logger.log(Level.INFO, "Executed INSERT_COORDINATES.");

            // INSERT location
            locationTablePreparedStatement = databaseHandler.getPreparedStatement(DML.INSERT_LOCATION, true);
            locationTablePreparedStatement.setDouble(1, person.getLocation().getX());
            locationTablePreparedStatement.setDouble(2, person.getLocation().getY());
            locationTablePreparedStatement.setDouble(3, person.getLocation().getZ());
            locationTablePreparedStatement.setString(4, person.getLocation().getName());
            if (locationTablePreparedStatement.executeUpdate() == 0) throw new SQLException();
            ResultSet locationKeys = locationTablePreparedStatement.getGeneratedKeys();
            long locationId = locationKeys.next() ? locationKeys.getLong(1) : throwEx();
            ServerApp.logger.log(Level.INFO, "Executed INSERT_LOCATION.");


            // INSERT person
            personTablePreparedStatement = databaseHandler.getPreparedStatement(DML.INSERT_PERSON, true);
            personTablePreparedStatement.setString(1, person.getName());
            personTablePreparedStatement.setLong(2, coordinatesId);
            personTablePreparedStatement.setTimestamp(3, Timestamp.valueOf(person.getCreationDate().toLocalDateTime()));
            personTablePreparedStatement.setInt(4, person.getHeight());
            personTablePreparedStatement.setTimestamp(5, Timestamp.valueOf(person.getBirthday()));
            personTablePreparedStatement.setString(6, person.getEyeColor().toString());
            if (person.getHairColor() != null) {
                personTablePreparedStatement.setString(7, person.getHairColor().toString());
            } else {
                personTablePreparedStatement.setNull(7, Types.VARCHAR);
            }
            personTablePreparedStatement.setLong(8, locationId);
            personTablePreparedStatement.setLong(9, databaseUserManager.getUserIdByUsername(user));

            if (personTablePreparedStatement.executeUpdate() == 0) throw new SQLException();
            ResultSet personKeys = personTablePreparedStatement.getGeneratedKeys();
            if (personKeys.next()) {
                int personId = personKeys.getInt(1);
                person.setId(personId);
            } else {
                throw new SQLException("No generated key returned for person");
            }
            ServerApp.logger.log(Level.INFO, "Executed INSERT_PERSON.");

            databaseHandler.commit();
            return true;

        } catch (SQLException e) {
            ServerApp.logger.log(Level.WARNING, "Failed to insert person: " + e.getMessage());
            databaseHandler.rollback();
        } finally {
            databaseHandler.closePreparedStatement(coordinatesTablePreparedStatement);
            databaseHandler.closePreparedStatement(locationTablePreparedStatement);
            databaseHandler.closePreparedStatement(personTablePreparedStatement);
            databaseHandler.setNormalMode();
        }
        return false;
    }

    public TreeSet<Person> getCollection() {
        TreeSet<Person> people = new TreeSet<>();
        PreparedStatement pst = null;
        try {
            pst = databaseHandler.getPreparedStatement(DML.SELECT_ALL_PERSON, false);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                people.add(createPerson(rs));
            }
        } catch (SQLException e) {
            ServerApp.logger.log(Level.WARNING, "Failed to fetch collection.");
        } finally {
            databaseHandler.closePreparedStatement(pst);
        }
        return people;
    }
    public void updatePersonById(long personId, Person updatedPerson) {
        PreparedStatement pstUpdateName = null;
        PreparedStatement pstUpdateHeight = null;
        PreparedStatement pstUpdateBirthday = null;
        PreparedStatement pstUpdateEyeColor = null;
        PreparedStatement pstUpdateHairColor = null;
        PreparedStatement pstUpdateCoordinates = null;
        PreparedStatement pstUpdateLocation = null;

        try {
            databaseHandler.setCommitMode();
            databaseHandler.setSavepoint();

            if (updatedPerson.getName() != null) {
                pstUpdateName = databaseHandler.getPreparedStatement(DML.UPDATE_PERSON_NAME_BY_ID, false);
                pstUpdateName.setString(1, updatedPerson.getName());
                pstUpdateName.setLong(2, personId);
                pstUpdateName.executeUpdate();
            }

            pstUpdateHeight = databaseHandler.getPreparedStatement(DML.UPDATE_PERSON_HEIGHT_BY_ID, false);
            pstUpdateHeight.setInt(1, updatedPerson.getHeight());
            pstUpdateHeight.setLong(2, personId);
            pstUpdateHeight.executeUpdate();

            pstUpdateBirthday = databaseHandler.getPreparedStatement(DML.UPDATE_PERSON_BIRTHDAY_BY_ID, false);
            pstUpdateBirthday.setTimestamp(1, Timestamp.valueOf(updatedPerson.getBirthday()));
            pstUpdateBirthday.setLong(2, personId);
            pstUpdateBirthday.executeUpdate();

            pstUpdateEyeColor = databaseHandler.getPreparedStatement(DML.UPDATE_PERSON_EYE_COLOR_BY_ID, false);
            pstUpdateEyeColor.setString(1, updatedPerson.getEyeColor().toString());
            pstUpdateEyeColor.setLong(2, personId);
            pstUpdateEyeColor.executeUpdate();

            pstUpdateHairColor = databaseHandler.getPreparedStatement(DML.UPDATE_PERSON_HAIR_COLOR_BY_ID, false);
            if (updatedPerson.getHairColor() != null) {
                pstUpdateHairColor.setString(1, updatedPerson.getHairColor().toString());
            } else {
                pstUpdateHairColor.setNull(1, Types.VARCHAR);
            }
            pstUpdateHairColor.setLong(2, personId);
            pstUpdateHairColor.executeUpdate();

            pstUpdateCoordinates = databaseHandler.getPreparedStatement(DML.UPDATE_COORDINATES_BY_PERSON_ID, false);
            pstUpdateCoordinates.setDouble(1, updatedPerson.getCoordinates().getX());
            pstUpdateCoordinates.setDouble(2, updatedPerson.getCoordinates().getY());
            pstUpdateCoordinates.setLong(3, personId);
            pstUpdateCoordinates.executeUpdate();

            pstUpdateLocation = databaseHandler.getPreparedStatement(DML.UPDATE_LOCATION_BY_PERSON_ID, false);
            pstUpdateLocation.setDouble(1, updatedPerson.getLocation().getX());
            pstUpdateLocation.setDouble(2, updatedPerson.getLocation().getY());
            pstUpdateLocation.setDouble(3, updatedPerson.getLocation().getZ());
            pstUpdateLocation.setString(4, updatedPerson.getLocation().getName());
            pstUpdateLocation.setLong(5, personId);
            pstUpdateLocation.executeUpdate();

            databaseHandler.commit();
        } catch (SQLException e) {
            ServerApp.logger.log(Level.WARNING, "Failed to update person: " + e.getMessage());
            databaseHandler.rollback();
        } finally {
            databaseHandler.closePreparedStatement(pstUpdateName);
            databaseHandler.closePreparedStatement(pstUpdateHeight);
            databaseHandler.closePreparedStatement(pstUpdateBirthday);
            databaseHandler.closePreparedStatement(pstUpdateEyeColor);
            databaseHandler.closePreparedStatement(pstUpdateHairColor);
            databaseHandler.closePreparedStatement(pstUpdateCoordinates);
            databaseHandler.closePreparedStatement(pstUpdateLocation);
            databaseHandler.setNormalMode();
        }
    }

    public void deleteCoordinatesByPersonId(long personId) {
        PreparedStatement pst = null;
        try {
            pst = databaseHandler.getPreparedStatement(DML.DELETE_COORDINATES_BY_PERSON_ID, false);
            pst.setLong(1, personId);
            int affectedRows = pst.executeUpdate();
            ServerApp.logger.log(Level.INFO, "Deleted coordinates (person_id=" + personId + "), affected rows: " + affectedRows);
        } catch (SQLException e) {
            ServerApp.logger.log(Level.WARNING, "Failed to delete coordinates by person id: " + e.getMessage());
        } finally {
            databaseHandler.closePreparedStatement(pst);
        }
    }

    public void deleteLocationByPersonId(long personId) {
        PreparedStatement pst = null;
        try {
            pst = databaseHandler.getPreparedStatement(DML.DELETE_LOCATION_BY_PERSON_ID, false);
            pst.setLong(1, personId);
            int affectedRows = pst.executeUpdate();
            ServerApp.logger.log(Level.INFO, "Deleted location (person_id=" + personId + "), affected rows: " + affectedRows);
        } catch (SQLException e) {
            ServerApp.logger.log(Level.WARNING, "Failed to delete location by person id: " + e.getMessage());
        } finally {
            databaseHandler.closePreparedStatement(pst);
        }
    }

    public void deletePersonById(long personId) {
        PreparedStatement pstDelete = null;

        try {
            pstDelete = databaseHandler.getPreparedStatement(DML.DELETE_PERSON_BY_ID, false);
            pstDelete.setLong(1, personId);
            pstDelete.executeUpdate();
        } catch (SQLException e) {
            ServerApp.logger.log(Level.WARNING, "Failed to delete person: " + e.getMessage());
        } finally {
            databaseHandler.closePreparedStatement(pstDelete);
        }
    }


    private Person createPerson(ResultSet rs) throws SQLException {
        int id = rs.getInt(COLUMNS.ID);
        String name = rs.getString(COLUMNS.NAME);
        int height = rs.getInt(COLUMNS.HEIGHT);
        LocalDateTime birthday = rs.getTimestamp(COLUMNS.BIRTHDAY).toLocalDateTime();
        Color eyeColor = Color.valueOf(rs.getString(COLUMNS.EYE_COLOR));
        Color hairColor = null;
        String hairColorStr = rs.getString(COLUMNS.HAIR_COLOR);
        if (hairColorStr != null) hairColor = Color.valueOf(hairColorStr);

        Coordinates coordinates = getCoordinatesById(rs.getInt(COLUMNS.COORDINATES_ID));
        Location location = getLocationById(rs.getInt(COLUMNS.LOCATION_ID));
        User user = databaseUserManager.getUserById(rs.getLong(COLUMNS.USER_ID));
        ZonedDateTime creationDate = rs.getTimestamp(COLUMNS.CREATION_DATE).toLocalDateTime().atZone(ZonedDateTime.now().getZone());

        return new Person(id, name, coordinates, height, birthday, eyeColor, hairColor, location,user);
    }

    private Location getLocationById(Integer id) throws SQLException {
        PreparedStatement pst = databaseHandler.getPreparedStatement(DML.SELECT_LOCATION_BY_ID, false);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return new Location(
                    rs.getDouble(COLUMNS.X),
                    rs.getDouble(COLUMNS.Y),
                    rs.getDouble(COLUMNS.Z),
                    rs.getString(COLUMNS.LOCATION_NAME)
            );
        }
        throw new SQLException("Location not found");
    }

    private Coordinates getCoordinatesById(Integer id){
        Coordinates coordinates = null;
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = databaseHandler.getPreparedStatement(DML.SELECT_COORDINATES_BY_ID, false);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            ServerApp.logger.log(Level.INFO, "Executed query SELECT_COORDINATES_BY_PERSON_ID");
            if (resultSet.next()){
                coordinates = new Coordinates(
                        resultSet.getDouble(COLUMNS.X),
                        resultSet.getDouble(COLUMNS.Y)
                );
            } else throw new SQLException();
        } catch (SQLException e) {
            ServerApp.logger.log(Level.WARNING, "An error occurred while executing the query SELECT_COORDINATES_BY_PERSON_ID!");
        } finally {
            databaseHandler.closePreparedStatement(preparedStatement);
        }
        return coordinates;
    }

    public Person getPersonById(long id) {
        PreparedStatement pst = null;
        try {
            pst = databaseHandler.getPreparedStatement(DML.SELECT_PERSON_BY_ID, false);
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) return createPerson(rs);
        } catch (SQLException e) {
            ServerApp.logger.log(Level.WARNING, "Failed to fetch person by id: " + e.getMessage());
        } finally {
            databaseHandler.closePreparedStatement(pst);
        }
        return null;
    }



    private long throwEx() throws SQLException {
        throw new SQLException("No generated key returned");
    }
}
