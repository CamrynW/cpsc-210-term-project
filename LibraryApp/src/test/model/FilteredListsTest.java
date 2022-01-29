package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * Test class for FilteredLists
 */
public class FilteredListsTest extends CollectionTest {

    FilteredLists filter;
    Book express;
    Book none;
    String noneString;
    String expressString;

    @BeforeEach
    public void setUp() {
        filter = new FilteredLists(testCollection);
        express = new Book("Murder on the Orient Express", "Agatha Christie",
                true, Book.Condition.POOR);
        none = new Book("And Then There Were None", "Agatha Christie",
                true, Book.Condition.FAIR);
        none.setBookRating(Book.Rating.THREE_STARS);
        express.setBookRating(Book.Rating.FOUR_STARS);

        noneString = none.getTitle() + ": " + none.getAuthor();
        expressString = express.getTitle() + ": " + express.getAuthor();
    }

    @Test
    public void testConstructor() {
        assertEquals(testCollection, filter.getCollection());
        assertEquals(testCollection.getName(), filter.getCollectionName());
    }

    @Test
    public void testGetNoBooksByAuthor() {
        testCollection.addBook(heights);
        testList = filter.getBookByAuthor(none.getAuthor());
        assertTrue(testList.isEmpty());
    }

    @Test
    public void testGetBooksByAuthor() {
        manyBooks.add(expressString);
        manyBooks.add(noneString);

        testCollection.addBook(alice);
        testCollection.addBook(express);
        testCollection.addBook(none);
        testList = filter.getBookByAuthor("Agatha Christie");

        assertEquals(2, testList.size());
        assertEquals(manyBooks, testList);
    }

    @Test
    public void testGetMoreBooksByAuthor() {
        Book extraBook = new Book("Extra Book", "Allie Someone", false, Book.Condition.FAIR);
        String extraString = extraBook.getTitle() + ": " + extraBook.getAuthor();
        manyBooks.add(extraString);
        manyBooks.add(expressString);
        manyBooks.add(noneString);

        testCollection.addBook(extraBook);
        testCollection.addBook(alice);
        testCollection.addBook(express);
        testCollection.addBook(none);
        testList = filter.getBookByAuthor("A");

        assertEquals(3, testList.size());
        assertEquals(manyBooks, testList);
    }

    @Test
    public void testGetBooksFromEmpty() {
        assertTrue(filter.getBookByAuthor(none.getAuthor()).isEmpty());
        assertTrue(filter.getUnreadBooks().isEmpty());
        assertTrue(filter.getRatedBooks(Book.Rating.ONE_STAR).isEmpty());
        assertTrue(filter.getLoanedBooks().isEmpty());
        assertTrue(filter.getBooksStartingWith("st").isEmpty());
    }

    @Test
    public void testGetNoBooksByRating() {
        testCollection.addBook(alice);
        testCollection.addBook(none);
        testCollection.addBook(express);
        testList = filter.getRatedBooks(Book.Rating.ONE_STAR);

        assertTrue(testList.isEmpty());
    }

    @Test
    public void testGetBooksByRating() {
        manyBooks.add(aliceString);
        manyBooks.add(expressString);

        testCollection.addBook(alice);
        testCollection.addBook(none);
        testCollection.addBook(express);
        testList = filter.getRatedBooks(Book.Rating.FOUR_STARS);

        assertEquals(2, testList.size());
        assertEquals(manyBooks, testList);
    }

    @Test
    public void testGetNoLoanedBooks() {
        testCollection.addBook(none);
        testList = filter.getLoanedBooks();
        assertTrue(testList.isEmpty());
    }

    @Test
    public void testGetLoanedBooks() {
        manyBooks.add(heightsString);

        heights.setLoanStatus(true);
        testCollection.addBook(heights);
        testCollection.addBook(none);
        testList = filter.getLoanedBooks();

        assertEquals(1, testList.size());
        assertEquals(manyBooks, testList);
    }

    @Test
    public void testGetNoUnreadBooks() {
        testCollection.addBook(alice);
        testList = filter.getUnreadBooks();
        assertTrue(testList.isEmpty());
    }

    @Test
    public void testGetUnreadBooks() {
        manyBooks.add(heightsString);

        testCollection.addBook(heights);
        testCollection.addBook(alice);
        testList = filter.getUnreadBooks();

        assertEquals(1, testList.size());
        assertEquals(manyBooks, testList);
    }

    @Test
    public void testGetNoBooksStartingWith() {
        testCollection.addBook(none);
        testList = filter.getBooksStartingWith("zz");
        assertTrue(testList.isEmpty());
    }

    @Test
    public void testGetBooksStartingWithMoreLetters() {
        manyBooks.add(heightsString);

        testCollection.addBook(heights);
        testList = filter.getBooksStartingWith("Wut");

        assertEquals(1, testList.size());
        assertEquals(manyBooks, testList);
    }

    @Test
    public void testGetBooksStartingWithUpperCase() {
        manyBooks.add(aliceString);
        manyBooks.add(noneString);

        testCollection.addBook(alice);
        testCollection.addBook(heights);
        testCollection.addBook(none);
        testList = filter.getBooksStartingWith("A");

        assertEquals(2, testList.size());
        assertEquals(manyBooks, testList);
    }
}
