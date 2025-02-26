/** Project: Lab3
 * Purpose Details: To create a database
 * Course:IST140
 * Author:Ulrich Commodore
 * Date Developed:02/21/2025
 * Last Date Changed:02/24/2025
 * Rev:

 */
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MongoCRUD {

    public static void performCRUDOperations() {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("your_database_name");
            MongoCollection<Document> collection = database.getCollection("customers");
            Scanner scanner = new Scanner(System.in);
            int choice = 2;
            do {
                System.out.println("===== Customer Management Menu =====");
                System.out.println("1. Insert Customer");
                System.out.println("2. Read Customers");
                System.out.println("3. Update Customer");
                System.out.println("4. Delete Customer");
                System.out.println("5. Exit");
                System.out.print("Please choose an option (1-5): ");
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice) {
                        case 1:
                            insertCustomer(scanner, collection);
                            break;
                        case 2:
                            readCustomers(collection);
                            break;
                        case 3:
                            updateCustomer(scanner, collection);
                            break;
                        case 4:
                            deleteCustomer(scanner, collection);
                            break;
                        case 5:
                            System.out.println("Exiting...");
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                } else {
                    System.out.println("Please enter a valid integer.");
                    scanner.next();
                }
            } while (choice != 7);
        }
    }

    private static void insertCustomer(Scanner scanner, MongoCollection<Document> collection) {
        System.out.print("Enter ID: ");
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

        Document newCustomer = new Document("id", id)
                .append("firstname", firstName)
                .append("lastname", lastName)
                .append("age", age)
                .append("emailaddress", email);

        collection.insertOne(newCustomer);
        System.out.println("Customer added successfully!");
    }

    private static void readCustomers(MongoCollection<Document> collection) {
        System.out.println("Customers in the database:");
        FindIterable<Document> customerDocuments = collection.find();
        List<Document> customers = new ArrayList<>();

        for (Document doc : customerDocuments) {
            Integer docId = doc.getInteger("id");
            String docFirstName = doc.getString("firstname");
            String docLastName = doc.getString("lastname");
            String docAge = doc.getString("age");
            String docEmail = doc.getString("emailaddress");

            System.out.println("ID: " + docId + ", First Name: " + docFirstName + ", Last Name: " + docLastName + ", Age: " + docAge + ", Email: " + docEmail);
        }
    }

    private static void updateCustomer(Scanner scanner, MongoCollection<Document> collection) {
        System.out.print("Enter Customer ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Document customerDocument = collection.find(Filters.eq("id", id)).first();
        if (customerDocument == null) {
            System.out.println("Customer not found.");
            return;
        }

        System.out.println("What would you like to update?");
        System.out.println("1. First Name");
        System.out.println("2. Last Name");
        System.out.println("3. Age");
        System.out.println("4. Email");
        System.out.print("Enter your choice (1-4): ");
        int updateChoice = scanner.nextInt();
        scanner.nextLine();

        String field = "";
        switch (updateChoice) {
            case 1:
                field = "firstname";
                System.out.print("Enter new First Name: ");
                break;
            case 2:
                field = "lastname";
                System.out.print("Enter new Last Name: ");
                break;
            case 3:
                field = "age";
                System.out.print("Enter new Age: ");
                break;
            case 4:
                field = "email_address";
                System.out.print("Enter new Email: ");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                return;
        }

        String newValue = scanner.nextLine();
        collection.updateOne(Filters.eq("id", id), new Document("$set", new Document(field, newValue)));
        System.out.println(field + " updated successfully!");
    }

    private static void deleteCustomer(Scanner scanner, MongoCollection<Document> collection) {
        System.out.print("Enter Customer ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Document customerDocument = collection.find(Filters.eq("id", id)).first();
        if (customerDocument != null) {
            collection.deleteOne(Filters.eq("id", id));
            System.out.println("Customer deleted successfully!");
        } else {
            System.out.println("Customer not found.");
        }
    }
}
