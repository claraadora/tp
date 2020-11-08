package seedu.address.model.meeting.meeting;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Optional;

/**
 * Represents a Person's location in the location book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLocation(String)}
 */
public class Location {
    public static final String EMPTY_LOCATION_FIELD = "-";
    public static final String MESSAGE_CONSTRAINTS = "Locations can take any value.";
    public static final String VALIDATION_REGEX = "[^\\s].*";

    /*
     * Represents the value of Location.
     */
    public final Optional<String> value;

    /**
     * Constructs an {@code Location}.
     *
     * @param location A valid location.
     */
    public Location(Optional<String> location) {
        if (location.isPresent()) {
            checkArgument(isValidLocation(location.get()), MESSAGE_CONSTRAINTS);
            value = location.get().isEmpty()
                    ? Optional.empty()
                    : location;
        } else {
            value = location;
        }
    }

    /**
     * Constructs an {@code Location}.
     *
     * @param location A valid location.
     */
    public Location(String location) {
        requireNonNull(location);
        checkArgument(isValidLocation(location), MESSAGE_CONSTRAINTS);
        if (location.isEmpty()) {
            value = Optional.empty();
        } else {
            value = Optional.of(location);
        }

    }

    /**
     * Returns true if a given string is a valid location.
     */
    public static boolean isValidLocation(String test) {
        if (test.isEmpty()) {
            return true;
        } else {
            return test.matches(VALIDATION_REGEX);
        }
    }

    @Override
    public String toString() {
        return value.orElse(EMPTY_LOCATION_FIELD);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Location
                && value.equals(((Location) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
