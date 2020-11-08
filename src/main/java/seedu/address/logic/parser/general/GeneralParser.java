package seedu.address.logic.parser.general;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.address.logic.commands.general.Command;
import seedu.address.logic.commands.general.ExitCommand;
import seedu.address.logic.commands.general.HelpCommand;
import seedu.address.logic.commands.general.SwitchCommand;
import seedu.address.logic.parser.TokenizedUserInput;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input for General Commands.
 */
public class GeneralParser {
    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        TokenizedUserInput tokenizedUserInput = TokenizedUserInput.getCommandWordArgumentsFromUserInput(userInput);
        String commandWord = tokenizedUserInput.getCommandWord();
        String arguments = tokenizedUserInput.getArguments();

        switch (commandWord) {

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case SwitchCommand.COMMAND_WORD:
            return new SwitchCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }

    }

    /**
     * Checks if user input is a general command.
     *
     * @param userInput full user input string
     * @return whether the user input is a general command
     */
    public boolean isGeneralCommand(String userInput) throws ParseException {
        TokenizedUserInput tokenizedUserInput = TokenizedUserInput.getCommandWordArgumentsFromUserInput(userInput);
        String commandWord = tokenizedUserInput.getCommandWord();
        return commandWord.equals(SwitchCommand.COMMAND_WORD) || commandWord.equals(ExitCommand.COMMAND_WORD)
                || commandWord.equals(HelpCommand.COMMAND_WORD);
    }

}
