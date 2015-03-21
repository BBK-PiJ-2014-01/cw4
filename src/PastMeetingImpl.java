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

    public PastMeetingImpl(int id, Set<Contact> contactSet, Calendar date, String notes) {
        super(id, contactSet, date);
        this.notes = notes;
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
