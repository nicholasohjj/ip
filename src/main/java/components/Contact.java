package components;

import java.util.Objects;

/**
 * Represents a contact in the application.
 */
public class Contact {
    private final String name;
    private final String phoneNumber;
    private final String email;

    /**
     * Constructs a Contact with the given details.
     */
    public Contact(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return name + " | " + phoneNumber + " | " + email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contact contact = (Contact) o;
        return Objects.equals(name, contact.name)
                && Objects.equals(phoneNumber, contact.phoneNumber)
                && Objects.equals(email, contact.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phoneNumber, email);
    }
}
