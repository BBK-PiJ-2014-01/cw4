/**
 * Created by Pierre on 28/02/2015.
 */

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ContactManagerContactsRelatedTest {
    ContactManagerImpl contactManager ;

    @Before
    public void buildUp() {
        contactManager = new ContactManagerImpl();
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
}
