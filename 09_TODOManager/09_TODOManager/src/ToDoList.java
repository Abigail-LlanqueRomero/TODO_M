import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ToDoList {
    private ArrayList<Task> tasks;

    public ToDoList() {
        this.tasks = new ArrayList<>();
    }

    public static ToDoList selectTaskList(Scanner scanner, ArrayList<ToDoList> toDoLists, ArrayList<String> listNames, ToDoList currentList) {
        if (listNames.isEmpty()) {
            System.out.println("NO List. Returning to ->current list.");
            return currentList;
        }

        System.out.println("Available Lists:");
        for (int i = 0; i < listNames.size(); i++) {
            System.out.println((i + 1) + ". " + listNames.get(i));
        }

        System.out.print("Enter number of list you want to switch to: ");
        int choice = scanner.nextInt();

        if (choice > 0 && choice <= toDoLists.size()) {
            System.out.println("Switched to: " + listNames.get(choice - 1));
            return toDoLists.get(choice - 1);
        } else {
            System.out.println("WRONG choice. Staying with the current list.");
            return currentList;
        }
    }

    //Example hardcoded for exportieren
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
        homeworkTasks.addTask(new Task("Write Essay", "Complete English essay from tagelsir", Task.Priority.MEDIUM, LocalDate.parse("02-01-2025", formatter)));
        homeworkTasks.addTask(new Task("Grind coding", "Code on project.", Task.Priority.LOW, LocalDate.parse("04-01-2025", formatter)));
        return homeworkTasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void addTaskFromUserInput(Scanner scanner) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        System.out.print("Enter Task Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Task Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter Due Date (DD-MM-YYYY): ");
        String dueDateStr = scanner.nextLine();
        LocalDate dueDate = LocalDate.parse(dueDateStr, formatter);

        Task.Priority priority = Task.Priority.MEDIUM;
        Task newTask = new Task(title, description, priority, dueDate);
        addTask(newTask);
    }

    public void removeTask(int taskId) {
        boolean removed = tasks.removeIf(task -> task.getId() == taskId);
        System.out.println(removed ? "Task successfully removed." : "Task ID not found.");
    }

    public void removeTaskFromUserInput(Scanner scanner) {
        System.out.print("Enter Task ID to remove: ");
        int taskId = scanner.nextInt();
        removeTask(taskId);
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

    public void markTaskAsCompletedFromUserInput(Scanner scanner) {
        System.out.print("Enter Task ID to mark as completed: ");
        int taskId = scanner.nextInt();
        markTaskAsCompleted(taskId);
    }

    public void showAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }

        tasks.forEach(task -> System.out.println(task.getTaskDescription()));
    }
    //Printss that match the priority
    public void filterTasksByPriority(Task.Priority priority) {
        tasks.stream()
                .filter(task -> task.getPriority() == priority)
                .forEach(task -> System.out.println(task.getTaskDescription()));
    }

    public void filterTasksByPriorityFromUserInput(Scanner scanner) {
        System.out.print("Enter Priority to filter by (1 = High, 2 = Medium, 3 = Low): ");
        int priorityChoice = scanner.nextInt();
        Task.Priority priority = switch (priorityChoice) {
            case 1 -> Task.Priority.HIGH;
            case 2 -> Task.Priority.MEDIUM;
            case 3 -> Task.Priority.LOW;
            default -> {
                System.out.println("Invalid choice. Defaulting to Medium.");
                yield Task.Priority.MEDIUM;
            }
        };
        filterTasksByPriority(priority);
    }

    public void showOverdueTasks() {
        List<Task> overdueTasks = tasks.stream()
                .filter(Task::isOverdue)
                .collect(Collectors.toList());
        if (overdueTasks.isEmpty()) {
            System.out.println("No overdue tasks.");
        } else {
            overdueTasks.forEach(task -> System.out.println(task.getTaskDescription()));
        }
    }

    public void filterTasksByStatus(boolean isCompleted) {
        tasks.stream()
                .filter(task -> task.isCompleted() == isCompleted)
                .forEach(task -> System.out.println(task.getTaskDescription()));
    }

    public void filterTasksByStatusFromUserInput(Scanner scanner) {
        System.out.print("Show Completed (true) or Open (false) tasks? ");
        boolean isCompleted = scanner.nextBoolean();
        filterTasksByStatus(isCompleted);
    }

    public void editTaskFromUserInput(Scanner scanner) {
        System.out.print("Enter Task ID to edit: ");
        int taskId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new Title: ");
        String newTitle = scanner.nextLine();
        System.out.print("Enter new Description: ");
        String newDescription = scanner.nextLine();
        System.out.print("Enter new Due Date (DD-MM-YYYY): ");
        String newDueDateStr = scanner.nextLine();
        LocalDate newDueDate = LocalDate.parse(newDueDateStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        tasks.stream()
                .filter(task -> task.getId() == taskId)
                .findFirst()
                .ifPresentOrElse(
                        task -> {
                            task.setTitle(newTitle);
                            task.setDescription(newDescription);
                            task.setDueDate(newDueDate);
                            System.out.println("Task updated successfully!");
                        },
                        () -> System.out.println("Task ID not found.")
                );
    }

    public static void createNewList(Scanner scanner, ArrayList<ToDoList> toDoLists, ArrayList<String> listNames) {
        System.out.print("Enter the name of the new list: ");
        String listName = scanner.nextLine();
        toDoLists.add(new ToDoList());
        listNames.add(listName);
        System.out.println("List '" + listName + "' created successfully!");
    }

    public static void showAllLists(ArrayList<String> listNames) {
        for (int i = 0; i < listNames.size(); i++) {
            System.out.println((i + 1) + ". " + listNames.get(i));
        }
    }

    public static ToDoList switchToAnotherList(Scanner scanner, ArrayList<ToDoList> toDoLists, ArrayList<String> listNames, ToDoList currentList) {
        showAllLists(listNames);
        System.out.print("Enter the number of the list you want to switch to: ");
        int listChoice = scanner.nextInt();
        if (listChoice > 0 && listChoice <= toDoLists.size()) {
            currentList = toDoLists.get(listChoice - 1);
            System.out.println("Switched to '" + listNames.get(listChoice - 1) + "'.");
        } else {
            System.out.println("Invalid choice.");
        }
        return currentList;
    }

    //enter "path"tasks.txt
    public void exportTasksToFile(Scanner scanner) {
        System.out.print("Enter the file path to export tasks: ");
        String filePath = scanner.nextLine();
        exportToFile(filePath);
    }

    public void exportToFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("ID;Title;Description;Priority;Status;Due Date\n");
            for (Task task : tasks) {
                String status = task.isCompleted() ? "Completed" : "Open";
                writer.write(task.getId() + ";" + task.getTitle() + ";" + task.getDescription() + ";" + task.getPriority() + ";" + status + ";" + task.getDueDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n");
            }
            System.out.println("Tasks exported successfully to " + filePath);
        } catch (IOException e) {
            System.out.println("An error occurred while exporting tasks: " + e.getMessage());
        }
    }
    //Change the prio of a task-> temporarlY(Call by Value- demo)
    public void testChangePriority() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the Task ID to change priority: ");
        int taskId = scanner.nextInt();

        System.out.println("Choose new priority: ");
        System.out.println("1. High");
        System.out.println("2. Medium");
        System.out.println("3. Low");

        int priorityChoice = scanner.nextInt();
        Task.Priority newPriority = switch (priorityChoice) {
            case 1 -> Task.Priority.HIGH;
            case 2 -> Task.Priority.MEDIUM;
            case 3 -> Task.Priority.LOW;
            default -> {
                System.out.println("Invalid choice. Defaulting to Medium.");
                yield Task.Priority.MEDIUM;
            }
        };

        changeTaskPriority(taskId, newPriority);
    }
    //manually change the priority (nachträglich gemacht wegen dem->Call by Value demo)
    public void changeTaskPriority(int taskId, Task.Priority newPriority) {
        tasks.stream()
                .filter(task -> task.getId() == taskId)
                .findFirst()
                .ifPresentOrElse(
                        task -> {
                            System.out.println("Current Priority: " + task.getPriority());
                            task.setPriority(newPriority);
                            System.out.println("Priority updated to: " + task.getPriority());
                        },
                        () -> System.out.println("Task ID not found.")
                );
    }
}
