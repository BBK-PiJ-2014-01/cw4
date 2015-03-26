/**
 * Created by Pierre on 28/02/2015.
 */

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ContactManagerContactsRelatedTest {
    ContactManagerImpl contactManager ;

    @Before
    public void buildUp() {
        File inputFile = new File("./TestSuite/Testdata-empty.txt");
        contactManager = new ContactManagerImpl(inputFile);
        ContactImpl.writeConfig(0);
    }

    @Test
    public void tests_addNewContact_IsAddedToContactSet() {
        int expected = 0;
        assertEquals("contactSet should be empty",expected,contactManager.getContactSet().size());
        contactManager.addNewContact("Pierre Meyer", "Birkbeck student");
        expected++;
        assertEquals("contactSet should have 1 element",expected,contactManager.getContactSet().size());
    }

    @Test(expected = NullPointerException.class)
    public void tests_AddNewContact_ThrowsNullPointerExceptionIfNameIsNull() {
        contactManager.addNewContact(null, "Birkbeck student");
    }

    @Test(expected = NullPointerException.class)
    public void tests_AddNewContact_ThrowsNullPointerExceptionIfNotesAreNull() {
        contactManager.addNewContact("Pierre Meyer",null);
    }

    @Test
    public void tests_getContacts_ReturnsContactsCorrespondingToRequestedIDs() {
        Contact c1 = new ContactImpl("Joe Blogg", "Estate agent");
        Contact c2 = new ContactImpl("Keith Smith","Grocer");
        Contact c3 = new ContactImpl("Bob Builder","Dentist");
        Contact c4 = new ContactImpl("Dave Moon","Pharmacist");
        Contact c5 = new ContactImpl("Barry White","Singer");
        contactManager.addNewContact(c1);
        contactManager.addNewContact(c2);
        contactManager.addNewContact(c3);
        contactManager.addNewContact(c4);
        contactManager.addNewContact(c5);

        Set<Contact> expectedContactOutputList = new LinkedHashSet<Contact>();
        expectedContactOutputList.add(c1);
        expectedContactOutputList.add(c3);
        expectedContactOutputList.add(c4);

        assertEquals("Output list does not include required contacts",expectedContactOutputList,contactManager.getContacts(1, 3, 4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void tests_getContacts_ThrowsIllegalArgumentExceptionIfInvalidID() {
        contactManager.addNewContact("Pierre Meyer","Student");
        contactManager.getContacts(2);
    }

    @Test
    public void tests_getContacts_ReturnsContactsWhoseNameContainsRequiredString() {
        Contact c1 = new ContactImpl("Joe Blogger", "Estate agent");
        Contact c2 = new ContactImpl("Keith Blogg","Grocer");
        Contact c3 = new ContactImpl("Bob Builder","Dentist");
        Contact c4 = new ContactImpl("Dave Moon","Pharmacist");
        Contact c5 = new ContactImpl("Bloggy Bull","Singer");
        contactManager.addNewContact(c1);
        contactManager.addNewContact(c2);
        contactManager.addNewContact(c3);
        contactManager.addNewContact(c4);
        contactManager.addNewContact(c5);

        Set<Contact> expectedContactOutputList = new LinkedHashSet<Contact>();
        expectedContactOutputList.add(c1);
        expectedContactOutputList.add(c2);
        expectedContactOutputList.add(c5);

        assertEquals("Output list does not include required contacts",expectedContactOutputList,contactManager.getContacts("Blog"));
    }

    @Test(expected = NullPointerException.class)
    public void tests_getContacts_ThrowsNullPointerExceptionIfNullParameter() {
        contactManager.addNewContact("Pierre Meyer", "Student");
        contactManager.getContacts("");
    }
}
