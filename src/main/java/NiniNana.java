import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.ArrayList;
public class NiniNana {
    private static final String LINE = "____________________________________________________________";
    private static final String GREETING = " Hello! I'm NiniNana\n What can I do for you?";
    private static final String GOODBYE = "Bye. Hope to see you again soon!";

    private static void printLine() {
        System.out.println(LINE);
    }

    private static void printLineWithMessage(String message) {
        printLine();
        System.out.println(message);
        printLine();
    }

    private static void chat() {
        ArrayList<String> store = new ArrayList<>();

        try (Scanner sc = new Scanner(System.in)) {
            String input;
            do {
                input = sc.nextLine().trim();

                if (input.equalsIgnoreCase("list")) {

                    if (store.isEmpty()) {
                        printLineWithMessage("The list is empty");
                        continue;
                    }
                    printLine();

                    for (int i = 0; i < store.size(); i++) {
                        System.out.printf("%d. %s%n", i+1, store.get(i));
                    }
                    printLine();
                } else if (!input.equalsIgnoreCase("bye")){
                    store.add(input);
                    printLineWithMessage("Added: " + input);
                }
            } while (!input.equalsIgnoreCase("bye"));
        }
    }

    public static void main(String[] args) {
        printLineWithMessage(GREETING);
        chat();
        printLineWithMessage(GOODBYE);
    }
}
