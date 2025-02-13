package components;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import exceptions.NiniException;


/**
 * Handles file storage operations for saving and loading tasks.
 * This class manages reading and writing task data to a file, ensuring
 * persistence of task lists between program runs.
 */
public class ContactStorage {
    private static final String DEFAULT_FILE_PATH = "./data/contacts.txt";

    private final String fileName;

    /**
     * Constructs a {@code Storage} object with the default file path {@code ./data/chat.txt}.
     */
    public ContactStorage() {
        this(DEFAULT_FILE_PATH);
    }

    /**
     * Constructs a {@code Storage} object with a specified file path.
     *
     * @param fileName The path to the file where tasks will be stored.
     */
    public ContactStorage(String fileName) {
        assert fileName != null && !fileName.isBlank() : "File name cannot be null or empty";
        this.fileName = fileName;
    }

    /**
     * Ensures that the directory for the storage file exists.
     * If the directory does not exist, it attempts to create it.
     * Displays an error message if the directory creation fails.
     */
    private void ensureFileDirectoryExists() throws IOException {
        File directory = new File(new File(fileName).getParent());
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Failed to create the 'data' directory for saving contacts.");
        }
    }

    /**
     * Loads contacts from the storage file.
     * Reads the file line by line, deserializing each line into a {@code contact} object.
     * If the file does not exist, it returns an empty list.
     *
     * @return An {@code ArrayList} of contacts loaded from the file.
     */
    public List<Contact> loadContacts() throws IOException, NiniException {
        File file = new File(fileName);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        List<Contact> contacts = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                assert line != null : "Read line should not be null";
                contacts.add(Contact.deserialize(line));
            }
        }
        return contacts;
    }

    /**
     * Saves a single contact to the storage file by appending it to the existing file.
     *
     * @param contact The contact to be saved.
     * @throws IOException If an error occurs while writing to the file.
     */
    public void saveContact(Contact contact) throws IOException {
        assert contact != null : "contact cannot be null";
        appendToFile(contact.serialize());
    }

    /**
     * Overwrites the storage file with the given list of contacts.
     * This method removes all previous data in the file and writes the new contacts.
     *
     * @param contacts The list of contacts to be saved.
     * @throws IOException If an error occurs while writing to the file.
     */
    public void overwriteContacts(List<Contact> contacts) throws IOException {
        assert contacts != null : "contacts list cannot be null";
        writeToFile(contacts);
    }

    /**
     * Appends a single serialized contact to the storage file.
     *
     * @param contactData The serialized contact string.
     * @throws IOException If an error occurs while writing to the file.
     */
    private void appendToFile(String contactData) throws IOException {
        ensureFileDirectoryExists();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(contactData + System.lineSeparator());
        }
    }

    /**
     * Writes a list of serialized contacts to the storage file, overwriting existing content.
     *
     * @param contacts The list of contacts to be written.
     * @throws IOException If an error occurs while writing to the file.
     */
    private void writeToFile(List<Contact> contacts) throws IOException {
        ensureFileDirectoryExists();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Contact contact : contacts) {
                assert contact != null : "contact in list cannot be null";
                writer.write(contact.serialize() + System.lineSeparator());
            }
        }
    }
}
