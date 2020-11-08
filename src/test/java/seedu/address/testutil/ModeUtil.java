package seedu.address.testutil;

import seedu.address.commons.ModeEnum;
import seedu.address.logic.commands.general.SwitchCommand;

public class ModeUtil {

    /**
     * Returns an switch command string for changing the {@code mode}.
     */
    public static String getSwitchCommand(ModeEnum mode) {
        return SwitchCommand.COMMAND_WORD + " " + mode.getArgument();
    }
}
