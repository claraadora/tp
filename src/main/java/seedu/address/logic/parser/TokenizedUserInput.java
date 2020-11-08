package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.general.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class TokenizedUserInput {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private final String commandWord;
    private final String arguments;

    TokenizedUserInput(String commandWord, String arguments) {
        this.commandWord = commandWord;
        this.arguments = arguments;
    }

    public static TokenizedUserInput getCommandWordArgumentsFromUserInput(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        return new TokenizedUserInput(commandWord, arguments);
    }

    public String getCommandWord() {
        return commandWord;
    }

    public String getArguments() {
        return arguments;
    }
}
