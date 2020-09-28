package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.mode.ExitCommand;
import seedu.address.logic.commands.mode.HelpCommand;
import seedu.address.logic.commands.person.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModelPerson;
import seedu.address.model.ModelPersonManager;
import seedu.address.model.person.UserPrefs;
import seedu.address.storage.StoragePersonManager;
import seedu.address.storage.person.JsonAddressBookStorage;
import seedu.address.storage.person.JsonUserPrefsStorage;
import seedu.address.testutil.ModeUtil;
import seedu.address.ui.ModeEnum;

class LogicModeManagerTest {

    private LogicMode logicMode;

    @BeforeEach
    public void setUp() {
        logicMode = new LogicModeManager();
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    // public void execute_commandExecutionError_throwsCommandException() {

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
    private void assertCommandSuccess(String inputCommand, String expectedMessage) throws CommandException, ParseException {
        CommandResult result = logicMode.execute(inputCommand);
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
        assertThrows(expectedException, expectedMessage, () -> logicMode.execute(inputCommand));
    }


    @Test
    public void parseCommand_isModeCommand() {
        assertTrue(logicMode.isModeCommand(ModeUtil.getSwitchCommand(ModeEnum.PERSON)));
        // TODO add more modes here
        assertTrue(logicMode.isModeCommand(ExitCommand.COMMAND_WORD));
        assertTrue(logicMode.isModeCommand(HelpCommand.COMMAND_WORD));
    }
}