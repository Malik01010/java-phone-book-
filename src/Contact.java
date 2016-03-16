import java.io.Serializable;

/*
 * Created by MapuH on 16-Mar-2016
 */
public class Contact implements Serializable {

    private String name;
    private String number;

    public Contact(String name, String number) {
        try {
            setName(name);
            setNumber(number);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if (!name.matches("^.{2,40}$")) {
            throw new Exception("Name cannot exceed 40 characters.");
        } else {
            this.name = name;
        }
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) throws Exception {
        if (!number.matches("^\\+?[0-9 ]{3,25}$")) {
            throw new Exception("Number can contain only '+', spaces and digits, max length 25.");
        } else {
            this.number = number;
        }
    }

    @Override
    public String toString() {
        return this.name + " : " + this.number;
    }
}
