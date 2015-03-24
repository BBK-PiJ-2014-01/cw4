/**
 * Created by Pierre on 08/03/2015
 *
 * Class FutureMeetingImpl (Implementation of the Interface FutureMeeting & Comparable)
 * A meeting to be held in the future.
 */

import java.util.Calendar;
import java.util.Set;

public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting, Comparable<FutureMeeting> {

    /**
     * Constructor for the class FutureMeetingImpl
     *
     * Primarily used to create a new record for a meeting set in the future.
     * Calls the super-class constructor where the unique Id is generated.
     *
     * @param contactSet list of contacts attending the meeting
     * @param date date of the meeting
     */
    public FutureMeetingImpl(Set<Contact> contactSet, Calendar date) {
        super(contactSet, date);
    }

    /**
     * Constructor for the class FutureMeetingImpl
     *
     * Primarily used by the Load() method, when importing future meeting records from the file contacts.txt
     * No meeting ID generated.
     *
     * @param id the unique identifier of the meeting
     * @param contactSet list of contacts attending the meeting
     * @param date date of the meeting
     */
    public FutureMeetingImpl(int id, Set<Contact> contactSet, Calendar date) {
        super(id, contactSet, date);
    }

    /**
     * Implementation of the Comparable Interface, allowing the comparison between two meeting dates
     *
     * @param meeting meeting whose date is compared with
     */
    @Override
    public int compareTo(FutureMeeting meeting) {
        return getDate().compareTo(meeting.getDate());
    }

}
