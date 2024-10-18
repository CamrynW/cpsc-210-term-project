package persistence;

import org.json.JSONObject;

/*
 * From JsonSerializationDemo. persistence.Writable
 * Allows for a JSON object to be returned when implemented
 */
public interface Writable {

    /*
     * From JsonSerializationDemo. persistence.Writable.toJson
     * EFFECTS: returns this as JSON object
     */
    JSONObject toJson();
}
