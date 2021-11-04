import java.util.Scanner;
import java.sql.*;

public class Customer extends Person {

    public Customer(Scanner input, ResultSet results, Statement statement){
        super("customer", input, results, statement);
    }

    public Customer (int id,  String firstName, String lastName, String phoneNum, String email, String address, ResultSet results, Statement statement){
        super("customer", id, firstName, lastName , phoneNum, email, address, results, statement);
    }

    public Customer (int id, ResultSet results, Statement statement){
        super ("customer", id, results, statement);
    }
}
