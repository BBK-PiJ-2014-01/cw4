/**
 * Created by Pierre on 28/03/2015.
 */

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ContactManagerDuplicatesRelatedTest {
    ContactManagerImpl contactManager ;
    Contact contact1;
    Contact contact2;

    @Test
    public void tests_getPastMeetingList_ReturnsNoDuplicates() {
        File inputFile = new File("./TestSuite/Testdata-duplicate1.txt");
        contactManager = new ContactManagerImpl(inputFile);
        MeetingImpl.writeConfig(0);
        ContactImpl.writeConfig(0);
        contact1 = new ContactImpl("Amelie Worth");
        List<PastMeeting> expectedMeetingList = new ArrayList<PastMeeting>();
        expectedMeetingList.add((PastMeeting)contactManager.getMeetingList().get(0));
        List<PastMeeting> outputList = contactManager.getPastMeetingList(contact1);
        assertEquals("List of meetings is invalid", expectedMeetingList, outputList);

    }

    @Test
    public void tests_getFutureMeetingList_ReturnsNoDuplicatesForSelectedContact() {
        File inputFile = new File("./TestSuite/Testdata-duplicate2.txt");
        contactManager = new ContactManagerImpl(inputFile);
        MeetingImpl.writeConfig(0);
        ContactImpl.writeConfig(0);
        contact2 = new ContactImpl("Brian Sprout");
        List<Meeting> expectedMeetingList = new ArrayList<Meeting>();
        expectedMeetingList.add(contactManager.getMeetingList().get(1));
        expectedMeetingList.add(contactManager.getMeetingList().get(0));
        List<Meeting> outputList = contactManager.getFutureMeetingList(contact2);
        assertEquals("List of meetings is invalid",expectedMeetingList, outputList);
    }

    @Test
    public void tests_getFutureMeetingList_ReturnsNoDuplicatesForSelectedDate() {
        File inputFile = new File("./TestSuite/Testdata-duplicate3.txt");
        contactManager = new ContactManagerImpl(inputFile);
        MeetingImpl.writeConfig(0);
        ContactImpl.writeConfig(0);
        List<Meeting> expectedMeetingList = new ArrayList<Meeting>();
        expectedMeetingList.add(contactManager.getMeetingList().get(1));
        expectedMeetingList.add(contactManager.getMeetingList().get(3));
        List<Meeting> outputList = contactManager.getFutureMeetingList(new GregorianCalendar(2014,Calendar.OCTOBER,12));
        assertEquals("List of meetings is invalid", expectedMeetingList, outputList);
    }
}
