/**
 * Created by Pierre on 14/03/2015.
 */

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.GregorianCalendar;
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
        contactManager.addFutureMeeting(myContactSet, new GregorianCalendar(2015, 10, 7));
        contactManager.addFutureMeeting(myContactSet, new GregorianCalendar(2015, 11, 20));
    }

    @Test
    public void tests_getMeeting_ReturnsNullForRequestedNonValidID() {
        int requestedID = 4;
        int expectedID = 4;
        assertEquals("Null should have been returned",null,contactManager.getMeeting(requestedID));
        assertFalse("Meeting should not have been found in meetingSet", contactManager.getMeetingSet().contains(contactManager.getMeeting(requestedID)));
    }

    @Test
    public void tests_getMeeting_ReturnsMeetingForRequestedValidID() {
        int requestedID = 3;
        int expectedID = 3;
        assertEquals("Returned meeting ID is not the one requested",expectedID,contactManager.getMeeting(requestedID).getId());
        assertTrue("Meeting should have been found in meetingSet",contactManager.getMeetingSet().contains(contactManager.getMeeting(requestedID)));
    }

}
