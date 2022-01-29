package ui.gui;

import model.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/*
 * Represents methods needed to view filtered lists of books
 */
public class ViewCard extends UpdateCollection implements ItemListener, ActionListener {

    private JPanel filterCard;
    DefaultListModel<String> unreadStrings = new DefaultListModel<>();
    DefaultListModel<String> loanedStrings = new DefaultListModel<>();
    DefaultListModel<String> ratedStrings = new DefaultListModel<>();
    private AddBookCard addBookCard;

    /*
     * Constructor
     * EFFECTS: instantiates a card which allows users to filter their collection
     */
    public ViewCard(DefaultListModel<Book> listModel, DefaultListModel<String> bookString) {
        super(listModel, bookString);
        addBookCard = new AddBookCard(listModel, bookString);
    }

    /*
     * EFFECTS: returns a JPanel which allows the user view their collection
     */
    public JPanel addViewCard() {
        String[] items = {"All Books", "Unread Books", "Loaned Books", "Books by Rating"};
        JPanel comboBox = comboBoxPanel(items);
        setComboBoxListener(this);

        filterCard = new JPanel(new CardLayout());
        JPanel viewAllPanel = makePanelWithPane(getStringModel());
        JPanel viewUnreadPanel = makePanelWithPane(unreadStrings);
        JPanel viewLoanedPanel = makePanelWithPane(loanedStrings);
        JPanel viewRatedPanel = new JPanel(new BorderLayout());
        viewRatedPanel.add(makePanelWithPane(ratedStrings));
        viewRatedPanel.add(sortByStarButtons(), BorderLayout.PAGE_END);

        filterCard.add(viewAllPanel, "All Books");
        filterCard.add(viewUnreadPanel, "Unread Books");
        filterCard.add(viewLoanedPanel, "Loaned Books");
        filterCard.add(viewRatedPanel, "Books by Rating");

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(comboBox, BorderLayout.PAGE_START);
        panel.add(filterCard, BorderLayout.CENTER);

        return panel;
    }

    /*
     * EFFECTS: creates a JPanel with a sorted JScrollPane
     */
    private JPanel makePanelWithPane(DefaultListModel<String> model) {
        JPanel panelWithPane = new JPanel();
        JList<String> stringJList = new JList<>(model);
        JScrollPane stringPane = new JScrollPane(stringJList);
        stringPane.setSize(100, 200);
        panelWithPane.add(stringPane);
        return panelWithPane;
    }

    /*
     * MODIFIES: this
     * EFFECTS: sorts books based on which comboBox item is chosen
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        String st = (String) e.getItem();
        if (st.equals("Unread Books")) {
            getUnreadBooks();
        } else if (st.equals("Loaned Books")) {
            getLoanedBooks();
        } else if (st.equals("Books by Rating")) {
            getRatedBooks(addBookCard.getRating());
        }
        CardLayout cl = (CardLayout) (filterCard.getLayout());
        cl.show(filterCard, (String) e.getItem());
    }

    /*
     * MODIFIES: this
     * EFFECTS: checks if any books are unread and adds them to unreadStrings
     */
    private void getUnreadBooks() {
        for (int i = 0; i < getModel().size(); i++) {
            Book b = getModel().get(i);
            String bookString = getStringModel().get(i);
            if (!b.getReadingStatus()) {
                if (!unreadStrings.contains(bookString)) {
                    unreadStrings.addElement(bookString);
                }
            } else {
                if (unreadStrings.contains(bookString)) {
                    unreadStrings.remove(i);
                }
            }
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: checks if any books are loaned out and adds them to loanedStrings
     */
    private void getLoanedBooks() {
        for (int i = 0; i < getModel().size(); i++) {
            Book b = getModel().get(i);
            String bookString = getStringModel().get(i);
            if (b.getLoanStatus()) {
                if (!loanedStrings.contains(bookString)) {
                    loanedStrings.addElement(bookString);
                }
            } else {
                if (loanedStrings.contains(bookString)) {
                    loanedStrings.remove(i);
                }
            }
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: sorts books by rating and adds them to ratedStrings
     */
    private void getRatedBooks(Book.Rating rating) {
        for (int i = 0; i < getModel().size(); i++) {
            Book b = getModel().get(i);
            String bookString = getStringModel().get(i);
            if (b.getRating().equals(rating)) {
                if (!ratedStrings.contains(bookString)) {
                    ratedStrings.addElement(bookString);
                }
            } else {
                if (ratedStrings.contains(bookString)) {
                    ratedStrings.remove(i);
                }
            }
        }
    }

    /*
     * EFFECTS: creates a JPanel of buttons for star ratings
     */
    private JPanel sortByStarButtons() {
        JRadioButton one = addBookCard.makeStarButton("one");
        JRadioButton two = addBookCard.makeStarButton("two");
        JRadioButton three = addBookCard.makeStarButton("three");
        JRadioButton four = addBookCard.makeStarButton("four");
        JRadioButton five = addBookCard.makeStarButton("five");
        one.addActionListener(this);
        two.addActionListener(this);
        three.addActionListener(this);
        four.addActionListener(this);
        five.addActionListener(this);

        ButtonGroup stars = new ButtonGroup();
        stars.add(one);
        stars.add(two);
        stars.add(three);
        stars.add(four);
        stars.add(five);

        JPanel starPanel = new JPanel();
        starPanel.add(one);
        starPanel.add(two);
        starPanel.add(three);
        starPanel.add(four);
        starPanel.add(five);
        return starPanel;
    }

    /*
     * MODIFIES: this
     * EFFECTS: sorts books in collection based on button pressed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("one")) {
            getRatedBooks(Book.Rating.ONE_STAR);
        } else if (e.getActionCommand().equals("two")) {
            getRatedBooks(Book.Rating.TWO_STARS);
        } else if (e.getActionCommand().equals("three")) {
            getRatedBooks(Book.Rating.THREE_STARS);
        } else if (e.getActionCommand().equals("four")) {
            getRatedBooks(Book.Rating.FOUR_STARS);
        } else if (e.getActionCommand().equals("five")) {
            getRatedBooks(Book.Rating.FIVE_STARS);
        }
    }
}
