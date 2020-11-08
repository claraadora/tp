package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.commons.ModeEnum;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.general.Command;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.general.GeneralParser;

/**
 * The main LogicManager of the app.
 */
public class LogicDispatcherManager implements LogicDispatcher {
    private final Logger logger = LogsCenter.getLogger(LogicDispatcherManager.class);
    private final GeneralParser generalParser;

    private final LogicPerson logicPerson;
    private final LogicDeliverable logicDeliverable;
    private final LogicMeeting logicMeeting;

    /**
     * Main Dispatcher of the app to the correct Logic Manager.
     *
     * @param logicPerson
     * @param logicDeliverable
     * @param logicMeeting
     */
    public LogicDispatcherManager(LogicPerson logicPerson, LogicDeliverable logicDeliverable,
                                  LogicMeeting logicMeeting) {
        this.generalParser = new GeneralParser();
        this.logicPerson = logicPerson;
        this.logicDeliverable = logicDeliverable;
        this.logicMeeting = logicMeeting;
    }

    @Override
    public CommandResult execute(String commandText, ModeEnum mode) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult = null;

        // catch all general commands and pass to modeParser
        if (isGeneralCommand(commandText) || mode == ModeEnum.DASHBOARD) {
            Command command = generalParser.parseCommand(commandText);
            commandResult = command.execute();
        } else {
            switch (mode) {
            case PERSON:
                commandResult = logicPerson.execute(commandText);
                break;
            case DELIVERABLE:
                commandResult = logicDeliverable.execute(commandText);
                break;
            case MEETING:
                commandResult = logicMeeting.execute(commandText);
                break;
            default:
                assert false : "from default: " + ModeEnum.getModeOptions();
            }
        }

        requireNonNull(commandResult);

        return commandResult;
    }

    public boolean isGeneralCommand(String commandText) throws ParseException {
        return generalParser.isGeneralCommand(commandText);
    }

}
