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
        contactManager = new ContactManagerImpl();
        MeetingImpl.setCounter(0);
        ContactImpl.setCounter(0);
        contact1 = new ContactImpl("Amelie Worth");
        contact2 = new ContactImpl("Brian Sprout");
        contact3 = new ContactImpl("Julie Miller");
        contactManager.addNewContact(contact1);
        contactManager.addNewContact(contact2);
        myContactSet = contactManager.getContacts(contact1.getId(),contact2.getId());
        anotherContactSet = contactManager.getContacts(contact1.getId(),contact3.getId());
    }

    @Test
    public void tests_addNewPastMeeting_IsAddedToMeetingSet() {
        int expected = 0;
        assertEquals("meetingSet should be empty",expected,contactManager.getMeetingSet().size());
        contactManager.addNewPastMeeting(myContactSet, new GregorianCalendar(2014, 11, 20),"That was a jolly good meeting");
        expected++;
        assertEquals("meetingSet should have 1 element",expected,contactManager.getMeetingSet().size());
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
}
