package persistence;

import model.Collection;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/*
 * Represents a writer that writes a JSON object to a destination file
 */
public class JsonWriter {
    private static final int TAB = 3;
    private PrintWriter writer;
    private String destination;

    /*
     * From JsonSerializationDemo. persistence.JsonWriter.JsonWriter
     * JsonWriter constructor
     * EFFECTS: allows for a JSON object to be written to a destination file
     */
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    /*
     * From JsonSerializationDemo. persistence.JsonWriter.open
     * MODIFIES: this
     * EFFECTS: opens writer. throws FileNotFoundException is file cannot be opened
     */
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    /*
     * From JsonSerializationDemo. persistence.JsonWriter.write
     * MODIFIES: this
     * EFFECTS: writes a collection to file as a JSON object
     */
    public void write(Collection c) {
        JSONObject json = c.toJson();
        saveToFile(json.toString(TAB));
    }

    /*
     * From JsonSerializationDemo. persistence.JsonWriter.close
     * MODIFIES: this
     * EFFECTS: closes writer
     */
    public void close() {
        writer.close();
    }

    /*
     * From JsonSerializationDemo. persistence.JsonWriter.saveToFile
     * MODIFIES: this
     * EFFECTS: writes string to file
     */
    private void saveToFile(String json) {
        writer.print(json);
    }
}
