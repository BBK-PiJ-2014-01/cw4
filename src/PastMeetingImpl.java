/**
 * Created by Pierre on 27/02/2015
 *
 * Class PastMeetingImpl (Implementation of the Interface PastMeeting & Comparable)
 * A meeting that was held in the past.
 * It includes notes about what happened and what was agreed
 */

import java.util.Calendar;
import java.util.Set;

public class PastMeetingImpl extends MeetingImpl implements PastMeeting {

    private String notes;

    /**
     * Constructor for the class PastMeetingImpl
     *
     * Primarily used to create a new record for a meeting that took place in the past.
     * After calling the super-class constructor where the unique Id is generated,
     * it add the notes to the meeting
     *
     * @param contactSet list of contacts attending the meeting
     * @param date date of the meeting
     * @param notes notes from the meeting
     */
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

    /**
     * Adds notes from a meeting. If previous notes were already recorded, the new notes are added on a new line.
     *
     * @param newNotes notes from the meeting
     */
    public void addNotes(String newNotes) {
        if (notes.equals(""))
            notes += newNotes;
        else
            notes = notes + "\n" + newNotes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNotes() {
        return notes;
    }

}
