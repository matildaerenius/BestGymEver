package javafiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

// Ansvarar för att läsa in kunddata från filen och skapa en lista med kunder
public class FileManager {
    private String fileName; // Privat variabel för att lagra namnet på filen som ska läsas

    // Konstruktor för att specificera filen som innehåller kunddata
    public FileManager(String fileName) {
        this.fileName = fileName;
    }

    // Metod för att läsa in kunddata från filen och returnerar en lista med kunder
    public List<Customer> readCustomers() throws IOException {
        List<Customer> customers = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName)); // Läser alla rader från filen och
                                                                        // lagrar dom i en lista av strängar

            // Varje kund post innehåller två rader (personnr, namn och betalningsdatum)
            for (int i = 0; i < lines.size(); i+=2) {
                try {
                    String[] customerData = lines.get(i).split(","); // Splittar den aktuella raden
                    if (customerData.length != 2) { // Kontrollerar om dataformatet är korrekt
                        throw new IllegalArgumentException("Error: Wrong format on customer line: " + lines.get(i));
                    }

                    // Tar bort onödiga mellanslag
                    String socialSecurityNumber = customerData[0].trim();
                    String name = customerData[1].trim();
                    LocalDate lastPayment = LocalDate.parse(lines.get(i+1).trim());

                    customers.add(new Customer(socialSecurityNumber, name, lastPayment)); // Lägger till ny kund

                } catch (DateTimeParseException e) { // Fångar och hanterar datumparsingfel
                    System.out.println("Error: Wrong date format on line " + (i + 2) + ": " + lines.get(i+1));
                } catch (IllegalArgumentException e) { // Fångar och hanterar ogiltiga argument
                    System.out.println("Error: Could not load customer data: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error: Could not read from file: " + fileName);
            throw e; // Släng vidare exeptionet efter att ha loggat det
        }

        return customers; // Returnerar listan med kunder från filen
    }

    // Metod för att söka efter en kund baserat på personnr eller namn
    public Customer findCustomer(List<Customer> customers, String find) {
        for (Customer customer : customers) {
            // Kontrollera om sökningen matchar kundens personnr eller namn
            if (customer.getSocialSecurityNumber().equals(find) || customer.getName().equalsIgnoreCase(find)) {
                return customer;
            }
        }
        return null; // Returnerar null om ingen kund hittas
    }
}
