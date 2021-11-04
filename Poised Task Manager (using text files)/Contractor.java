import java.util.Scanner;

public class Contractor extends Person {

    public Contractor(Scanner input){
        super("Contractor", input);
    }

    public Contractor(String type, String firstName, String lastName, String phoneNum, String email, String address){
        super("Contractor", firstName, lastName , phoneNum, email, address);
    }
}
