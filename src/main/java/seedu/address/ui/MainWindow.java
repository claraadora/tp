package seedu.address.ui;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.general.SwitchCommand.MESSAGE_SUCCESS;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.ModeEnum;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.LogicDeliverable;
import seedu.address.logic.LogicDispatcher;
import seedu.address.logic.LogicMeeting;
import seedu.address.logic.LogicPerson;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String CLICK_MODE_MESSAGE = "%1$s button clicked!\nResult: Switched to: %1$s";

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private ModeEnum mode;
    private LogicDispatcher logicDispatcher;
    private LogicPerson logicPerson;
    private LogicDeliverable logicDeliverable;
    private LogicMeeting logicMeeting;
    private Calendar calendar;

    // Independent Ui parts residing in this Ui container
    private CalendarListPanel calendarListPanel;
    private ProjectCompletionStatusPanel projectCompletionStatusPanel;
    private PersonListPanel personListPanel;
    private DeliverableListPanel deliverableListPanel;
    private MeetingListPanel meetingListPanel;
    private DeliverableDetailsPanel deliverableDetailsPanel;
    private MeetingDetailsPanel meetingDetailsPanel;
    private PersonDetailsPanel personDetailsPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private Button helpButton;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button deliverableButton;

    @FXML
    private Button meetingButton;

    @FXML
    private Button personButton;

    @FXML
    private StackPane leftPanelPlaceholder;

    @FXML
    private StackPane rightPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} {@code LogicMode},
     * {@code LogicPerson} and {@code LogicDeliverable}.
     */
    public MainWindow(Stage primaryStage, LogicDispatcher logicDispatcher, LogicPerson logicPerson,
                      LogicDeliverable logicDeliverable, LogicMeeting logicMeeting) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logicDispatcher = logicDispatcher;
        this.logicPerson = logicPerson;
        this.logicDeliverable = logicDeliverable;
        this.logicMeeting = logicMeeting;
        this.calendar = new Calendar(logicDeliverable.getInternalDeliverableList(),
                logicMeeting.getInternalMeetingList());;

        // Configure the UI
        // all managers' Gui points to same GuiSettings object so its fine
        setWindowDefaultSize(logicPerson.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();

        mode = ModeEnum.DASHBOARD; // default to dashboard
        setUnderlineButton(dashboardButton);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpButton, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a Button.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(Button button, KeyCombination keyCombination) {
        requireNonNull(button);
        Scene scene = button.getScene();
        requireNonNull(scene);

        scene.getAccelerators().put(
                keyCombination,
                new Runnable() {
                    @FXML public void run() {
                        button.fire();
                    }
                }
        );
    }

    /**
     * Change Ui according to current mode.
     * @param mode the mode to change Ui to.
     */
    public void switchMode(ModeEnum mode) {
        requireNonNull(mode);
        this.mode = mode;
        rightPanelPlaceholder.getChildren().clear(); // clear details panel
        leftPanelPlaceholder.getChildren().clear(); // remove current list
        statusbarPlaceholder.getChildren().clear(); // remove current status bar
        resultDisplay.setFeedbackToUser(String.format(MESSAGE_SUCCESS, mode)); // if userinput is through clicking
        switch (mode) {
        case DASHBOARD:
            rightPanelPlaceholder.getChildren().add(calendarListPanel.getRoot());
            leftPanelPlaceholder.getChildren().add(projectCompletionStatusPanel.getRoot());
            setUnderlineButton(dashboardButton);
            projectCompletionStatusPanel.updateOcp();
            break;
        case PERSON:
            leftPanelPlaceholder.getChildren().add(personListPanel.getRoot());
            StatusBarFooter statusBarFooter = new StatusBarFooter(logicPerson.getAddressBookFilePath());
            statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());
            setUnderlineButton(personButton);
            break;
        case DELIVERABLE:
            leftPanelPlaceholder.getChildren().add(deliverableListPanel.getRoot());
            statusBarFooter = new StatusBarFooter(logicDeliverable.getDeliverableBookFilePath());
            statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());
            setUnderlineButton(deliverableButton);
            break;
        case MEETING:
            leftPanelPlaceholder.getChildren().add(meetingListPanel.getRoot());
            statusBarFooter = new StatusBarFooter(logicMeeting.getMeetingBookFilePath());
            statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());
            setUnderlineButton(meetingButton);
            break;
        default:
            assert false : "from default: " + ModeEnum.getModeOptions();
        }
    }

    private void setUnderlineButton(Button button) {
        dashboardButton.setUnderline(false);
        personButton.setUnderline(false);
        deliverableButton.setUnderline(false);
        meetingButton.setUnderline(false);
        button.setUnderline(true);
    }

    /**
     * Switches to dashboard mode.
     */
    public void switchDashboard() {
        logger.info(String.format(CLICK_MODE_MESSAGE, "Dashboard"));
        switchMode(ModeEnum.DASHBOARD);
    }

    /**
     * Switches to contact mode.
     */
    public void switchPerson() {
        logger.info(String.format(CLICK_MODE_MESSAGE, "Contact"));
        switchMode(ModeEnum.PERSON);
    }

    /**
     * Switches to deliverable mode.
     */
    public void switchDeliverable() {
        logger.info(String.format(CLICK_MODE_MESSAGE, "Deliverable"));
        switchMode(ModeEnum.DELIVERABLE);
    }

    /**
     * Switches to meeting mode.
     */
    public void switchMeeting() {
        logger.info(String.format(CLICK_MODE_MESSAGE, "Meeting"));
        switchMode(ModeEnum.MEETING);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {

        calendarListPanel = new CalendarListPanel(calendar.getTimeEvents());
        projectCompletionStatusPanel = new ProjectCompletionStatusPanel(logicDeliverable.getInternalDeliverableList());
        leftPanelPlaceholder.getChildren().add(projectCompletionStatusPanel.getRoot());
        rightPanelPlaceholder.getChildren().add(calendarListPanel.getRoot());

        personListPanel = new PersonListPanel(logicPerson.getFilteredPersonList());
        deliverableListPanel = new DeliverableListPanel(logicDeliverable.getFilteredDeliverableList());
        meetingListPanel = new MeetingListPanel(logicMeeting.getFilteredMeetingList());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logicPerson.setGuiSettings(guiSettings); // its just to save last guiSetting used
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Updates the details panel whenever a command is executed. This is not called after a switch command
     * since the details panel should be left empty after a switch in mode has been made.
     */
    private void updateDetailsPanel() {
        rightPanelPlaceholder.getChildren().clear();

        switch (mode) {
        case PERSON:
            if (logicPerson.getPersonInView() != null) {
                personDetailsPanel = new PersonDetailsPanel(logicPerson.getPersonInView());
                rightPanelPlaceholder.getChildren().add(personDetailsPanel.getRoot());
            }
            break;
        case DELIVERABLE:
            if (logicDeliverable.getDeliverableInView() != null) {
                deliverableDetailsPanel = new DeliverableDetailsPanel(logicDeliverable.getDeliverableInView());
                rightPanelPlaceholder.getChildren().add(deliverableDetailsPanel.getRoot());
            }
            break;
        case MEETING:
            if (logicMeeting.getMeetingInView() != null) {
                meetingDetailsPanel = new MeetingDetailsPanel(logicMeeting.getMeetingInView());
                rightPanelPlaceholder.getChildren().add(meetingDetailsPanel.getRoot());
            }
            break;
        default:
            assert false : "invalid mode type: " + ModeEnum.getModeOptions();
        }
    }

    /**
     * Executes the command and returns the result.
     *
     * @see LogicPerson#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logicDispatcher.execute(commandText, mode);

            requireNonNull(commandResult);

            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            } else if (commandResult.isExit()) {
                handleExit();
            } else if (commandResult.getMode() != null) {
                switchMode(commandResult.getMode());
            } else {
                updateDetailsPanel();
                calendar.updateCalendarList();
                projectCompletionStatusPanel.updateOcp();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
