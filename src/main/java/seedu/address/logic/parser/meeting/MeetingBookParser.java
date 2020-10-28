package seedu.address.logic.parser.meeting;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.meeting.ClearCommand;
import seedu.address.logic.commands.meeting.Command;
import seedu.address.logic.commands.meeting.DeleteCommand;
import seedu.address.logic.commands.meeting.EditCommand;
import seedu.address.logic.commands.meeting.ViewCommand;
import seedu.address.logic.commands.mode.HelpCommand;
import seedu.address.logic.commands.person.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input for meeting.
 */
public class MeetingBookParser {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case ViewCommand.COMMAND_WORD:
            return new ViewCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }

    }

    /**
     * Checks if user input is a mode command.
     *
     * @param userInput full user input string
     * @return whether the user input is a mode command
     */
    public boolean isMeetingCommand(String userInput) {
        return userInput.startsWith(Command.COMMAND_WORD);
    }

}
