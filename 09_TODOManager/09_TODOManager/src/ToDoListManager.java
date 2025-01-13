import java.util.ArrayList;
import java.util.Scanner;

public class ToDoListManager {
    private ArrayList<ToDoList> toDoLists;
    private ArrayList<String> listNames;
    private Scanner scanner;

    public ToDoListManager() {
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

            System.out.println("1. Select Task List    |  2. Add Task            |  3. Show All Tasks    |  4. Mark Task as Completed");
            System.out.println("5. Show Overdue Tasks  |  6. Filter by Priority  |  7. Filter by Status  |  8. Remove Task");
            System.out.println("9. Exit                |  10. Edit Task          |  11. Create New List  |  12. Show All Lists");
            System.out.println("13. Switch List        |  14. Export to txt      |  15. Change Priority");
            System.out.print("\nEnter the number you want (1-15): ");

            int choice = InputValid.getValidChoice(scanner);

            switch (choice) {
                case 1 -> currentList = ToDoList.selectTaskList(scanner, toDoLists, listNames, currentList);
                case 2 -> currentList.addTaskFromUserInput(scanner);
                case 3 -> currentList.showAllTasks();
                case 4 -> currentList.markTaskAsCompletedFromUserInput(scanner);
                case 5 -> currentList.showOverdueTasks();
                case 6 -> currentList.filterTasksByPriorityFromUserInput(scanner);
                case 7 -> currentList.filterTasksByStatusFromUserInput(scanner);
                case 8 -> currentList.removeTaskFromUserInput(scanner);
                case 9 -> {
                    System.out.println("Exit, bye, bye.");
                    running = false;
                }
                case 10 -> currentList.editTaskFromUserInput(scanner);
                case 11 -> ToDoList.createNewList(scanner, toDoLists, listNames);
                case 12 -> ToDoList.showAllLists(listNames);
                case 13 -> currentList = ToDoList.switchToAnotherList(scanner, toDoLists, listNames, currentList);
                case 14 -> currentList.exportTasksToFile(scanner);
                case 15 -> currentList.testChangePriority();
                default -> System.out.println("Invalid choice, try again.");
            }
        }
    }
}
