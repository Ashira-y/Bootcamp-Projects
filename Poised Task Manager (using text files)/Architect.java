import java.util.Scanner;
 
 public class Architect extends Person {

    public Architect(Scanner input){
        super("Architect", input);
    }

    public Architect (String type, String firstName, String lastName, String phoneNum, String email, String address){
        super("Architect", firstName, lastName , phoneNum, email, address);
    }   
}
