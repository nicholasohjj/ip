package commands.contacts;

import java.util.List;
import java.util.stream.IntStream;

import commands.Command;
import components.Contact;
import components.ContactList;
import components.ContactStorage;
import components.TaskList;
import components.TaskStorage;
import exceptions.NiniException;

/**
 * Represents a command that finds contacts in the contact list based on a keyword.
 * The search is case-insensitive and matches contacts whose descriptions contain the keyword.
 */
public class FindContactCommand extends Command {

    private static final String ASSERT_KEYWORD_NULL = "Keyword cannot be null or empty";
    private static final String ASSERT_CONTACT_LIST_NULL = "Contact list cannot be null";
    private static final String ASSERT_MATCHING_contactS_NULL = "Matching contacts list should not be null";
    private static final String EMPTY_LIST_MESSAGE = "The list is empty.";
    private static final String contact_LIST_HEADER = "Here are the contacts in your list:";

    private final String keyword;

    /**
     * Constructs a FindCommand with the specified keyword.
     *
     * @param keyword The keyword to search for in contact descriptions.
     */
    public FindContactCommand(String keyword) {
        assert keyword != null && !keyword.isBlank() : ASSERT_KEYWORD_NULL;
        this.keyword = keyword;
    }

    /**
     * Executes the find command by searching for contacts that contain the specified keyword.
     * If matching contacts are found, they are displayed to the user. Otherwise, a message
     * indicating no matches is shown.
     *
     * @param contactList The list of contacts to search within.
     * @param contactStorage  The storage component (not used in this command).
     * @return         A list of contacts matching the keyword
     * @throws NiniException If an error occurs during execution.
     */
    @Override
    public String execute(TaskList taskList, ContactList contactList,
                          TaskStorage taskStorage, ContactStorage contactStorage) throws NiniException {
        assert contactList != null : ASSERT_CONTACT_LIST_NULL;

        List<Contact> matchingcontacts = searchContacts(contactList);

        if (matchingcontacts.isEmpty()) {
            return "No matching contacts found";
        } else {
            return showContactList(matchingcontacts);
        }
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


    /**
     * Searches for contacts in the contact list that contain the keyword.
     *
     * @param contactList The contact list to search within.
     * @return A list of contacts that contain the keyword in their description.
     */
    private List<Contact> searchContacts(ContactList contactList) {
        List<Contact> matchingcontacts = contactList.findContacts(keyword);
        assert matchingcontacts != null : ASSERT_MATCHING_contactS_NULL;
        return matchingcontacts;
    }
}
