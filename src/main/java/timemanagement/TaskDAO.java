package timemanagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    public int insert(Task task) throws SQLException {
        String sql = """
            INSERT INTO tasks(goal_id,name,description,priority,deadline,estimated_minutes,status)
            VALUES(?,?,?,?,?,?,?)
            """;

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, task.getGoalId());
            ps.setString(2, task.getName());
            ps.setString(3, task.getDescription());
            ps.setString(4, task.getPriority());
            ps.setString(5, task.getDeadline());
            ps.setInt(6, task.getEstimatedMinutes());
            ps.setString(7, task.getStatus());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    public List<Task> findByUser(int userId) throws SQLException {
        List<Task> list = new ArrayList<>();

        String sql = """
            SELECT t.*
            FROM tasks t
            JOIN goals g ON t.goal_id = g.id
            WHERE g.user_id=?
            ORDER BY t.deadline
            """;

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Task(
                            rs.getInt("id"),
                            rs.getInt("goal_id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getString("priority"),
                            rs.getString("deadline"),
                            rs.getInt("estimated_minutes"),
                            rs.getString("status")
                    ));
                }
            }
        }

        return list;
    }

    public void update(Task task) throws SQLException {
        String sql = """
            UPDATE tasks
            SET name=?, description=?, priority=?, deadline=?,
                estimated_minutes=?, status=?
            WHERE id=?
            AND goal_id IN (SELECT id FROM goals WHERE user_id=?)
            """;

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, task.getName());
            ps.setString(2, task.getDescription());
            ps.setString(3, task.getPriority());
            ps.setString(4, task.getDeadline());
            ps.setInt(5, task.getEstimatedMinutes());
            ps.setString(6, task.getStatus());
            ps.setInt(7, task.getId());
            ps.setInt(8, task.getGoalId());
            ps.executeUpdate();
        }
    }

    public void delete(int taskId, int userId) throws SQLException {
        String sql = """
            DELETE FROM tasks
            WHERE id=?
            AND goal_id IN (SELECT id FROM goals WHERE user_id=?)
            """;

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, taskId);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }
}
