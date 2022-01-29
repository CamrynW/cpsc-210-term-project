package persistence;

import model.Book;
import model.Collection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Test class for JsonReader
 */
public class JsonReaderTest extends JsonTest {

    JsonReader reader;
    Collection collect;

    @Test
    public void testReadValidFile() {
        try {
            reader = new JsonReader("./data/testCollection.json");
            collect = reader.read();
            assertEquals("my full collection", collect.getName());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReadInvalidFile() {
        try {
            reader = new JsonReader("./data/noFileFound.json");
            collect = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    public void testReadEmptyCollection() {
        reader = new JsonReader("./data/testEmptyCollection.json");
        try {
            collect = reader.read();
            assertEquals("my empty collection", collect.getName());
            assertTrue(collect.getAllBooks().isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReadCollection() {
        String itReview = "Only kind of scary in the beginning. Clowns are still the worst though.";
        reader = new JsonReader("./data/testCollection.json");
        try {
            collect = reader.read();
            assertEquals("my full collection", collect.getName());
            LinkedList<Book> books = collect.getAllBooks();
            checkBookConstructor(books.get(0), "The Dead Zone", "Stephen King", false, Book.Condition.FAIR);
            checkBookConstructor(books.get(1), "IT", "Stephen King", true, Book.Condition.POOR);
            checkBookExtras(books.get(1), Book.Rating.THREE_STARS, true, itReview);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
