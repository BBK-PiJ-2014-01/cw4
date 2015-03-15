/**
 * Created by Pierre on 14/03/2015.
 */

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

public class ContactManagerMeetingsRelatedTest {

    ContactManagerImpl contactManager ;
    Contact contact1;
    Contact contact2;
    Set<Contact> myContactSet;

    @Before
    public void buildUp() {
        contactManager = new ContactManagerImpl();
        MeetingImpl.setCounter(0);
        ContactImpl.setCounter(0);
        contact1 = new ContactImpl("Amelie Worth");
        contact2 = new ContactImpl("Brian Sprout");
        contactManager.addNewContact(contact1);
        contactManager.addNewContact(contact2);
        myContactSet = contactManager.getContacts(contact1.getId(),contact2.getId());
        contactManager.addFutureMeeting(myContactSet, new GregorianCalendar(2015, 9, 12));
        contactManager.addNewPastMeeting(myContactSet, new GregorianCalendar(2012, 9, 12), "Notes1");
        contactManager.addFutureMeeting(myContactSet, new GregorianCalendar(2015, 10, 7));
        contactManager.addNewPastMeeting(myContactSet, new GregorianCalendar(2015, 9, 12), "Notes2");
        contactManager.addFutureMeeting(myContactSet, new GregorianCalendar(2015, 11, 20));
    }

    @Test
    public void tests_getMeeting_ReturnsNullForRequestedNonValidID() {
        int requestedID = 6;
        int expectedID = 6;
        assertEquals("Null should have been returned",null,contactManager.getMeeting(requestedID));
        assertFalse("Meeting should not have been found in meetingSet", contactManager.getMeetingList().contains(contactManager.getMeeting(requestedID)));
    }

    @Test
    public void tests_getMeeting_ReturnsMeetingForRequestedValidID() {
        int requestedID = 3;
        int expectedID = 3;
        assertEquals("Returned meeting ID is not the one requested",expectedID,contactManager.getMeeting(requestedID).getId());
        assertTrue("Meeting should have been found in meetingSet",contactManager.getMeetingList().contains(contactManager.getMeeting(requestedID)));
    }

    @Test
    public void tests_getFutureMeetingList_IsEmptyIfNoMeetingForSpecifiedDate() {
        assertTrue("List should be empty", contactManager.getFutureMeetingList(new GregorianCalendar(2017, 9, 12)).isEmpty());
    }

    @Test
    public void tests_getFutureMeetingList_ReturnsMeetingListForSpecifiedDate() {
        List<Meeting> expectedMeetingList = new ArrayList<Meeting>();
        expectedMeetingList.add(contactManager.getMeetingList().get(0));
        expectedMeetingList.add(contactManager.getMeetingList().get(3));
        assertEquals("Output list of meetings is not valid",expectedMeetingList,contactManager.getFutureMeetingList(new GregorianCalendar(2015, 9, 12)));
    }
}
