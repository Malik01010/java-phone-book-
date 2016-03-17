import java.util.*;
import java.io.*;

public class PhoneBook {

    private static final String DATA_PATH = "src/contacts.dat";

    public static void saveContacts(List<Contact> contacts) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_PATH))) {
            oos.writeObject(contacts);
            oos.close();

        } catch (IOException ioex) {
            System.err.println(ioex.toString());
        }
    }

    public static List<Contact> loadContacts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_PATH))) {
            List<Contact> contacts = (List<Contact>) ois.readObject();
            ois.close();
            return contacts;

        } catch (ClassNotFoundException | IOException ex) {
            System.err.println(ex.toString());
            return null;
        }
    }

    public static void listCommands() {
        System.out.println("Type a command or 'exit' to quit:");
        System.out.println("list - lists all saved contacts in alphabetical  order");
        System.out.println("show - finds a contact by name");
        System.out.println("find - searches for a contact by number");
        System.out.println("add - saves a new contact entry into the phone book");
        System.out.println("delete - removes a contact from the phone book");
        System.out.println("help - lists all valid commands");
        System.out.println("---------------------------");
    }

    public static void main(String[] args) {

        System.out.println("PHONE BOOK 0.1");
        System.out.println("===========================");
        listCommands();
        System.out.print("> ");

        Scanner input = new Scanner(System.in);
        String line = input.nextLine().trim();

        while (!line.equals("exit")) {

            switch (line) {
                case "list":
                    System.out.println("list command");
                    break;
                case "show":
                    System.out.println("show command");
                    break;
                case "find":
                    System.out.println("find command");
                    break;
                case "add":
                    System.out.println("add command");
                    break;
                case "delete":
                    System.out.println("delete command");
                    break;
                case "help":
                    listCommands();
                    break;
                default:
                    System.out.println("Invalid command!");
                    break;
            }


            System.out.print("\n> ");
            line = input.nextLine().trim();
        }

        List<Contact> test = loadContacts();
        System.out.println(test);

        System.out.println("'Phone Book 0.1' terminated.");
    }
}
