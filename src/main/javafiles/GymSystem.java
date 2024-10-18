package javafiles;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

// Huvudprogram
public class GymSystem {
    private FileManager fileManager; // Privat variabel för att hantera kunddata
    private VisitLogger visitLogger; // Privat variabel för att logga besök

    // Konstruktor för att initisera kund och logghanterare med filnamn
    public GymSystem(String customerFile, String visitLogFile) {
        this.fileManager = new FileManager(customerFile);
        this.visitLogger = new VisitLogger(visitLogFile);
    }

    // Metod för att starta systemet
    public void start() {
        try {
            List<Customer> customers = fileManager.readCustomers();
            Scanner scanner = new Scanner(System.in);

            // Kör loopen tills användaren vill avsluta
            while (true) {
                String input = promptUserForInput(scanner);
                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting program!");
                    break;
                }
                handleCustomerSearch(customers, input);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading customer file: " + e.getMessage());
        }
    }

    // Prompta användaren för inmatning
    private String promptUserForInput(Scanner scanner) {
        System.out.print("\nEnter your customer's social security number or name: ");
        return scanner.nextLine().trim();
    }

    // Hantera kundsökning och medlemskap
    private void handleCustomerSearch(List<Customer> customers, String input) {
        Customer customer = fileManager.findCustomer(customers, input);
        LocalDate currentDate = LocalDate.now();

        if (customer == null) {
            System.out.println("The customer is unauthorized and has never been a member");
            return;
        }

        if (MemberControl.isCurrentMember(customer, currentDate)) {
            handleCurrentMember(customer);
        } else if (MemberControl.isFormerMember(customer, currentDate)) {
            System.out.println("The customer is a former member");
        }
    }

    // Hantera besök av nuvarande medlemmar
    private void handleCurrentMember(Customer customer) {
        System.out.println("The customer is a current member");
        try {
            visitLogger.logTraining(customer); // Logga kundens besök
        } catch (IOException e) {
            System.out.println("An error occurred while logging: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        GymSystem gymSystem = new GymSystem(
                "src/main/resources/customer_data.txt",
                "src/main/resources/visit_log.txt"
        );
        gymSystem.start();
    }
}