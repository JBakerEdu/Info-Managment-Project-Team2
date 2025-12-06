package edu.westga.dsdm.project.azurepgsql;

import edu.westga.dsdm.project.model.Event;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DBEventOps {

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

    public static void deleteEvent(int eventId) throws Exception {
        String sql = "DELETE FROM events WHERE event_id = ?";

        try (Connection conn = AzurePgsqlServer.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, eventId);
            stmt.executeUpdate();
        }
    }

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


}
