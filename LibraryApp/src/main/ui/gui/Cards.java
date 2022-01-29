package ui.gui;

import model.Book;
import ui.sound.MidiSynth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Represents a JFrame which holds the entire app
 */
public class Cards extends JFrame implements ActionListener {

    protected static final int WIDTH = 100;
    protected static final int HEIGHT = 350;
    private static final String ADD = "Add";
    private static final String EDIT = "Edit";
    private static final String SEARCH = "Search";
    private static final String VIEW = "View";
    private static final String USER = "User";
    private JPanel cards;
    private JButton add;
    private JButton edit;
    private JButton search;
    private JButton view;
    private JButton user;
    private AddBookCard makeBook;
    private EditCard editCard;
    private SearchCard searchCard;
    private ViewCard viewCard;
    private UserCard userCard;
    private final DefaultListModel<Book> listModel = new DefaultListModel<>();
    private final DefaultListModel<String> bookModel = new DefaultListModel<>();
    private MidiSynth midiSynth;

    /*
     * Constructor
     * MODIFIES: this
     * EFFECTS: adds Components to pane
     */
    public Cards() {
        instantiate();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentToPane(this.getContentPane());
        this.pack();
        this.setVisible(true);
    }

    /*
     * MODIFIES: this
     * EFFECTS: instantiates fields
     */
    private void instantiate() {
        makeBook = new AddBookCard(listModel, bookModel);
        editCard = new EditCard(listModel, bookModel);
        searchCard = new SearchCard(listModel, bookModel);
        viewCard = new ViewCard(listModel, bookModel);
        userCard = new UserCard(listModel, bookModel);

        midiSynth = new MidiSynth();
        midiSynth.open();
    }

    /*
     * From CardLayoutDemoProject
     * MODIFIES: pane
     * EFFECTS: sets up multiple JPanels and toolBar
     */
    public void addComponentToPane(Container pane) {
        cards = new JPanel(new CardLayout());
        JToolBar toolBar = makeToolBar();
        JTabbedPane addBookTabs = new JTabbedPane();
        addBookTabs.add("Tab with Title", makeBook.addBookCard());
        addBookTabs.add("Tab with Review",
                makeBook.getWithSubmitButton(makeBook.ratingReviewCard(),
                        AddBookCard.EDIT_READ));
        addBookTabs.add("Tab with Loan Status",
                makeBook.getWithSubmitButton(makeBook.loanCard(), AddBookCard.EDIT_LOAN));

        cards.add(addBookTabs, add.getActionCommand());
        cards.add(editCard.addEditCard(), edit.getActionCommand());
        cards.add(searchCard.addSearchCard(), search.getActionCommand());
        cards.add(viewCard.addViewCard(), view.getActionCommand());
        cards.add(userCard.addUserCard(), user.getActionCommand());

        pane.add(toolBar, BorderLayout.PAGE_END);
        pane.add(cards, BorderLayout.CENTER);
    }

    /*
     * EFFECTS: creates a new ToolBar
     */
    public JToolBar makeToolBar() {
        JToolBar toolBar = new JToolBar("Tool Bar");
        toolBar.setPreferredSize(new Dimension(WIDTH, HEIGHT / 5));
        addButtons(toolBar);
        return toolBar;
    }

    /*
     * MODIFIES: toolBar
     * EFFECTS: adds buttons to toolbar
     */
    protected void addButtons(JToolBar toolBar) {
        add = makeButton("book", ADD);
        toolBar.add(add);
        edit = makeButton("edit", EDIT);
        toolBar.add(edit);
        search = makeButton("search", SEARCH);
        toolBar.add(search);
        view = makeButton("view", VIEW);
        toolBar.add(view);
        user = makeButton("user", USER);
        toolBar.add(user);
    }

    /*
     * From ToolBarDemo. ToolBarDemo.makeNavigationButton
     * EFFECTS: creates a new button with an image and a string command
     */
    protected JButton makeButton(String imageName, String cmd) {
        Dimension buttonSize = new Dimension(WIDTH / 5, 60);
        String imageLocation = "./data/images/" + imageName + ".png";

        JButton button = new JButton();
        button.setActionCommand(cmd);
        button.addActionListener(this);
        button.setPreferredSize(buttonSize);

        if (imageLocation != null) {
            ImageIcon icon = new ImageIcon(imageLocation, cmd);
            button.setIcon(icon);
            button.setMargin(new Insets(100, 100, 100, 100));
        } else {
            button.setText(cmd);
            System.err.println("Resource not found: "
                    + imageLocation);
        }
        return button;
    }

    /*
     * EFFECTS: shows a JPanel in cards based on the actionCommand
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, e.getActionCommand());
    }
}
