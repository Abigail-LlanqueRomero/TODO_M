import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputValidator {

    public static int getValidChoice(Scanner scanner, int min, int max) {
        int choice = -1;
        boolean valid = false;

        while (!valid) {
            System.out.print("Please choose a number between " + min + " and " + max + ": ");
            String input = scanner.nextLine().trim();
            try {
                choice = Integer.parseInt(input);
                if (choice >= min && choice <= max) {
                    valid = true;
                } else {
                    System.out.println("Choice out of range.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
        return choice;
    }

    //only valid id and no letters
    public static int getValidTaskId(Scanner scanner, String prompt) {
        int taskId = -1;
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                taskId = Integer.parseInt(input);
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
        return taskId;
    }

    // control format-> date is checked
    public static LocalDate getValidDateInput(Scanner scanner, String prompt) {
        LocalDate date = null;
        boolean valid = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        while (!valid) {
            System.out.print(prompt);
            String dateStr = scanner.nextLine().trim();
            try {
                date = LocalDate.parse(dateStr, formatter);
                valid = true;
            } catch (Exception e) {
                System.out.println("WRONG. Please enter in dd-MM-yyyy format.");
            }
        }
        return date;
    }

    public static String getValidFilePath(Scanner scanner, String prompt) {
        String filePath = "";
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt);
            filePath = scanner.nextLine().trim();
            if (!filePath.isEmpty()) {
                valid = true;
            } else {
                System.out.println("File path cannot be empty.");
            }
        }
        return filePath;
    }
}
