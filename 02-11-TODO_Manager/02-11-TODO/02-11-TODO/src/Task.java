import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task {
    private static int idCounter = 0;
    private int id;
    private String title;
    private String description;
    private boolean completed;
    private Priority priority;
    private LocalDate dueDate;

    public enum Priority {
        HIGH, MEDIUM, LOW;

        public String toString() {
            return switch (this) {
                case HIGH -> "High";
                case MEDIUM -> "Medium";
                case LOW -> "Low";
            };
        }
    }

    public Task(String title, String description, Priority priority, LocalDate dueDate) {
        this.id = ++idCounter;
        this.title = title;
        this.description = description;
        this.completed = false;
        this.priority = priority != null ? priority : Priority.MEDIUM;
        this.dueDate = dueDate;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markAsCompleted() {
        this.completed = true;
    }

    public String getTaskDescription() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String status = completed ? "Completed" : "Open";
        return String.format("Task ID: %d\nTitle: %s\nDescription: %s\nStatus: %s\nPriority: %s\nDue Date: %s\n",
                id, title, description, status, priority, dueDate.format(formatter));
    }
}
