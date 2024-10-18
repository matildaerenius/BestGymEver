package testjavafiles;

import javafiles.Customer;
import javafiles.FileManager;
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
        assertEquals(1, logLines.size());
        assertTrue(logLines.get(0).contains("Matilda Erenius"));
        assertTrue(logLines.get(0).contains("0107202905"));
        assertTrue(logLines.get(0).contains(LocalDate.now().toString()));


        // Kontrollera att loggningen innehåller korrekt datum och tid
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String expectedDateTime = now.format(formatter);

        assertTrue(logLines.get(0).contains(expectedDateTime)); // Kontrollera att loggningen innehåller dagens datum
        // och tid i rätt format
    }

    // Testar att kasta IOException när filen inte finns
    @Test
    public void testIOExceptionOnMissingFile() {
        Customer customer = new Customer("0107202905", "Matilda Erenius", LocalDate.now());
        VisitLogger visitlogger = new VisitLogger("src/test/testresources/non_existent_file.txt");

        assertThrows(IOException.class, () -> {
            visitlogger.logTraining(customer); // Förväntas en IOException kastas
        });
    }

    // Testar att flera loggposter kan skrivas till filen
    @Test
    public void testMultipleLogEntries() throws IOException {
        Customer customer1 = new Customer("0107202905", "Matilda Erenius", LocalDate.now());
        Customer customer2 = new Customer("0012014122", "Rose Philipsen", LocalDate.now());

        VisitLogger visitLogger = new VisitLogger(logFilePath.toString());
        visitLogger.logTraining(customer1);
        visitLogger.logTraining(customer2);

        List<String> logLines = Files.readAllLines(logFilePath);
        assertEquals(2, logLines.size()); // Kontrollera att två rader har lagts till

        assertTrue(logLines.get(0).contains("Matilda Erenius"));
        assertTrue(logLines.get(1).contains("Rose Philipsen"));
    }
}
