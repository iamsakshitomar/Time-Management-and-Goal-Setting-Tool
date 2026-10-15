package timemanagement;

public abstract class GoalItem {
    protected int id;
    protected String name;
    protected String status;

    protected GoalItem(int id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getStatus() { return status; }

    public void setStatus(String status) {
        this.status = status;
    }

    public abstract String getDisplayText();
}
