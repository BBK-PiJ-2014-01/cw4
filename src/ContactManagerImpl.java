/**
 * Created by Pierre on 02/03/2015
 * Implementation of the Interface ContactManager
 */

import java.util.LinkedHashSet;
import java.util.Set;

public class ContactManagerImpl implements ContactManager {

    private Set<Contact> contactSet;

    public ContactManagerImpl(){
        contactSet = new LinkedHashSet<Contact>();
    }

    public Set<Contact> getContactSet() {
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

    public void addNewContact(Contact newContact) {
        contactSet.add(newContact);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Contact> getContacts(int...ids) {
        Set<Contact> outputContactSet = new LinkedHashSet<Contact>();
        for (int id : ids) {
            boolean found = false;
            for (Contact contact : getContactSet()) {
                if (contact.getId() == id) {
                    outputContactSet.add(contact);
                    found = true;
                }
            }
            if (!found)
                throw new IllegalArgumentException();
        }
        return(outputContactSet);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Contact> getContacts(String name) {
        Set<Contact> outputContactSet = new LinkedHashSet<Contact>();
        if (name.equals(""))
            throw new NullPointerException();
        else
            for (Contact contact : getContactSet()) {
                if (contact.getName().contains(name))
                    outputContactSet.add(contact);
            }
        return(outputContactSet);
    }
}
