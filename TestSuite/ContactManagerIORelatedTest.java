/**
 * Created by Pierre on 14/03/2015.
 */

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ContactManagerIORelatedTest {
    ContactManagerImpl contactManager ;
    Contact contact1;
    Contact contact2;
    Contact contact3;
    Contact contact4;
    Set<Contact> myMeetingContactSet1;
    Set<Contact> myMeetingContactSet2;

    @Before
    public void buildUp() {
        contact1 = new ContactImpl("Joe Blogg", "Estate agent");
        contact2 = new ContactImpl("Keith Smith","Grocer");
        contact3 = new ContactImpl("Bob Builder","Dentist");
        contact4 = new ContactImpl("Dave Moon","Pharmacist");
        contactManager.addNewContact(contact1);
        contactManager.addNewContact(contact2);
        contactManager.addNewContact(contact3);
        contactManager.addNewContact(contact4);
        myMeetingContactSet1 = new LinkedHashSet<Contact>();
        myMeetingContactSet1.add(contact1);
        myMeetingContactSet1.add(contact3);
        myMeetingContactSet2 = new LinkedHashSet<Contact>();
        myMeetingContactSet2.add(contact2);
        myMeetingContactSet2.add(contact4);
        contactManager.addFutureMeeting(myMeetingContactSet1, new GregorianCalendar(2015,12,20));
        contactManager.addFutureMeeting(myMeetingContactSet2, new GregorianCalendar(2015, 10,25));
        //contactManager.addNewPastMeeting(myMeetingContactSet1, new GregorianCalendar(2014,8,8),"Notes");
    }

    @Test
    public void tests_flushGeneratesCorrectFile() {
        File expectedFile = new File("expectedcontacts.txt");
        File outputFile = new File("contacts.txt");
        contactManager.flush();
        assertEquals("Output file contacts.txt is not generated properly", expectedFile, outputFile);
    }

}
