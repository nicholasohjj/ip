package commands.contacts;

import java.util.List;
import java.util.stream.IntStream;

import commands.Command;
import components.Contact;
import components.ContactList;
import components.ContactStorage;
import components.TaskList;
import components.TaskStorage;

/**
 * Represents a command to display the list of tasks.
 * This command retrieves the tasks from the task list and displays them to the user.
 */
public class ListContactCommand extends Command {

    private static final String EMPTY_LIST_MESSAGE = "The contact list is empty.";
    private static final String contact_LIST_HEADER = "Here are the contacts in your list:";

    /**
     * Executes the list command.
     * Retrieves and displays all contacts stored in the contact list.
     *
     * @param contactList The contact list containing the contacts.
     * @param contactStorage  The storage component (not used in this command).
     * @return         The list of contacts.
     */
    @Override
    public String execute(TaskList taskList, ContactList contactList,
                          TaskStorage taskStorage, ContactStorage contactStorage) {
        return showContactList(contactList.getContacts());
    }

    /**
     * Displays the list of contacts currently stored.
     *
     * @param contacts The list of contacts to be displayed.
     */
    public String showContactList(List<Contact> contacts) {
        if (contacts.isEmpty()) {
            return EMPTY_LIST_MESSAGE;
        }

        return IntStream.range(0, contacts.size())
                .mapToObj(i -> String.format("%d. %s", i + 1, contacts.get(i)))
                .reduce(contact_LIST_HEADER, (list, contact) -> list + "\n" + contact);
    }
}
