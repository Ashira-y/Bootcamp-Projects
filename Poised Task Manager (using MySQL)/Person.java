import java.util.Scanner;
import java.sql.*;

public class Person {

    // Attributes:
    String type;
    int id;
    String firstName;
    String lastName;
    String phoneNum;
    String email;
    String address;

    // Constructors:
    public Person(String type, Scanner input, ResultSet results, Statement statement){
        this.type = type;
        this.id = creatingNewId(results, statement);  
        this.populate(input);    
    }

    public Person (String type, int id, String firstName, String lastName, String phoneNum, String email, String address, ResultSet results, Statement statement){
        this.type = type;
        this.id = creatingNewId(results, statement);          
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.email = email;
        this.address = address;
    }

    public Person (String type, int id, ResultSet results, Statement statement){
        this.type = type;
        this.id = id;

        try{
            results = statement.executeQuery("SELECT * FROM " + type + " WHERE id=" + id);
            System.out.println();
            while (results.next()) {
                this.firstName = results.getString("first_name");
                this.lastName = results.getString("last_name");
                this.phoneNum = results.getString("phone_num");
                this.email = results.getString("email");
                this.address = results.getString("address");
            } 
       }
       catch (SQLException e){
           e.printStackTrace();
       }  
    }

    // Creating getters and setter to update the contact details
    public String getemail(){
        return email;
    }
    public int getId(){
        return id;
    }
    public void setemail(String newEmail){
        email = newEmail;
    }
    public String getphoneNum(){
        return phoneNum;
    }
    public void setphoneNum(String newNum){
        phoneNum = newNum;
    }
    public String getaddress(){
        return address;
    }
    public void setaddress(String newAddress){
        address = newAddress;
    }

    // Populate method - asking the user to enter in the relevant details
    public void populate(Scanner input){

        System.out.println("\n" + type + " details ");
        System.out.println("\nEnter the " + type + "'s first name:  ");
        this.firstName = input.nextLine();
        System.out.println("\nEnter the " + type + "'s last name:  ");
        this.lastName = input.nextLine();
        System.out.println("\nEnter the " + type + "'s phone number: ");
        this.phoneNum = input.nextLine();
        System.out.println("\nEnter the "+ type + "'s email address: ");
        this.email = input.nextLine();
        System.out.println("\nEnter the " + type + "'s address: ");
        this.address = input.nextLine();

    }

    // This method combines the user first and last name creating their fullname
    public String getFullName(){
        String fullName = firstName + " " + lastName;
        return fullName;
    }

    // This method creates a new ID
    public int creatingNewId(ResultSet results, Statement statement){
        try{
            String sqlQuery = "SELECT MAX(id) as id FROM " + type;
            results = statement.executeQuery(sqlQuery);

            int newId = 1;
            while(results.next()){
                newId = results.getInt("id") + 1;
            }   

            return newId;
        }
        catch (SQLException e){
            e.printStackTrace();
            return 1;
            
        }
    }

    // This method write the new person object to the database
    public void writePersonToDatabase(Statement statement){
        try{
            int rowsAffected;
   
            String sqlQuery2 = "INSERT INTO " + type + " VALUES (" + id + ", '" + firstName + "', '" + lastName + "', '" + phoneNum + "', '"  + email + "', '" + address + "')";

            rowsAffected = statement.executeUpdate(sqlQuery2);
            System.out.println("Query complete, " + rowsAffected + " rows added"); 
        }
        catch (SQLException e){
            e.printStackTrace(); 
        }
    }

    // This method updates the person object in the database
    public void updatingDetailsToDatabase(Statement statement){
        try{
            int rowsAffected;
         
            String sqlQuery2 = "UPDATE " + type + " SET phone_num='" + phoneNum + "', email='"  + email + "', address='" + address + "' WHERE id=" + id;

            rowsAffected = statement.executeUpdate(sqlQuery2);
            System.out.println("Query complete, " + rowsAffected + " rows added"); 
             
        }
        catch (SQLException e){
            e.printStackTrace(); 
        }
    }

}


