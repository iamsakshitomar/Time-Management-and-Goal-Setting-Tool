package timemanagement;

import javax.swing.JOptionPane;

public class DeadlineReminder implements Reminder {
    @Override
    public void sendReminder(Task task) {
        JOptionPane.showMessageDialog(
                null,
                "Upcoming deadline:\n" + task.getName() + "\nDue: " + task.getDeadline(),
                "Task Reminder",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
