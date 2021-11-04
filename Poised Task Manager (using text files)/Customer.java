import java.util.Scanner;

public class Customer extends Person {

    public Customer(Scanner input){
        super("Customer", input);
    }

    public Customer (String type, String firstName, String lastName, String phoneNum, String email, String address){
        super("Customer", firstName, lastName , phoneNum, email, address);
    }
}
