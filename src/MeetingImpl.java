/**
 * Created by Pierre on 24/02/2015
 * Implementation of the Interface Meeting
 */

import java.io.*;
import java.util.Calendar;
import java.util.Set;

public class MeetingImpl implements Meeting {

    private static int counter = 0;

    private int uniqueID;
    private Calendar date;
    private Set<Contact> attendance;

    /**
     * Constructor for the class MeetingImpl
     *
     * @param contactSet list of contacts attending the meeting
     * @param date date of the meeting
     */
    public MeetingImpl(Set<Contact> contactSet, Calendar date) {
        counter++;
        uniqueID = counter;
        this.date = date;
        attendance = contactSet;
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

    public static void setCounter(int number) {
        counter = number;
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

    private void writeConfig(int id) {
        File file = new File("./meeting.config");
        PrintWriter out;
        try {
            PrintWriter out = new PrintWriter(file);
            out.write(id);
        } catch (FileNotFoundException ex) {

            System.out.println("Cannot write to file " + file + ".");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            out.close();
        }
    }

    private void closeReader(Reader reader) {
        try {
            if (reader != null)
                reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
