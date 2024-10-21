package testjavafiles;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javafiles.Customer;
import javafiles.FileManager;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class FileManagerTest {

    // Testar att korrekt kunddata laddas in från fil
    @Test
    public void testCorrectCustomerDataLoading() throws IOException {
        // Skapar en FileManager med en specifik testfil
        FileManager fileManager = new FileManager("src/test/testresources/test_customer_data.txt");
        List<Customer> customers = fileManager.readCustomers();

        // Kontrollera att rätt antal kunder läses in
        assertEquals(3, customers.size());

        // Kontrollera att den första kunden har rätt data
        Customer customer1 = customers.get(0);
        assertEquals("7911061234", customer1.getSocialSecurityNumber());
        assertEquals("Fritjoff Flacon", customer1.getName());
        assertEquals(LocalDate.of(2023, 12, 16), customer1.getLastPayment());
    }

    // Testar att hitta en kund med namn
    @Test
    public void testFindCustomerByName() throws IOException {
        // Skapar en FileManager med en specifik testfil
        FileManager fileManager = new FileManager("src/test/testresources/test_customer_data.txt");
        List<Customer> customers = fileManager.readCustomers();

        Customer customer = fileManager.findCustomer(customers, "Fritjoff Flacon");
        assertNotNull(customer);
        assertEquals("Fritjoff Flacon", customer.getName());
    }

    // Testar att hitta kund med personnummer
    @Test
    public void testFindCustomerBySocialSecurityNumber() throws IOException {
        // Skapar en FileManager med en specifik testfil
        FileManager fileManager = new FileManager("src/test/testresources/test_customer_data.txt");
        List<Customer> customers = fileManager.readCustomers();

        Customer customer = fileManager.findCustomer(customers, "7911061234");
        assertNotNull(customer);
        assertEquals("Fritjoff Flacon", customer.getName());
    }

    // Testar att söka efter en kund som inte finns
    @Test
    public void testFindNonExistentCustomer() throws IOException {
        // Skapar en FileManager med en specifik testfil
        FileManager fileManager = new FileManager("src/test/testresources/test_customer_data.txt");
        List<Customer> customers = fileManager.readCustomers();

        Customer customer = fileManager.findCustomer(customers, "Nonexistent Name");
        assertNull(customer);
    }

    // Testar om felaktigt datum hanteras korrekt
    @Test
    public void testInvalidDateFormatHandling() throws IOException {
        // Skapar en FileManager med en specifik testfil
        FileManager fileManager = new FileManager("src/test/testresources/test_invalid_date.txt");
        List<Customer> customers = fileManager.readCustomers();
        assertEquals(2, customers.size()); // Endast 2 av 3 rader ska laddas korrekt

        // Kontrollerar att felmeddelande skrivs ut i konsolen
    }

    // Testar att kasta IOException när filen inte finns
    @Test
    public void testIOExceptionOnMissingFile() {
        FileManager fileManager = new FileManager("src/test/testresources/non_existent_file.txt");

        assertThrows(IOException.class, () -> {
            fileManager.readCustomers(); // Förväntas en IOException kastas
        });
    }
}