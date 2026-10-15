package timemanagement;

public class Schedule {
    private int id;
    private int taskId;
    private String date;
    private String startTime;
    private String endTime;

    public Schedule(int id, int taskId, String date, String startTime, String endTime) {
        this.id = id;
        this.taskId = taskId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Schedule(int taskId, String date, String startTime, String endTime) {
        this(0, taskId, date, startTime, endTime);
    }

    public int getId() { return id; }
    public int getTaskId() { return taskId; }
    public String getDate() { return date; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
}
