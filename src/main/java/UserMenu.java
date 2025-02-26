/** Project: Lab3
 * Purpose Details: To create a database
 * Course:IST140
 * Author:Ulrich Commodore
 * Date Developed:02/21/2025
 * Last Date Changed:02/24/2025
 * Rev:

 */
import java.util.Scanner;

public class UserMenu {

    public static void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice = 2;

        do {
            System.out.println("\n==== Database Selection Menu ====");
            System.out.println("1. MongoDB");
            System.out.println("2. MySQL");
            System.out.println("3. Exit");
            System.out.print("Pick your database: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
                scanner.next();
                continue;
            }

            switch (choice) {
                case 1:
                    MongoCRUD.performCRUDOperations();
                    break;
                case 2:
                    MySQLCRUD.performCRUDOperations();
                    break;
                case 3:
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please select between 1 and 3.");
            }
        } while (choice != 7);

        scanner.close();
    }
}

