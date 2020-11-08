package seedu.address.logic.commands.general;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.ModeEnum;
import seedu.address.logic.commands.CommandResult;

public class SwitchCommandTest {

    @Test
    public void execute_validMode_success() {
        SwitchCommand switchPersonCommand = new SwitchCommand(ModeEnum.PERSON);
        String expectedPersonMessage = String.format(SwitchCommand.MESSAGE_SUCCESS, ModeEnum.PERSON);
        assertCommandSuccess(switchPersonCommand, new CommandResult(expectedPersonMessage, false, false,
                ModeEnum.PERSON));

        SwitchCommand switchDeliverableCommand = new SwitchCommand(ModeEnum.DELIVERABLE);
        String expectedDeliverableMessage = String.format(SwitchCommand.MESSAGE_SUCCESS, ModeEnum.DELIVERABLE);
        assertCommandSuccess(switchDeliverableCommand, new CommandResult(expectedDeliverableMessage, false, false,
                ModeEnum.DELIVERABLE));

        SwitchCommand switchMeetingCommand = new SwitchCommand(ModeEnum.MEETING);
        String expectedMeetingMessage = String.format(SwitchCommand.MESSAGE_SUCCESS, ModeEnum.MEETING);
        assertCommandSuccess(switchMeetingCommand, new CommandResult(expectedMeetingMessage, false, false,
                ModeEnum.MEETING));

        SwitchCommand switchDashboardCommand = new SwitchCommand(ModeEnum.DASHBOARD);
        String expectedDashboardMessage = String.format(SwitchCommand.MESSAGE_SUCCESS, ModeEnum.DASHBOARD);
        assertCommandSuccess(switchDashboardCommand, new CommandResult(expectedDashboardMessage, false, false,
                ModeEnum.DASHBOARD));
    }

    @Test
    public void equals() {
        SwitchCommand switchFirstCommand = new SwitchCommand(ModeEnum.PERSON);
        SwitchCommand switchSecondCommand = new SwitchCommand(ModeEnum.DELIVERABLE);

        // same object -> returns true
        assertTrue(switchFirstCommand.equals(switchFirstCommand));

        // same values -> returns true
        SwitchCommand switchFirstCommandCopy = new SwitchCommand(ModeEnum.PERSON);
        assertTrue(switchFirstCommand.equals(switchFirstCommandCopy));

        // different types -> returns false
        assertFalse(switchFirstCommand.equals(1));

        // null -> returns false
        assertFalse(switchFirstCommand.equals(null));

        // different mode -> returns false
        assertFalse(switchFirstCommand.equals(switchSecondCommand));
    }
}
