/**
 * Created by Pierre on 27/02/2015
 * Implementation of the Interface PastMeeting
 */

import java.util.Calendar;
import java.util.Set;

public class PastMeetingImpl extends MeetingImpl implements PastMeeting, Comparable<PastMeeting> {

    private String notes;

    public PastMeetingImpl(Set<Contact> contactSet, Calendar date, String notes) {
        super(contactSet, date);
        this.notes = notes;
    }

    /**
     * Constructor for the class PastMeetingImpl
     *
     * Primarily used by the Load() method, when importing past meeting records from the file contacts.txt
     * No meeting ID generated.
     *
     * @param id the unique identifier of the meeting
     * @param contactSet list of contacts attending the meeting
     * @param date date of the meeting
     * @param notes notes from the meeting
     */
    public PastMeetingImpl(int id, Set<Contact> contactSet, Calendar date, String notes) {
        super(id, contactSet, date);
        this.notes = notes;
    }

    public void addNotes(String newNotes) {
        if (notes.equals(""))
            notes += newNotes;
        else
            notes = notes + "\n" + newNotes;
    }

    @Override
    public String getNotes() {
        return notes;
    }

    @Override
    public int compareTo(PastMeeting meeting) {
        return getDate().compareTo(meeting.getDate());
    }
}
