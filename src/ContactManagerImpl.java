/**
 * Created by Pierre on 02/03/2015
 * Implementation of the Interface ContactManager
 */

import java.util.LinkedHashSet;
import java.util.Set;

public class ContactManagerImpl implements ContactManager {

    private Set contactSet;

    public ContactManagerImpl(){
        contactSet = new LinkedHashSet<ContactImpl>();
    }

    public Set getContactSet() {
        return(contactSet);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNewContact(String name, String notes) {
        if (name.equals(null)||notes.equals(null))
            throw new NullPointerException();
        else {
            Contact newContact = new ContactImpl(name, notes);
            contactSet.add(newContact);
        }
    }
}
