/**
 * Created by Pierre on 08/03/2015.
 */

import java.util.Calendar;
import java.util.Set;

public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting {

    public FutureMeetingImpl(Set<Contact> contactSet, Calendar date) {
        super(contactSet, date);
    }

    public FutureMeetingImpl(int id, Set<Contact> contactSet, Calendar date) {
        super(id, contactSet, date);
    }

/*
    @Override
    public int compareTo(Meeting meeting) {
        return getDate().compareTo(meeting.getDate());
    }
*/
}
