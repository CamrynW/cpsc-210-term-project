package model;

import exceptions.BookDoesNotExistException;
import exceptions.IndexGreaterThanException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;

/*
 * Represents a list of Books
 */
public class Collection implements Writable {

    private String name;
    private LinkedList<Book> collection;

    /*
     * Library constructor
     * EFFECTS: creates a new collection of books
     */
    public Collection(String name) {
        this.name = name;
        collection = new LinkedList<>();
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds b to collection
     */
    public void addBook(Book b) {
        collection.add(b);
    }

    /*
     * MODIFIES: this
     * EFFECTS: if b is not in collection, throws BookDoesNotExistException,
     *          otherwise, removes b from collection
     */
    public void removeBook(Book b) throws BookDoesNotExistException {
        if (!collection.contains(b)) {
            throw new BookDoesNotExistException();
        }
        collection.remove(b);
    }


    /*
     * EFFECTS: returns true if collection has a book with the given title
     */
    public Boolean isInCollection(String st) {
        Boolean inCollection = false;
        for (Book b : collection) {
            if (b.getTitle().equals(st)) {
                inCollection = true;
            }
        }
        return inCollection;
    }

    /*
     * EFFECTS: returns all books in collection in the form of "Title: Author"
     */
    public LinkedList<String> viewAllBooks() {
        LinkedList<String> allBooks = new LinkedList<>();
        String oneBook;
        for (Book b : collection) {
            oneBook = b.getTitle() + ": " + b.getAuthor();
            allBooks.add(oneBook);
        }
        return allBooks;
    }

    /*
     * EFFECTS: returns number of books in collection
     */
    public int getNumOfBooks() {
        return collection.size();
    }

    /*
     * EFFECTS: returns index of b in collection
     */
    public int getIndexOf(Book b) {
        int index = collection.indexOf(b);
        return index;
    }

    /*
     * EFFECTS: if index is >= collection size, throws new IndexGreaterThanException,
     *          otherwise, returns Book at index
     */
    public Book getBookAtIndex(int index) throws IndexGreaterThanException {
        if (index >= collection.size()) {
            throw new IndexGreaterThanException();
        }
        Book b = collection.get(index);
        return b;
    }

    /*
     * EFFECTS: returns a new list of all books
     */
    public LinkedList<Book> getAllBooks() {
        LinkedList<Book> allBooks = new LinkedList<>();
        for (Book b : collection) {
            allBooks.add(b);
        }
        return allBooks;
    }

    /*
     * EFFECTS: gets name of collection
     */
    public String getName() {
        return name;
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets name of collection
     */
    public void setName(String n) {
        name = n;
    }

    /*
     * From JsonSerializationDemo. model.WorkRoom.toJson
     * EFFECTS: returns collection with books as a JSON object
     */
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("books", booksToJson());
        return jsonObject;
    }

    /*
     * From JsonSerializationDemo. model.WorkRoom.thingiesToJson
     * EFFECTS: returns books as a JSON array
     */
    private JSONArray booksToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Book b : getAllBooks()) {
            jsonArray.put(b.toJson());
        }
        return jsonArray;
    }
}
