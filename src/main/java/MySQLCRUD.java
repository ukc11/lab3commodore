/** Project: Lab3
 * Purpose Details: To create a database
 * Course:IST140
 * Author:Ulrich Commodore
 * Date Developed:02/21/2025
 * Last Date Changed:02/24/2025
 * Rev:

 */
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLCRUD{
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/CustomerDB";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "IST888IST888";

    public static void main(String[] args) {
        performCRUDOperations();
    }

    public static void performCRUDOperations() {
        Scanner scanner = new Scanner(System.in);
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            int choice;
            do {
                System.out.println("Menu:");
                System.out.println("1. Add Customer");
                System.out.println("2. View Customers");
                System.out.println("3. Update Customer");
                System.out.println("4. Delete Customer");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        addCustomer(scanner, connection);
                        break;
                    case 2:
                        viewCustomers(connection);
                        break;
                    case 3:
                        System.out.print("Enter Customer ID to update: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine();
                        updateCustomer(connection, updateId, scanner);
                        break;
                    case 4:
                        System.out.print("Enter Customer ID to delete: ");
                        int deleteId = scanner.nextInt();
                        deleteCustomer(connection, deleteId);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 5);
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static void addCustomer(Scanner scanner, Connection connection) throws SQLException {
        System.out.print("Enter Customer ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Age: ");
        String age = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        insertCustomer(connection, id, firstName, lastName, age, email);
    }

    private static void insertCustomer(Connection connection, int id, String firstName, String lastName, String age, String email) throws SQLException {
        String sql = "INSERT INTO Customers (id, firstName, lastName, age, email) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, age);
            preparedStatement.setString(5, email);
            preparedStatement.executeUpdate();
            System.out.println("Customer added successfully!");
        }
    }

    private static void viewCustomers(Connection connection) throws SQLException {
        List<Customer> customers = getAllCustomers(connection);
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            for (Customer customer : customers) {
                System.out.println(customer);
            }
        }
    }

    private static List<Customer> getAllCustomers(Connection connection) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT id, firstName, lastName, age, email FROM Customers";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String age = resultSet.getString("age");
                String email = resultSet.getString("email");
                customers.add(new Customer(id, firstName, lastName, age, email));
            }
        }
        return customers;
    }

    private static void updateCustomer(Connection connection, int id, Scanner scanner) throws SQLException {
        System.out.println("What would you like to update?");
        System.out.println("1. First Name");
        System.out.println("2. Last Name");
        System.out.println("3. Age");
        System.out.println("4. Email");
        System.out.print("Enter your choice (1-4): ");
        int updateChoice = scanner.nextInt();
        scanner.nextLine();

        String sql = "";
        switch (updateChoice) {
            case 1:
                System.out.print("Enter new First Name: ");
                sql = "UPDATE Customers SET firstName = ? WHERE id = ?";
                break;
            case 2:
                System.out.print("Enter new Last Name: ");
                sql = "UPDATE Customers SET lastName = ? WHERE id = ?";
                break;
            case 3:
                System.out.print("Enter new Age: ");
                sql = "UPDATE Customers SET age = ? WHERE id = ?";
                break;
            case 4:
                System.out.print("Enter new Email: ");
                sql = "UPDATE Customers SET email = ? WHERE id = ?";
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                return;
        }
        String newValue = scanner.nextLine();
        updateChoice(connection, sql, newValue, id);
        System.out.println("Field updated successfully!");
    }

    private static void deleteCustomer(Connection connection, int id) throws SQLException {
        String sql = "DELETE FROM Customers WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Customer deleted successfully!");
            } else {
                System.out.println("Customer with ID " + id + " not found.");
            }
        }
    }

    private static void updateChoice(Connection connection, String sql, String value, int id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, value);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        }
    }
}
