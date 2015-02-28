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
    PastMeetingImpl meeting;
    Set contactSet;
    Contact contact;

    @Before
    public void buildUp() {
        contactSet = new LinkedHashSet<ContactImpl>();
        contact = new ContactImpl(7, "Pierre Meyer");
        contactSet.add(contact);
        meeting = new PastMeetingImpl(1,new GregorianCalendar(2015,02,28),contactSet,"That was a very effective meeting");
    }

    @Test
    public void tests_getNotes_ReturnsNullWhenNoNotes() {
        String expected = null;
        assertNull("The return value should be NULL",meeting.getNotes());
    }

    @Test
    public void tests_getNotes_ReturnsNotesWhenExisting() {
        String expected = "That was a very effective meeting";
        assertEquals(expected, meeting.getNotes());
    }
}
