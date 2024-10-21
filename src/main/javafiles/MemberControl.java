package javafiles;

import java.time.LocalDate;

// Hanterar logiken för att angöra vilken kategori en kund tillhör
public class MemberControl {
    private static final int ONE_YEAR = 1;

    // Metod för att kontrollera om kunden är en nuvarande medlem
    public static boolean isCurrentMember(Customer customer, LocalDate currentDate) {
        LocalDate lastPayment = customer.getLastPayment();
        if (lastPayment == null) {
            return false; // Ingen betalning, därför inte en nuvarande medlem
        }
        return lastPayment.isAfter(currentDate.minusYears(ONE_YEAR));
    }

    // Metod för att kontrollera om kunden är en före detta medlem
    public static boolean isFormerMember(Customer customer, LocalDate currentDate) {
        LocalDate lastPayment = customer.getLastPayment();
        if (lastPayment == null) {
            return false; // Ingen betalning, därför inte en tidigare medlem
        }
        return lastPayment.isBefore(currentDate.minusYears(ONE_YEAR));
    }
}