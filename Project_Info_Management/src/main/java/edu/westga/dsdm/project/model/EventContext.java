package edu.westga.dsdm.project.model;

public class EventContext {

    private static EventContext instance;
    private Event selectedEvent;

    private EventContext() {}

    public static EventContext getInstance() {
        if (instance == null) {
            instance = new EventContext();
        }
        return instance;
    }

    public Event getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(Event event) {
        this.selectedEvent = event;
    }
}
