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
        System.out.println("list - lists all saved contacts in alphabetical  order");
        System.out.println("show - finds a contact by name");
        System.out.println("find - searches for a contact by number");
        System.out.println("add - saves a new contact entry into the phone book");
        System.out.println("edit - modifies an existing contact");
        System.out.println("delete - removes a contact from the phone book");
        System.out.println("help - lists all valid commands");
        System.out.println("---------------------------");
    }

    public static void listContacts() {
        List<Contact> contacts = loadContacts();
        if (contacts == null) {
            System.out.println("Sorry, could not load list of contacts!");
        } else {
            Collections.sort(contacts, new ContactComparator());
            for (Contact entry : contacts) {
                System.out.println(entry);
            }
        }

        System.out.println();
        System.out.println("Type a command or 'exit' to quit. For a list of valid commands use 'help':");
    }

    public static void showContact(Scanner input) {
        System.out.println("Enter the name you are looking for:");
        String name = input.nextLine().trim();

        List<Contact> contacts = loadContacts();
        if (contacts == null) {
            System.out.println("Sorry, could not load list of contacts!");
        } else {

            boolean found = false;
            for (Contact entry : contacts) {
                if (entry.getName().equals(name)) {
                    System.out.println(entry);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Sorry, nothing found!");
            }
        }

        System.out.println();
        System.out.println("Type a command or 'exit' to quit. For a list of valid commands use 'help':");
    }

    public static void findContact(Scanner input) {
        System.out.println("Enter a number to see to whom does it belong:");
        String number = input.nextLine().trim();

        while (!number.matches("^\\+?[0-9 ]{3,25}$")) {
            System.out.println("Invalid number! May contain only digits, spaces and '+'. Min length 3, max length 25.");
            System.out.println("Enter number:");
            number = input.nextLine().trim();
        }

        List<Contact> contacts = loadContacts();
        if (contacts == null) {
            System.out.println("Sorry, could not load list of contacts!");
        } else {
            boolean found = false;
            for (Contact entry : contacts) {
                if (entry.getNumber().equals(number)) {
                    System.out.println(entry);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("Sorry, number not found!");
            }
        }

        System.out.println();
        System.out.println("Type a command or 'exit' to quit. For a list of valid commands use 'help':");
    }

    public static void addContact(Scanner input) {
        System.out.println("You are about to add a new contact to the phone book.");
        Contact newContact;
        while (true) {
            System.out.println("Enter contact name:");
            String name = input.nextLine().trim();
            System.out.println("Enter contact number:");
            String number = input.nextLine().trim();

            newContact = new Contact(name, number);
            if (newContact.getNumber() != null) {
                break;
            }
        }

        System.out.println(newContact);

        List<Contact> contacts = loadContacts();
        if (contacts == null) {
            System.out.println("Could not load list of contacts! Creating new phone book...");
            contacts = new ArrayList<Contact>();
        }

        if (newContact.exists(contacts)) {
            System.out.println("This name already exists in the phone book!");
            System.out.println("You can use 'show'to view it or 'edit' to modify the record.");
        } else {
            contacts.add(newContact);
            saveContacts(contacts);
            System.out.printf("Successfully added contact '%s' !\n", newContact);
        }

        System.out.println();
        System.out.println("Type a command or 'exit' to quit. For a list of valid commands use 'help':");
    }

    public static void editContact(Scanner input) {
        System.out.println("Enter name of the contact you would like to modify:");
        String name = input.nextLine().trim();

        List<Contact> contacts = loadContacts();
        if (contacts == null) {
            System.out.println("Sorry, could not load list of contacts!");
        } else {
            int position = -1;

            for (int i = 0; i < contacts.size(); i++) {
                if (contacts.get(i).getName().equals(name)) {
                    position = i;
                    break;
                }
            }

            if (position > -1) {
                System.out.printf("Current number for %s is %s\n",
                        contacts.get(position).getName(), contacts.get(position).getNumber());
                System.out.println("Enter a new number:");
                String number = input.nextLine().trim();
                while (true) {
                    try {
                        contacts.get(position).setNumber(number);
                        System.out.printf("Number for %s was changed to %s. Record updated!\n",
                                contacts.get(position).getName(), contacts.get(position).getNumber());
                        saveContacts(contacts);
                        break;
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                        System.out.println("Enter a new number:");
                    }
                    number = input.nextLine().trim();
                }

            } else {
                System.out.println("Sorry, name not found!");
            }

        }

        System.out.println();
        System.out.println("Type a command or 'exit' to quit. For a list of valid commands use 'help':");
    }

    public static void deleteContact(Scanner input) {
        System.out.println("Enter name of the contact to be deleted:");
        String name = input.nextLine().trim();

        List<Contact> contacts = loadContacts();
        if (contacts == null) {
            System.out.println("Sorry, could not load list of contacts!");
        } else {
            int position = -1;
            for (int i = 0; i < contacts.size(); i++) {
                if (contacts.get(i).getName().equals(name)) {
                    position = i;
                    break;
                }
            }

            if (position > -1) {
                System.out.printf("Contact '%s' will be deleted. Are you sure? [Y/N]:\n", contacts.get(position));
                String confirmation = input.nextLine().trim().toLowerCase();
                confLabel:
                while (true) {
                    switch (confirmation) {
                        case "y":
                            contacts.remove(position);
                            saveContacts(contacts);
                            System.out.println("Contact was deleted successfully!");
                            break confLabel;
                        case "n":
                            break confLabel;
                        default:
                            System.out.println("Delete contact? [Y/N]:");
                            break;
                    }
                    confirmation = input.nextLine().trim().toLowerCase();
                }

            } else {
                System.out.println("Sorry, name not found!");
            }
        }

        System.out.println();
        System.out.println("Type a command or 'exit' to quit. For a list of valid commands use 'help':");
    }

    public static void main(String[] args) {

        System.out.println("PHONE BOOK (ver 0.1)");
        System.out.println("===========================");
        System.out.println("Type a command or 'exit' to quit. For a list of valid commands use 'help':");
        listCommands();
        System.out.print("> ");

        Scanner input = new Scanner(System.in);
        String line = input.nextLine().trim();

        while (!line.equals("exit")) {

            switch (line) {
                case "list":
                    listContacts();
                    break;
                case "show":
                    showContact(input);
                    break;
                case "find":
                    findContact(input);
                    break;
                case "add":
                    addContact(input);
                    break;
                case "edit":
                    editContact(input);
                    break;
                case "delete":
                    deleteContact(input);
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

        System.out.println("'Phone Book 0.1' terminated.");
    }
}
