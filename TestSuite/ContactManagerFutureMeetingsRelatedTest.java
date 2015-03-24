/**
 * Created by Pierre on 07/03/2015.
 */

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.util.*;

public class ContactManagerFutureMeetingsRelatedTest {

    ContactManagerImpl contactManager ;
    Contact contact1;
    Contact contact2;
    Contact contact3;
    Set<Contact> myContactSet;

    @Before
    public void buildUp() {
        File inputFile = new File("./src/empty.txt");
        contactManager = new ContactManagerImpl(inputFile);
        //MeetingImpl.setCounter(0);
        //ContactImpl.setCounter(0);
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
        assertEquals("meetingSet should be empty",expected,contactManager.getMeetingList().size());
        contactManager.addFutureMeeting(myContactSet, new GregorianCalendar(2015, 11, 20));
        expected++;
        assertEquals("meetingSet should have 1 element",expected,contactManager.getMeetingList().size());
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
        assertTrue("Meeting should have been found in meetingSet",contactManager.getMeetingList().contains(contactManager.getFutureMeeting(requestedMeetingId)));
        assertTrue("Meeting should not be in the past",contactManager.getFutureMeeting(requestedMeetingId) instanceof FutureMeeting);
    }

    @Test
    public void tests_getFutureMeeting_ReturnsNullIfNoMeetingForRequestedId() {
        int requestedMeetingId = 1000;
        int expectedMeetingId = requestedMeetingId;
        assertEquals("Null should have been returned",null,contactManager.getFutureMeeting(requestedMeetingId));
        assertFalse("Meeting should not have been found in meetingSet", contactManager.getMeetingList().contains(contactManager.getFutureMeeting(requestedMeetingId)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void tests_getFutureMeeting_ThrowsIllegalArgumentExceptionIfMeetingIsInThePast() {
        contactManager.addNewPastMeeting(myContactSet, new GregorianCalendar(2012, 07, 01), "Awesome meeting");
        contactManager.getFutureMeeting(1);
    }

    @Test
    public void tests_getFutureMeetingList_ReturnsNullIfNoMeetingScheduledWithContact() {
        Set<Contact> contactSet1 = new LinkedHashSet<Contact>();
        Set<Contact> contactSet2 = new LinkedHashSet<Contact>();
        contactSet1.add(contact1);
        contactSet2.add(contact2);

        contactManager.addFutureMeeting(contactSet2, new GregorianCalendar(2016,7,1,18,12,15));
        contactManager.addFutureMeeting(contactSet2, new GregorianCalendar(2016,7,1,5,26,45));
        contactManager.addFutureMeeting(contactSet2, new GregorianCalendar(2016,6,1));

        List<Meeting> outputMeetingList = contactManager.getFutureMeetingList(contact1);
        assertEquals("Method should return Null value",null, outputMeetingList);
    }

    @Test
    public void tests_getFutureMeetingList_ReturnsMeetingListWithMeetingsScheduledWithContact_SortedNoDuplicate() {
        Set<Contact> contactSet1 = new LinkedHashSet<Contact>();
        Set<Contact> contactSet2 = new LinkedHashSet<Contact>();
        contactSet1.add(contact1);
        contactSet2.add(contact2);

        contactManager.addFutureMeeting(contactSet1, new GregorianCalendar(2016,7,1,18,12,15));
        contactManager.addFutureMeeting(contactSet1, new GregorianCalendar(2016,7,1,5,26,45));
        contactManager.addFutureMeeting(contactSet2, new GregorianCalendar(2016,6,1));
        contactManager.addNewPastMeeting(contactSet1, new GregorianCalendar(2013,7,1), "Waste of time");
        contactManager.addFutureMeeting(contactSet1, new GregorianCalendar(2016,8,1));
        contactManager.addNewPastMeeting(contactSet2, new GregorianCalendar(2013,2,1), "Waste of time");
        contactManager.addFutureMeeting(contactSet1, new GregorianCalendar(2015,7,1));

        List<Meeting> expectedMeetingList = new ArrayList<Meeting>();
        expectedMeetingList.add(contactManager.getMeetingList().get(6));
        expectedMeetingList.add(contactManager.getMeetingList().get(1));
        expectedMeetingList.add(contactManager.getMeetingList().get(0));
        expectedMeetingList.add(contactManager.getMeetingList().get(4));

        List<Meeting> outputMeetingList = contactManager.getFutureMeetingList(contact1);
        assertEquals("List of meetings is invalid",expectedMeetingList, outputMeetingList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tests_getFutureMeetingList_ThrowsIllegalArgumentExceptionIfContactDoesNotExist() {
        contactManager.getFutureMeetingList(contact3);
    }
}
