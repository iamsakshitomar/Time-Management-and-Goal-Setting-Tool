package timemanagement;

import java.time.LocalDate;
import java.util.List;

public class ReminderService implements Runnable {
    private final int userId;
    private final TaskDAO taskDAO = new TaskDAO();
    private final Reminder reminder = new DeadlineReminder();
    private volatile boolean running = true;

    public ReminderService(int userId) {
        this.userId = userId;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                List<Task> tasks = taskDAO.findByUser(userId);
                LocalDate today = LocalDate.now();

                for (Task task : tasks) {
                    if (!"Completed".equalsIgnoreCase(task.getStatus())
                            && task.getDeadline() != null
                            && !task.getDeadline().isBlank()) {

                        LocalDate deadline = LocalDate.parse(task.getDeadline());

                        if (!deadline.isBefore(today)
                                && !deadline.isAfter(today.plusDays(1))) {
                            javax.swing.SwingUtilities.invokeLater(
                                    () -> reminder.sendReminder(task)
                            );
                        }
                    }
                }

                Thread.sleep(60000);

            } catch (Exception ignored) {
                // Background service must not crash the GUI.
            }
        }
    }
}
