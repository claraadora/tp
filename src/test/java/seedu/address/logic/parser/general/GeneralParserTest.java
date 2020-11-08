package seedu.address.logic.parser.general;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.ModeEnum;
import seedu.address.logic.commands.general.ExitCommand;
import seedu.address.logic.commands.general.HelpCommand;
import seedu.address.logic.commands.general.SwitchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.ModeUtil;

public class GeneralParserTest {

    private final GeneralParser parser = new GeneralParser();

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_switch() throws Exception {
        SwitchCommand command = (SwitchCommand) parser.parseCommand(ModeUtil.getSwitchCommand(ModeEnum.PERSON)
        );
        assertEquals(new SwitchCommand(ModeEnum.PERSON), command);
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () ->
                parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_isModeCommand() throws ParseException {
        assertTrue(parser.isGeneralCommand(ModeUtil.getSwitchCommand(ModeEnum.PERSON)));
        assertTrue(parser.isGeneralCommand(ExitCommand.COMMAND_WORD));
        assertTrue(parser.isGeneralCommand(HelpCommand.COMMAND_WORD));
    }
}
