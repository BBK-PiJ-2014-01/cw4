/**
 * Created by Pierre on 14/03/2015.
 */

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

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
        contactManager = new ContactManagerImpl();
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
        contactManager.addFutureMeeting(myMeetingContactSet1, new GregorianCalendar(2015,12,20,15,30,20));
        contactManager.addFutureMeeting(myMeetingContactSet2, new GregorianCalendar(2015,12,25,12,26,47));
        contactManager.addNewPastMeeting(myMeetingContactSet1, new GregorianCalendar(2014,8,8,12,25,36),"Notes");
    }

    @Test
    public void tests_flushGeneratesCorrectFile() {
        File expectedFile = new File("./src/expectedcontacts.txt");
        File outputFile = new File("./src/contacts.txt");
        contactManager.flush();
        BufferedReader output = null;
        BufferedReader expected = null;
        try {
            output = new BufferedReader(new FileReader(outputFile));
            expected = new BufferedReader(new FileReader(expectedFile));
            String outputLine;
            String expectedLine;
            while ((outputLine = output.readLine()) != null) {
                expectedLine = expected.readLine();
                assertEquals("Line is different",expectedLine,outputLine);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            closeReader(output);
            closeReader(expected);
        }
    }

    private void closeReader(Reader reader) {
        try {
            if (reader != null)
                reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
