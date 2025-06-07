package Server.Utility.Enum;

/**
 * Class for saving DML queries related to the Person entity.
 */
public interface DML {

    //------PERSON TABLE-------------------------------------------------------------------------------------------------
    String SELECT_ALL_PERSON = "SELECT * FROM " + TABLES.PERSON;

    String SELECT_PERSON_BY_ID = SELECT_ALL_PERSON + " WHERE " +
            COLUMNS.ID + " = ?";

    String INSERT_PERSON = "INSERT INTO " + TABLES.PERSON + " (" +
            COLUMNS.NAME + ", " +
            COLUMNS.COORDINATES_ID + ", " +
            COLUMNS.CREATION_DATE + ", " +
            COLUMNS.HEIGHT + ", " +
            COLUMNS.BIRTHDAY + ", " +
            COLUMNS.EYE_COLOR + ", " +
            COLUMNS.HAIR_COLOR + ", " +
            COLUMNS.LOCATION_ID + ", " +
            COLUMNS.USER_ID + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING " + COLUMNS.ID;;

    String UPDATE_PERSON_NAME_BY_ID = "UPDATE " + TABLES.PERSON + " SET " +
            COLUMNS.NAME + " = ? WHERE " + COLUMNS.ID + " = ?";

    String UPDATE_PERSON_HEIGHT_BY_ID = "UPDATE " + TABLES.PERSON + " SET " +
            COLUMNS.HEIGHT + " = ? WHERE " + COLUMNS.ID + " = ?";

    String UPDATE_PERSON_BIRTHDAY_BY_ID = "UPDATE " + TABLES.PERSON + " SET " +
            COLUMNS.BIRTHDAY + " = ? WHERE " + COLUMNS.ID + " = ?";

    String UPDATE_PERSON_EYE_COLOR_BY_ID = "UPDATE " + TABLES.PERSON + " SET " +
            COLUMNS.EYE_COLOR + " = ? WHERE " + COLUMNS.ID + " = ?";

    String UPDATE_PERSON_HAIR_COLOR_BY_ID = "UPDATE " + TABLES.PERSON + " SET " +
            COLUMNS.HAIR_COLOR + " = ? WHERE " + COLUMNS.ID + " = ?";

    String DELETE_PERSON_BY_ID = "DELETE FROM " + TABLES.PERSON + " WHERE " +
            COLUMNS.ID + " = ?";

    //------COORDINATES TABLE--------------------------------------------------------------------------------------------
    String SELECT_ALL_COORDINATES = "SELECT * FROM " + TABLES.COORDINATES;

    String SELECT_COORDINATES_BY_ID = SELECT_ALL_COORDINATES + " WHERE " +
            COLUMNS.ID + " = ?";

    String INSERT_COORDINATES = "INSERT INTO " +
            TABLES.COORDINATES + " (" +
            COLUMNS.X + ", " +
            COLUMNS.Y + ") VALUES (?, ?)";

    String UPDATE_COORDINATES_BY_PERSON_ID = "UPDATE " + TABLES.COORDINATES + " SET " +
            COLUMNS.X + " = ?, " +
            COLUMNS.Y + " = ?" +
            " FROM " + TABLES.PERSON +
            " WHERE " + TABLES.COORDINATES + "." + COLUMNS.ID + " = " +
            TABLES.PERSON + "." + COLUMNS.COORDINATES_ID +
            " AND " + TABLES.PERSON + "." + COLUMNS.ID + " = ?";

    String DELETE_COORDINATES_BY_PERSON_ID = "DELETE FROM " + TABLES.COORDINATES +
            " USING " + TABLES.PERSON +
            " WHERE " + TABLES.COORDINATES + "." + COLUMNS.ID + " = " +
            TABLES.PERSON + "." + COLUMNS.COORDINATES_ID +
            " AND " + TABLES.PERSON + "." + COLUMNS.ID + " = ?";

    //------LOCATION TABLE (x, y, z, name)-------------------------------------------------------------------------------
    String SELECT_ALL_LOCATION = "SELECT * FROM " + TABLES.LOCATION;

    String SELECT_LOCATION_BY_ID = SELECT_ALL_LOCATION + " WHERE " +
            COLUMNS.ID + " = ?";

    String INSERT_LOCATION = "INSERT INTO " +
            TABLES.LOCATION + " (" +
            COLUMNS.X + ", " +
            COLUMNS.Y + ", " +
            COLUMNS.Z + ", " +
            COLUMNS.LOCATION_NAME + ") VALUES (?, ?, ?, ?)";

    String UPDATE_LOCATION_BY_PERSON_ID = "UPDATE " + TABLES.LOCATION + " SET " +
            COLUMNS.X + " = ?, " +
            COLUMNS.Y + " = ?, " +
            COLUMNS.Z + " = ?, " +
            COLUMNS.LOCATION_NAME + " = ?" +
            " FROM " + TABLES.PERSON +
            " WHERE " + TABLES.LOCATION + "." + COLUMNS.ID + " = " +
            TABLES.PERSON + "." + COLUMNS.LOCATION_ID +
            " AND " + TABLES.PERSON + "." + COLUMNS.ID + " = ?";

    String DELETE_LOCATION_BY_PERSON_ID = "DELETE FROM " + TABLES.LOCATION +
            " USING " + TABLES.PERSON +
            " WHERE " + TABLES.LOCATION + "." + COLUMNS.ID + " = " +
            TABLES.PERSON + "." + COLUMNS.LOCATION_ID +
            " AND " + TABLES.PERSON + "." + COLUMNS.ID + " = ?";
}
