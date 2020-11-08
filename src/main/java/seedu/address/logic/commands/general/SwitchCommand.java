package seedu.address.logic.commands.general;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.ModeEnum;
import seedu.address.logic.commands.CommandResult;

/**
 * Switches the mode of the application.
 */
public class SwitchCommand extends Command {

    public static final String COMMAND_WORD = "switch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switches mode.\n"
            + "Parameters: MODE (must be " + ModeEnum.getModeOptions()
            + ")\n"
            + "Example: " + COMMAND_WORD + " " + ModeEnum.DELIVERABLE.getArgument();


    public static final String MESSAGE_SUCCESS = "Switched to: %1$s";

    private final ModeEnum mode;

    /**
     * Creates a SwitchCommand to switch the specified {@code ModeEnum}
     */
    public SwitchCommand(ModeEnum mode) {
        requireNonNull(mode);
        this.mode = mode;
    }

    @Override
    public CommandResult execute() {
        return new CommandResult(String.format(MESSAGE_SUCCESS, mode), false, false, mode);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SwitchCommand // instanceof handles nulls
                && mode.equals(((SwitchCommand) other).mode)); // state check
    }
}
