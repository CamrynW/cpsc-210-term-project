package ui.gui;

import model.Book;
import ui.sound.Music;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
 * Represents a series of Panels which create a new book
 */
public class AddBookCard extends UpdateCollection implements ActionListener, ItemListener {

    protected static final String EDIT_READ = "Change reading status";
    protected static final String EDIT_LOAN = "Change loan status";
    private static final String PERFECT = "Perfect";
    private static final String GOOD = "Good";
    private static final String FAIR = "Fair";
    private static final String POOR = "Poor";
    private static final String ONE = "ONE";
    private static final String TWO = "TWO";
    private static final String THREE = "THREE";
    private static final String FOUR = "FOUR";
    private static final String FIVE = "FIVE";
    private boolean isLoaned;
    private boolean hasRead;
    private Book.Condition condition;
    private Book.Rating rating;
    private JTextField inputTitle;
    private JTextField inputAuthor;
    private JTextArea inputReview;

    /*
     * Constructor
     * EFFECTS: instantiates a card which shows all fields required to make a book
     */
    public AddBookCard(DefaultListModel<Book> listModel, DefaultListModel<String> bookString) {
        super(listModel, bookString);
        inputTitle = new JTextField();
        inputAuthor = new JTextField();
        inputReview = new JTextArea();

        condition = Book.Condition.PERFECT;
        rating = Book.Rating.UNRATED;
    }

    /*
     * EFFECTS: creates a new book
     */
    private Book makeNewBook() {
        String ti = inputTitle.getText();
        String au = inputAuthor.getText();
        Boolean re = hasRead;
        Book.Condition co = condition;

        return new Book(ti, au, re, co);
    }

    /*
     * EFFECTS: returns a JPanel which allows the user to set a book's title,
     *          author, condition, and read status
     */
    public JPanel addBookCard() {
        JPanel card = new JPanel(new GridBagLayout());
        String[] items = {PERFECT, GOOD, FAIR, POOR};
        JPanel comboBoxPanel = comboBoxPanel(items);
        setComboBoxListener(this);

        card.add(new JLabel("Title: "), fitConstraints(8, 0, 0));
        card.add(inputTitle, fitConstraints(8, 1, 0));
        card.add(new JLabel("Author: "), fitConstraints(8, 0, 1));
        card.add(inputAuthor, fitConstraints(8, 1, 1));
        card.add(new JLabel("Condition: "), fitConstraints(8, 0, 2));
        card.add(comboBoxPanel, fitConstraints(8, 1, 2));
        card.add(new JLabel("Read? "), fitConstraints(8, 0, 3));
        card.add(yesOrNoButtons("No", "Yes"),
                fitConstraints(8, 1, 3));
        card.add(submitButton("Submit"), fitConstraints(8, 2,
                GridBagConstraints.PAGE_END));

        return card;
    }

    /*
     * MODIFIES: this
     * EFFECTS: chooses a book condition
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        String c = (String) e.getItem();
        if (c.equals(PERFECT)) {
            condition = Book.Condition.PERFECT;
        } else if (c.equals(GOOD)) {
            condition = Book.Condition.GOOD;
        } else if (c.equals(FAIR)) {
            condition = Book.Condition.FAIR;
        } else if (c.equals(POOR)) {
            condition = Book.Condition.POOR;
        }
    }

    /*
     * EFFECTS: creates a JPanel of two radio buttons asking a yes or no question
     */
    public JPanel yesOrNoButtons(String negativeCmd, String positiveCmd) {
        JPanel yesOrNo = new JPanel(new GridBagLayout());

        JRadioButton no = new JRadioButton("No");
        no.setMnemonic(KeyEvent.VK_N);
        no.setSelected(true);
        no.setActionCommand(negativeCmd);
        no.addActionListener(this::simpleActionListener);

        JRadioButton yes = new JRadioButton("Yes");
        yes.setMnemonic(KeyEvent.VK_Y);
        yes.setActionCommand(positiveCmd);
        yes.addActionListener(this::simpleActionListener);

        ButtonGroup group = new ButtonGroup();
        group.add(no);
        group.add(yes);

        yesOrNo.add(no);
        yesOrNo.add(yes);
        return yesOrNo;
    }

    /*
     * MODIFIES: this
     * EFFECTS: actionListener for yesOrNoButtons. Sets hasRead for Book
     */
    public void simpleActionListener(ActionEvent e) {
        if (e.getActionCommand().equals("Yes")) {
            hasRead = true;
        } else if (e.getActionCommand().equals("Loaned")) {
            isLoaned = true;
        }
    }

    /*
     * EFFECTS: creates a JPanel which allows the user to set a book's loan status
     */
    public JPanel loanCard() {
        JPanel card = new JPanel(new GridBagLayout());

        card.add(new JLabel("Is this book currently loaned out?"),
                fitConstraints(8, 0, 1));
        card.add(yesOrNoButtons("Not Loaned", "Loaned"),
                fitConstraints(8, 1, 1));

        return card;
    }

