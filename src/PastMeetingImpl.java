/**
 * Created by Pierre on 27/02/2015
 * Implementation of the Interface PastMeeting
 */

import java.util.Calendar;
import java.util.Set;

public class PastMeetingImpl extends MeetingImpl implements PastMeeting {

    private String notes;

    public PastMeetingImpl(Calendar date, Set<Contact> contactSet, String notes) {
        super(date,contactSet);
        this.notes = notes;
    }

    @Override
    public String getNotes() {
        return notes;
    }

}
