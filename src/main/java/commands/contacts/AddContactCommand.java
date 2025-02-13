package commands.contacts;

import java.io.IOException;

import commands.Command;
import components.Contact;
import components.ContactList;
import components.ContactStorage;
import components.TaskList;
import components.TaskStorage;

/**
 * Represents a command to add a task to the task list.
 * This command updates the task list, notifies the user, and saves the task to storage.
 */
public class AddContactCommand extends Command {

    private static final String ERROR_STORAGE = "Error saving contact to storage: ";
    private static final String ASSERT_CONTACT_NULL = "Contact cannot be null";
    private static final String ASSERT_CONTACT_LIST_NULL = "Contact list cannot be null";
    private static final String ASSERT_STORAGE_NULL = "Storage cannot be null";
    private static final String ASSERT_contactLIST_SIZE = "Contact list size should increase by 1";

    private final Contact contact;

    /**
     * Constructs an {@code AddCommand} with the specified contact.
     *
     * @param contact The contact to be added.
     */
    public AddContactCommand(Contact contact) {
        assert contact != null : ASSERT_CONTACT_NULL;
        this.contact = contact;
    }

    /**
     * Executes the add contact command.
     * Adds the contact to the contact list, displays a confirmation message to the user,
     * and saves the contact to storage.
     *
     * @param contactList The contact list to which the contact is added.
     * @param contactStorage  The storage component responsible for saving contacts.
     * @return
     */
    @Override
    public String execute(TaskList taskList, ContactList contactList,
                          TaskStorage taskStorage, ContactStorage contactStorage) {
        assert contactList != null : ASSERT_CONTACT_LIST_NULL;
        assert contactStorage != null : ASSERT_STORAGE_NULL;

        int initialSize = contactList.size();
        contactList.addContact(contact);
        assert contactList.size() == initialSize + 1 : ASSERT_contactLIST_SIZE;

        String confirmationMessage = showcontactAdded(contact, contactList.size());
        return saveContactToStorage(contactStorage, confirmationMessage);
    }

    /**
     * Displays a message confirming that a contact has been added.
     *
     * @param contact The contact that was added.
     * @param size The total number of contacts after the addition.
     * @return     String message showing contact added
     */
    public String showcontactAdded(Contact contact, int size) {
        return String.format(
                "Got it. I've added this contact:\n  %s\nNow you have %d contacts in the list.",
                contact, size);
    }


    /**
     * Saves the contact to storage and returns an appropriate message.
     *
     * @param contactStorage             The storage component responsible for saving contacts.
     * @param confirmationMessage The confirmation message to return on success.
     * @return The confirmation message if successful, or an error message if saving fails.
     */
    private String saveContactToStorage(ContactStorage contactStorage, String confirmationMessage) {
        try {
            contactStorage.saveContact(contact);
            return confirmationMessage;
        } catch (IOException e) {
            return ERROR_STORAGE + e.getMessage();
        }
    }

    /**
     * Returns the contact associated with this command.
     *
     * @return The contact that was added.
     */
    public Contact getAddedContact() {
        return this.contact;
    }
}
