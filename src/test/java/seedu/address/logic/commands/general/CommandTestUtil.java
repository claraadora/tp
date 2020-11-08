package seedu.address.logic.commands.general;

import static org.junit.jupiter.api.Assertions.assertEquals;

import seedu.address.logic.commands.CommandResult;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command,
                                            CommandResult expectedCommandResult) {
        CommandResult result = command.execute();
        assertEquals(expectedCommandResult, result);
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, CommandResult)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command,
                                            String expectedMessage) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, expectedCommandResult);
    }

}
