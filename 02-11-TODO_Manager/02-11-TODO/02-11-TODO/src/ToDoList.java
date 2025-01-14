import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

//AL-> call-by-reference
public class ToDoList {
    private ArrayList<Task> tasks;

    public ToDoList() {
        this.tasks = new ArrayList<>();
    }

    //just examples hardcoded
    public static ToDoList createDefaultTBZTasks() {
        ToDoList tbzTasks = new ToDoList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        tbzTasks.addTask(new Task("Volunteer Ironmen Event", "Prepare and assist in Thun for the Ironmen event on February 2025", Task.Priority.HIGH, LocalDate.parse("01-01-2025", formatter)));
        tbzTasks.addTask(new Task("Hobonichi Planner", "Purchase a Hobonichi planner and write in upcoming vacations", Task.Priority.MEDIUM, LocalDate.parse("03-01-2025", formatter)));
        return tbzTasks;
    }

    public static ToDoList createDefaultHomeworkTasks() {
        ToDoList homeworkTasks = new ToDoList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        homeworkTasks.addTask(new Task("Write", "Read the book: die Physiker, write easy conclusion (1000 words).", Task.Priority.MEDIUM, LocalDate.parse("02-01-2025", formatter)));
        homeworkTasks.addTask(new Task("Coding", "Continue coding -> sell the product.", Task.Priority.LOW, LocalDate.parse("04-01-2025", formatter)));
        return homeworkTasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void addTaskFromUserInput(Scanner scanner, LocalDate dueDate) {
        System.out.print("Enter Task Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Task Description: ");
        String description = scanner.nextLine();

        Task.Priority priority = Task.Priority.MEDIUM;
        Task newTask = new Task(title, description, priority, dueDate);
        addTask(newTask);
        System.out.println("Task added successfully!");
    }


    public void markTaskAsCompleted(int taskId) {
        tasks.stream()
                .filter(task -> task.getId() == taskId)
                .findFirst()
                .ifPresentOrElse(
                        Task::markAsCompleted,
                        () -> System.out.println("Task ID not found.")
                );
    }

    public void showAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }
        tasks.forEach(task -> System.out.println(task.getTaskDescription()));
    }
    // eyport in .txt
    public void exportTasksToFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("ID;Title;Description;Priority;Status;Due Date\n");
            for (Task task : tasks) {
                String status = task.isCompleted() ? "Completed" : "Open";
                writer.write(task.getId() + ";" + task.getTitle() + ";" + task.getDescription() + ";" + task.getPriority() + ";" + status + ";" + task.getDueDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n");
            }
            System.out.println("Tasks exported successfully to " + filePath);
        } catch (IOException e) {
            System.out.println("Fail while exporting tasks: " + e.getMessage());
        }
    }

    // temporarly priority-> call-by value (demo)
    public void changeTaskPriorityFromUserInput(Scanner scanner, int taskId) {
        tasks.stream()
                .filter(task -> task.getId() == taskId)
                .findFirst()
                .ifPresentOrElse(
                        task -> {
                            System.out.println("Current Priority: " + task.getPriority());
                            System.out.println("Choose the new priority:");
                            System.out.println("1. LOW");
                            System.out.println("2. MEDIUM");
                            System.out.println("3. HIGH");

                            int priorityChoice = InputValidator.getValidChoice(scanner, 1, 3);
                            Task.Priority newPriority = switch (priorityChoice) {
                                case 1 -> Task.Priority.LOW;
                                case 2 -> Task.Priority.MEDIUM;
                                case 3 -> Task.Priority.HIGH;
                                default -> Task.Priority.MEDIUM;
                            };

                            task.setPriority(newPriority);
                            System.out.println("Priority updated to: " + task.getPriority());
                        },
                        () -> System.out.println("Task ID not found.")
                );
    }
}


