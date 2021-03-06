/**
 * Created by Pierre on 14/03/2015.
 */

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.*;

public class ContactManagerPastMeetingsRelatedTest {

    ContactManagerImpl contactManager ;
    Contact contact1;
    Contact contact2;
    Contact contact3;
    Set<Contact> myContactSet;
    Set<Contact> emptyContactSet;
    Set<Contact> anotherContactSet;

    @Before
    public void buildUp() {
        File inputFile = new File("./TestSuite/Testdata-empty.txt");
        contactManager = new ContactManagerImpl(inputFile);
        //MeetingImpl.setCounter(0);
        //ContactImpl.setCounter(0);
        contact1 = new ContactImpl("Amelie Worth");
        contact2 = new ContactImpl("Brian Sprout");
        contact3 = new ContactImpl("Julie Miller");
        contactManager.addNewContact(contact1);
        contactManager.addNewContact(contact2);
        myContactSet = contactManager.getContacts(contact1.getId(),contact2.getId());
        anotherContactSet = new LinkedHashSet<Contact>();
        anotherContactSet.add(contact3);
        emptyContactSet = new LinkedHashSet<Contact>();
    }

    @Test
    public void tests_addNewPastMeeting_IsAddedToMeetingSet() {
        int expected = 0;
        assertEquals("meetingSet should be empty",expected,contactManager.getMeetingList().size());
        contactManager.addNewPastMeeting(myContactSet, new GregorianCalendar(2014, 11, 20),"That was a jolly good meeting");
        expected++;
        assertEquals("meetingSet should have 1 element",expected,contactManager.getMeetingList().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void tests_addNewPastMeeting_ThrowsIllegalArgumentExceptionIfListContactIsEmpty() {
        contactManager.addNewPastMeeting(emptyContactSet, new GregorianCalendar(2013, 01, 30),"Great meeting");
    }

    @Test(expected = IllegalArgumentException.class)
    public void tests_addNewPastMeeting_ThrowsIllegalArgumentExceptionIfAnyContactDoesNotExist() {
        contactManager.addNewPastMeeting(anotherContactSet, new GregorianCalendar(2013, 01, 30),"Great meeting");
    }

    @Test(expected = NullPointerException.class)
    public void tests_addNewPastMeeting_NullPointerExceptionIfContactSetParameterIsNull() {
        contactManager.addNewPastMeeting(null, new GregorianCalendar(2013, 01, 30),"Great meeting");
    }

    @Test(expected = NullPointerException.class)
    public void tests_addNewPastMeeting_NullPointerExceptionIfDateParameterIsNull() {
        contactManager.addNewPastMeeting(myContactSet, null,"Great meeting");
    }

    @Test(expected = NullPointerException.class)
    public void tests_addNewPastMeeting_NullPointerExceptionIfTextMessageParameterIsNull() {
        contactManager.addNewPastMeeting(myContactSet, new GregorianCalendar(2013, 01, 30), null);
    }

    @Test
    public void tests_getPastMeeting_ReturnsMeetingForRequestedValidId() {
        contactManager.addNewPastMeeting(myContactSet, new GregorianCalendar(2012, 07, 01), "Good meeting");
        int requestedMeetingId = contactManager.getMeetingList().get(contactManager.getMeetingList().size()-1).getId();
        int expectedMeetingId = requestedMeetingId;
        assertEquals("Returned meeting ID is not the one requested",expectedMeetingId,contactManager.getPastMeeting(requestedMeetingId).getId());
        assertTrue("Meeting should have been found in meetingSet",contactManager.getMeetingList().contains(contactManager.getPastMeeting(requestedMeetingId)));
        assertTrue("Meeting should not be in the future",contactManager.getPastMeeting(requestedMeetingId) instanceof PastMeeting);
    }

    @Test
    public void tests_getPastMeeting_ReturnsNullIfNoMeetingForRequestedId() {
        int requestedMeetingId = 1000;
        int expectedMeetingId = requestedMeetingId;
        assertEquals("Null should have been returned",null,contactManager.getPastMeeting(requestedMeetingId));
        assertFalse("Meeting should not have been found in meetingSet", contactManager.getMeetingList().contains(contactManager.getPastMeeting(requestedMeetingId)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void tests_getPastMeeting_ThrowsIllegalArgumentExceptionIfMeetingIsInTheFuture() {
        contactManager.addFutureMeeting(myContactSet, new GregorianCalendar(2012, 07, 01));
        contactManager.getPastMeeting(1);
    }

    @Test
    public void tests_getPastMeetingList_ReturnsNullIfNoMeetingScheduledWithContact() {
        Set<Contact> contactSet1 = new LinkedHashSet<Contact>();
        Set<Contact> contactSet2 = new LinkedHashSet<Contact>();
        contactSet1.add(contact1);
        contactSet2.add(contact2);

        contactManager.addNewPastMeeting(contactSet2, new GregorianCalendar(2013,7,1,15,35,23), "Waste of time");
        contactManager.addNewPastMeeting(contactSet2, new GregorianCalendar(2013,7,1,15,35,24), "Waste of time");
        contactManager.addNewPastMeeting(contactSet2, new GregorianCalendar(2013,7,1,15,35,25), "Waste of time");

        List<PastMeeting> outputMeetingList = contactManager.getPastMeetingList(contact1);
        assertEquals("Method should return Null value", null, outputMeetingList);
    }

    @Test
    public void tests_getFutureMeetingList_ReturnsMeetingListWithMeetingsScheduledWithContact_SortedNoDuplicate() {
        Set<Contact> contactSet1 = new LinkedHashSet<Contact>();
        Set<Contact> contactSet2 = new LinkedHashSet<Contact>();
        contactSet1.add(contact1);
        contactSet2.add(contact2);

        contactManager.addNewPastMeeting(contactSet1, new GregorianCalendar(2013,7,1,15,35,24), "Nice time");
        contactManager.addFutureMeeting(contactSet1, new GregorianCalendar(2016,7,1));
        contactManager.addFutureMeeting(contactSet2, new GregorianCalendar(2016,6,1));
        contactManager.addNewPastMeeting(contactSet1, new GregorianCalendar(2013,7,1), "Awful");
        contactManager.addFutureMeeting(contactSet1, new GregorianCalendar(2016,5,1));
        contactManager.addNewPastMeeting(contactSet2, new GregorianCalendar(2013,2,1), "Waste of time");
        contactManager.addNewPastMeeting(contactSet1, new GregorianCalendar(2013,7,1,15,35,23), "Waste of time");

        List<PastMeeting> expectedMeetingList = new ArrayList<PastMeeting>();
        expectedMeetingList.add((PastMeeting)contactManager.getMeetingList().get(3));
        expectedMeetingList.add((PastMeeting)contactManager.getMeetingList().get(6));
        expectedMeetingList.add((PastMeeting)contactManager.getMeetingList().get(0));

        List<PastMeeting> outputMeetingList = contactManager.getPastMeetingList(contact1);
        assertEquals("List of meetings is invalid",expectedMeetingList, outputMeetingList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tests_getPastMeetingList_ThrowsIllegalArgumentExceptionIfContactDoesNotExist() {
        contactManager.getPastMeetingList(contact3);
    }

}
