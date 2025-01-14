import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu_Manager {
    private ArrayList<ToDoList> toDoLists;
    private ArrayList<String> listNames;
    private Scanner scanner;

    public Menu_Manager() {
        toDoLists = new ArrayList<>();
        listNames = new ArrayList<>();
        scanner = new Scanner(System.in);

        ToDoList tbzTasks = ToDoList.createDefaultTBZTasks();
        ToDoList homeworkTasks = ToDoList.createDefaultHomeworkTasks();

        toDoLists.add(tbzTasks);
        toDoLists.add(homeworkTasks);
        listNames.add("TBZ Tasks");
        listNames.add("Homework Tasks");
    }

    public void start() {
        boolean running = true;
        ToDoList currentList = toDoLists.get(0);

        while (running) {
            System.out.println("\n//////////////////////////////////////////////////////////////////////");
            System.out.println("                    ToDo List Manager");
            System.out.println("//////////////////////////////////////////////////////////////////////");
            System.out.println("════════════════════════════ Main Menu ═══════════════════════════════\n");

            System.out.println("1. Add Task            |  2. Show All Tasks    |  3. Mark Task as Completed");
            System.out.println("4. Export to txt       |  5. Change Priority   |  6. Exit");

            int choice = InputValidator.getValidChoice(scanner, 1, 6);

            switch (choice) {
                case 1:
                    LocalDate dueDate = InputValidator.getValidDateInput(scanner, "Enter due date (DD-MM-YYYY): ");
                    currentList.addTaskFromUserInput(scanner, dueDate);
                    break;

                case 2:
                    currentList.showAllTasks();
                    break;

                case 3:
                    int taskId = InputValidator.getValidTaskId(scanner, "Enter Task ID: ");
                    currentList.markTaskAsCompleted(taskId);
                    break;

                case 4:
                    String filePath = InputValidator.getValidFilePath(scanner, "Enter the path for exporting the tasks (example: C:\\Users\\abiga\\Documents\\NY\\tbz.txt): ");
                    currentList.exportTasksToFile(filePath);
                    break;

                case 5:
                    int taskIdForPriorityChange = InputValidator.getValidTaskId(scanner, "Enter Task ID to change priority: ");
                    currentList.changeTaskPriorityFromUserInput(scanner, taskIdForPriorityChange);
                    break;

                case 6:
                    System.out.println("Exit, bye, bye.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }
}


