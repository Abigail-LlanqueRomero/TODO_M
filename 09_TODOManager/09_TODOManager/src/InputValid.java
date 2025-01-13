import java.util.Scanner;
public class InputValid {

    public static int getValidChoice(Scanner scanner) {
        int choice;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > 15) {
                    System.out.println("Invalid choice. Please enter a number between 1 and 15.");
                } else {
                    return choice;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    public static int getValidTaskId(Scanner scanner) {
        int taskId;
        while (true) {
            try {
                taskId = Integer.parseInt(scanner.nextLine());
                if (taskId <= 0) {
                    System.out.println("Invalid Task ID. Please enter a positive number.");
                } else {
                    return taskId;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid Task ID.");
            }
        }
    }

    public static Task.Priority getValidPriorityChoice(Scanner scanner) {
        int choice;
        while (true) {
            System.out.println("Choose priority (1 = High, 2 = Medium, 3 = Low): ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        return Task.Priority.HIGH;
                    case 2:
                        return Task.Priority.MEDIUM;
                    case 3:
                        return Task.Priority.LOW;
                    default:
                        System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}
