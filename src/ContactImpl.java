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
    }

    public ContactImpl(String name, String notes) {
        initialise(name);
        addNotes(notes);
    }

    public ContactImpl(int id, String name, String notes) {
        uniqueID = id;
        this.name = name;
        this.notes = notes;
    }

    private void initialise(String name) {
        counter++;
        uniqueID = counter;
        this.name = name;
        notes = "";
    }

    public static void setCounter(int number) {
        counter = number;
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
