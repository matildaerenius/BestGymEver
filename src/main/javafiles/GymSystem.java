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

    // Metod för att starta systemet som låter användaren söka efter kunder och logga träning
    public void start() {
        try {
            // Läser in alla kunder från filen
            List<Customer> customers = fileManager.readCustomers();
            Scanner scanner = new Scanner(System.in);

            // Loopar tills användaren väljer att avbryta
            while(true) {
                System.out.print("\nEnter your customer's social security number or name: ");
                String input = scanner.nextLine().trim();

                // Kontrollerar om användaren vill avsluta
                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting program!");
                    break; // Bryter loopen
                }

                // Anropar findCustomer för att söka efter kund baserat på input från användaren
                Customer customer = fileManager.findCustomer(customers, input);
                LocalDate currentDate = LocalDate.now();

                // Kontrollerar vilken kategori kunden tillhör
                if (customer == null) {
                    System.out.println("The customer is unauthorized and has never been a member");
                } else if (MemberControl.isCurrentMember(customer, currentDate)) {
                    System.out.println("The customer is a current member");
                    try {
                        visitLogger.logTraining(customer); // Logga kundens besök
                    } catch (IOException e) {
                        System.out.println("An error occurred while logging: " + e.getMessage());
                    }
                } else if (MemberControl.isFormerMember(customer, currentDate)) {
                    System.out.println("The customer is former member");
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading customer file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        GymSystem gymSystem = new GymSystem("src/main/resources/customer_data.txt", "src/main/resources/visit_log.txt");
        gymSystem.start();
    }
}
