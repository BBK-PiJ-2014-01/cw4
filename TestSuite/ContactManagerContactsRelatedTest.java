/**
 * Created by Pierre on 28/02/2015.
 */

import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class ContactManagerContactsRelatedTest {
    ContactManager contactManager;

    @BeforeClass
    public void buildUp() {
        contactManager = new ContactManagerImpl();
    }

    @Test
    public void tests_addNewContact_IsAddedToContactSet() {
        assertThat("contactSet should be empty",contactManager.contactSet, hasSize(0));
        addNewContact("Pierre Meyer","Birkbeck student");
        assertThat("contactSet should have 1 element",contactManager.contactSet, hasSize(1));
    }

    @Test(expected = NullPointerException.class)
    public void tests_AddNewContact_ThrowsNullPointerExceptionIfNameIsNull() {
        addNewContact(null,"Birkbeck student");
    }

    @Test(expected = NullPointerException.class)
    public void tests_AddNewContact_ThrowsNullPointerExceptionIfNotesAreNull() {
        addNewContact("Pierre Meyer",null);
    }
}
