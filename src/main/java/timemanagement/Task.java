package timemanagement;

public class Task extends GoalItem {
    private int goalId;
    private String description;
    private String priority;
    private String deadline;
    private int estimatedMinutes;

    public Task(int id, int goalId, String name, String description,
                String priority, String deadline, int estimatedMinutes,
                String status) {
        super(id, name, status);
        this.goalId = goalId;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
        this.estimatedMinutes = estimatedMinutes;
    }

    public Task(int goalId, String name, String description,
                String priority, String deadline, int estimatedMinutes,
                String status) {
        this(0, goalId, name, description, priority, deadline, estimatedMinutes, status);
    }

    public int getGoalId() { return goalId; }
    public String getDescription() { return description; }
    public String getPriority() { return priority; }
    public String getDeadline() { return deadline; }
    public int getEstimatedMinutes() { return estimatedMinutes; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPriority(String priority) { this.priority = priority; }
    public void setDeadline(String deadline) { this.deadline = deadline; }
    public void setEstimatedMinutes(int estimatedMinutes) { this.estimatedMinutes = estimatedMinutes; }

    @Override
    public String getDisplayText() {
        return name + " | " + priority + " | " + status + " | Due: " + deadline;
    }

    @Override
    public String toString() {
        return id + " - " + getDisplayText();
    }
}
