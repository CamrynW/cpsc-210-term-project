package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Tests for Book
 */
public class BookTest {

    Book holmes;

    @BeforeEach
    public void setUp() {
        holmes = new Book("Sherlock Holmes", "Arthur Conan Doyle",
                false, Book.Condition.GOOD);
    }

    @Test
    public void testBookConstructor() {
        assertEquals("Sherlock Holmes", holmes.getTitle());
        assertEquals("Arthur Conan Doyle", holmes.getAuthor());
        assertFalse(holmes.getReadingStatus());
        assertEquals(Book.Condition.GOOD, holmes.getCondition());
        assertEquals(Book.Rating.UNRATED, holmes.getRating());
        assertFalse(holmes.getLoanStatus());
        assertTrue(holmes.getReview().isEmpty());
    }

    @Test
    public void testBookSetTitle() {
        holmes.setTitle("A Study in Scarlet");
        assertEquals("A Study in Scarlet", holmes.getTitle());
    }

    @Test
    public void testBookSetAuthor() {
        holmes.setAuthor("Not Arthur Conan Doyle");
        assertEquals("Not Arthur Conan Doyle", holmes.getAuthor());
    }

    @Test
    public void testBookSetCondition() {
        holmes.setCondition(Book.Condition.POOR);
        assertEquals(Book.Condition.POOR, holmes.getCondition());
    }

    @Test
    public void testBookSetRatingFOUR() {
        holmes.setBookRating(Book.Rating.FOUR_STARS);
        assertEquals(Book.Rating.FOUR_STARS, holmes.getRating());
    }

    @Test
    public void testBookSetLoan() {
        holmes.setLoanStatus(true);
        assertTrue(holmes.getLoanStatus());
    }

    @Test
    public void testWriteReview() {
        String testReview = "Maybe not the nicest detective but certainly the most iconic.";
        holmes.writeBookReview(testReview);
        assertEquals(testReview, holmes.getReview());
    }

    @Test
    public void testReadBook() {
        holmes.readBook();
        assertTrue(holmes.getReadingStatus());
    }

    @Test
    public void testToJson() {
        JSONObject json = holmes.toJson();
        assertEquals(holmes.getTitle(), json.get("title"));
        assertEquals(holmes.getAuthor(), json.get("author"));
        assertEquals(holmes.getReadingStatus(), json.get("reading status"));
        assertEquals(holmes.getCondition(), json.get("condition"));
        assertEquals(holmes.getRating(), json.get("rating"));
        assertEquals(holmes.getLoanStatus(), json.get("loan status"));
        assertEquals(holmes.getReview(), json.get("review"));
    }
}
