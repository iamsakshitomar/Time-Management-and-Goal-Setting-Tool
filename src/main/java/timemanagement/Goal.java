package timemanagement;

public class Goal extends GoalItem {
    private int userId;
    private String description;
    private String startDate;
    private String targetDate;
    private String priority;
    private String category;

    public Goal(int id, int userId, String name, String description,
                String startDate, String targetDate, String priority,
                String category, String status) {
        super(id, name, status);
        this.userId = userId;
        this.description = description;
        this.startDate = startDate;
        this.targetDate = targetDate;
        this.priority = priority;
        this.category = category;
    }

    public Goal(int userId, String name, String description,
                String startDate, String targetDate, String priority,
                String category, String status) {
        this(0, userId, name, description, startDate, targetDate, priority, category, status);
    }

    public int getUserId() { return userId; }
    public String getDescription() { return description; }
    public String getStartDate() { return startDate; }
    public String getTargetDate() { return targetDate; }
    public String getPriority() { return priority; }
    public String getCategory() { return category; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public void setTargetDate(String targetDate) { this.targetDate = targetDate; }
    public void setPriority(String priority) { this.priority = priority; }
    public void setCategory(String category) { this.category = category; }

    @Override
    public String getDisplayText() {
        return name + " | " + priority + " | " + status + " | Due: " + targetDate;
    }

    @Override
    public String toString() {
        return id + " - " + getDisplayText();
    }
}
