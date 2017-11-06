package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

import java.util.function.Predicate;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;

import seedu.address.commons.events.model.RecyclebinChangeEvent;
import seedu.address.commons.events.ui.CalendarSelectionChangedEvent;
import seedu.address.commons.events.ui.EventPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ScheduleUpdateEvent;
import seedu.address.model.event.Event;

import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.EmptyListException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.ui.EventListPanel;


/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final AddressBook recycleBin;
    private final FilteredList<ReadOnlyPerson> filteredRecycle;
    private final FilteredList<ReadOnlyPerson> filteredPersons;
    private final FilteredList<Event> filteredEvents;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyAddressBook recycleBin, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, recycleBin, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.recycleBin = new AddressBook(recycleBin);
        filteredRecycle = new FilteredList<>(this.recycleBin.getPersonList());
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredEvents = new FilteredList<>(this.addressBook.getEventList());
    }

    public ModelManager() {
        this(new AddressBook(), new AddressBook(), new UserPrefs());
    }


    @Override
    public void resetData(ReadOnlyAddressBook newData, ReadOnlyAddressBook newRecyclebin) {
        addressBook.resetData(newData);
        recycleBin.resetData(newRecyclebin);

        EventsCenter.getInstance().post(new ScheduleUpdateEvent(getEventList()));
        indicateAddressBookChanged();
        indicateRecycleBinChanged();
    }
    @Override
    public void resetRecyclebin(ReadOnlyAddressBook newData) {
        recycleBin.resetData(newData);
        indicateRecycleBinChanged();
    }

    @Override
    public void fresh() {
        indicateRecycleBinChanged();
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }
    @Override
    public ReadOnlyAddressBook getRecycleBin() {
        return recycleBin;
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    private void indicateRecycleBinChanged() {
        raise(new RecyclebinChangeEvent(recycleBin));
    }

    @Override
    public synchronized void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();

    }

    @Override
    public synchronized void deletePerson(ArrayList<ReadOnlyPerson> targets) throws PersonNotFoundException,
            DuplicatePersonException {

        for (ReadOnlyPerson s : targets) {
            if (recycleBin.getPersonList().contains(s)) {
                addressBook.removePerson(s);
            } else {
                addressBook.removePerson(s);
                recycleBin.addPerson(s);
            }
        }
        indicateRecycleBinChanged();
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void deleteBinPerson(ReadOnlyPerson target) throws PersonNotFoundException {
        recycleBin.removePerson(target);
        indicateRecycleBinChanged();
    }

    @Override
    public synchronized void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }
    @Override
    public synchronized void restorePerson(ReadOnlyPerson person) throws DuplicatePersonException,
            PersonNotFoundException {
        addressBook.addPerson(person);
        recycleBin.removePerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        updateFilteredBinList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
        indicateAddressBookChanged();
    }
    @Override
    public synchronized void restorePerson(ArrayList<ReadOnlyPerson> targets) throws DuplicatePersonException,
            PersonNotFoundException {
        boolean flag = true;
        for (ReadOnlyPerson s : targets) {
            if (addressBook.getPersonList().contains(s)) {
                recycleBin.removePerson(s);
            } else {
                recycleBin.removePerson(s);
                addressBook.addPerson(s);
                flag = false;
            }
        }
        if (!flag) {
            indicateAddressBookChanged();
        }
        indicateRecycleBinChanged();
    }

    @Override
    public void sortPerson(Comparator<ReadOnlyPerson> sortType, boolean isDescending) throws EmptyListException {
        addressBook.sortPerson(sortType, isDescending);
    }


    @Override
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }


    @Override
    public void updateListOfPerson(ArrayList<ReadOnlyPerson> targets, ArrayList<ReadOnlyPerson> editedPersons)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(targets, editedPersons);

        addressBook.updateListOfPerson(targets, editedPersons);
        indicateAddressBookChanged();
    }

    @Override
    public void addEvent(ArrayList<ReadOnlyPerson> targets, ArrayList<ReadOnlyPerson> editedPersons, Event event)
            throws DuplicateEventException, DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(targets, editedPersons, event);

        addressBook.addEvent(targets, editedPersons, event);

        EventsCenter.getInstance().post(new ScheduleUpdateEvent(getEventList()));
        indicateAddressBookChanged();

    }

    @Override
    public void removeEvents(ArrayList<ReadOnlyPerson> targets, ArrayList<ReadOnlyPerson> editedPersons,
                             ArrayList<Event> toRemoveEvents)
            throws DuplicatePersonException, PersonNotFoundException, EventNotFoundException {
        requireAllNonNull(targets, editedPersons);

        addressBook.removeEvents(targets, editedPersons, toRemoveEvents);

        EventsCenter.getInstance().post(new ScheduleUpdateEvent(getEventList()));
        indicateAddressBookChanged();
    }

    @Override
    public void sortEvents(LocalDate date) {
        requireAllNonNull(date);
        addressBook.sortEvents(date);
    }

    @Override
    public ObservableList<Event> getEventList() {
        return FXCollections.unmodifiableObservableList(filteredEvents);

    }


    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }
    @Override
    public ObservableList<ReadOnlyPerson> getRecycleBinPersonList() {
        return FXCollections.unmodifiableObservableList(filteredRecycle);
    }

    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }
    @Override
    public void updateFilteredBinList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredRecycle.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && filteredPersons.equals(other.filteredPersons);
    }


    /**
     * Handle event when Event in Event list is clicked.
     *
     * Update {@code FilteredList<ReadOnlyPerson> filteredPersons} to show members of Event upon clicking on Event.
     * @see EventListPanel#setEventHandlerForSelectionChangeEvent()
     *
     * @param event
     */
    @Subscribe
    private void handleEventPanelSelectionChangedEvent(EventPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        updateFilteredPersonList(p ->
            event.getMemberAsArrayList().contains(p)
        );
    }

    /**
     * Handle event when date in CalenderView is clicked.
     *
     * Update master UniqueEventList by running a sort with the given date as reference.
     * Comparator logic and sorting details is found in {@see UniqueEventList#sort(LocalDate)}
     *
     * @param event
     */
    @Subscribe
    private void handleCalendarSelectionChangedEvent(CalendarSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        sortEvents(event.getSelectedDate());
    }


}
