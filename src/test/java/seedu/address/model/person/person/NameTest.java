package seedu.address.model.person.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // alphabets only
        assertFalse(Name.isValidName("peter*")); // alphabets only
        assertFalse(Name.isValidName("12345")); // alphabets only
        assertFalse(Name.isValidName("peter the 2nd")); // alphabets only
        assertFalse(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names but alphabets only

        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
    }

    @Test
    public void compareTo() {
        Name peterJack = new Name("peter jack");
        Name peterJackCaps = new Name("Peter Jack");
        Name peter = new Name("peter");

        // small letter > big letter
        assertTrue(peterJack.compareTo(peterJackCaps) > 0);

        // longer name > shorter name
        assertTrue(peterJack.compareTo(peter) > 0);

        assertThrows(NullPointerException.class, () -> peterJack.compareTo(null));
    }
}
