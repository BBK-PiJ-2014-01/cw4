/**
 * Created by PierreM on 03/02/2015.
 * Testing the implementation of Interface Contact
 */

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ContactTest {
    ContactImpl contact;

    @Before
    public void buildUp() {
        contact = new ContactImpl(100, "Pierre Meyer");
    }

    @Test
    public void tests_getID_returns_ContactID() {
        int expected = 100;
        assertEquals(expected, contact.getId());
    }

    @Test
    public void tests_getName_returns_ContactName() {
        String expected = "Mark Holt";
        assertEquals(expected, contact.getName());
    }

    @Test
    public void tests_getNotes_returns_NULL_ifNoNotes_returnsNotes_ifNotesAdded() {
        assertNull("Returned value should be Null",contact.getNotes());
        String expected = "He is the TTL CTO";
        contact.addNotes(expected);
        assertEquals("Expected and actual notes do not match", expected, contact.getNotes());
    }
}
