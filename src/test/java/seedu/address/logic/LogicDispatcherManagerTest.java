package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.ModeEnum;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.general.ExitCommand;
import seedu.address.logic.commands.general.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.deliverable.ModelDeliverable;
import seedu.address.model.deliverable.ModelDeliverableManager;
import seedu.address.model.meeting.ModelMeeting;
import seedu.address.model.meeting.ModelMeetingManager;
import seedu.address.model.person.ModelPerson;
import seedu.address.model.person.ModelPersonManager;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.deliverable.JsonDeliverableBookStorage;
import seedu.address.storage.deliverable.StorageDeliverableManager;
import seedu.address.storage.meeting.JsonMeetingBookStorage;
import seedu.address.storage.meeting.StorageMeetingManager;
import seedu.address.storage.person.JsonAddressBookStorage;
import seedu.address.storage.person.StoragePersonManager;
import seedu.address.testutil.ModeUtil;

class LogicDispatcherManagerTest {

    @TempDir
    public Path temporaryFolder;

    private LogicDispatcher logicDispatcher;

    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("contactbook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StoragePersonManager storagePerson = new StoragePersonManager(addressBookStorage, userPrefsStorage);
        ModelPerson modelPerson = new ModelPersonManager();
        LogicPerson logicPerson = new LogicPersonManager(modelPerson, storagePerson);

        JsonDeliverableBookStorage deliverableBookStorage =
                new JsonDeliverableBookStorage(temporaryFolder.resolve("deliverablebook.json"));
        StorageDeliverableManager storageDeliverable =
                new StorageDeliverableManager(deliverableBookStorage, userPrefsStorage);
        ModelDeliverable modelDeliverable = new ModelDeliverableManager();
        LogicDeliverable logicDeliverable = new LogicDeliverableManager(modelDeliverable, storageDeliverable);

        JsonMeetingBookStorage meetingBookStorage =
                new JsonMeetingBookStorage(temporaryFolder.resolve("meetingbook.json"));
        StorageMeetingManager storageMeeting = new StorageMeetingManager(meetingBookStorage, userPrefsStorage);
        ModelMeeting modelMeeting = new ModelMeetingManager();
        LogicMeeting logicMeeting = new LogicMeetingManager(modelMeeting, storageMeeting);

        logicDispatcher = new LogicDispatcherManager(logicPerson, logicDeliverable, logicMeeting);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String exitCommand = ExitCommand.COMMAND_WORD;
        assertCommandSuccess(exitCommand, ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage)
            throws CommandException, ParseException {
        CommandResult result = logicDispatcher.execute(inputCommand, ModeEnum.DASHBOARD);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage) {
        assertThrows(expectedException, expectedMessage, () ->
                logicDispatcher.execute(inputCommand, ModeEnum.DASHBOARD));
    }


    @Test
    public void parseCommand_isModeCommand() throws ParseException {
        assertTrue(logicDispatcher.isGeneralCommand(ModeUtil.getSwitchCommand(ModeEnum.PERSON)));
        assertTrue(logicDispatcher.isGeneralCommand(ExitCommand.COMMAND_WORD));
        assertTrue(logicDispatcher.isGeneralCommand(HelpCommand.COMMAND_WORD));
    }
}
