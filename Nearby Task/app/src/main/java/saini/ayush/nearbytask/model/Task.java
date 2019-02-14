package saini.ayush.nearbytask.model;

public class Task {

    private int id;
    private String task;
    private String timestamp;
    private String title;

    public Task() {
    }

    public int getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }
}


