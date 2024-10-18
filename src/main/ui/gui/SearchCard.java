package ui.gui;

import model.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/*
 * Represents all methods which allow a user to search
 */
public class SearchCard extends UpdateCollection implements ActionListener, ItemListener {

    private static final String TITLE = "Title";
    private static final String AUTHOR = "Author";
    private JTextField enterSearch;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private Boolean isTitle;
    private Book searchedBook;

    /*
     * Constructor
     * EFFECTS: creates a card which allows for the user to search their collection
     */
    public SearchCard(DefaultListModel<Book> listModel, DefaultListModel<String> bookString) {
        super(listModel, bookString);
        isTitle = true;
        searchedBook = null;

        enterSearch = new JTextField(15);
        enterSearch.setText("Enter your search here!");
        enterSearch.addActionListener(this);

        textArea = new JTextArea(8, 8);
        textArea.setEditable(false);
        scrollPane = new JScrollPane(textArea);
        scrollPane.setSize(5, 10);
    }

    /*
     * EFFECTS: creates a JPanel with a search bar and text area
     */
    public JPanel addSearchCard() {
        JPanel card = new JPanel(new GridBagLayout());
        String[] items = {TITLE, AUTHOR};
        JPanel titleAuthorChooser = comboBoxPanel(items);
        setComboBoxListener(this);

        JPanel bar = new JPanel(new GridBagLayout());
        bar.add(titleAuthorChooser, fitConstraints(0, 1, 0));
        bar.add(enterSearch, fitConstraints(0, 2, 0));

        card.add(bar, fitConstraints(0, 1, 0));
        card.add(scrollPane, fitConstraints(0, 1, 1));

        return card;
    }

    /*
     * EFFECTS: prints the string form of the book searched in enterSearch
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String text = enterSearch.getText();

        for (int i = 0; i <= getModel().size(); i++) {
            Book b = getModel().get(i);
            if (getStringModel().size() == 1) {
                searchedBook = b;
                textArea.setText(printBook(b));
            } else {
                appendToTextArea(b, text, i);
            }

        }
        textArea.selectAll();
        enterSearch.selectAll();
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    /*
     * MODIFIES: this
     * EFFECTS: appends information about a book to TextArea
     */
    private void appendToTextArea(Book b, String text, int i) {
        if (isTitle) {
            if (b.getTitle().equals(text)) {
                searchedBook = b;
                textArea.setText(printBook(b));
            } else if (b.getTitle().startsWith(text)) {
                textArea.append(getStringModel().get(i) + "\n");
            }
        } else {
            if (b.getAuthor().equals(text)) {
                searchedBook = b;
                textArea.setText(printBook(b));
            } else if (b.getAuthor().startsWith(text)) {
                textArea.append(getStringModel().get(i) + "\n");
            }
        }
    }

    /*
     * EFFECTS: returns a series of book attributes in string form
     */
    private String printBook(Book b) {
        String attributes = "Title: " + b.getTitle() + "\n" + "Author: " + b.getAuthor() + "\n"
                + "Has Been Read?: " + b.getReadingStatus() + "\n" + "Condition: " + b.getCondition()
                + "\n" + "Rating out of 5: " + b.getRating() + "\n" + "Currently On Loan?: " + b.getLoanStatus()
                + "\n" + "Review: " + b.getReview() + "\n";

        return attributes;
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets isTitle to true if TITLE is chosen.
     *          sets isTitle to false if AUTHOR is chosen.
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        String st = (String) e.getItem();
        if (st.equals(TITLE)) {
            isTitle = true;
        } else if (st.equals(AUTHOR)) {
            isTitle = false;
        }
    }

    /*
     * EFFECTS: returns searched book
     */
    public Book getSearchedBook() {
        return searchedBook;
    }
}
