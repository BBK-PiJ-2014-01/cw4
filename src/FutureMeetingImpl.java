/**
 * Created by Pierre on 08/03/2015.
 */

import java.util.Calendar;
import java.util.Set;

public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting, Comparable<FutureMeeting> {

    public FutureMeetingImpl(Set<Contact> contactSet, Calendar date) {
        super(contactSet, date);
    }

    public FutureMeetingImpl(int id, Set<Contact> contactSet, Calendar date) {
        super(id, contactSet, date);
    }

    @Override
    public int compareTo(FutureMeeting meeting) {
        return getDate().compareTo(meeting.getDate());
    }

}
