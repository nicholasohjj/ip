package components;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manages a list of contacts, providing methods to add, remove, mark, unmark,
 * retrieve, and sort contacts based on deadlines or event start times.
 */
public class ContactList {

    private static final String ERROR_INVALID_INDEX = "Error: Invalid contact index.";
    private final List<Contact> contacts;

    /**
     * Constructs an empty {@code contactList}.
     */
    public ContactList() {
        contacts = new ArrayList<>();
    }

    /**
     * Constructs a {@code contactList} with an existing list of contacts.
     *
     * @param contacts The list of contacts to initialize the contact list with.
     */
    public ContactList(List<Contact> contacts) {
        assert contacts != null : "contacts list cannot be null";
        this.contacts = new ArrayList<>(contacts);
    }

    /**
     * Returns the list of contacts.
     *
     * @return The {@code ArrayList} containing all contacts.
     */
    public List<Contact> getContacts() {
        return new ArrayList<>(contacts);
    }

    /**
     * Adds a contact to the contact list.
     *
     * @param contact The contact to be added.
     */
    public void addContact(Contact contact) {
        assert contact != null : "contact to add cannot be null";
        contacts.add(contact);
    }

    /**
     * Returns the number of contacts in the contact list.
     *
     * @return The total number of contacts.
     */
    public int size() {
        return contacts.size();
    }

    /**
     * Removes a contact from the contact list at the specified index.
     *
     * @param index The index of the contact to be removed.
     * @return The removed contact.
     */
    public Contact removeContact(int index) {
        validateIndex(index);
        return contacts.remove(index);
    }

    /**
     * Retrieves a contact from the contact list at the specified index.
     *
     * @param index The index of the contact to retrieve.
     * @return The contact at the given index.
     */
    public Contact getContact(int index) {
        validateIndex(index);
        return contacts.get(index);
    }

    /**
     * Checks if the provided index is within the valid range of the contact list.
     *
     * @param index The index to validate.
     * @throws IndexOutOfBoundsException If the index is out of range.
     */
    private void validateIndex(int index) {
        if (index < 0 || index >= contacts.size()) {
            throw new IndexOutOfBoundsException(ERROR_INVALID_INDEX);
        }
    }

    /**
     * Finds and returns a list of contacts whose descriptions contain the specified keyword.
     * The search is case-insensitive.
     *
     * @param keyword The keyword to search for in contact names.
     * @return A list of contacts whose names contain the specified keyword.
     */
    public List<Contact> findContacts(String keyword) {
        assert !keyword.isBlank() : "Search keyword cannot be null or empty";

        if (keyword.isBlank()) {
            throw new IllegalArgumentException("Search keyword cannot be null or empty");
        }
        return this.contacts.stream()
                .filter(contact -> contact.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}
