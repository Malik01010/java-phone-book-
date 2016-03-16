/*
 * Created by MapuH on 15-Mar-2016
 */

import java.util.*;
import java.io.*;

public class Phonebook {

    private static final String DATA_PATH = "contacts.dat";

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

    public static void start() {
        Menu myMenu = new Menu();
        myMenu.listOptions();
    }

    public static void main(String[] args) {

        /*
        Contact marin = new Contact("Marin", "0887 17 44 11");

        List<Contact> contacts = new ArrayList<>();
        contacts.add(marin);

        saveContacts(contacts);
        */

        List<Contact> test = loadContacts();
        System.out.println(test);
    }
}
