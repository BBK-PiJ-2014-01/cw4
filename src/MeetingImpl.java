/**
 * Created by Pierre on 24/02/2015
 * Implementation of the Interface Meeting
 */

import java.util.Calendar;
import java.util.Set;

public class MeetingImpl implements Meeting {

    private static int counter = 0;

    private int uniqueID;
    private Calendar date;
    private Set<Contact> attendance;

    public MeetingImpl(Set<Contact> contactSet, Calendar date) {
        counter++;
        uniqueID = counter;
        this.date = date;
        attendance = contactSet;
    }

    public static void setCounter(int number) {
        counter = number;
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
