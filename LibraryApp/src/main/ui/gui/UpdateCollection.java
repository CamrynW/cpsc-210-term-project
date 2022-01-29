package ui.gui;

import model.Book;
import model.Collection;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.sound.MidiSynth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
 * https://stackoverflow.com/questions/39000561/jlist-not-updating-on-separate-pane-swing-java
 * Contains the entire collection
 */
public abstract class UpdateCollection {

    private JList<Book> list;
    private JList<String> bookStrings;
    private Collection collection;

    private static final String JSON_STORE = "./data/collection.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    protected MidiSynth midiSynth;
    private String name;
    private JComboBox comboBox;

    /*
     * Constructor
     * EFFECTS: creates a JList with listModel
     */
    public UpdateCollection(DefaultListModel<Book> listModel, DefaultListModel<String> bookString) {
        name = "my Collection";
        collection = new Collection(name);
        bookStrings = new JList<>(bookString);

        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        midiSynth = new MidiSynth();
        midiSynth.open();
    }

    /*
     * MODIFIES: collection, this
     * EFFECTS: sets collection name
     */
    public void setCollectionName(String name) {
        collection.setName(name);
        this.name = name;
    }

    /*
     * From JsonSerializationDemo. ui.WorkRoomApp.saveWorkRoom
     * EFFECTS: saves the collection of books to file
     */
    public void saveCollection() {
        try {
            collection = new Collection(collection.getName());
            for (int i = 0; i < getModel().size(); i++) {
                Book b = getModel().get(i);
                collection.addBook(b);
            }
            jsonWriter.open();
            jsonWriter.write(collection);
            jsonWriter.close();
            System.out.println("Saved " + getCollection().getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    /*
     * From JsonSerializationDemo. ui.WorkRoomApp.loadWorkRoom
     * EFFECTS: loads the collection of books from file
     */
    public void loadCollection() {
        try {
            collection = jsonReader.read();
            for (Book b: collection.getAllBooks()) {
                if (!getModel().contains(b)) {
                    getModel().addElement(b);
                    list = new JList<>(getModel());
                }
            }
            for (String st: collection.viewAllBooks()) {
                if (!getStringModel().contains(st)) {
                    getStringModel().addElement(st);
                    bookStrings = new JList<>(getStringModel());
                }
            }
            System.out.println("Loaded " + getCollection().getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    /*
     * EFFECTS: makes constraints for GridBagLayout
     */
    public GridBagConstraints fitConstraints(int padY, int gridX, int gridY) {
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.ipadx = 1;
        constraints.ipady = padY;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.gridx = gridX;
        constraints.gridy = gridY;

        return constraints;
    }

    /*
     * EFFECTS: creates a JPanel with a combo box
     */
    public JPanel comboBoxPanel(String[] comboBoxItems) {
        JPanel combo = new JPanel();
        comboBox = new JComboBox(comboBoxItems);
        comboBox.setEditable(false);
        combo.add(comboBox);
        return combo;
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets an item listener for comboBox
     */
    public void setComboBoxListener(ItemListener i) {
        comboBox.addItemListener(i);
    }

    /*
     * getters
     */
    public JList<Book> getList() {
        return list;
    }

    public Collection getCollection() {
        return collection;
    }

    public JList<String> getBookStrings() {
        return bookStrings;
    }

    public DefaultListModel<Book> getModel() {
        return (DefaultListModel<Book>) list.getModel();
    }

    public DefaultListModel<String> getStringModel() {
        return (DefaultListModel<String>) bookStrings.getModel();
    }
}
