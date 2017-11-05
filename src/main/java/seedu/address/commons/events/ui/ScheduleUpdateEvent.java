package seedu.address.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.event.Event;

/**
 * When schedule is being updated
 */
public class ScheduleUpdateEvent extends BaseEvent {

    private ObservableList<Event> events;

    public ScheduleUpdateEvent(ObservableList<Event> events) {
        this.events = events;
    }

    public ObservableList<Event> getEvents() {
        return events;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}