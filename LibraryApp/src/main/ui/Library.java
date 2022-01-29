package ui;

import exceptions.BookDoesNotExistException;
import exceptions.IndexGreaterThanException;
import model.Book;
import model.Collection;
import model.FilteredLists;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.gui.Cards;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/*
 * Represents a Library Application
 */
public class Library {

    private static final String JSON_STORE = "./data/collection.json";
    private static Set<String> SELECTS;
    private FilteredLists filter;
    private Collection myBooks;
    private Scanner input;
    private MakeNewBook makeNewBook;
    private Menu menu;
    private String find;
    private String name;
    private String auto;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    /*
     * EFFECTS: runs the library application
     */
    public Library() {
        try {
            SwingUtilities.invokeAndWait(() -> new Cards());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /*
     * From TellerApp. ui.TellerApp.runTeller
     * MODIFIES: this
     * EFFECTS: processes user input
     */
    public void runLibraryApp() {
        boolean going = true;
        String command = null;

        instantiate();

        while (going) {
            menu.displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                going = false;
            } else {
                processCommands(command);
            }
        }
    }

    /*
     * From TellerApp. ui.TellerApp.processCommand
     * MODIFIES: this
     * EFFECTS: processes specific user commands
     */
    public void processCommands(String input) {
        if (input.equals("a")) {
            createANewBook();
        } else if (input.equals("e")) {
            editExistingBook();
        } else if (input.equals("t")) {
            searchBy();
        } else if (input.equals("s")) {
            saveCollection();
        } else if (input.equals("l")) {
            loadCollection();
        } else if (input.equals("v")) {
            viewFilteredLists();
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes a new collection
     */
    public void instantiate() {
        SELECTS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("a", "l", "u", "r", "n")));
        name = "my collection";
        myBooks = new Collection(name);
        filter = new FilteredLists(myBooks);
        makeNewBook = new MakeNewBook(myBooks);
        input = new Scanner(System.in);
        menu = new Menu();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates a new book
     */
    private void createANewBook() {
        makeNewBook.makeANewBook();
    }

    /*
     * MODIFIES: this
     * EFFECTS: lets the user remove a book of their choosing from their collection
     */
    private void removeChosenBook() {
        auto = autoComplete();
        if (myBooks.isInCollection(auto) || isOnlyOne(auto)) {
            Book b = getCertainBook(auto);
            try {
                myBooks.removeBook(b);
            } catch (BookDoesNotExistException e) {
                System.out.println("This book doesn't exist!");
            }
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets a book's reading status to true, allows user to rate and review
     */
    private void finishedReading() {
        auto = autoComplete();
        if (myBooks.isInCollection(auto) || isOnlyOne(auto)) {
            Book b = getCertainBook(auto);
            b.readBook();
            makeNewBook.bookRating(b);
            makeNewBook.bookReview(b);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: changes a book's loaning status based on user input
     */
    private void changeLoanStatus() {
        auto = autoComplete();
        if (myBooks.isInCollection(auto) || isOnlyOne(auto)) {
            Book b = getCertainBook(auto);
            makeNewBook.bookLoan(b);
        }
    }

    /*
     * EFFECTS: allows the user to edit an existing book or remove it from their collection
     */
    private void editExistingBook() {
        String select = "";
        menu.displayEditMenu();

        while (!(select.equals("f") || select.equals("l") || select.equals("r"))) {
            select = input.next();
        }
        if (select.equals("f")) {
            finishedReading();
        } else if (select.equals("l")) {
            changeLoanStatus();
        } else if (select.equals("r")) {
            removeChosenBook();
        }
    }

    /*
     * From TellerApp. ui.TellerApp.processCommand
     * EFFECTS: allows the user to search for books by title or author name
     */
    private void searchBy() {
        String search = "";
        menu.displaySearchMenu();
        while (!(search.equals("t") || search.equals("a"))) {
            search = input.next();
            search = search.toLowerCase();
        }

        if (search.equals("t")) {
            System.out.println("Please enter the title of the book you wish to search: ");
            auto = autoComplete();
            printBookAttributes(getCertainBook(auto));

        } else if (search.equals("a")) {
            System.out.println("Please enter the name of the author you wish to search: ");
            auto = makeNewBook.searchByString();
            printBooks(filter.getBookByAuthor(auto));
        }
    }

    /*
     * EFFECTS: suggests a list of books based on current console interaction
     */
    private String autoComplete() {
        System.out.println("Please enter the title of the book: ");
        find = "";
        while (!isOnlyOne(find)) {
            find = input.nextLine();
            if (!find.isEmpty()) {
                printBooks(filter.getBooksStartingWith(find));
            }
        }
        return find;
    }

    /*
     * EFFECTS: returns true if there is only 1 book starting with the given string
     */
    private Boolean isOnlyOne(String st) {
        return (filter.getBooksStartingWith(st).size() == 1);
    }

    /*
     * EFFECTS: prints the current number of books in a collection
     */
    private void numOfBooks() {
        if (myBooks.getNumOfBooks() == 1) {
            System.out.println("You currently have " + myBooks.getNumOfBooks() + " book in your collection.");
        } else {
            System.out.println("You currently have " + myBooks.getNumOfBooks() + " books in your collection.");
        }
    }

    /*
     * From TellerApp. ui.TellerApp.selectAccount
     * EFFECTS: different options for how a user can view their collection.
     *          Prints a list of books in the form Title: Author
     */
    private void viewFilteredLists() {
        String select = "";
        menu.displayListMenu();

        while (!SELECTS.contains(select)) {
            select = input.next();
        }
        if (select.equals("a")) {
            printBooks(myBooks.viewAllBooks());
        } else if (select.equals("l")) {
            printBooks(filter.getLoanedBooks());
        } else if (select.equals("u")) {
            printBooks(filter.getUnreadBooks());
        } else if (select.equals("r")) {
            askAboutRatedBookList();
        } else if (select.equals("n")) {
            numOfBooks();
        }
    }

    /*
     * From TellerApp. ui.TellerApp.selectAccount
     * EFFECTS: prints a list of books with a given rating
     */
    private void askAboutRatedBookList() {
        int rate = 0;
        System.out.println("Please enter a rating between 1 and 5");
        while (!(rate == 1 || rate == 2 || rate == 3 || rate == 4 || rate == 5)) {
            rate = input.nextInt();
        }

        if (rate == 1) {
            printRatedBooks(Book.Rating.ONE_STAR);
        } else if (rate == 2) {
            printRatedBooks(Book.Rating.TWO_STARS);
        } else if (rate == 3) {
            printRatedBooks(Book.Rating.THREE_STARS);
        } else if (rate == 4) {
            printRatedBooks(Book.Rating.FOUR_STARS);
        } else if (rate == 5) {
            printRatedBooks(Book.Rating.FIVE_STARS);
        } else {
            printRatedBooks(Book.Rating.UNRATED);
        }
    }

    /*
     * EFFECTS: prints all books with a given star rating in the form Title: Author
     */
    private void printRatedBooks(Book.Rating star) {
        printBooks(filter.getRatedBooks(star));
    }

    /*
     * REQUIRES: there cannot be two books with the same title
     * EFFECTS: returns a book with the given title.
     */
    private Book getCertainBook(String st) {
        int index = 0;
        for (Book b : myBooks.getAllBooks()) {
            if (b.getTitle().equals(st)) {
                index = myBooks.getIndexOf(b);
            } else if (b.getTitle().startsWith(st)) {
                index = myBooks.getIndexOf(b);
            }
        }
        Book book = null;
        try {
            book = myBooks.getBookAtIndex(index);
        } catch (IndexGreaterThanException e) {
            System.out.println("Error: index exceeds collection size");
        }
        return book;
    }

    /*
     * EFFECTS: prints a book's title, author, reading status,
     *          condition, rating, loan status, and review
     */
    private void printBookAttributes(Book b) {
        System.out.println("Title: " + b.getTitle());
        System.out.println("Author: " + b.getAuthor());
        System.out.println("Has Been Read?: " + b.getReadingStatus());
        System.out.println("Condition: " + b.getCondition());
        System.out.println("Rating out of 5: " + b.getRating());
        System.out.println("Currently On Loan?: " + b.getLoanStatus());
        System.out.println("Review: " + b.getReview());
    }

    /*
     *  From JsonSerializationDemo. ui.WorkRoomApp.printThingies
     *  EFFECTS: prints all books with authors to console
     */
    private void printBooks(LinkedList<String> books) {
        for (String st : books) {
            System.out.println(st);
        }
    }

    /*
     * From JsonSerializationDemo. ui.WorkRoomApp.saveWorkRoom
     * EFFECTS: saves the collection of books to file
     */
    private void saveCollection() {
        try {
            jsonWriter.open();
            jsonWriter.write(myBooks);
            jsonWriter.close();
            System.out.println("Saved " + myBooks.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    /*
     * From JsonSerializationDemo. ui.WorkRoomApp.loadWorkRoom
     * EFFECTS: loads the collection of books from file
     */
    private void loadCollection() {
        try {
            myBooks = jsonReader.read();
            filter = new FilteredLists(myBooks);
            makeNewBook = new MakeNewBook(myBooks);
            System.out.println("Loaded " + myBooks.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
