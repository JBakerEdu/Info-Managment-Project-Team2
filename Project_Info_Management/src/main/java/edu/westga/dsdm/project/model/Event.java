package edu.westga.dsdm.project.model;

import java.time.LocalDateTime;

/**
 * Represents an event owned by an organizer.
 *
 * Mirrors the events table in the database.
 *
 * Columns:
 *  - event_id SERIAL PRIMARY KEY
 *  - user_id INT NOT NULL REFERENCES organizers(user_id)
 *  - title VARCHAR(255) NOT NULL
 *  - event_location VARCHAR(50) NOT NULL
 *  - description TEXT
 *  - rubric TEXT
 *  - start_datetime TIMESTAMP NOT NULL
 *  - end_datetime TIMESTAMP NOT NULL
 *  - status VARCHAR(50) DEFAULT 'Scheduled'
 * 
 * @author Jacob Baker
 * @version Fall 2025
 */
public class Event {

    private int eventId;
    private int organizerUserId;
    private String title;
    private String eventLocation;
    private String description;
    private String rubric;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String status;

    /**
     * Gets the event ID.
     * 
     * @return the event ID
     */
    public int getEventId() {
        return this.eventId;
    }

    /**
     * Sets the event ID.
     * 
     * @param eventId the event ID to set
     */
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    /**
     * Gets the organizer's user ID.
     * 
     * @return the organizer user ID
     */
    public int getOrganizerUserId() {
        return this.organizerUserId;
    }

    /**
     * Sets the organizer's user ID.
     * 
     * @param organizerUserId the organizer's user ID to set
     */
    public void setOrganizerUserId(int organizerUserId) {
        this.organizerUserId = organizerUserId;
    }

    /**
     * Gets the event title.
     * 
     * @return the event title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Sets the event title.
     * 
     * @param title the event title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the event location.
     * 
     * @return the event location
     */
    public String getEventLocation() {
        return this.eventLocation;
    }

    /**
     * Sets the event location.
     * 
     * @param eventLocation the event location to set
     */
    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    /**
     * Gets the event description.
     * 
     * @return the event description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the event description.
     * 
     * @param description the event description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the event rubric.
     * 
     * @return the event rubric
     */
    public String getRubric() {
        return this.rubric;
    }

    /**
     * Sets the event rubric.
     * 
     * @param rubric the event rubric to set
     */
    public void setRubric(String rubric) {
        this.rubric = rubric;
    }

    /**
     * Gets the event's start date and time.
     * 
     * @return the event's start date and time
     */
    public LocalDateTime getStartDateTime() {
        return this.startDateTime;
    }

    /**
     * Sets the event's start date and time.
     * 
     * @param startDateTime the event's start date and time to set
     */
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * Gets the event's end date and time.
     * 
     * @return the event's end date and time
     */
    public LocalDateTime getEndDateTime() {
        return this.endDateTime;
    }

    /**
     * Sets the event's end date and time.
     * 
     * @param endDateTime the event's end date and time to set
     */
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     * Gets the event status (e.g., "Scheduled").
     * 
     * @return the event status
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * Sets the event status.
     * 
     * @param status the event status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
