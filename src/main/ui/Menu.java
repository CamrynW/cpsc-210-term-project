package ui;


/*
 *  Represents all menus needed for the user interface
 */
public class Menu {

    /*
     * Constructor for Menu
     * EFFECTS: allows for menus to be called
     */
    public Menu() {
    }

    /*
     * From TellerApp. ui.TellerApp.displayMenu
     * EFFECTS: displays a menu of options to the user
     */
    public void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add a new book to your collection");
        System.out.println("\te -> edit a book in your collection");
        System.out.println("\tt -> search by title or author");
        System.out.println("\tv -> view your collection");
        System.out.println("\ts -> save collection to file");
        System.out.println("\tl -> load collection from file");
        System.out.println("\tq -> quit");
    }

    /*
     *  EFFECTS: displays a menu for all list options
     */
    public void displayListMenu() {
        System.out.println("\nPlease choose one of the following options: ");
        System.out.println("\ta -> view all books");
        System.out.println("\tl -> view all loaned books");
        System.out.println("\tu -> view all unread books");
        System.out.println("\tr -> view all books with a given rating");
        System.out.println("\tn -> view the current number of books in your collection");
    }

    /*
     *  EFFECTS: displays a menu for all book editing options
     */
    public void displayEditMenu() {
        System.out.println("\nPlease choose one of the following options: ");
        System.out.println("\tf -> change a book's reading status");
        System.out.println("\tl -> change a book's loan status");
        System.out.println("\tr -> remove a book from your collection");
    }

    /*
     *  EFFECTS: displays a menu for all search options
     */
    public void displaySearchMenu() {
        System.out.println("\nPlease choose a method to search by: ");
        System.out.println("\tt -> title");
        System.out.println("\ta -> author");
    }
}
