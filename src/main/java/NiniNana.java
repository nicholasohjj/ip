public class NiniNana {
    private static final String LINE = "____________________________________________________________";
    private static final String GREETING = " Hello! I'm NiniNana\n What can I do for you?";
    private static final String GOODBYE = "Bye. Hope to see you again soon!";

    private static void printLine() {
        System.out.println(LINE);
    }

    private static void printMessage(String message) {
        System.out.println(message);
    }

    public static void main(String[] args) {
        printLine();
        printMessage(GREETING);
        printLine();
        printMessage(GOODBYE);
        printLine();
    }
}
