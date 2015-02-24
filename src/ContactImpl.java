/**
 * Created by PierreM on 03/02/2015.
 * Implementation of the Interface Contact
 */
public class ContactImpl implements Contact{
    private int uniqueID;
    private String name;
    private String notes;

    public ContactImpl(int number, String name) {
        uniqueID = number;
        this.name = name;
    }

    @Override
    public int getId() {
        return(uniqueID);
    }

    @Override
    public String getName() {
        return(name);
    }

    @Override
    public String getNotes() {
        return(notes);
    }

    @Override
    public void addNotes(String notes) {
        this.notes = notes;
    }

}
