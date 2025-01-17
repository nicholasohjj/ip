import java.util.Scanner;

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
        try (Scanner sc = new Scanner(System.in)) {
            String input;
            do {
                input = sc.nextLine();
                printLineWithMessage(input);
            } while (!input.equalsIgnoreCase("bye"));
        }
    }

    public static void main(String[] args) {
        printLineWithMessage(GREETING);
        chat();
        printLineWithMessage(GOODBYE);
    }
}
