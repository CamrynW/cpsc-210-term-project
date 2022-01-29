package model;

import exceptions.BookDoesNotExistException;
import exceptions.IndexGreaterThanException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Test class for Collection
 */
public class CollectionTest {

    Collection testCollection;
    LinkedList<String> manyBooks;
    LinkedList<String> testList;
    Book alice;
    Book heights;
    String heightsString;
    String aliceString;

    @BeforeEach
    public void setUpLibrary() {
        testCollection = new Collection("my collection");
        manyBooks = new LinkedList<>();
        testList = new LinkedList<>();
        alice = new Book("Alice in Wonderland", "Lewis Carrol",
                true, Book.Condition.GOOD);
        heights = new Book("Wuthering Heights", "Jane Austen",
                false, Book.Condition.PERFECT);
        alice.setBookRating(Book.Rating.FOUR_STARS);

        heightsString = heights.getTitle() + ": " + heights.getAuthor();
        aliceString = alice.getTitle() + ": " + alice.getAuthor();
    }

    @Test
    public void testCollectionConstructor() {
        assertEquals("my collection", testCollection.getName());
        assertEquals(0, testCollection.getNumOfBooks());
    }

    @Test
    public void testSetName() {
        String newName = "my books";
        testCollection.setName(newName);
        assertEquals(newName, testCollection.getName());
    }

    @Test
    public void testAddOneBook() {
        try {
            testCollection.addBook(alice);
            assertEquals(1, testCollection.getNumOfBooks());
            assertEquals(alice, testCollection.getBookAtIndex(0));
        } catch (IndexGreaterThanException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testAddManyBooks() {
        testCollection.addBook(alice);
        testCollection.addBook(heights);

        LinkedList<Book> testBooks = new LinkedList<>();
        testBooks.add(alice);
        testBooks.add(heights);

        assertEquals(2, testCollection.getNumOfBooks());
        assertEquals(testBooks, testCollection.getAllBooks());
    }

    @Test
    public void testRemoveBookNoExceptionThrown() {
        try {
            testCollection.addBook(alice);
            testCollection.removeBook(alice);
        } catch (BookDoesNotExistException e) {
            fail("Should not have thrown exception");
        }
        assertEquals(0, testCollection.getNumOfBooks());
    }

    @Test
    public void testRemoveBookThrowException() {
        try {
            testCollection.removeBook(heights);
            fail("Exception should have been thrown");
        } catch (BookDoesNotExistException e) {
            //expected
        }
    }

    @Test
    public void testIsNotInCollection() {
        testCollection.addBook(alice);
        Boolean testFalse = testCollection.isInCollection(heights.getTitle());
        assertFalse(testFalse);
    }

    @Test
    public void testIsInCollection() {
        testCollection.addBook(heights);
        Boolean testTrue = testCollection.isInCollection(heights.getTitle());
        assertTrue(testTrue);
    }

    @Test
    public void testGetBookAtIndexNoException() {
        try {
            testCollection.addBook(alice);
            int in = testCollection.getIndexOf(alice);
            assertEquals(alice, testCollection.getBookAtIndex(in));
        } catch (IndexGreaterThanException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testGetBookAtIndexCatchException() {
        try {
            testCollection.getBookAtIndex(5);
            fail("Exception should have been thrown");
        } catch (IndexGreaterThanException e) {
            //expected
        }
    }

    @Test
    public void testViewEmptyBooks() {
        assertTrue(testCollection.viewAllBooks().isEmpty());
    }

    @Test
    public void testViewAllBooks() {
        manyBooks.add(aliceString);
        manyBooks.add(heightsString);

        testCollection.addBook(alice);
        testCollection.addBook(heights);
        testList = testCollection.viewAllBooks();

        assertEquals(2, testList.size());
        assertEquals(manyBooks, testList);
    }

    @Test
    public void testCollectionToJson() {
        testCollection.addBook(alice);
        testCollection.addBook(heights);
        JSONObject testJson = testCollection.toJson();
        JSONArray books = testJson.getJSONArray("books");
        JSONObject aliceToJson = alice.toJson();
        JSONObject aliceTest = books.getJSONObject(0);

        assertEquals(testCollection.getName(), testJson.getString("name"));
        assertTrue(aliceTest.get("title").equals(aliceToJson.get("title")));
        assertTrue(aliceTest.get("author").equals(aliceToJson.get("author")));
    }
}