    /*
     * EFFECTS: returns a JPanel which allows the user to set a rating and review
     */
    public JPanel ratingReviewCard() {
        JPanel ratingReview = new JPanel(new GridBagLayout());

        inputReview.setColumns(20);
        inputReview.setRows(5);

        ratingReview.add(new JLabel("Rating: "), fitConstraints(8, 0, 0));
        ratingReview.add(starButtons());
        ratingReview.add(new JLabel("Review: "), fitConstraints(8, 0, 2));
        ratingReview.add(inputReview, fitConstraints(8, 1, 2));

        return ratingReview;
    }

    /*
     * EFFECTS: creates a button group of star buttons
     */
    public JPanel starButtons() {
        JPanel starsCard = new JPanel();
        JRadioButton oneStar = makeStarButton(ONE);
        JRadioButton twoStar = makeStarButton(TWO);
        JRadioButton threeStar = makeStarButton(THREE);
        JRadioButton fourStar = makeStarButton(FOUR);
        JRadioButton fiveStar = makeStarButton(FIVE);

        ButtonGroup stars = new ButtonGroup();
        stars.add(oneStar);
        stars.add(twoStar);
        stars.add(threeStar);
        stars.add(fourStar);
        stars.add(fiveStar);

        starsCard.add(oneStar);
        starsCard.add(twoStar);
        starsCard.add(threeStar);
        starsCard.add(fourStar);
        starsCard.add(fiveStar);

        return starsCard;
    }

    /*
     * EFFECTS: creates a radio button for star ratings
     */
    public JRadioButton makeStarButton(String cmd) {
        JRadioButton star = new JRadioButton(cmd);
        star.setActionCommand(cmd);
        star.addActionListener(this::starActionListener);
        return star;
    }

    /*
     * MODIFIES: this
     * EFFECTS: actionListener for starButtons. sets a books star rating
     */
    public void starActionListener(ActionEvent e) {
        if (e.getActionCommand().equals(ONE)) {
            rating = Book.Rating.ONE_STAR;
        } else if (e.getActionCommand().equals(TWO)) {
            rating = Book.Rating.TWO_STARS;
        } else if (e.getActionCommand().equals(THREE)) {
            rating = Book.Rating.THREE_STARS;
        } else if (e.getActionCommand().equals(FOUR)) {
            rating = Book.Rating.FOUR_STARS;
        } else if (e.getActionCommand().equals(FIVE)) {
            rating = Book.Rating.FIVE_STARS;
        }
    }

    /*
     * MODIFIES: panel
     * EFFECTS: adds a submit button to panel
     */
    public JPanel getWithSubmitButton(JPanel panel, String cmd) {
        panel.add(submitButton(cmd),
                fitConstraints(8, 1, GridBagConstraints.PAGE_END));
        return panel;
    }

    /*
     * EFFECTS: creates a button which submits entered work
     */
    public JButton submitButton(String cmd) {
        JButton submitButton = new JButton("Submit");
        submitButton.setText("Submit");
        submitButton.setActionCommand(cmd);
        submitButton.addActionListener(this);
        submitButton.setPreferredSize(new Dimension(100, 50));

        return submitButton;
    }

    /*
     * MODIFIES: this
     * EFFECTS: ActionEvent for submitButton.
     *          adds created book to collection, sets loan status, rating, and review
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Submit")) {
            Music.playSound(Music.SOUND_ONE);
            Book book = makeNewBook();
            getModel().addElement(book);
            getStringModel().addElement(book.getTitle() + ": " + book.getAuthor());
            inputTitle.setText("");
            inputAuthor.setText("");

        } else if (e.getActionCommand().equals(EDIT_LOAN)) {
            midiSynth.play(6, 100, 50);
            Book b = getBookAtPos();
            b.setLoanStatus(isLoaned);

        } else if (e.getActionCommand().equals(EDIT_READ)) {
            Music.playSound(Music.SOUND_FOUR);
            Book b = getBookAtPos();
            String review = inputReview.getText();
            b.writeBookReview(review);
            b.setBookRating(rating);
            inputReview.setText("");
        }
    }

    /*
     * EFFECTS: returns the current book
     */
    public Book getBookAtPos() {
        Book bookAt = null;
        if (getModel().size() >= 1) {
            int pos = getModel().getSize() - 1;
            bookAt = getModel().get(pos);
        }
        return bookAt;
    }

    /*
     * getters
     */
    public boolean getIsLoaned() {
        return isLoaned;
    }

    public Book.Rating getRating() {
        return rating;
    }

    public JTextArea getInputReview() {
        return inputReview;
    }
}
