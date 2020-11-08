package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.person.Command;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.person.AddressBookParser;
import seedu.address.model.person.ModelPerson;
import seedu.address.model.person.ReadOnlyAddressBook;
import seedu.address.model.person.person.Person;
import seedu.address.storage.person.StoragePerson;

/**
 * The main LogicManager of the app.
 */
public class LogicPersonManager implements LogicPerson {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";

    private final ModelPerson modelPerson;
    private final StoragePerson storagePerson;
    private final AddressBookParser addressBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicPersonManager(ModelPerson modelPerson, StoragePerson storagePerson) {
        this.modelPerson = modelPerson;
        this.storagePerson = storagePerson;
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {

        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText);
        commandResult = command.execute(modelPerson);

        try {
            storagePerson.saveAddressBook(modelPerson.getAddressBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return modelPerson.getAddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return modelPerson.getFilteredPersonList();
    }

    @Override
    public Person getPersonInView() {
        return modelPerson.getPersonInView();
    }

    @Override
    public Path getAddressBookFilePath() {
        return modelPerson.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return modelPerson.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        modelPerson.setGuiSettings(guiSettings);
    }
}
