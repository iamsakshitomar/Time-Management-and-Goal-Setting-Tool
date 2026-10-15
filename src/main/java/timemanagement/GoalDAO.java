package timemanagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GoalDAO {

    public int insert(Goal goal) throws SQLException {
        String sql = """
            INSERT INTO goals(user_id,name,description,start_date,target_date,priority,category,status)
            VALUES(?,?,?,?,?,?,?,?)
            """;

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, goal.getUserId());
            ps.setString(2, goal.getName());
            ps.setString(3, goal.getDescription());
            ps.setString(4, goal.getStartDate());
            ps.setString(5, goal.getTargetDate());
            ps.setString(6, goal.getPriority());
            ps.setString(7, goal.getCategory());
            ps.setString(8, goal.getStatus());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    public List<Goal> findByUser(int userId) throws SQLException {
        List<Goal> list = new ArrayList<>();
        String sql = "SELECT * FROM goals WHERE user_id=? ORDER BY target_date";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Goal(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getString("start_date"),
                            rs.getString("target_date"),
                            rs.getString("priority"),
                            rs.getString("category"),
                            rs.getString("status")
                    ));
                }
            }
        }
        return list;
    }

    public void update(Goal goal) throws SQLException {
        String sql = """
            UPDATE goals
            SET name=?, description=?, start_date=?, target_date=?,
                priority=?, category=?, status=?
            WHERE id=? AND user_id=?
            """;

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, goal.getName());
            ps.setString(2, goal.getDescription());
            ps.setString(3, goal.getStartDate());
            ps.setString(4, goal.getTargetDate());
            ps.setString(5, goal.getPriority());
            ps.setString(6, goal.getCategory());
            ps.setString(7, goal.getStatus());
            ps.setInt(8, goal.getId());
            ps.setInt(9, goal.getUserId());
            ps.executeUpdate();
        }
    }

    public void delete(int goalId, int userId) throws SQLException {
        String sql = "DELETE FROM goals WHERE id=? AND user_id=?";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, goalId);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }

    // Demonstrates JDBC transaction management.
    public void insertGoalWithFirstTask(Goal goal, Task task) throws SQLException {
        String goalSql = """
            INSERT INTO goals(user_id,name,description,start_date,target_date,priority,category,status)
            VALUES(?,?,?,?,?,?,?,?)
            """;

        String taskSql = """
            INSERT INTO tasks(goal_id,name,description,priority,deadline,estimated_minutes,status)
            VALUES(?,?,?,?,?,?,?)
            """;

        try (Connection con = Database.getConnection()) {
            con.setAutoCommit(false);

            try {
                int goalId;

                try (PreparedStatement ps = con.prepareStatement(
                        goalSql, Statement.RETURN_GENERATED_KEYS)) {

                    ps.setInt(1, goal.getUserId());
                    ps.setString(2, goal.getName());
                    ps.setString(3, goal.getDescription());
                    ps.setString(4, goal.getStartDate());
                    ps.setString(5, goal.getTargetDate());
                    ps.setString(6, goal.getPriority());
                    ps.setString(7, goal.getCategory());
                    ps.setString(8, goal.getStatus());
                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (!rs.next()) throw new SQLException("Goal ID was not generated.");
                        goalId = rs.getInt(1);
                    }
                }

                try (PreparedStatement ps = con.prepareStatement(taskSql)) {
                    ps.setInt(1, goalId);
                    ps.setString(2, task.getName());
                    ps.setString(3, task.getDescription());
                    ps.setString(4, task.getPriority());
                    ps.setString(5, task.getDeadline());
                    ps.setInt(6, task.getEstimatedMinutes());
                    ps.setString(7, task.getStatus());
                    ps.executeUpdate();
                }

                con.commit();

            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }
}
