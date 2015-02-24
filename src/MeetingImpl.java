/**
 * Created by Pierre on 24/02/2015
 * Implementation of the Interface Meeting
 */

import java.util.Calendar;
import java.util.Set;

public class MeetingImpl implements Meeting {
    private int uniqueID;
    private Calendar date;
    private Set<Contact> attendance;

    public MeetingImpl(int number, Calendar date, Set<Contact> contactSet) {
        uniqueID = number;
        this.date = date;
        attendance = contactSet;
    }

    @Override
    public int getId() {
        return uniqueID;
    }

    @Override
    public Calendar getDate() {
        return date;
    }

    @Override
    public Set<Contact> getContacts() {
        return attendance;
    }
}
