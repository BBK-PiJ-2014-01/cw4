/**
 * Created by Pierre on 02/03/2015
 * Implementation of the Interface ContactManager
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class ContactManagerImpl implements ContactManager {

    private Set<Contact> contactSet;
    private List<Meeting> meetingList;

    /**
     * Constructor for the class ContactManagerImpl
     * Loads in memory all previously saved contact and meeting details, from the file contacts.txt
     */
    public ContactManagerImpl(){
        contactSet = new LinkedHashSet<Contact>();
        meetingList = new ArrayList<Meeting>();
        File inputFile = new File("./contacts.txt");
        load(inputFile);
    }

    /**
     * Constructor for the class ContactManagerImpl
     * Loads in memory contact and meeting details from a specified file
     * Primarily used to load test data.
     *
     * @param inputFile name of the file to restore contact and meeting details from
     */
    public ContactManagerImpl(File inputFile){
        contactSet = new LinkedHashSet<Contact>();
        meetingList = new ArrayList<Meeting>();
        load(inputFile);
    }

    /**
     * Returns the set of contacts of the ContactManager
     *
     * @return the set of contacts of the ContactManager
     */
    public Set<Contact> getContactSet() {
        return(contactSet);
    }

    /**
     * Returns the list of meetings of the ContactManager
     *
     * @return the list of meetings of the ContactManager
     */
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
        Set<FutureMeetingImpl> tempSet = new HashSet<FutureMeetingImpl>();
        if (!foundContact(getContactSet(), contact))
            throw new IllegalArgumentException();
        else {
            for(Meeting meeting : getMeetingList()) {
                if (meeting instanceof FutureMeeting) {
                    if (foundContact(meeting.getContacts(), contact))
                           tempSet.add((FutureMeetingImpl)meeting);
                }
            }
        }
        List<FutureMeetingImpl> outputMeetingList = new ArrayList<FutureMeetingImpl>(tempSet);
        Collections.sort(outputMeetingList);
        if(outputMeetingList.isEmpty())
            return(null);
        else
            return(new ArrayList<Meeting>(outputMeetingList));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> getFutureMeetingList(Calendar date) {
        Set<Meeting> tempSet = new HashSet<Meeting>();
        for(Meeting meeting : getMeetingList()) {
            if(meeting.getDate().equals(date)) {
                tempSet.add(meeting);
            }
        }
        List<Meeting> outputMeetingList = new ArrayList<Meeting>(tempSet);
        return(outputMeetingList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PastMeeting> getPastMeetingList(Contact contact) {
        Set<PastMeetingImpl> tempSet = new HashSet<PastMeetingImpl>();
        if (!foundContact(getContactSet(), contact))
            throw new IllegalArgumentException();
        else {
            for(Meeting meeting : getMeetingList()) {
                if (meeting instanceof PastMeeting) {
                    if (foundContact(meeting.getContacts(), contact))
                        tempSet.add((PastMeetingImpl) meeting);
                }
            }
        }
        List<PastMeetingImpl> outputMeetingList = new ArrayList<PastMeetingImpl>(tempSet);
        Collections.sort(outputMeetingList);
        if (outputMeetingList.isEmpty())
            return(null);
        else
            return(new ArrayList<PastMeeting>(outputMeetingList));
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
    public void addMeetingNotes(int id, String text) throws IllegalArgumentException, NullPointerException {
        Calendar rightNow = GregorianCalendar.getInstance();
        PastMeetingImpl meeting = null;
        Meeting returnedMeeting = getMeeting(id);
        if(returnedMeeting == null)
            throw new IllegalArgumentException();
        if(text.equals(null))
            throw new NullPointerException();
        if (returnedMeeting.getDate().after(rightNow))
            throw new IllegalStateException();
        if (returnedMeeting instanceof PastMeeting){
            meeting = (PastMeetingImpl) returnedMeeting;
            meeting.addNotes(text);
        } else
            if (returnedMeeting instanceof FutureMeeting) {
                PastMeeting convertedMeeting = futureToPast((FutureMeeting) returnedMeeting);
                meeting = (PastMeetingImpl) convertedMeeting;
                meeting.addNotes(text);
                flush();
            } else
                System.out.println("Meeting object has an unknown class type");
    }

    /**
     * Converts a meeting from type FutureMeeting to PastMeeting, when the date is set in the past
     *
     * @return converted meeting with type PastMeeting
     */
    private PastMeeting futureToPast(FutureMeeting meeting) {
        PastMeeting outputMeeting = new PastMeetingImpl(meeting.getId(),meeting.getContacts(),meeting.getDate(),"");
        int index = getMeetingList().indexOf(meeting);
        getMeetingList().remove(index);
        getMeetingList().add(outputMeeting);
        return(outputMeeting);
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

    /**
     * Adds a contact to the ContactManager
     *
     * @param newContact contact to add to the ContactManager
     */
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

    /**
     * Searches and returns if a contact is part of a specified set of contacts
     *
     * @return flag indicating if the contact has been found or not ('true' if found, 'false' in not found)
     */
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
        File outputFile = new File("./contacts.txt");
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
                    PastMeetingImpl tempMeeting = (PastMeetingImpl) meeting;
                    pastMeetingElement.addContent(new Element("notes").setText("" + tempMeeting.getNotes()));
                    document.getRootElement().addContent(pastMeetingElement);
                }
            }

            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(document, new FileOutputStream(outputFile));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads in memory all contact and meeting details from a specified file, to the ContactManager
     * using the JDOM third party parser
     *
     * @param inputFile source file the data is loaded from
     */
    private void load(File inputFile) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(inputFile);
            Element rootElement = document.getRootElement();

            //Retrieving Contact details
            List<Element> contactList = rootElement.getChildren("Contact");
            contactSet = retrieveContact(contactList);
            //Retrieving FutureMeeting details
            List<Element> futureMeetingList = rootElement.getChildren("FutureMeeting");
            for (int i = 0; i < futureMeetingList.size(); i++) {
                Element futureMeeting = futureMeetingList.get(i);
                int futureMeetingId = Integer.parseInt(futureMeeting.getChild("uniqueID").getText());
                Calendar futureMeetingDate = retrieveDate(futureMeeting.getChild("date").getText());
                List<Element> futureMeetingContactList = futureMeetingList.get(i).getChildren("Contact");
                Set<Contact> futureMeetingContactSet = retrieveContact(futureMeetingContactList);
                FutureMeeting newMeeting = new FutureMeetingImpl(futureMeetingId, futureMeetingContactSet, futureMeetingDate);
                meetingList.add(newMeeting);
            }
            //Retrieving PastMeeting details
            List<Element> pastMeetingList = rootElement.getChildren("PastMeeting");
            for (int i = 0; i < pastMeetingList.size(); i++) {
                Element pastMeeting = pastMeetingList.get(i);
                int pastMeetingId = Integer.parseInt(pastMeeting.getChild("uniqueID").getText());
                Calendar pastMeetingDate = retrieveDate(pastMeeting.getChild("date").getText());
                List<Element> pastMeetingContactList = pastMeetingList.get(i).getChildren("Contact");
                Set<Contact> pastMeetingContactSet = retrieveContact(pastMeetingContactList);
                String pastMeetingNotes = pastMeeting.getChild("notes").getText();
                PastMeeting newMeeting = new PastMeetingImpl(pastMeetingId, pastMeetingContactSet, pastMeetingDate, pastMeetingNotes);
                meetingList.add(newMeeting);
            }
        } catch (IOException e) {
            System.out.println("IOException");
        } catch (JDOMException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Converts a string with the format 'yyyy-MM-dd HH:mm:ss' into a date with type Calendar
     *
     * @return a date with type Calendar
     */
    private Calendar retrieveDate(String text) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar outputDate = Calendar.getInstance();
        try {
            Date parsedDate = dateFormat.parse(text);
            outputDate.setTime(parsedDate);
        } catch(ParseException ex) {
            ex.printStackTrace();
        }
        return(outputDate);
    }

    /**
     * Returns a set of contacts, based on a list of JDOM created elements
     *
     * @return a set of contacts
     */
    private Set<Contact> retrieveContact(List<Element> contactElementList) {
        Set<Contact> outputContactSet = new LinkedHashSet<Contact>();
        for (int i = 0; i < contactElementList.size(); i++) {
            Element contactElement = contactElementList.get(i);
            int contactId = Integer.parseInt(contactElement.getChild("uniqueID").getText());
            Contact contact = new ContactImpl(contactId, contactElement.getChild("name").getText(), contactElement.getChild("notes").getText());
            outputContactSet.add(contact);
        }
        return(outputContactSet);
    }
}
