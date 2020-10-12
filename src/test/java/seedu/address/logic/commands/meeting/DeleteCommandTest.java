package seedu.address.logic.commands.meeting;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.model.UserPrefs;
import seedu.address.model.meeting.ModelMeeting;
import seedu.address.model.meeting.meeting.Meeting;
import seedu.address.model.meeting.ModelMeetingManager;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.meeting.CommandTestUtil.*;
import static seedu.address.logic.commands.meeting.DeleteCommand.MESSAGE_DELETE_MEETING_SUCCESS;
import static seedu.address.logic.commands.meeting.DeleteCommand.MESSAGE_MEETING_DISPLAYED_INDEX;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetingBook;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeeting;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

public class DeleteCommandTest {

    private ModelMeeting modelMeeting = new ModelMeetingManager(getTypicalMeetingBook(), new UserPrefs());

    @Test
    void execute_validIndexUnfilteredList_success() {
        Meeting meetingToDelete = getTypicalMeeting().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(MESSAGE_DELETE_MEETING_SUCCESS, meetingToDelete);

        ModelMeetingManager expectedModel= new ModelMeetingManager(modelMeeting.getMeetingBook(), new UserPrefs());
        expectedModel.deleteMeeting(meetingToDelete);

        assertCommandSuccess(deleteCommand, modelMeeting, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(modelMeeting.getFilteredMeetingList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, modelMeeting, MESSAGE_MEETING_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showMeetingAtIndex(modelMeeting, INDEX_FIRST_PERSON);

        Meeting personToDelete = modelMeeting.getFilteredMeetingList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(MESSAGE_DELETE_MEETING_SUCCESS, personToDelete);

        ModelMeeting expectedModelPerson = new ModelMeetingManager(modelMeeting.getMeetingBook(), new UserPrefs());
        expectedModelPerson.deleteMeeting(personToDelete);
        showNoMeeting(expectedModelPerson);

        assertCommandSuccess(deleteCommand, modelMeeting, expectedMessage, expectedModelPerson);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showMeetingAtIndex(modelMeeting, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < modelMeeting.getMeetingBook().getMeetingList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, modelMeeting, MESSAGE_MEETING_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoMeeting(ModelMeeting modelMeeting) {
        modelMeeting.updateFilteredMeetingList(p -> false);

        assertTrue(modelMeeting.getFilteredMeetingList().isEmpty());
    }
}