import java.util.Scanner;
import java.sql.*;

public class Contractor extends Person {

    public Contractor(Scanner input, ResultSet results, Statement statement){
        super("contractor", input, results, statement);
    }

    public Contractor(String type, int id, String firstName, String lastName, String phoneNum, String email, String address, ResultSet results, Statement statement){
        super("contractor", id, firstName, lastName , phoneNum, email, address, results, statement);
    }

    public Contractor (int id, ResultSet results, Statement statement){
        super ("contractor", id, results, statement);
    }
}
