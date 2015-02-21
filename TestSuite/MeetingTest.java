/**
 * Created by PierreM on 18/02/2015
 * Testing the implementation of Interface Meeting
 */

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MeetingTest {
    MeetingImpl meeting;

    @Before
    public void buildUp() {
        contact = new ContactImpl(100, "Pierre Meyer");
    }

    @Test
    public void tests_getID_ReturnsContactID() {
        int expected = 100;
        assertEquals(expected, contact.getId());
    }
}