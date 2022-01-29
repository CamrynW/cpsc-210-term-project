package ui;

import model.Book;
import model.Collection;

import java.util.Scanner;

/*
 * Represents a new Book created with user input
 */
public class MakeNewBook {

    private Scanner in;
    private int rate;
    private String selection;
    private String find;
    private Collection collection;

    /*
     * Constructor for MakeNewBook
     * EFFECTS: allows for a new book to be created in c
     */
    public MakeNewBook(Collection c) {
        collection = c;
        in = new Scanner(System.in);
    }

    /*
     * Constructor for MakeNewBook
     * MODIFIES: collection
     * EFFECTS: creates a new Book
     */
    public void makeANewBook() {
        String ti = bookTitle();
        String aut = bookAuthor();
        Book.Condition con = bookCondition();
        Boolean rea = bookIsRead();

        Book book = new Book(ti, aut, rea, con);

        if (book.getReadingStatus()) {
            bookRating(book);

            bookReview(book);
        }
        collection.addBook(book);
    }

    /*
     * EFFECTS: returns given input as a book's title
     */
    private String bookTitle() {
        String title = "";
        System.out.println("Enter the title: ");
        title = searchByString();
        return title;
    }

    /*
     * EFFECTS: returns given input as a book's author
     */
    private String bookAuthor() {
        String name = "";
        System.out.println("Enter the author's name: ");
        name = searchByString(); //watch
        return name;
    }

    /*
     * EFFECTS: returns given input as a book's condition
     */
    private Book.Condition bookCondition() {
        Book.Condition c = null;
        String choose = "";
        System.out.println("What kind of condition is it in? ");
        while (!(choose.equals("perfect") || choose.equals("good")
                || choose.equals("fair") || choose.equals("poor"))) {
            System.out.println("Please choose from: perfect, good, fair, or poor");
            choose = in.next();
            choose = choose.toLowerCase();
        }

        if (choose.equals("perfect")) {
            c = Book.Condition.PERFECT;
        } else if (choose.equals("good")) {
            c = Book.Condition.GOOD;
        } else if (choose.equals("fair")) {
            c = Book.Condition.FAIR;
        } else if (choose.equals("poor")) {
            c = Book.Condition.POOR;
        }
        return c;
    }

    /*
     * EFFECTS: returns user input as whether a book has already been read
     */
    private Boolean bookIsRead() {
        pickYesOrNo("Have you read it before?");
        return selection.equals("yes");
    }

    /*
     * MODIFIES: b
     * EFFECTS: sets a book's star rating based on user input
     */
    protected void bookRating(Book b) {
        pickYesOrNo("Would you like to rate this book?");
        rate = 0;
        if (selection.equals("yes")) {
            System.out.println("Please enter a rating between 1 and 5");
            while (!(rate == 1 || rate == 2 || rate == 3 || rate == 4 || rate == 5)) {
                rate = in.nextInt();
            }
        }
        if (rate == 1) {
            b.setBookRating(Book.Rating.ONE_STAR);
        } else if (rate == 2) {
            b.setBookRating(Book.Rating.TWO_STARS);
        } else if (rate == 3) {
            b.setBookRating(Book.Rating.THREE_STARS);
        } else if (rate == 4) {
            b.setBookRating(Book.Rating.FOUR_STARS);
        } else if (rate == 5) {
            b.setBookRating(Book.Rating.FIVE_STARS);
        }
    }

    /*
     * MODIFIES: b
     * EFFECTS: creates a new book review with given input
     */
    protected void bookReview(Book b) {
        //String review = "";
        pickYesOrNo("Would you like to leave a review? ");
        if (selection.equals("yes")) {
            System.out.println("Write your review below: ");
            String review = searchByString(); //watch
            b.writeBookReview(review);
        }
    }

    /*
     * MODIFIES: b
     * EFFECTS: sets whether a book is currently on loan
     */
    protected void bookLoan(Book b) {
        pickYesOrNo("Is this book currently on loan? ");
        if (selection.equals("yes")) {
            b.setLoanStatus(true);
        } else {
            b.setLoanStatus(false);
        }
    }

    /*
     * From TellerApp. ui.TellerApp.selectAccount
     * EFFECTS: forces the user to pick either yes or no in response to a question
     */
    private void pickYesOrNo(String question) {
        selection = "";
        while (!(selection.equals("yes") || selection.equals("no"))) {
            System.out.println(question);
            selection = in.next();
            selection = selection.toLowerCase();
        }
    }

    /*
     * EFFECTS: allows user to search using words and spaces
     */
    public String searchByString() {
        find = "";
        for (int i = 0; i < 2;) {
            while (find.isEmpty()) {
                find = in.nextLine();
            }
            i++;
        }
        return find;
    }
}
