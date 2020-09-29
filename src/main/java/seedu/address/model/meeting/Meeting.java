package seedu.address.model.meeting;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Meeting {

    // TODO: Make objects of the fields
    private final String title;
    private final String description;
    private final String from;
    private final String to;
    private final String contacts;

    /**
     * Every field must be present and not null.
     */
    public Meeting(String title, String description, String from, String to, String contacts) {
        requireAllNonNull(title, description, from, to, contacts);
        this.title = title;
        this.description = description;
        this.from = from;
        this.to = to;
        this.contacts = contacts;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getContacts() {
        return contacts;
    }

    // TODO: Add missing methods from AddressBook

    @Override
    public int hashCode() {
        return Objects.hash(title, description, from, to, contacts);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Title: ")
                .append(getDescription())
                .append(" Description: ")
                .append(getFrom())
                .append(" From: ")
                .append(getTo())
                .append(" To: ");
        return builder.toString();
    }

}