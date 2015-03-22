/**
 * Created by PierreM on 18/02/2015.
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ContactTest.class,
        MeetingTest.class,
        PastMeetingTest.class,
        ContactManagerContactsRelatedTest.class,
        ContactManagerFutureMeetingsRelatedTest.class,
        ContactManagerPastMeetingsRelatedTest.class,
        ContactManagerMeetingsRelatedTest.class,
        ContactManagerIORelatedTest.class,
})

public class AllTest {
}
