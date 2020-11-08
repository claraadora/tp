package seedu.address.model.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Optional;

/**
 * Represents a Deliverable's description in the deliverable book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */

public class Description {

    public static final String EMPTY_DESCRIPTION_FIELD = "-";
    public static final String MESSAGE_CONSTRAINTS = "Descriptions can take any value.";
    public static final String VALIDATION_REGEX = "[^\\s].*";
    public final Optional<String> value;

    /**
     * Constructs a {@code Description}.
     *
     * @param description A valid description.
     */
    public Description(Optional<String> description) {
        if (description.isPresent()) {
            checkArgument(isValidDescription(description.get()), MESSAGE_CONSTRAINTS);
            value = description.get().isEmpty()
                    ? Optional.empty()
                    : description;
        } else {
            value = description;
        }

    }

    /**
     * Constructs a {@code Description}.
     *
     * @param description A valid description.
     */
    public Description(String description) {
        requireNonNull(description);
        checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
        if (description.isEmpty()) {
            value = Optional.empty();
        } else {
            value = Optional.of(description);
        }
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        if (test.isEmpty()) {
            return true;
        } else {
            return test.matches(VALIDATION_REGEX);
        }

    }


    @Override
    public String toString() {
        return value.orElse(EMPTY_DESCRIPTION_FIELD);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description) // instanceof handles nulls
                && value.equals((((Description) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
