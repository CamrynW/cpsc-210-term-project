package persistence;


import model.Book;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * Represents a way to check a JSON object against a Book
 */
public class JsonTest {

    public void checkBookConstructor(Book b, String ti, String au, Boolean re, Book.Condition co) {
        assertEquals(ti, b.getTitle());
        assertEquals(au, b.getAuthor());
        assertEquals(re, b.getReadingStatus());
        assertEquals(co, b.getCondition());
    }

    public void checkBookExtras(Book b, Book.Rating ra, Boolean lo, String re) {
        assertEquals(ra, b.getRating());
        assertEquals(lo, b.getLoanStatus());
        assertEquals(re, b.getReview());
    }
}
