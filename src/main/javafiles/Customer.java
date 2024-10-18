package javafiles;

import java.time.LocalDate;

// Representerar en kund med dess attribut.
public class Customer {
    // Privata variabler som representerar en kund
    private String socialSecurityNumber;
    private String name;
    private LocalDate lastPayment;

    // Konstruktor för att skapa en kund
    public Customer(String socialSecurityNumber, String name, LocalDate lastPayment) {
        this.socialSecurityNumber = socialSecurityNumber;
        this.name = name;
        this.lastPayment = lastPayment;
    }
    // Getter för att hämta kundens personnr
    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }
    // Getter för att hämta kundens namn
    public String getName() {
        return name;
    }
    // Getter för att hämta datumet då kunden senast betalade
    public LocalDate getLastPayment() {
        return lastPayment;
    }
}
