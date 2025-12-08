package edu.westga.dsdm.project.azurepgsql;

import edu.westga.dsdm.project.model.Event;
import edu.westga.dsdm.project.model.User;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DBEventOps class provides database operations for handling events.
 * This includes methods to insert, delete, retrieve, and map events from the database.
 * 
 * @author Jacob Baker
 * @version Fall 2025
 */
public class DBEventOps {

    /**
     * Maps a ResultSet row to an Event object.
     *
     * @param rs the ResultSet containing the event data
     * @return an Event object populated with data from the ResultSet
     * @throws SQLException if an SQL error occurs while processing the result set
     */
    private static Event mapRow(ResultSet rs) throws SQLException {
        Event e = new Event();
        e.setEventId(rs.getInt("event_id"));
        e.setOrganizerUserId(rs.getInt("user_id"));
        e.setTitle(rs.getString("title"));
        e.setEventLocation(rs.getString("event_location"));
        e.setDescription(rs.getString("description"));
        e.setRubric(rs.getString("rubric"));

        Timestamp start = rs.getTimestamp("start_datetime");
        Timestamp end = rs.getTimestamp("end_datetime");

        if (start != null) e.setStartDateTime(start.toLocalDateTime());
        if (end != null) e.setEndDateTime(end.toLocalDateTime());

        e.setStatus(rs.getString("status"));

        return e;
    }

    /**
     * Retrieves a list of events by the organizer's user ID.
     *
     * @param organizerUserId the user ID of the event organizer
     * @return a list of events created by the given organizer
     * @throws Exception if an error occurs while querying the database
     */
    public static List<Event> findByOrganizer(int organizerUserId) throws Exception {
        String sql = "SELECT * FROM events WHERE user_id = ?";
        Connection conn = AzurePgsqlServer.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, organizerUserId);
        ResultSet rs = stmt.executeQuery();

        List<Event> list = new ArrayList<>();
        while (rs.next()) {
            list.add(mapRow(rs));
        }
        return list;
    }

    /**
     * Inserts a new event into the database.
     *
     * @param organizerUserId the user ID of the event organizer
     * @param title the title of the event
     * @param location the location of the event
     * @param description a description of the event
     * @param rubric the rubric for the event
     * @param start the start date and time of the event
     * @param end the end date and time of the event
     * @return the newly created Event object
     * @throws Exception if an error occurs while inserting the event into the database
     */
    public static Event insertEvent(
            int organizerUserId,
            String title,
            String location,
            String description,
            String rubric,
            LocalDateTime start,
            LocalDateTime end
    ) throws Exception {

        String sql = "INSERT INTO events (user_id, title, event_location, description, rubric, start_datetime, end_datetime) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING event_id, status";

        Connection conn = AzurePgsqlServer.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, organizerUserId);
        stmt.setString(2, title);
        stmt.setString(3, location);
        stmt.setString(4, description);
        stmt.setString(5, rubric);
        stmt.setTimestamp(6, Timestamp.valueOf(start));
        stmt.setTimestamp(7, Timestamp.valueOf(end));

        ResultSet rs = stmt.executeQuery();
        rs.next();

        Event event = new Event();
        event.setEventId(rs.getInt("event_id"));
        event.setStatus(rs.getString("status"));
        event.setOrganizerUserId(organizerUserId);
        event.setTitle(title);
        event.setEventLocation(location);
        event.setDescription(description);
        event.setRubric(rubric);
        event.setStartDateTime(start);
        event.setEndDateTime(end);

        return event;
    }

    /**
     * Deletes an event from the database by event ID.
     *
     * @param eventId the ID of the event to be deleted
     * @throws Exception if an error occurs while deleting the event from the database
     */
    public static void deleteEvent(int eventId) throws Exception {
        String sql = "DELETE FROM events WHERE event_id = ?";

        try (Connection conn = AzurePgsqlServer.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, eventId);
            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves a list of all events from the database.
     *
     * @return a list of all events in the database
     * @throws Exception if an error occurs while querying the database
     */
    public static List<Event> findAllEvents() throws Exception {
        String sql = "SELECT event_id, user_id, title, event_location, description, rubric, "
                + "start_datetime, end_datetime, status "
                + "FROM events";

        List<Event> events = new ArrayList<>();

        try (Connection conn = AzurePgsqlServer.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                events.add(mapRow(rs));
            }
        }

        return events;
    }

    public static void registerStudentToEvent(User teacher, Event event, User student) throws Exception {
        String sql = "{ CALL register_student_for_event(?, ?, ?) }";

        try {
            Connection conn = AzurePgsqlServer.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, teacher.getUserId());
            stmt.setInt(2, event.getEventId());
            stmt.setInt(3, student.getUserId());

            System.out.println(teacher.getUserId() + ", " + event.getEventId() + ", " + student.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<int> getRegisteredEvents(int userId) throws Exception {
        String sql = "SELECT * FROM event_registrations WHERE user_id = ?";

        Connection conn = AzurePgsqlServer.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();

        List<int> list = new ArrayList<>();
        while (rs.next()) {
            list.add(mapRow(rs));
        }
        return list;
    }
}
