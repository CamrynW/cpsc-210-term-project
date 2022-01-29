package model;

import java.util.LinkedList;

/*
 * Represents a class of filtered lists
 */
public class FilteredLists {

    private Collection collection;
    private String name;

    /*
     * FilteredLists constructor
     * EFFECTS: allows for a collection to be filtered by given parameters
     */
    public FilteredLists(Collection c) {
        collection = c;
        name = c.getName();
    }

    /*
     * EFFECTS: returns a list of an author's books in the form "Title: Author"
     */
    public LinkedList<String> getBookByAuthor(String st) {
        Collection booksByAuthor = new Collection(name);
        for (Book b : collection.getAllBooks()) {
            if (b.getAuthor().startsWith(st)) {
                booksByAuthor.addBook(b);
            }
        }
        return booksByAuthor.viewAllBooks();
    }

    /*
     * EFFECTS: returns a list of books with rating br in the form "Title: Author"
     */
    public LinkedList<String> getRatedBooks(Book.Rating br) {
        Collection booksByRating = new Collection(name);
        for (Book b : collection.getAllBooks()) {
            if (b.getRating() == br) {
                booksByRating.addBook(b);
            }
        }
        return booksByRating.viewAllBooks();
    }

    /*
     * EFFECTS: returns a list of books that are currently loaned in the form "Title: Author"
     */
    public LinkedList<String> getLoanedBooks() {
        Collection loanedBooks = new Collection(name);
        for (Book b : collection.getAllBooks()) {
            if (b.getLoanStatus()) {
                loanedBooks.addBook(b);
            }
        }
        return loanedBooks.viewAllBooks();
    }

    /*
     * EFFECTS: returns a list of books that are currently unread in the form "Title: Author"
     */
    public LinkedList<String> getUnreadBooks() {
        Collection unread = new Collection(name);
        for (Book b : collection.getAllBooks()) {
            if (!b.getReadingStatus()) {
                unread.addBook(b);
            }
        }
        return unread.viewAllBooks();
    }

    /*
     * EFFECTS: returns a list of books that begin with the given string
     */
    public LinkedList<String> getBooksStartingWith(String st) {
        Collection startWith = new Collection(name);
        for (Book b : collection.getAllBooks()) {
            if (b.getTitle().startsWith(st)) {
                startWith.addBook(b);
            }
        }
        return startWith.viewAllBooks();
    }

    /*
     * Getters
     */
    public Collection getCollection() {
        return collection;
    }

    public String getCollectionName() {
        return name;
    }

}
