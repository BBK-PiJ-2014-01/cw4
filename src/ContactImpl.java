/**
 * Created by PierreM on 03/02/2015.
 * Implementation of the Interface Contact
 */
public class ContactImpl implements Contact{
    private int number;
    private String name;
    private String notes;

    public ContactImpl(int number, String name) {
        this.number = number;
        this.name = name;
    }

    @Override
    public int getId() {
        return(number);
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
