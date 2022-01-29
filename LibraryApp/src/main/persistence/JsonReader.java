package persistence;

import model.Book;
import model.Collection;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/*
 * Represents a reader that reads a JSON object from a source file
 */
public class JsonReader {
    private String source;

    /*
     * From JsonSerializationDemo. persistence.JsonReader.JsonReader
     * EFFECTS: constructs reader to read from source file
     */
    public JsonReader(String source) {
        this.source = source;
    }

    /*
     * From JsonSerializationDemo. persistence.JsonReader.read
     * EFFECTS: throws IOException if an error occurs reading data from file
     */
    public Collection read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCollection(jsonObject);
    }

    /*
     * From JsonSerializationDemo. persistence.JsonReader.readFile
     * EFFECTS: reads source file as string and returns it
     */
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    /*
     * From JsonSerializationDemo. persistence.JsonReader.parseWorkRoom
     * EFFECTS: parses collection from JSON object and returns it
     */
    private Collection parseCollection(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Collection co = new Collection(name);
        addBooks(co, jsonObject);
        return co;
    }

    /*
     * From JsonSerializationDemo. persistence.JsonReader.addThingies
     * MODIFIES: c
     * EFFECTS: parses books from JSON object and adds them to collection
     */
    private void addBooks(Collection c, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("books");
        for (Object json : jsonArray) {
            JSONObject nextBook = (JSONObject) json;
            addOneBook(c, nextBook);
        }
    }

    /*
     * MODIFIES: c
     * EFFECTS: parses book from JSON object and adds it to collection
     */
    private void addOneBook(Collection c, JSONObject jsonObject) {
        String name = jsonObject.getString("title");
        String author = jsonObject.getString("author");
        Boolean read = jsonObject.getBoolean("reading status");
        Book.Condition condition = Book.Condition.valueOf(jsonObject.getString("condition"));
        Book.Rating rating = Book.Rating.valueOf(jsonObject.getString("rating"));
        Boolean loan = jsonObject.getBoolean("loan status");
        String review = jsonObject.getString("review");

        Book book = new Book(name, author, read, condition);
        book.setBookRating(rating);
        book.setLoanStatus(loan);
        book.writeBookReview(review);

        c.addBook(book);
    }

}
