/**
 * Created by Pierre on 24/02/2015
 *
 * Class MeetingImpl (Implementation of the Interface Meeting)
 * Class representing meetings
 * Meetings have unique IDs, scheduled date and a list of participating contacts
 */

import java.io.*;
import java.util.Calendar;
import java.util.Set;

public class MeetingImpl implements Meeting, Comparable<MeetingImpl> {

    private int uniqueID;
    private Calendar date;
    private Set<Contact> attendance;

    /**
     * Constructor for the class MeetingImpl
     * Generate uniqueIDs by incrementing a counter with the latest value saved on disk.
     *
     * @param contactSet list of contacts attending the meeting
     * @param date date of the meeting
     */
    public MeetingImpl(Set<Contact> contactSet, Calendar date) {
        uniqueID = readConfig() + 1;
        this.date = date;
        attendance = contactSet;
        writeConfig(uniqueID);
    }

    /**
     * Constructor for the class MeetingImpl
     *
     * Primarily used by the Load() method, when importing meeting records from the file contacts.txt
     * No meeting ID generated.
     *
     * @param id the unique identifier of the meeting
     * @param contactSet list of contacts attending the meeting
     * @param date date of the meeting
     */
    public MeetingImpl(int id, Set<Contact> contactSet, Calendar date) {
        uniqueID = id;
        this.date = date;
        attendance = contactSet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getId() {
        return uniqueID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Calendar getDate() {
        return date;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Contact> getContacts() {
        return attendance;
    }

    /**
     * Reads the last meeting Id generated and saved on disk (file meeting.config)
     *
     * @return the last id allocated to a meeting record. If no config file found, returns 0.
     */
    private int readConfig() {
        int number = 0;
        BufferedReader fileReader = null;
        File file = new File("./meeting.config");
        try {
            fileReader = new BufferedReader(new FileReader(file));
            number = fileReader.read();
        } catch (FileNotFoundException ex) {
            return(number);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            closeReader(fileReader);
        }
        return(number);
    }

    /**
     * Writes on disk (file meeting.config) the last meeting Id generated
     * Static method so it can be used to set specific values in a testing environment
     *
     * @param id allocated to the last generated meeting record.
     */
    public static void writeConfig(int id) {
        File file = new File("./meeting.config");
        PrintWriter out = null;
        try {
            out = new PrintWriter(file);
            out.write(id);
        } catch (FileNotFoundException ex) {
            System.out.println("Cannot write to file " + file + ".");
        } finally {
            if (out != null)
                out.close();
        }
    }

    /**
     * Closes a reader in a try {} catch() statement
     *
     * @param reader Reader to be closed
     */
    private void closeReader(Reader reader) {
        try {
            if (reader != null)
                reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Implementation of the Comparable Interface, allowing the comparison between two meeting dates
     *
     * @param meeting meeting whose date is compared with
     */
    @Override
    public int compareTo(MeetingImpl meeting) throws NullPointerException {
        if (meeting == null)
            throw new NullPointerException();
        return getDate().compareTo(meeting.getDate());
    }
}
