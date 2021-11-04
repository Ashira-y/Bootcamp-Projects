import java.util.*;
import java.sql.*;

public class Book {

    // Attributes:
    int id1;
    String title;
    String author;
    int qty;

    // Constructor for manully filling in details
    public Book(int id1, String title, String author, int qty){
        this.id1 = id1;
        this.title = title;
        this.author = author;
        this.qty = qty;
    }

    // Constructor which calls the populatDetails() method
    public Book(Scanner input, ResultSet results,  Statement statement){
        this.id1 = creatingNewId(results, statement);
        populateDetails(input);
    }

    // Getters and Setters:
    public int getId(){
        return id1;
    }
    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }
    public int getQty(){
        return qty;
    }
    public void setTitle(String newTitle){
        title = newTitle;
    }
    public void setAuthor(String newAuthor){
        author = newAuthor;
    }
    public void setQty(int newQty){
        qty = newQty;
    }


    // populateDetails() method asks the user to enter in details
    private void populateDetails(Scanner input){
        System.out.println("\nEnter the title: ");
        this.title = input.nextLine();
        System.out.println("\nEnter the author: ");
        this.author = input.nextLine();
        this.qty = getQtyAsInt(input);
    }

    // This method checks that the qty is a valid number
    private static int getQtyAsInt(Scanner input){
        while(true){
            System.out.println("\nEnter the qty: ");
            String stringQty = input.nextLine();

            try{
                int intQty = Integer.parseInt(stringQty);
                return intQty;
            }
            catch (Exception e){
                System.out.println("Please enter a valid qty");
                continue;
            }
        }
    }

    // This method creates a new ID through finding the max ID and adding 1 to it
    public static int creatingNewId(ResultSet results,  Statement statement){
        try{
            // Getting the max id from the database
            String sqlQuery = "SELECT MAX(id) as id FROM books";
            results = statement.executeQuery(sqlQuery);

            int newId = 1;
            
            while(results.next()){
                newId = results.getInt("id") + 1;
            }

            results.close();
            statement.close();  

            return newId;
        }
        catch (SQLException e){
            e.printStackTrace();
            return 1;
        }
    }

    // This method writes to the database
    public void writeToDatabase(ResultSet results,  Statement statement){
        try{
            int rowsAffected;
        
            String sqlQuery2 = "INSERT INTO books VALUES (" + this.id1 + ", '" + this.title + "', '" + this.author + "', " + this.qty + ")";
            
            rowsAffected = statement.executeUpdate(sqlQuery2);
            System.out.println("Query complete, " + rowsAffected + " rows added"); 

            statement.close();    
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }   

    // This method displays a menu for updating a book in the database
    public Boolean updatingDetails(Scanner input){
        while(true){         
            System.out.println("\nUpdating details: select an option or 'r' to discard and return: \n1. Update Title \n2. Update Author \n3. Update Qty \n4. Save and return");
            String option = input.nextLine();
    
            if (option.equalsIgnoreCase("r")){
                return false;
            }

            else if (option.equals("1")){
                System.out.println("\nEnter the updated title: ");
                String updatedTile = input.nextLine();
                setTitle(updatedTile);
            }

            else if (option.equals("2")){
                System.out.println("\nEnter the updated author: ");
                String updatedAuthoer = input.nextLine();
                setAuthor(updatedAuthoer);
            }

            else if (option.equals("3")){
                int updatedQty = getQtyAsInt(input);
                setQty(updatedQty);
            }

            else if (option.equals("4")){
                return true;
            }
            
            else{
                System.out.println("\nInvalid option!");
            }
        }
    }
}
