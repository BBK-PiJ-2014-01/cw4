/**
 * Created by PierreM on 18/02/2015
 * Testing the implementation of Interface Meeting
 */

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MeetingTest {
    MeetingImpl meeting;
    Set contactSet;
    Contact contact;

    @Before
    public void buildUp() {
        contactSet = new LinkedHashSet<ContactImpl>();
        contact = new ContactImpl("Pierre Meyer");
        contactSet.add(contact);
        meeting = new MeetingImpl(1,new GregorianCalendar(2015,02,28),contactSet);
    }

    @Test
    public void tests_getID_ReturnsMeetingID() {
        int expected = 1;
        assertEquals(expected, meeting.getId());
    }

    @Test
    public void tests_getDate_ReturnsMeetingDate() {
        Calendar expected = new GregorianCalendar(2015,02,28);
        assertEquals(expected, meeting.getDate());
    }

    @Test
    public void tests_getContacts_ReturnsDetailsOfPeopleAttendingMeeting() {
        Set expected = contactSet;
        assertEquals(expected, meeting.getContacts());
    }
}