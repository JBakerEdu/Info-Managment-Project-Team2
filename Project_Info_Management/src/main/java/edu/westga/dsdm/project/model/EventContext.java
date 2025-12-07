package edu.westga.dsdm.project.model;

/**
 * EventContext is a singleton class that holds the selected event.
 * This class is responsible for providing a centralized place to access and modify the selected event.
 * 
 * It ensures that only one instance of the selected event can be managed throughout the application.
 * 
 * @author Jacob Baker
 * @version Fall 2025
 */
public class EventContext {

    private static EventContext instance;
    private Event selectedEvent;
    private EventContext() {}

     /**
     * Retrieves the singleton instance of the EventContext.
     * 
     * @return the singleton instance of EventContext
     */
    public static EventContext getInstance() {
        if (instance == null) {
            instance = new EventContext();
        }
        return instance;
    }

    /**
     * Gets the currently selected event.
     * 
     * @return the selected event
     */
    public Event getSelectedEvent() {
        return selectedEvent;
    }

    /**
     * Sets the event as the selected event.
     * 
     * @param event the event to set as selected
     */
    public void setSelectedEvent(Event event) {
        this.selectedEvent = event;
    }
}
