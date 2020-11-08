package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.meeting.Command;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.meeting.MeetingBookParser;
import seedu.address.model.meeting.ModelMeeting;
import seedu.address.model.meeting.ReadOnlyMeetingBook;
import seedu.address.model.meeting.meeting.Meeting;
import seedu.address.storage.meeting.StorageMeeting;

/**
 * Manages the logic for the meeting feature.
 */
public class LogicMeetingManager implements LogicMeeting {

    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to meeting file: ";
    private final ModelMeeting modelMeeting;
    private final StorageMeeting storageMeeting;
    private final MeetingBookParser meetingBookParser;

    /**
     * Constructs a {@code LogicMeetingManager} with the given {@code ModelMeeting} and {@code Storage}.
     */
    public LogicMeetingManager(ModelMeeting modelMeeting, StorageMeeting storageMeeting) {
        this.modelMeeting = modelMeeting;
        this.storageMeeting = storageMeeting;
        this.meetingBookParser = new MeetingBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {

        CommandResult commandResult;
        Command command = meetingBookParser.parseCommand(commandText);
        commandResult = command.execute(modelMeeting);

        try {
            storageMeeting.saveMeetingBook(modelMeeting.getMeetingBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyMeetingBook getMeetingBook() {
        return modelMeeting.getMeetingBook();
    }

    @Override
    public Meeting getMeetingInView() {
        return modelMeeting.getMeetingInView();
    }

    @Override
    public ObservableList<Meeting> getFilteredMeetingList() {
        return modelMeeting.getFilteredMeetingList();
    }

    @Override
    public ObservableList<Meeting> getInternalMeetingList() {
        return modelMeeting.getInternalMeetingList();
    }

    @Override
    public Path getMeetingBookFilePath() {
        return modelMeeting.getMeetingBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return modelMeeting.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        modelMeeting.setGuiSettings(guiSettings);
    }

}
