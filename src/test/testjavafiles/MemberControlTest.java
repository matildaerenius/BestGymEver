package testjavafiles;
import javafiles.MemberControl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javafiles.Customer;
import java.time.LocalDate;

public class MemberControlTest {

    // Testar om en kund är en nuvarande kund
    @Test
    public void testIsCurrentMember() {
        // Skapa en kund med en betalning som är 6 månader gammal
        Customer currentCustomer = new Customer("7406040068", "Current Member",
                LocalDate.now().minusMonths(6));
        assertTrue(MemberControl.isCurrentMember(currentCustomer, LocalDate.now()));

        // Skapa en kund med en betalning som är 2 år gammal (utgått medlemskap)
        Customer expiredCustomer = new Customer("7204042396", "Expired Member",
                LocalDate.now().minusYears(2));
        assertFalse(MemberControl.isCurrentMember(expiredCustomer, LocalDate.now()));
    }

    // Testar om en kund är en före detta medlem
    @Test
    public void testIsFormerMember() {
        // Skapa en kund med en betalning som är 2 år gammal (före detta medlem)
        Customer formerCustomer = new Customer("7204042396", "Former Member",
                LocalDate.now().minusYears(2));
        assertTrue(MemberControl.isFormerMember(formerCustomer, LocalDate.now()));

        // Skapa en kund med en betalning som är 6 månader gammal (nuvarande medlem)
        Customer currentCustomer = new Customer("7406040068", "Current Member",
                LocalDate.now().minusMonths(6));
        assertFalse(MemberControl.isFormerMember(currentCustomer, LocalDate.now()));
    }

    // Testar om en obehörig kund inte är medlem eller före detta medlem
    @Test
    public void testUnauthorizedCustomer() {
        // Skapa en obehörig kund med null som senaste betalningsdatum
        Customer unauthorizedCustomer = new Customer("0107202905", "Unauthorized Customer", null);

        // Kontrollera att obehörig kund inte är medlem eller tidigare medlem
        assertFalse(MemberControl.isCurrentMember(unauthorizedCustomer, LocalDate.now()));
        assertFalse(MemberControl.isFormerMember(unauthorizedCustomer, LocalDate.now()));
    }
}
