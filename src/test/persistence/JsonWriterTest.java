package persistence;

import exceptions.IndexGreaterThanException;
import exceptions.BookDoesNotExistException;
import model.Book;
import model.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Test class for JsonWriter
 */
public class JsonWriterTest extends JsonTest {

    JsonWriter writer;
    JsonReader reader;
    Collection collect;
    Book dead;
    Book body;
    Book it;
    String itReview;


    @BeforeEach
    public void setUp() {
        dead = new Book("The Dead Zone", "Stephen King", false, Book.Condition.FAIR);
        body = new Book("The Body", "Stephen King", false, Book.Condition.GOOD);
        it = new Book("IT", "Stephen King", true, Book.Condition.POOR);
        it.setBookRating(Book.Rating.THREE_STARS);
        it.setLoanStatus(true);
        itReview = "Only kind of scary in the beginning. Clowns are still the worst though.";
        it.writeBookReview(itReview);
    }

    @Test
    public void testWriterThrowException() {
        try {
            writer = new JsonWriter("./data/\0test.json");
            writer.open();
            fail("FileNotFoundException should have been thrown");
        } catch (FileNotFoundException e) {
            // expected
        }
    }

    @Test
    public void testWriterEmptyCollection() {
        try {
            collect = new Collection("my empty collection");
            writer = new JsonWriter("./data/testEmptyCollection.json");
            writer.open();
            writer.write(collect);
            writer.close();

            reader = new JsonReader("./data/testEmptyCollection.json");
            collect = reader.read();
            assertEquals("my empty collection", collect.getName());
            assertTrue(collect.getAllBooks().isEmpty());
        } catch (FileNotFoundException e) {
            fail("File destination is not valid");
        } catch (IOException e) {
            fail("File could not be found");
        }
    }

    @Test
    public void testWriterCollection() {
        try {
            collect = new Collection("my full collection");
            writer = new JsonWriter("./data/testCollection.json");
            collect.addBook(dead);
            collect.addBook(it);
            writer.open();
            writer.write(collect);
            writer.close();

            reader = new JsonReader("./data/testCollection.json");
            collect = reader.read();
            assertEquals("my full collection", collect.getName());
            LinkedList<Book> books = collect.getAllBooks();
            checkBookConstructor(books.get(0), "The Dead Zone", "Stephen King", false, Book.Condition.FAIR);
            checkBookConstructor(books.get(1), "IT", "Stephen King", true, Book.Condition.POOR);
            checkBookExtras(books.get(0), Book.Rating.UNRATED, false, "");
            checkBookExtras(books.get(1), Book.Rating.THREE_STARS, true, itReview);

        } catch (FileNotFoundException e) {
            fail("File destination is not valid");
        } catch (IOException e) {
            fail("File could not be found");
        }
    }

    @Test
    public void testWriterEditExistingCollection() {
        try {
            String review = "I still haven't actually read this one.";
            collect = new Collection("my un-edited collection");
            writer = new JsonWriter("./data/testEditCollection.json");
            collect.addBook(dead);
            collect.addBook(body);
            writer.open();
            writer.write(collect);
            writer.close();

            Book edited = collect.getBookAtIndex(1);
            edited.readBook();
            edited.setBookRating(Book.Rating.THREE_STARS);
            edited.setLoanStatus(true);
            edited.writeBookReview(review);
            collect.setName("my edited collection");

            writer.open();
            writer.write(collect);
            writer.close();

            reader = new JsonReader("./data/testEditCollection.json");
            collect = reader.read();
            LinkedList<Book> books = collect.getAllBooks();
            assertEquals("my edited collection", collect.getName());
            checkBookConstructor(books.get(0), "The Dead Zone", "Stephen King", false, Book.Condition.FAIR);
            checkBookConstructor(books.get(1), "The Body", "Stephen King", true, Book.Condition.GOOD);
            checkBookExtras(books.get(1), Book.Rating.THREE_STARS, true, review);

        } catch (FileNotFoundException e) {
            fail("File destination is not valid");
        } catch (IOException e) {
            fail("File could not be found");
        } catch (IndexGreaterThanException e) {
            fail("Index exceeds collection size");
        }
    }

    @Test
    public void testWriterRemoveFromCollection() {
        try {
            collect = new Collection("my removed collection");
            writer = new JsonWriter("./data/testRemoveCollection.json");
            collect.addBook(dead);
            writer.open();
            writer.write(collect);
            writer.close();

            writer.open();
            collect.removeBook(dead);
            writer.write(collect);
            writer.close();

            reader = new JsonReader("./data/testRemoveCollection.json");
            collect = reader.read();
            LinkedList<Book> books = collect.getAllBooks();
            assertEquals("my removed collection", collect.getName());
            assertTrue(books.isEmpty());

        } catch (FileNotFoundException e) {
            fail("File destination is not valid");
        } catch (IOException e) {
            fail("File could not be found");
        } catch (BookDoesNotExistException e) {
            fail("Book could not be removed");
        }
    }
}
