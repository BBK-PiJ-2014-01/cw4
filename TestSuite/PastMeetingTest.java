/**
 * Created by Pierre on 27/02/2015.
 * Testing the implementation of Interface PastMeeting
 */

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Set;

public class PastMeetingTest {
    PastMeeting meeting1;
    PastMeeting meeting2;
    Set contactSet;
    Contact contact;

    @Before
    public void buildUp() {
        contactSet = new LinkedHashSet<ContactImpl>();
        contact = new ContactImpl("Pierre Meyer");
        contactSet.add(contact);
    }

    @Test
    public void tests_getNotes_ReturnsEmptyStringWhenNoNotes() {
        meeting1 = new PastMeetingImpl(contactSet, new GregorianCalendar(2015,02,28),"");
        String expected = "";
        assertEquals("Returned string should be empty",expected, meeting1.getNotes());
    }

    @Test
    public void tests_getNotes_ReturnsNotesWhenExisting() {
        meeting2 = new PastMeetingImpl(contactSet, new GregorianCalendar(2015,02,28),"That was a very effective meeting");
        String expected = "That was a very effective meeting";
        assertEquals(expected, meeting2.getNotes());
    }
}
