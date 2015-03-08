/**
 * Created by Pierre on 02/03/2015
 * Implementation of the Interface ContactManager
 */

import java.util.Calendar;
import java.util.IllegalFormatException;
import java.util.LinkedHashSet;
import java.util.Set;

public class ContactManagerImpl implements ContactManager {

    private Set<Contact> contactSet;
    private Set<Meeting> meetingSet;

    public ContactManagerImpl(){
        contactSet = new LinkedHashSet<Contact>();
        meetingSet = new LinkedHashSet<Meeting>();
    }

    public Set<Contact> getContactSet() {
        return(contactSet);
    }

    public Set<Meeting> getMeetingSet() {
        return(meetingSet);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int addFutureMeeting(Set<Contact> contacts, Calendar date) throws IllegalArgumentException {
        int newMeetingId = 0;
        Calendar rightNow = Calendar.getInstance();
        if (date.before(rightNow))
            throw new IllegalArgumentException();
        else {
            for (Contact meetingContact : contacts) {
                if (!foundContact(meetingContact)) {
                    throw new IllegalArgumentException();
                }
                FutureMeeting meeting = new FutureMeetingImpl(contacts, date);
                newMeetingId = meeting.getId();
                meetingSet.add(meeting);
            }
        }
        return(newMeetingId);
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

    private boolean foundContact(Contact myContact) {
        boolean found = false;
        for (Contact contact : getContactSet()) {
            if (contact.getId() == myContact.getId()) {
                found = true;
                break;
            }
        }
        return(found);
    }
}
