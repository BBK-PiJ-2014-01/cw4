import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by Pierre on 21/03/2015.
 */
public class ContactManagerMeetingNotesRelatedTest {

    ContactManagerImpl contactManager ;
    Contact contact1;
    Contact contact2;
    Set<Contact> myContactSet;

    @Before
    public void buildUp() {
        File inputFile = new File("./src/notestestdata.txt");
        contactManager = new ContactManagerImpl(inputFile);
        MeetingImpl.setCounter(1);
        ContactImpl.setCounter(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tests_addMeetingNotes_ThrowsIllegalArgumentExceptionIfMeetingDoesNotExist() {
        contactManager.addMeetingNotes(100,"A jolly good meeting");
    }

    @Test(expected = IllegalStateException.class)
    public void tests_addMeetingNotes_ThrowsIllegalStateExceptionIfMeetingSetForFutureDate() {
        contactManager.addMeetingNotes(1,"A jolly good meeting");
    }

    @Test(expected = NullPointerException.class)
    public void tests_addMeetingNotes_ThrowsNullPointerExceptionIfNotesAreNull() {
        contactManager.addMeetingNotes(1,null);
    }

    //Not finished
    @Test
    public void tests_addMeetingNotes_AddsFurtherNotesToPastMeeting() {
        String expected = "Notes 1";
        PastMeeting pastMeeting = (PastMeeting) contactManager.getMeetingList().get(1);
        assertEquals("Wrong notes",expected,pastMeeting.getNotes());
        contactManager.addMeetingNotes(2, "Notes 2");
        expected = "Notes 1\n"+"Notes 2";
        assertEquals("Wrong notes",expected,pastMeeting.getNotes());
    }

    //Not finished
    @Test
    public void tests_addMeetingNotes_ConvertsFutureMeetingToPastMeetingWithNotes() {
        String expected = "Plenty to think about";
        contactManager.addMeetingNotes(5,"Plenty to think about");
        PastMeeting pastMeeting = (PastMeeting) contactManager.getMeetingList().get(1);
        assertEquals("Wrong notes",expected,pastMeeting.getNotes());
    }

}
