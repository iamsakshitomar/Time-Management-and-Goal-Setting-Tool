package timemanagement;

import java.sql.*;

public final class Database {
    private static final String URL = "jdbc:sqlite:time_management.db";

    private Database() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initialize() {
        String users = """
            CREATE TABLE IF NOT EXISTS users(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                email TEXT NOT NULL UNIQUE,
                password_hash TEXT NOT NULL
            )
            """;

        String goals = """
            CREATE TABLE IF NOT EXISTS goals(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL,
                name TEXT NOT NULL,
                description TEXT,
                start_date TEXT,
                target_date TEXT,
                priority TEXT,
                category TEXT,
                status TEXT,
                FOREIGN KEY(user_id) REFERENCES users(id)
            )
            """;

        String tasks = """
            CREATE TABLE IF NOT EXISTS tasks(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                goal_id INTEGER NOT NULL,
                name TEXT NOT NULL,
                description TEXT,
                priority TEXT,
                deadline TEXT,
                estimated_minutes INTEGER,
                status TEXT,
                FOREIGN KEY(goal_id) REFERENCES goals(id) ON DELETE CASCADE
            )
            """;

        String schedules = """
            CREATE TABLE IF NOT EXISTS schedules(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                task_id INTEGER NOT NULL,
                schedule_date TEXT NOT NULL,
                start_time TEXT NOT NULL,
                end_time TEXT NOT NULL,
                FOREIGN KEY(task_id) REFERENCES tasks(id) ON DELETE CASCADE
            )
            """;

        try (Connection con = getConnection();
             Statement st = con.createStatement()) {

            st.execute("PRAGMA foreign_keys = ON");
            st.executeUpdate(users);
            st.executeUpdate(goals);
            st.executeUpdate(tasks);
            st.executeUpdate(schedules);

        } catch (SQLException e) {
            JOptionPaneUtil.error("Database initialization failed:\n" + e.getMessage());
        }
    }
}
