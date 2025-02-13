package components;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages a list of contacts.
 */
public class ContactList {

    private final List<Contact> contacts;

    /**
     * Constructs an empty ContactList.
     */
    public ContactList() {
        this.contacts = new ArrayList<>();
    }

    /**
     * Adds a new contact.
     */
    public void addContact(Contact contact) {
        this.contacts.add(contact);
    }

    /**
     * Removes a contact by name.
     */
    public boolean removeContact(Contact name) {
        return contacts.removeIf(contact -> contact.getName().equalsIgnoreCase(String.valueOf(name)));
    }

    /**
     * Returns a list of all contacts.
     */
    public List<Contact> getContacts() {
        return contacts;
    }

    /**
     * Finds contacts by name.
     */
    public List<Contact> findContact(String name) {
        List<Contact> result = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(contact);
            }
        }
        return result;
    }
}
