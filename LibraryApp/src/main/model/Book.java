package model;

import org.json.JSONObject;
import persistence.Writable;

/*
 * Represents a book having a title, author, reading status, condition, rating, loan status, and a review
 */
public class Book implements Writable {

    public enum Condition { PERFECT, GOOD, FAIR, POOR }

    public enum Rating { FIVE_STARS, FOUR_STARS, THREE_STARS, TWO_STARS, ONE_STAR, UNRATED }

    private String title;             // a book's title
    private String author;            // a book's author
    private Boolean hasRead;          // keeps track if the book has been read
    private Condition condition;      // a book's condition. one of PERFECT, GOOD, FAIR, or POOR
    private Rating rating;            // a book's rating. out of 5, or UNRATED.
    private Boolean loaned;           // keeps track if the book is currently on loan
    private String review;            // a book's review

    /*
     * Book constructor
     * REQUIRES: title and author have a non-zero length
     * EFFECTS: creates a new book with title set to ti. author set to au. hasRead set to re.
     *          condition set to bc. rating is initially UNRATED, loaned is initially false,
     *          and review is initially empty.
     */
    public Book(String ti, String au, Boolean re, Condition bc) {
        this.title = ti;
        this.author = au;
        this.hasRead = re;
        this.condition = bc;

        rating = Rating.UNRATED;
        loaned = false;
        review = "";
    }

    /*
     * setters
     */
    public void setTitle(String newTitle) {
        title = newTitle;
    }

    public void setAuthor(String newAuthor) {
        author = newAuthor;
    }

    public void setCondition(Condition newCondition) {
        condition = newCondition;
    }

    public void readBook() {
        hasRead = true;
    }

    public void setBookRating(Rating newRating) {
        rating = newRating;
    }

    public void setLoanStatus(Boolean newStatus) {
        loaned = newStatus;
    }

    public void writeBookReview(String newReview) {
        review = newReview;
    }

    /*
     * getters
     */
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Boolean getReadingStatus() {
        return hasRead;
    }

    public Condition getCondition() {
        return condition;
    }

    public Rating getRating() {
        return rating;
    }

    public Boolean getLoanStatus() {
        return loaned;
    }

    public String getReview() {
        return review;
    }

    /*
     *  EFFECTS: returns book as a JSON object
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("author", author);
        json.put("reading status", hasRead);
        json.put("condition", condition);
        json.put("rating", rating);
        json.put("loan status", loaned);
        json.put("review", review);

        return json;
    }
}
