package timemanagement;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DashboardFrame extends JFrame {
    private final int userId;
    private final GoalDAO goalDAO = new GoalDAO();
    private final TaskDAO taskDAO = new TaskDAO();
    private final ScheduleDAO scheduleDAO = new ScheduleDAO();

    private final DefaultListModel<String> goalModel = new DefaultListModel<>();
    private final DefaultListModel<String> taskModel = new DefaultListModel<>();
    private final DefaultListModel<String> scheduleModel = new DefaultListModel<>();

    private final JLabel summary = new JLabel();

    private List<Goal> currentGoals = List.of();
    private List<Task> currentTasks = List.of();

    private ReminderService reminderService;

    public DashboardFrame(int userId) {
        this.userId = userId;

        setTitle("Time Management Dashboard");
        setSize(1050, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buildUI();
        refreshAll();
        startReminderThread();
    }

    private void buildUI() {
        AppTheme.install();
        getContentPane().setBackground(AppTheme.BACKGROUND);

        JPanel header = new JPanel(new BorderLayout(12, 8));
        header.setBackground(AppTheme.NAVY);
        header.setBorder(BorderFactory.createEmptyBorder(22, 26, 20, 26));

        JPanel titleBlock = new JPanel();
        titleBlock.setOpaque(false);
        titleBlock.setLayout(new BoxLayout(titleBlock, BoxLayout.Y_AXIS));
        JLabel eyebrow = new JLabel("FOCUSFLOW  /  PERSONAL WORKSPACE");
        eyebrow.setFont(new Font("Segoe UI", Font.BOLD, 10));
        eyebrow.setForeground(new Color(125, 210, 202));
        JLabel title = new JLabel("Your goals. Your time. Your progress.");
        title.setFont(new Font("Segoe UI", Font.BOLD, 23));
        title.setForeground(Color.WHITE);
        JLabel subtitle = new JLabel("Build momentum with a plan that works for you.");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(213, 220, 236));
        titleBlock.add(eyebrow);
        titleBlock.add(Box.createVerticalStrut(6));
        titleBlock.add(title);
        titleBlock.add(Box.createVerticalStrut(5));
        titleBlock.add(subtitle);

        summary.setFont(new Font("Segoe UI", Font.BOLD, 12));
        summary.setForeground(Color.WHITE);
        summary.setOpaque(true);
        summary.setBackground(new Color(43, 57, 91));
        summary.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        header.add(titleBlock, BorderLayout.CENTER);
        header.add(summary, BorderLayout.SOUTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabs.setBackground(AppTheme.BACKGROUND);
        tabs.setForeground(AppTheme.TEXT);
        tabs.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));
        tabs.addTab("  Goals  ", goalPanel());
        tabs.addTab("  Tasks  ", taskPanel());
        tabs.addTab("  Schedule  ", schedulePanel());
        tabs.addTab("  Progress  ", progressPanel());

        JButton refresh = new JButton("↻  Refresh");
        JButton logout = new JButton("Log out");
        AppTheme.styleButton(refresh, false);
        AppTheme.styleButton(logout, true);
        refresh.addActionListener(e -> refreshAll());
        logout.addActionListener(e -> {
            stopReminderThread();
            new LoginFrame().setVisible(true);
            dispose();
        });

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottom.setBackground(AppTheme.BACKGROUND);
        bottom.setBorder(BorderFactory.createEmptyBorder(0, 18, 12, 18));
        bottom.add(refresh);
        bottom.add(logout);

        setLayout(new BorderLayout());
        add(header, BorderLayout.NORTH);
        add(tabs, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    private JPanel goalPanel() {
        JPanel panel = contentPanel();
        JList<String> list = new JList<>(goalModel);
        AppTheme.styleList(list);
        JScrollPane scroll = styledScroll(list);

        JButton add = actionButton("+  Add Goal", true);
        JButton update = actionButton("Edit selected", false);
        JButton delete = actionButton("Delete selected", false);
        add.addActionListener(e -> addGoal());
        update.addActionListener(e -> updateGoal(list.getSelectedIndex()));
        delete.addActionListener(e -> deleteGoal(list.getSelectedIndex()));

        panel.add(sectionHeader("Goals", "Set clear targets and keep your priorities visible."), BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(actionBar(add, update, delete), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel taskPanel() {
        JPanel panel = contentPanel();
        JList<String> list = new JList<>(taskModel);
        AppTheme.styleList(list);
        JScrollPane scroll = styledScroll(list);

        JButton add = actionButton("+  Add Task", true);
        JButton update = actionButton("Edit selected", false);
        JButton delete = actionButton("Delete selected", false);
        add.addActionListener(e -> addTask());
        update.addActionListener(e -> updateTask(list.getSelectedIndex()));
        delete.addActionListener(e -> deleteTask(list.getSelectedIndex()));

        panel.add(sectionHeader("Tasks", "Turn your goals into manageable next steps."), BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(actionBar(add, update, delete), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel schedulePanel() {
        JPanel panel = contentPanel();
        JList<String> list = new JList<>(scheduleModel);
        AppTheme.styleList(list);
        JButton add = actionButton("+  Add Schedule", true);
        add.addActionListener(e -> addSchedule());
        panel.add(sectionHeader("Schedule", "Give important work a dedicated time slot."), BorderLayout.NORTH);
        panel.add(styledScroll(list), BorderLayout.CENTER);
        panel.add(actionBar(add), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel progressPanel() {
        JPanel panel = contentPanel();
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        area.setForeground(AppTheme.TEXT);
        area.setBackground(AppTheme.CARD);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        JButton refresh = actionButton("Calculate progress", true);
        refresh.addActionListener(e -> updateProgress(area));
        panel.add(sectionHeader("Progress overview", "Review completed tasks and momentum for each goal."), BorderLayout.NORTH);
        panel.add(styledScroll(area), BorderLayout.CENTER);
        panel.add(actionBar(refresh), BorderLayout.SOUTH);
        updateProgress(area);
        return panel;
    }

    private JPanel contentPanel() {
        JPanel panel = new JPanel(new BorderLayout(14, 14));
        panel.setBackground(AppTheme.BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 4, 4, 4));
        return panel;
    }

    private JPanel sectionHeader(String titleText, String subtitleText) {
        JPanel panel = new JPanel();
        panel.setBackground(AppTheme.BACKGROUND);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel title = AppTheme.heading(titleText, 19);
        JLabel subtitle = AppTheme.muted(subtitleText);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createVerticalStrut(4));
        panel.add(subtitle);
        return panel;
    }

    private JScrollPane styledScroll(Component component) {
        JScrollPane scroll = new JScrollPane(component);
        scroll.setBorder(BorderFactory.createLineBorder(AppTheme.BORDER));
        scroll.getViewport().setBackground(AppTheme.CARD);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    private JButton actionButton(String text, boolean primary) {
        JButton button = new JButton(text);
        AppTheme.styleButton(button, primary);
        return button;
    }

    private JPanel actionBar(JButton... buttons) {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        bar.setBackground(AppTheme.BACKGROUND);
        for (JButton button : buttons) bar.add(button);
        return bar;
    }

    private void refreshAll() {
        try {
            currentGoals = goalDAO.findByUser(userId);
            currentTasks = taskDAO.findByUser(userId);

            goalModel.clear();
            for (Goal g : currentGoals) {
                goalModel.addElement(g.toString());
            }

            taskModel.clear();
            for (Task t : currentTasks) {
                taskModel.addElement(t.toString());
            }

            scheduleModel.clear();
            for (String row : scheduleDAO.findForUser(userId)) {
                scheduleModel.addElement(row);
            }

            long completed = currentTasks.stream()
                    .filter(t -> "Completed".equalsIgnoreCase(t.getStatus()))
                    .count();

            summary.setText(
                    "Goals: " + currentGoals.size()
                    + "     Tasks: " + currentTasks.size()
                    + "     Completed: " + completed
                    + "     Pending: " + (currentTasks.size() - completed)
            );

        } catch (Exception e) {
            JOptionPaneUtil.error(this, "Refresh failed:\n" + e.getMessage());
        }
    }

    private void addGoal() {
        JTextField name = new JTextField();
        JTextField description = new JTextField();
        JTextField start = new JTextField(java.time.LocalDate.now().toString());
        JTextField target = new JTextField(java.time.LocalDate.now().plusDays(7).toString());
        JComboBox<String> priority = new JComboBox<>(new String[]{"Low", "Medium", "High"});
        JTextField category = new JTextField("Study");

        Object[] fields = {
                "Goal Name:", name,
                "Description:", description,
                "Start Date (YYYY-MM-DD):", start,
                "Target Date (YYYY-MM-DD):", target,
                "Priority:", priority,
                "Category:", category
        };

        int result = JOptionPane.showConfirmDialog(
                this, fields, "Add Goal", JOptionPane.OK_CANCEL_OPTION);

        if (result != JOptionPane.OK_OPTION) return;

        try {
            Validator.validateRequired(name.getText(), "Goal name");
            Validator.validateDate(start.getText().trim(), "Start date");
            Validator.validateDate(target.getText().trim(), "Target date");

            Goal goal = new Goal(
                    userId,
                    name.getText().trim(),
                    description.getText().trim(),
                    start.getText().trim(),
                    target.getText().trim(),
                    priority.getSelectedItem().toString(),
                    category.getText().trim(),
                    "Active"
            );

            goalDAO.insert(goal);
            refreshAll();

        } catch (Exception e) {
            JOptionPaneUtil.error(this, e.getMessage());
        }
    }

    private void updateGoal(int index) {
        if (index < 0 || index >= currentGoals.size()) {
            JOptionPaneUtil.error(this, "Select a goal first.");
            return;
        }

        Goal old = currentGoals.get(index);

        JTextField name = new JTextField(old.getName());
        JTextField description = new JTextField(old.getDescription());
        JTextField start = new JTextField(old.getStartDate());
        JTextField target = new JTextField(old.getTargetDate());
        JComboBox<String> priority = new JComboBox<>(new String[]{"Low", "Medium", "High"});
        priority.setSelectedItem(old.getPriority());
        JTextField category = new JTextField(old.getCategory());
        JComboBox<String> status = new JComboBox<>(new String[]{"Active", "Completed"});
        status.setSelectedItem(old.getStatus());

        Object[] fields = {
                "Goal Name:", name,
                "Description:", description,
                "Start Date:", start,
                "Target Date:", target,
                "Priority:", priority,
                "Category:", category,
                "Status:", status
        };

        if (JOptionPane.showConfirmDialog(
                this, fields, "Update Goal", JOptionPane.OK_CANCEL_OPTION)
                != JOptionPane.OK_OPTION) return;

        try {
            Validator.validateRequired(name.getText(), "Goal name");
            Validator.validateDate(start.getText().trim(), "Start date");
            Validator.validateDate(target.getText().trim(), "Target date");

            old.setName(name.getText().trim());
            old.setDescription(description.getText().trim());
            old.setStartDate(start.getText().trim());
            old.setTargetDate(target.getText().trim());
            old.setPriority(priority.getSelectedItem().toString());
            old.setCategory(category.getText().trim());
            old.setStatus(status.getSelectedItem().toString());

            goalDAO.update(old);
            refreshAll();

        } catch (Exception e) {
            JOptionPaneUtil.error(this, e.getMessage());
        }
    }

    private void deleteGoal(int index) {
        if (index < 0 || index >= currentGoals.size()) {
            JOptionPaneUtil.error(this, "Select a goal first.");
            return;
        }

        if (JOptionPane.showConfirmDialog(
                this, "Delete selected goal and its tasks?",
                "Confirm", JOptionPane.YES_NO_OPTION)
                != JOptionPane.YES_OPTION) return;

        try {
            goalDAO.delete(currentGoals.get(index).getId(), userId);
            refreshAll();
        } catch (Exception e) {
            JOptionPaneUtil.error(this, "Delete failed:\n" + e.getMessage());
        }
    }

    private void addTask() {
        if (currentGoals.isEmpty()) {
            JOptionPaneUtil.error(this, "Create a goal before adding a task.");
            return;
        }

        JComboBox<Goal> goalBox = new JComboBox<>(currentGoals.toArray(new Goal[0]));
        JTextField name = new JTextField();
        JTextField description = new JTextField();
        JComboBox<String> priority = new JComboBox<>(new String[]{"Low", "Medium", "High"});
        JTextField deadline = new JTextField(java.time.LocalDate.now().plusDays(2).toString());
        JTextField minutes = new JTextField("60");

        Object[] fields = {
                "Goal:", goalBox,
                "Task Name:", name,
                "Description:", description,
                "Priority:", priority,
                "Deadline (YYYY-MM-DD):", deadline,
                "Estimated Minutes:", minutes
        };

        if (JOptionPane.showConfirmDialog(
                this, fields, "Add Task", JOptionPane.OK_CANCEL_OPTION)
                != JOptionPane.OK_OPTION) return;

        try {
            Validator.validateRequired(name.getText(), "Task name");
            Validator.validateDate(deadline.getText().trim(), "Deadline");
            int mins = Validator.minutes(minutes.getText().trim());

            Goal goal = (Goal) goalBox.getSelectedItem();

            Task task = new Task(
                    goal.getId(),
                    name.getText().trim(),
                    description.getText().trim(),
                    priority.getSelectedItem().toString(),
                    deadline.getText().trim(),
                    mins,
                    "Pending"
            );

            taskDAO.insert(task);
            refreshAll();

        } catch (Exception e) {
            JOptionPaneUtil.error(this, e.getMessage());
        }
    }

    private void updateTask(int index) {
        if (index < 0 || index >= currentTasks.size()) {
            JOptionPaneUtil.error(this, "Select a task first.");
            return;
        }

        Task old = currentTasks.get(index);

        JTextField name = new JTextField(old.getName());
        JTextField description = new JTextField(old.getDescription());
        JComboBox<String> priority = new JComboBox<>(new String[]{"Low", "Medium", "High"});
        priority.setSelectedItem(old.getPriority());
        JTextField deadline = new JTextField(old.getDeadline());
        JTextField minutes = new JTextField(String.valueOf(old.getEstimatedMinutes()));
        JComboBox<String> status =
                new JComboBox<>(new String[]{"Pending", "In Progress", "Completed"});
        status.setSelectedItem(old.getStatus());

        Object[] fields = {
                "Task Name:", name,
                "Description:", description,
                "Priority:", priority,
                "Deadline:", deadline,
                "Estimated Minutes:", minutes,
                "Status:", status
        };

        if (JOptionPane.showConfirmDialog(
                this, fields, "Update Task", JOptionPane.OK_CANCEL_OPTION)
                != JOptionPane.OK_OPTION) return;

        try {
            Validator.validateRequired(name.getText(), "Task name");
            Validator.validateDate(deadline.getText().trim(), "Deadline");

            old.setName(name.getText().trim());
            old.setDescription(description.getText().trim());
            old.setPriority(priority.getSelectedItem().toString());
            old.setDeadline(deadline.getText().trim());
            old.setEstimatedMinutes(Validator.minutes(minutes.getText().trim()));
            old.setStatus(status.getSelectedItem().toString());

            taskDAO.update(old);
            refreshAll();

        } catch (Exception e) {
            JOptionPaneUtil.error(this, e.getMessage());
        }
    }

    private void deleteTask(int index) {
        if (index < 0 || index >= currentTasks.size()) {
            JOptionPaneUtil.error(this, "Select a task first.");
            return;
        }

        if (JOptionPane.showConfirmDialog(
                this, "Delete selected task?",
                "Confirm", JOptionPane.YES_NO_OPTION)
                != JOptionPane.YES_OPTION) return;

        try {
            taskDAO.delete(currentTasks.get(index).getId(), userId);
            refreshAll();
        } catch (Exception e) {
            JOptionPaneUtil.error(this, "Delete failed:\n" + e.getMessage());
        }
    }

    private void addSchedule() {
        if (currentTasks.isEmpty()) {
            JOptionPaneUtil.error(this, "Create a task first.");
            return;
        }

        JComboBox<Task> taskBox = new JComboBox<>(currentTasks.toArray(new Task[0]));
        JTextField date = new JTextField(java.time.LocalDate.now().toString());
        JTextField start = new JTextField("18:00");
        JTextField end = new JTextField("19:00");

        Object[] fields = {
                "Task:", taskBox,
                "Date (YYYY-MM-DD):", date,
                "Start Time:", start,
                "End Time:", end
        };

        if (JOptionPane.showConfirmDialog(
                this, fields, "Add Schedule", JOptionPane.OK_CANCEL_OPTION)
                != JOptionPane.OK_OPTION) return;

        try {
            Validator.validateDate(date.getText().trim(), "Date");

            Task task = (Task) taskBox.getSelectedItem();

            scheduleDAO.insert(
                    new Schedule(
                            task.getId(),
                            date.getText().trim(),
                            start.getText().trim(),
                            end.getText().trim()
                    ),
                    userId
            );

            refreshAll();

        } catch (Exception e) {
            JOptionPaneUtil.error(this, "Schedule failed:\n" + e.getMessage());
        }
    }

    private void updateProgress(JTextArea area) {
        StringBuilder sb = new StringBuilder();

        if (currentGoals.isEmpty()) {
            area.setText("No goals available.");
            return;
        }

        for (Goal goal : currentGoals) {
            long total = currentTasks.stream()
                    .filter(t -> t.getGoalId() == goal.getId())
                    .count();

            long completed = currentTasks.stream()
                    .filter(t -> t.getGoalId() == goal.getId())
                    .filter(t -> "Completed".equalsIgnoreCase(t.getStatus()))
                    .count();

            int percent = total == 0 ? 0 : (int) ((completed * 100) / total);

            sb.append("Goal: ").append(goal.getName()).append("\n");
            sb.append("Completed: ").append(completed)
                    .append("/").append(total).append("\n");
            sb.append("Progress: ").append(percent).append("%\n");
            sb.append("--------------------------------------------------\n");
        }

        area.setText(sb.toString());
    }

    private void startReminderThread() {
        reminderService = new ReminderService(userId);
        Thread thread = new Thread(reminderService, "Deadline-Reminder-Thread");
        thread.setDaemon(true);
        thread.start();
    }

    private void stopReminderThread() {
        if (reminderService != null) {
            reminderService.stop();
        }
    }
}
