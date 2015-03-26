/**
 * Created by PierreM on 03/02/2015
 * 
 * Class ContactImpl (Implementation of the Interface Contact)
 * A contact is a person we are making business with or may do in the future
 * Contacts have unique IDs, a name, and notes that the user may want to save about them
 */

import java.io.*;

public class ContactImpl implements Contact{

    private int uniqueID;
    private String name;
    private String notes;

    /**
     * Constructor for the class ContactImpl
     * Generate uniqueIDs by incrementing a counter with the latest value saved on disk.
     *
     * @param name name of a new contact
     */
    public ContactImpl(String name) {
        initialise(name);
    }

    /**
     * Constructor for the class ContactImpl
     * Generate uniqueIDs by incrementing a counter with the latest value saved on disk.
     *
     * Notes can be added to the contact details.
     *
     * @param name name of a new contact
     * @param notes notes about the contact
     */
    public ContactImpl(String name, String notes) {
        initialise(name);
        addNotes(notes);
    }

    /**
     * Constructor for the class ContactImpl
     * Primarily used by the Load() method, when importing meeting records from the file contacts.txt
     * No meeting ID generated.
     *
     * @param id the unique identifier of the contact
     * @param name name of a new contact
     * @param notes notes about the contact
     */
    public ContactImpl(int id, String name, String notes) {
        uniqueID = id;
        this.name = name;
        this.notes = notes;
    }

    /**
     * Initialisation of all variables of a new instance of Class ContactImpl
     *
     * @param name name of the new contact
     */
    private void initialise(String name) {
        uniqueID = readConfig() + 1;
        this.name = name;
        notes = "";
        writeConfig(uniqueID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getId() {
        return(uniqueID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNotes() {
        return(notes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNotes(String newNotes) {
        if (notes.equals(""))
            notes += newNotes;
        else
            notes = notes + "\n" + newNotes;
    }

    /**
     * Reads the last contact Id generated and saved on disk (file contact.config)
     *
     * @return the last id allocated to a contact record. If no config file found, returns 0.
     */
    private int readConfig() {
        int number = 0;
        BufferedReader fileReader = null;
        File file = new File("./contact.config");
        try {
            fileReader = new BufferedReader(new FileReader(file));
            number = fileReader.read();
        } catch (FileNotFoundException ex) {
            return(number);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            closeReader(fileReader);
        }
        return(number);
    }

    /**
     * Writes on disk (file contact.config) the last contact Id generated
     *
     * @param id allocated to the last generated contact record.
     */
    public static void writeConfig(int id) {
        File file = new File("./contact.config");
        PrintWriter out = null;
        try {
            out = new PrintWriter(file);
            out.write(id);
        } catch (FileNotFoundException ex) {
            System.out.println("Cannot write to file " + file + ".");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            out.close();
        }
    }

    /**
     * Closes a reader in a try {} catch() statement
     *
     * @param reader Reader to be closed
     */
    private void closeReader(Reader reader) {
        try {
            if (reader != null)
                reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

