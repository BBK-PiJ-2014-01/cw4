/**
 * Created by PierreM on 02/02/2015.
 */
public interface ContactManager {

    /**
     * Create a new contact with the specified name and notes.
     *
     * @param name the name of the contact.
     * @param notes notes to be added about the contact.
     * @throws NullPointerException if the name or the notes are null
     */
    void addNewContact(String name, String notes);
    
}
