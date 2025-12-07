package edu.westga.dsdm.project.model;

import java.time.LocalDateTime;
import java.util.List;

import edu.westga.dsdm.project.azurepgsql.DBEventOps;

/**
 * Provides higher-level operations for managing events.
 * 
 * This class acts as a wrapper around the database operations related to events.
 * It provides an abstraction layer for fetching, creating, and deleting events, making it easier to work with events in the UI layer.
 * 
 * @author Jacob Baker
 * @version Fall 2025
 */
public class EventManager {

    /**
     * Retrieves a list of events for a specific organizer.
     * 
     * @param organizerUserId the user ID of the organizer whose events are to be retrieved
     * @return List of events associated with the organizer
     * @throws Exception if an error occurs while fetching events from the database
     */
    public static List<Event> getEventsForOrganizer(int organizerUserId) throws Exception {
        return DBEventOps.findByOrganizer(organizerUserId);
    }

    /**
     * Creates a new event for a specific organizer.
     * 
     * @param organizerUserId the user ID of the organizer who is creating the event
     * @param title the title of the event
     * @param location the location where the event will take place
     * @param description a brief description of the event
     * @param rubric the rubric or criteria for the event
     * @param start the start date and time of the event
     * @param end the end date and time of the event
     * @return the created Event object
     * @throws Exception if an error occurs while inserting the event into the database
     */
    public static Event createEventForOrganizer(
            int organizerUserId,
            String title,
            String location,
            String description,
            String rubric,
            LocalDateTime start,
            LocalDateTime end
    ) throws Exception {

        return DBEventOps.insertEvent(
                organizerUserId,
                title,
                location,
                description,
                rubric,
                start,
                end
        );
    }

    /**
     * Deletes an event by its ID.
     * 
     * @param eventId the ID of the event to be deleted
     * @throws Exception if an error occurs while deleting the event from the database
     */
    public static void deleteEvent(int eventId) throws Exception {
        DBEventOps.deleteEvent(eventId);
    }

    /**
     * Fetches all events from the database.
     *
     * @return List of events.
     * @throws Exception if there is an error fetching events from the database.
     */
    public static List<Event> getAllEvents() throws Exception {
        return DBEventOps.findAllEvents();
    }
}
