package components;

import java.util.Objects;

import exceptions.InvalidDataException;

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

    /**
     * Serializes the contact into a string format for storage.
     *
     * @return A string representation of the contact.
     */
    public String serialize() {
        return String.format("%s | %s | %s", name, phoneNumber, email);
    }

    /**
     * Deserializes a string into a Contact object.
     *
     * @param data The serialized string representation of a contact.
     * @return A Contact object reconstructed from the serialized data.
     * @throws InvalidDataException If the data is invalid, incorrectly formatted, or incomplete.
     */
    public static Contact deserialize(String data) throws InvalidDataException {
        if (data == null || data.isBlank()) {
            throw new InvalidDataException("Cannot deserialize null or empty data.");
        }

        String[] parts = data.split("\\|");

        if (parts.length < 3) {
            throw new InvalidDataException("Incomplete data for contact deserialization.");
        }

        String name = parts[0].trim();
        String phoneNumber = parts[1].trim();
        String email = parts[2].trim();

        if (name.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()) {
            throw new InvalidDataException("Contact details cannot be empty.");
        }

        return new Contact(name, phoneNumber, email);
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
