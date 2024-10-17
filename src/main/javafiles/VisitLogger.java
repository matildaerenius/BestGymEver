package javafiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

// Ansvarar för att logga träningsbesök till en fil
public class VisitLogger {
    private String logFile; // Privat variabel för att lagra sökvägen till loggfilen

    // Konstruktor för att specificera filen där träningsbesöken loggas
    public VisitLogger(String logFile) {
        this.logFile = logFile;
    }
    // Metod för att logga kundens träningsbesök i loggfilen
    public void logTraining(Customer customer) throws IOException {
        // Hämta aktuell tid och formatera den till en läsbar string
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        // Skapa loggposten med namn, personnummer, datum och tid
        String logEntry = customer.getName() + ", " + customer.getSocialSecurityNumber()
                + ", " + formattedDateTime;

        // Skriver träningsinfon till filen (lägger till om det redan finns innehåll)
        try {
            Files.write(Paths.get(logFile), List.of(logEntry), StandardOpenOption.APPEND);
            System.out.println("Training visits logged for: " + customer.getName()+ " at " + formattedDateTime);
    } catch (IOException e) {
            System.out.println("Error: Could not write to log file: " + logFile);
            throw e; // Kastar vidare exeptionet
        }
    }
}
