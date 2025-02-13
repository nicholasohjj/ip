package commands.contacts;

import java.io.IOException;
import java.util.Arrays;

import commands.Command;
import components.Contact;
import components.ContactList;
import components.ContactStorage;
import components.TaskList;
import components.TaskStorage;
import exceptions.InvalidContactNumberException;
import exceptions.NiniException;

/**
 * Represents a command to delete a contact from the contact list.
 * This command removes a contact from the list, updates storage, and notifies the user.
 */
public class DeleteContactCommand extends Command {

    private static final String ASSERT_CONTACTLIST_NULL = "Contact list cannot be null";
    private static final String ASSERT_STORAGE_NULL = "Storage cannot be null";
    private static final String ASSERT_CONTACT_INDEX_NEGATIVE = "Contact index must be non-negative";
    private static final String ERROR_INVALID_CONTACT_NUMBER =
            "Invalid contact. Please enter a number between 1 and ";
    private static final String ERROR_STORAGE_UPDATE = "Error saving updated contact list: ";

    private final int[] contactIndices;

    /**
     * Constructs a {@code DeleteCommand} with the specified contact index.
     *
     * @param contactIndices The indices of the contacts to be deleted (zero-based).
     */
    public DeleteContactCommand(int... contactIndices) {
        assert contactIndices != null && contactIndices.length > 0 : "Contact indices cannot be null or empty";
        this.contactIndices = contactIndices;
    }

    /**
     * Executes the delete contact command.
     * Removes the contact from the contact list, displays a confirmation message,
     * and updates the storage.
     *
     * @param contactList The contact list from which the contact is deleted.
     * @param contactStorage  The storage component responsible for saving contacts.
     * @throws NiniException If the contact index is invalid or an error occurs while updating storage.
     */
    @Override
    public String execute(TaskList taskList, ContactList contactList,
                          TaskStorage taskStorage, ContactStorage contactStorage) throws NiniException {
        assert contactList != null : ASSERT_CONTACTLIST_NULL;
        assert contactStorage != null : ASSERT_STORAGE_NULL;

        int initialSize = contactList.size();
        StringBuilder confirmationMessage = new StringBuilder();

        int[] sortedIndices = Arrays.stream(contactIndices)
                .distinct()
                .sorted()
                .toArray();

        for (int contactIndex : sortedIndices) {
            assert contactIndex >= 0 : ASSERT_CONTACT_INDEX_NEGATIVE;
            validateIndex(contactList, contactIndex);

            Contact removedContact = contactList.removeContact(contactIndex);
            confirmationMessage.append(showContactRemoved(removedContact, contactList.size())).append("\n");
        }

        updateStorage(contactStorage, contactList, confirmationMessage, initialSize);
        return confirmationMessage.toString().trim();
    }

    /**
     * Displays a message confirming that a contact has been removed.
     *
     * @param contact The contact that was removed.
     * @param size The total number of contacts after the removal.
     */
    public String showContactRemoved(Contact contact, int size) {
        return String.format(
                "Noted. I've removed this contact:\n  %s\nNow you have %d contacts in the list.",
                contact, size);
    }

    /**
     * Validates if the contact index is within the valid range.
     *
     * @param contactList The contact list.
     * @param contactIndex The contact index to validate.
     * @throws InvalidContactNumberException If the index is out of bounds.
     */
    private void validateIndex(ContactList contactList, int contactIndex) throws InvalidContactNumberException {
        try {
            contactList.getContact(contactIndex); // Triggers ContactList's validateIndex()
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidContactNumberException(ERROR_INVALID_CONTACT_NUMBER + contactList.size() + ".");
        }
    }

    /**
     * Updates storage after contacts are deleted.
     *
     * @param contactStorage The storage component.
     * @param contactList The updated contact list.
     * @param confirmationMessage The confirmation message builder.
     * @param initialSize The initial size of the contact list.
     */
    private void updateStorage(ContactStorage contactStorage,
                               ContactList contactList, StringBuilder confirmationMessage, int initialSize) {
        try {
            if (contactList.size() < initialSize) {
                contactStorage.overwriteContacts(contactList.getContacts());
            }
        } catch (IOException e) {
            confirmationMessage.append("\n").append(ERROR_STORAGE_UPDATE).append(e.getMessage());
        }
    }

    /**
     * Returns the index of the contact to be deleted.
     *
     * @return The zero-based index of the contact.
     */
    public int[] getDeleteIndices() {
        return this.contactIndices;
    }
}
