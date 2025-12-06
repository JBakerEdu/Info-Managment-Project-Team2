package edu.westga.dsdm.project.model;

import java.time.LocalDateTime;
import java.util.List;

import edu.westga.dsdm.project.azurepgsql.DBEventOps;

/**
 * Provides higher-level operations for managing events.
 *
 * Wraps DBEventOps for use by UI controllers.
 * 
 * @author Jacob Baker
 * @version Fall 2025
 */
public class EventManager {

    public static List<Event> getEventsForOrganizer(int organizerUserId) throws Exception {
        return DBEventOps.findByOrganizer(organizerUserId);
    }

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
