/** Project: Lab3
 * Purpose Details: To create a database
 * Course:IST140
 * Author:Ulrich Commodore
 * Date Developed:02/21/2025
 * Last Date Changed:02/24/2025
 * Rev:

 */
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Customer ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        System.out.print("Enter First Name: ");
        String firstname = scanner.nextLine();

        System.out.print("Enter Last Name: ");
        String lastname = scanner.nextLine();

        System.out.print("Enter Age: ");
        String age = scanner.nextLine();

        System.out.print("Enter Email Address: ");
        String email_address = scanner.nextLine();

        Customer customer = new Customer(id, firstname, lastname, age, email_address);

        System.out.println("Customer Created: " + customer);


        UserMenu.displayMenu();

        scanner.close();
    }
}

