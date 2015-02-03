/**
 * Created by PierreM on 03/02/2015.
 */
public class ContactImpl {
    private int number;
    private String name;
    private String notes;

    public ContactImpl(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public int getID() {
        return(number);
    }

    public String getName() {
        return(name);
    }

    public String getNotes() {
        return(notes);
    }

    public void addNotes(String notes) {
        this.notes = notes;
    }

}
