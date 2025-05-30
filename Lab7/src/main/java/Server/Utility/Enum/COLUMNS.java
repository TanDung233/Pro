package Server.Utility.Enum;

/**
 * Class for saving column names related to the Person entity.
 */
public interface COLUMNS {
    // PERSON table columns
    String ID = "id";
    String NAME = "name";
    String COORDINATES_ID = "coordinates_id";
    String CREATION_DATE = "creation_date";
    String HEIGHT = "height";
    String BIRTHDAY = "birthday";
    String EYE_COLOR = "eye_color";
    String HAIR_COLOR = "hair_color";
    String LOCATION_ID = "location_id";
    String USER_ID = "user_id";

    // COORDINATES table columns
    String X = "x";
    String Y = "y";
    String Z = "z";

    String LOCATION_NAME = "location_name";

    // USER table columns
    String USERNAME = "username";
    String PASSWORD = "password";
}
