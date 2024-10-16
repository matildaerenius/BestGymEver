package testjavafiles;

import javafiles.Customer;
import javafiles.VisitLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VisitLoggerTest {
    // Definierar sökvägen till loggfilen som ska användas i testerna
    private Path logFilePath = Paths.get("src/test/testresources/test_visit_log.txt");

    // Metod som körs innan varje test för att nollställa loggfilen
    @BeforeEach
    public void clearLogFile() throws IOException {
        // Tömmer filen genom att öppna den med inget innehåll
        Files.write(logFilePath, new byte[0]);
    }

    // Testar loggning av träning för en kund
    @Test
    public void testLogTraining() throws IOException {
        // Skapa en ny kund med ett personnummer, namn och dagens datum
        Customer customer = new Customer("0107202905", "Matilda Erenius", LocalDate.now());

        // Skapa en instans av VisitLogger och ange sökvägen till loggfilen
        VisitLogger visitLogger = new VisitLogger(logFilePath.toString());
        visitLogger.logTraining(customer); // Logga träning för kunden

        List<String> logLines = Files.readAllLines(logFilePath); // Läser alla rader från loggfilen
        assertEquals(1, logLines.size());  // Kontrollerar att en rad har lagts till i loggfilen
        assertTrue(logLines.get(0).contains("Matilda Erenius")); // Kontrollerar att loggningen innehåller kundens namn
        assertTrue(logLines.get(0).contains("0107202905")); // Kontrollerar att loggningen innehåller kundens personnummer
        assertTrue(logLines.get(0).contains(LocalDate.now().toString())); // Kontrollerar att loggningen innehåller
                                                                        // dagens datum i rätt format

        // Kontrollera att loggningen innehåller korrekt datum och tid
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String expectedDateTime = now.format(formatter);

        assertTrue(logLines.get(0).contains(expectedDateTime)); // Kontrollera att loggningen innehåller dagens datum
                                                                // och tid i rätt format
    }
}

