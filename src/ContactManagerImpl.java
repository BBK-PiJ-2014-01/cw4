/**
 * Created by Pierre on 02/03/2015
 * Implementation of the Interface ContactManager
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Future;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class ContactManagerImpl implements ContactManager {

    private Set<Contact> contactSet;
    private List<Meeting> meetingList;

    public ContactManagerImpl(){
        contactSet = new LinkedHashSet<Contact>();
        meetingList = new ArrayList<Meeting>();
    }

    public Set<Contact> getContactSet() {
        return(contactSet);
    }

    public List<Meeting> getMeetingList() {
        return(meetingList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int addFutureMeeting(Set<Contact> contacts, Calendar date) throws IllegalArgumentException {
        int newMeetingId = 0;
        Calendar rightNow = GregorianCalendar.getInstance();
        if (date.before(rightNow))
            throw new IllegalArgumentException();
        else {
            for (Contact meetingContact : contacts) {
                if (!foundContact(getContactSet(), meetingContact))
                    throw new IllegalArgumentException();
            }
            FutureMeeting meeting = new FutureMeetingImpl(contacts, date);
            newMeetingId = meeting.getId();
            meetingList.add(meeting);
        }
        return(newMeetingId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PastMeeting getPastMeeting(int id) throws IllegalArgumentException {
        PastMeeting requestedMeeting = null;
        Meeting meeting = getMeeting(id);
        if (meeting instanceof PastMeeting)
            requestedMeeting = (PastMeeting) meeting;
        else
            if (meeting != null)
                throw new IllegalArgumentException();
        return(requestedMeeting);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FutureMeeting getFutureMeeting(int id) throws IllegalArgumentException {
        FutureMeeting requestedMeeting = null;
        Meeting meeting = getMeeting(id);
        if (meeting instanceof FutureMeeting)
            requestedMeeting = (FutureMeeting) meeting;
        else
            if (meeting != null)
                throw new IllegalArgumentException();
        return(requestedMeeting);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Meeting getMeeting(int id) {
        Meeting requestedMeeting = null;
        for (Meeting meeting : getMeetingList()) {
            if (meeting.getId() == id) {
                requestedMeeting = meeting;
                break;
            }
        }
        return(requestedMeeting);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> getFutureMeetingList(Contact contact) {
        List<Meeting> outputMeetingList = new ArrayList<Meeting>();
        if (!foundContact(getContactSet(), contact))
            throw new IllegalArgumentException();
        else {
            for(Meeting meeting : getMeetingList()) {
                if (meeting instanceof FutureMeeting) {
                    if (foundContact(meeting.getContacts(), contact))
                           outputMeetingList.add(meeting);
                }
            }
        }
        //Collections.sort(outputMeetingList);
        return(outputMeetingList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PastMeeting> getPastMeetingList(Contact contact) {
        List<PastMeeting> outputMeetingList = new ArrayList<PastMeeting>();
        if (!foundContact(getContactSet(), contact))
            throw new IllegalArgumentException();
        else {
            for(Meeting meeting : getMeetingList()) {
                if (meeting instanceof PastMeeting) {
                    if (foundContact(meeting.getContacts(), contact))
                        outputMeetingList.add((PastMeeting) meeting);
                }
            }
        }
        //Collections.sort(outputMeetingList);
        return(outputMeetingList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) throws IllegalArgumentException, NullPointerException {
        if((contacts.equals(null))||(date.equals(null))||(text.equals(null)))
            throw new NullPointerException();
        else
            if (contacts.isEmpty())
                throw new IllegalArgumentException();
            else
                for (Contact meetingContact : contacts)
                    if (!foundContact(getContactSet(), meetingContact))
                        throw new IllegalArgumentException();
        PastMeeting meeting = new PastMeetingImpl(contacts, date, text);
        meetingList.add(meeting);
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

    private boolean foundContact(Set<Contact> contactSet, Contact myContact) {
        boolean found = false;
        for (Contact contact : contactSet) {
            if (contact.getId() == myContact.getId()) {
                found = true;
                break;
            }
        }
        return(found);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void flush() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        File outputFile = new File("./src/contacts.txt");
        Document document = null;

        try {
            document = new Document();
            document.setRootElement(new Element("ContactManagerData"));

            for (Contact contact : contactSet) {
                Element contactElement = new Element("Contact");
                contactElement.addContent(new Element("uniqueID").setText("" + contact.getId()));
                contactElement.addContent(new Element("name").setText(contact.getName()));
                contactElement.addContent(new Element("notes").setText(contact.getNotes()));
                document.getRootElement().addContent(contactElement);
            }
            for (Meeting meeting : meetingList) {
                if (meeting instanceof FutureMeeting) {
                    Element futureMeetingElement = new Element("FutureMeeting");
                    futureMeetingElement.addContent(new Element("uniqueID").setText("" + meeting.getId()));
                    futureMeetingElement.addContent(new Element("date").setText("" + dateFormat.format(meeting.getDate().getTime())));
                    for (Contact contact : meeting.getContacts()) {
                        Element contactElement = new Element("Contact");
                        futureMeetingElement.addContent(contactElement);
                        contactElement.addContent(new Element("uniqueID").setText("" + contact.getId()));
                        contactElement.addContent(new Element("name").setText(contact.getName()));
                        contactElement.addContent(new Element("notes").setText(contact.getNotes()));

                    }
                    document.getRootElement().addContent(futureMeetingElement);
                }
                if (meeting instanceof PastMeeting) {
                    Element pastMeetingElement = new Element("PastMeeting");
                    pastMeetingElement.addContent(new Element("uniqueID").setText("" + meeting.getId()));
                    pastMeetingElement.addContent(new Element("date").setText("" + dateFormat.format(meeting.getDate().getTime())));
                    for (Contact contact : meeting.getContacts()) {
                        Element contactElement = new Element("Contact");
                        pastMeetingElement.addContent(contactElement);
                        contactElement.addContent(new Element("uniqueID").setText("" + contact.getId()));
                        contactElement.addContent(new Element("name").setText(contact.getName()));
                        contactElement.addContent(new Element("notes").setText(contact.getNotes()));

                    }
                    document.getRootElement().addContent(pastMeetingElement);
                }
            }

            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(document, new FileOutputStream(outputFile));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
