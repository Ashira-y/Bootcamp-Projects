 import java.util.Scanner;
 import java.sql.*;
 
 public class Architect extends Person {

    public Architect(Scanner input, ResultSet results, Statement statement){
        super("architect", input, results, statement);
    }

    public Architect (int id, String firstName, String lastName, String phoneNum, String email, String address, ResultSet results, Statement statement){
        super("architect", id, firstName, lastName , phoneNum, email, address, results, statement);
    }   

    public Architect (int id, ResultSet results, Statement statement){
        super ("architect", id, results, statement);
    }
}
