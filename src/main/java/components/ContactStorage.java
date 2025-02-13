package components;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles storage for contacts.
 */
public class ContactStorage {
    private final String filePath;

    public ContactStorage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads contacts from the file specified in the file path.
     * Each line in the file is expected to follow the format: "Name | PhoneNumber | Email".
     *
     * @return A list of contacts loaded from the file.
     * @throws IOException If an error occurs while reading the file.
     */
    public List<Contact> loadContacts() throws IOException {
        List<Contact> contacts = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" \\| ");
            if (parts.length == 3) {
                contacts.add(new Contact(parts[0], parts[1], parts[2]));
            }
        }
        reader.close();
        return contacts;
    }

    /**
     * Saves the list of contacts to the file specified in the file path.
     * Each contact is written to a new line using the format: "Name | PhoneNumber | Email".
     *
     * @param contacts The list of contacts to be saved.
     * @throws IOException If an error occurs while writing to the file.
     */
    public void saveContacts(List<Contact> contacts) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        for (Contact contact : contacts) {
            writer.write(contact.toString() + "\n");
        }
        writer.close();
    }
}
