package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.deliverable.Command;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.deliverable.DeliverableBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.deliverable.ModelDeliverable;
import seedu.address.model.deliverable.ReadOnlyDeliverableBook;
import seedu.address.model.deliverable.deliverable.Deliverable;
import seedu.address.storage.deliverable.StorageDeliverable;


/**
 * Logic manager for deliverables
 */
public class LogicDeliverableManager implements LogicDeliverable {

    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to deliverable file: ";
    private final ModelDeliverable modelDeliverable;
    private final StorageDeliverable storageDeliverable;
    private final DeliverableBookParser deliverableBookParser;

    /**
     * Constructs a {@code LogicDeliverableManager} with the given {@code ModelDeliverable}
     * and {@code StorageDeliverable}.
     */
    public LogicDeliverableManager(ModelDeliverable modelDeliverable, StorageDeliverable storageDeliverable) {
        this.modelDeliverable = modelDeliverable;
        this.storageDeliverable = storageDeliverable;
        this.deliverableBookParser = new DeliverableBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {

        CommandResult commandResult;
        Command command = deliverableBookParser.parseCommand(commandText);
        commandResult = command.execute(modelDeliverable);

        try {
            storageDeliverable.saveDeliverableBook(modelDeliverable.getDeliverableBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyDeliverableBook getDeliverableBook() {
        return modelDeliverable.getDeliverableBook();
    }

    @Override
    public ObservableList<Deliverable> getFilteredDeliverableList() {
        return modelDeliverable.getFilteredDeliverableList();
    }

    @Override
    public ObservableList<Deliverable> getInternalDeliverableList() {
        return modelDeliverable.getInternalDeliverableList();
    }

    @Override
    public Deliverable getDeliverableInView() {
        return modelDeliverable.getDeliverableInView();
    }

    @Override
    public Path getDeliverableBookFilePath() {
        return modelDeliverable.getDeliverableBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return modelDeliverable.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        modelDeliverable.setGuiSettings(guiSettings);
    }
}
