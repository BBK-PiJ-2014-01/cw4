/**
 * Created by Pierre on 07/03/2015.
 */

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.GregorianCalendar;
import java.util.Set;

public class ContactManagerFutureMeetingsRelatedTest {

    ContactManagerImpl contactManager ;
    Contact contact1;
    Contact contact2;
    Contact contact3;
    Set<Contact> myContactSet;

    @Before
    public void buildUp() {
        contactManager = new ContactManagerImpl();
        MeetingImpl.setCounter(0);
        ContactImpl.setCounter(0);
        contact1 = new ContactImpl("Amelie Worth");
        contact2 = new ContactImpl("Brian Sprout");
        contact3 = new ContactImpl("Julie Miller");
        contactManager.addNewContact(contact1);
        contactManager.addNewContact(contact2);
        myContactSet = contactManager.getContacts(contact1.getId(),contact2.getId());
    }

    @Test
    public void tests_addFutureMeeting_IsAddedToMeetingSet() {
        int expected = 0;
        assertEquals("meetingSet should be empty",expected,contactManager.getMeetingSet().size());
        contactManager.addFutureMeeting(myContactSet, new GregorianCalendar(2015, 11, 20));
        expected++;
        assertEquals("meetingSet should have 1 element",expected,contactManager.getMeetingSet().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void tests_addFutureMeeting_ThrowsIllegalArgumentExceptionIfDateIsInThePast() {
        contactManager.addFutureMeeting(myContactSet, new GregorianCalendar(2015,01,30));
    }

    @Test(expected = IllegalArgumentException.class)
    public void tests_addFutureMeeting_ThrowsIllegalArgumentExceptionIfContactIsUnknown() {
        myContactSet.add(contact3);
        contactManager.addFutureMeeting(myContactSet, new GregorianCalendar(2015,12,20));
    }

    @Test
    public void tests_getFutureMeeting_ReturnsMeetingForRequestedValidId() {
        int requestedMeetingId = contactManager.addFutureMeeting(myContactSet, new GregorianCalendar(2015,07,01));
        int expectedMeetingId = requestedMeetingId;
        assertEquals("Returned meeting ID is not the one requested",expectedMeetingId,contactManager.getFutureMeeting(requestedMeetingId).getId());
        assertTrue("Meeting should have been found in meetingSet",contactManager.getMeetingSet().contains(contactManager.getFutureMeeting(requestedMeetingId)));
        assertTrue("Meeting should not be in the past",contactManager.getFutureMeeting(requestedMeetingId) instanceof FutureMeeting);
    }

    @Test
    public void tests_getFutureMeeting_ReturnsNullIfNoMeetingForRequestedId() {
        int requestedMeetingId = 1;
        int expectedMeetingId = requestedMeetingId;
        assertEquals("Null should have been returned",null,contactManager.getFutureMeeting(requestedMeetingId));
        assertFalse("Meeting should not have been found in meetingSet", contactManager.getMeetingSet().contains(contactManager.getFutureMeeting(requestedMeetingId)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void tests_getFutureMeeting_ThrowsIllegalArgumentExceptionIfMeetingIsInThePast() {
        //
    }
}
