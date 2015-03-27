/**
 * Created by PierreM on 03/02/2015.
 * Testing the implementation of Interface Contact
 */

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ContactTest {
    Contact contact;

    @Before
    public void buildUp() {
        ContactImpl.writeConfig(0);
        contact = new ContactImpl("Pierre Meyer");
    }

    @Test
    public void tests_getID_ReturnsUniqueContactID() {
        int expected = 1;
        assertEquals("Returned Contact ID is not correct", expected, contact.getId());
        // Adding a new contact to test the new ID is incremented by 1
        contact = new ContactImpl("Rob Smith");
        expected++;
        assertEquals("Returned Contact ID is not correct", expected, contact.getId());
    }

    @Test
    public void tests_getName_ReturnsContactName() {
        String expected = "Pierre Meyer";
        assertEquals("Returned Contact Name is not correct", expected, contact.getName());
    }

    @Test
    public void tests_getNotes_ReturnsEmptyStringWhenNoNotesAdded() {
        String expected = "";
        assertEquals("Returned string should be empty",expected, contact.getNotes());
    }

    @Test
    public void tests_getNotes_ReturnsNotesIfNotesAdded() {
        String expected = "Birkbeck Student";
        contact.addNotes(expected);
        assertEquals("Expected and actual notes do not match", expected, contact.getNotes());
        String newNotes = "Start in September 2014";
        contact.addNotes(newNotes);
        expected = expected + "\n" + newNotes;
        assertEquals("Expected and actual notes do not match", expected, contact.getNotes());
    }
}
