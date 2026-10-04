package timemanagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {

    public void insert(Schedule schedule, int userId) throws SQLException {
        String sql = """
            INSERT INTO schedules(task_id,schedule_date,start_time,end_time)
            SELECT ?,?,?,?
            WHERE EXISTS (
                SELECT 1 FROM tasks t
                JOIN goals g ON t.goal_id=g.id
                WHERE t.id=? AND g.user_id=?
            )
            """;

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, schedule.getTaskId());
            ps.setString(2, schedule.getDate());
            ps.setString(3, schedule.getStartTime());
            ps.setString(4, schedule.getEndTime());
            ps.setInt(5, schedule.getTaskId());
            ps.setInt(6, userId);
            ps.executeUpdate();
        }
    }

    public List<String> findForUser(int userId) throws SQLException {
        List<String> rows = new ArrayList<>();

        String sql = """
            SELECT s.id, t.name, s.schedule_date, s.start_time, s.end_time
            FROM schedules s
            JOIN tasks t ON s.task_id=t.id
            JOIN goals g ON t.goal_id=g.id
            WHERE g.user_id=?
            ORDER BY s.schedule_date, s.start_time
            """;

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rows.add(
                        rs.getInt("id") + " | " +
                        rs.getString("name") + " | " +
                        rs.getString("schedule_date") + " | " +
                        rs.getString("start_time") + " - " +
                        rs.getString("end_time")
                    );
                }
            }
        }

        return rows;
    }
}
