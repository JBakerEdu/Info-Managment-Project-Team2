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

    public int getEventId() {
        return this.eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getOrganizerUserId() {
        return this.organizerUserId;
    }

    public void setOrganizerUserId(int organizerUserId) {
        this.organizerUserId = organizerUserId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEventLocation() {
        return this.eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRubric() {
        return this.rubric;
    }

    public void setRubric(String rubric) {
        this.rubric = rubric;
    }

    public LocalDateTime getStartDateTime() {
        return this.startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return this.endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
