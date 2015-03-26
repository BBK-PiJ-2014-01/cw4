/**
 * Created by Pierre on 14/03/2015.
 */

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

    @Test
    public void tests_flush_GeneratesCorrectFile() {
        File inputFile = new File("./TestSuite/Testdata-empty.txt");
        contactManager = new ContactManagerImpl(inputFile);

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

        File expectedFile = new File("./TestSuite/TestData-expectedIO.txt");
        File outputFile = new File("./contacts.txt");
        contactManager.flush();
        compareFiles(expectedFile, outputFile);
    }

    @Test
    public void tests_load_PullsCorrectlyAllDataOnFileIntoContactAndMeetingCollections() {
        File inputFile = new File("./src/loadcontacts.txt");
        File outputFile = new File("./src/contacts.txt");
        contactManager = new ContactManagerImpl(inputFile);
        contactManager.flush();
        compareFiles(outputFile, inputFile);
    }

    private void compareFiles(File file1, File file2) {
        BufferedReader file2reader = null;
        BufferedReader file1reader = null;
        try {
            file2reader = new BufferedReader(new FileReader(file2));
            file1reader = new BufferedReader(new FileReader(file1));
            String file2Line;
            String file1Line;
            while ((file2Line = file2reader.readLine()) != null) {
                file1Line = file1reader.readLine();
                assertEquals("Line is different",file1Line,file2Line);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            closeReader(file2reader);
            closeReader(file1reader);
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
