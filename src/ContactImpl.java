/**
 * Created by PierreM on 03/02/2015
 * Implementation of the Interface Contact
 */

public class ContactImpl implements Contact{

    private static int counter = 0;

    private int uniqueID;
    private String name;
    private String notes;

    public ContactImpl(String name) {
        initialise(name);
        notes = "";
    }

    public ContactImpl(String name, String notes) {
        initialise(name);
        addNotes(notes);
    }

    private void initialise(String name) {
        counter++;
        uniqueID = counter;
        this.name = name;
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

}
