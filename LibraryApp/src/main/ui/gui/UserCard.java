package ui.gui;

import model.Book;
import ui.sound.Music;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Represents everything needed for a user profile
 */
public class UserCard extends UpdateCollection implements ActionListener {

    private static final String SAVE = "Save";
    private static final String LOAD = "Load";
    private JTextField fillInName;
    private int size;
    private JLabel sizeLabel;

    /*
     * Constructor
     * EFFECTS: instantiates a card which shows the collection name, size,
     *          and allows the user to save and load their collection
     */
    public UserCard(DefaultListModel<Book> listModel, DefaultListModel<String> bookString) {
        super(listModel, bookString);
        size = 0;
        sizeLabel = new JLabel("Number of books currently in your collection: " + size);
        fillInName = new JTextField(getCollection().getName());
        fillInName.setColumns(15);
    }

    /*
     * EFFECTS: returns a JPanel which allows the user to view user information
     */
    public JPanel addUserCard() {
        JPanel card = new JPanel(new GridBagLayout());
        JLabel nameLabel = new JLabel("Collection name: ");
        JPanel buttons = new JPanel();
        buttons.add(button(SAVE));
        buttons.add(button(LOAD));

        card.add(nameLabel, fitConstraints(0, 0, 1));
        card.add(fillInName, fitConstraints(0, 1, 1));
        card.add(sizeLabel, fitConstraints(0, 0, 2));
        card.add(buttons, fitConstraints(0, 0, 3));

        JPanel userCard = new JPanel();
        userCard.add(card);

        return userCard;
    }

    /*
     * EFFECTS: creates a button
     */
    public JPanel button(String cmd) {
        JPanel savePanel = new JPanel();
        JButton save = new JButton(cmd);
        save.setText(cmd);
        save.setActionCommand(cmd);
        save.addActionListener(this);
        save.setPreferredSize(new Dimension(100, 50));

        savePanel.add(save);

        return savePanel;
    }

    /*
     * EFFECTS: ActionEvent for saveButton. Saves the collection
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(SAVE)) {
            Music.playSound(Music.SOUND_TWO);
            setName(fillInName.getText());
            setSize();
            saveCollection();
        } else if (e.getActionCommand().equals(LOAD)) {
            Music.playSound(Music.SOUND_THREE);
            loadCollection();
            setName(getCollection().getName());
            setSize();
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets collection name to string from fillInName
     */
    private void setName(String name) {
        setCollectionName(name);
        fillInName.setText(name);
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets collection name to string from fillInName
     */
    private void setSize() {
        size = getModel().getSize();
        sizeLabel.setText("Number of books currently in your collection: " + size);
    }
}
