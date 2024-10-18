package ui.gui;

import exceptions.BookDoesNotExistException;
import model.Book;
import ui.sound.Music;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Represents a series of JPanels for editing, viewing, and user info
 */
public class EditCard extends UpdateCollection implements ActionListener {

    private static final String EDIT_REMOVE = "Remove a book from your collection";
    private final AddBookCard makeBook;
    private final SearchCard searchCard;
    private JPanel editCard;
    private JFrame popUp;


    /*
     * Constructor
     * EFFECTS: creates a card which allows a user to edit books in their collection
     */
    public EditCard(DefaultListModel<Book> listModel, DefaultListModel<String> bookString) {
        super(listModel, bookString);
        makeBook = new AddBookCard(listModel, bookString);
        searchCard = new SearchCard(listModel, bookString);

    }

    /*
     * EFFECTS: returns a JPanel which allows the user to edit a book
     */
    public JPanel addEditCard() {
        editCard = new JPanel();
        JPanel editRead = makeBook.ratingReviewCard();
        JPanel editLoan = makeBook.loanCard();
        JPanel remove = new JPanel(new GridBagLayout());
        editRead.add(editButton(AddBookCard.EDIT_READ, "Submit"),
                fitConstraints(8, 1, GridBagConstraints.PAGE_END));
        editLoan.add(editButton(AddBookCard.EDIT_LOAN, "Submit"),
                fitConstraints(8, 1, GridBagConstraints.PAGE_END));
        remove.add(editButton(EDIT_REMOVE, "Remove book"),
                fitConstraints(8, 1, GridBagConstraints.PAGE_END));

        JTabbedPane editBookTabs = new JTabbedPane();
        editBookTabs.add(AddBookCard.EDIT_READ, editRead);
        editBookTabs.add(AddBookCard.EDIT_LOAN, editLoan);
        editBookTabs.add(EDIT_REMOVE, remove);

        editCard.add(searchCard.addSearchCard());
        editCard.add(editBookTabs);

        return editCard;
    }

    /*
     * EFFECTS: creates a button to be used with editActionListener
     */
    private JButton editButton(String cmd, String text) {
        JButton button = new JButton(text);
        button.setActionCommand(cmd);
        button.addActionListener(this);
        return button;
    }

    /*
     * pop-up JFrame is from DialogDemoProject
     * MODIFIES: this
     * EFFECTS: edits the searched book
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Book b = searchCard.getSearchedBook();
        if (e.getActionCommand().equals(AddBookCard.EDIT_READ)) {
            midiSynth.play(11, 1, 1);
            b.readBook();
            b.setBookRating(makeBook.getRating());
            b.writeBookReview(makeBook.getInputReview().getText());

        } else if (e.getActionCommand().equals(AddBookCard.EDIT_LOAN)) {
            midiSynth.play(15, 1, 1);
            b.setLoanStatus(makeBook.getIsLoaned());

        } else if (e.getActionCommand().equals(EDIT_REMOVE)) {
            midiSynth.play(18, 1, 1);
            makePopUp(b);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: asks for confirmation before removing a book from collection
     */
    private void makePopUp(Book b) {
        int n = JOptionPane.showConfirmDialog(
                popUp, "Are you sure you want to remove this book?",
                "IMPORTANT!",
                JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            Music.playSound(Music.SAD_SOUND);
            if (getModel().contains(b)) {
                int index = getModel().indexOf(b);
                getModel().remove(index);
                getStringModel().remove(index);
                try {
                    getCollection().removeBook(b);
                } catch (BookDoesNotExistException e) {
                    exceptionPopUp();
                }
            }
        } else if (n == JOptionPane.NO_OPTION) {
            midiSynth.play(12, 1, 1);
        }
    }

    /*
     * EFFECTS: creates a PopUp window when a book cannot be removed
     */
    private void exceptionPopUp() {
        JFrame exceptionPopUp = new JFrame();
        JOptionPane.showConfirmDialog(
                exceptionPopUp, "This book cannot be removed",
                "Sorry!", JOptionPane.OK_OPTION);

    }
}
