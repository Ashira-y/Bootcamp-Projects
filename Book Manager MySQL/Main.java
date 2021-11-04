import java.sql.*;
import java.util.*;

public class Main {
    
    // This method returns a boolean that is used to determine if a number has been enetered
    private static Boolean isNumber(String toCheck){
        Boolean isNum;

        try{
            Integer.parseInt(toCheck);
            isNum = true;
            return isNum;
        }
        catch (Exception e){
            isNum = false;
            return isNum;
        }
    }

    // This method gets a valid ID from the user for referencing a book
    private static int getValidIdFromUser(ResultSet results,  Statement statement, Scanner input){   
        while (true){                        
            System.out.println("\nSelect book id: ");
            String toEdit = input.nextLine();

            if (isNumber(toEdit) == true){
                int bookToEdit = Integer.parseInt(toEdit);

                try{
                    results = statement.executeQuery("SELECT * FROM books where id=" + bookToEdit);
                    if(results.next()) {
                        return bookToEdit;
                    }
                    else{
                        System.out.println("\nPlease enter a valid id!");
                    }    
                }
                catch (Exception e){
                    System.out.println("\nPlease enter a valid id!");
                }
            }
            else {
                System.out.println("\nPlease enter a valid id!");
                continue;
            }
        }
    }
  
    // This method displays all the books in the database
    public static void printAllFromTable(Statement statement) throws SQLException{
        ResultSet results = statement.executeQuery("SELECT id, title, author, qty FROM books");
        System.out.println();
        while (results.next()) {
            System.out.println(results.getInt("id") + ", " + results.getString("title") + ", " + results.getString("author") + ", " + results.getInt("qty"));
        }
        System.out.println();
    }  

    // The main method displays a menu where the user can choose between various options
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);

        try{
            // Connect to the library_db database, via the jdbc:mysql: channel on localhost (this PC)
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebooks_db?useSSL=true", "otheruser", "swordfish");
            
            Statement statement = connection.createStatement();
            ResultSet results;
            int rowsAffected;

           results = statement.executeQuery("SELECT * FROM books");
    
            // Displaying a menu
            while (true){
                System.out.println("Books database (Q to quit): \n1. Enter a new book into the database \n2. Update book information \n3. Delete books from database \n4. Search the database to find a specific book \n5. Display all books");  
                String option = input.nextLine();
    
                // Quitting the programme
                if (option.equalsIgnoreCase("q")){
                    break;
                }
                
                // Adding a new book and displaying it.
                else if (option.equals("1")){
                    Book newBook = new Book(input, results, statement); 
                    newBook.writeToDatabase(results, statement);

                    System.out.println("\nNew book created: ");
                    results = statement.executeQuery("SELECT * FROM books WHERE id=" + newBook.getId());
                    while (results.next()){
                        System.out.println("\nBook id: " + results.getInt("id") + "\nTitle: " + results.getString("title") + "\nAuthor: " + results.getString("author") + "\nQuantity: " + results.getInt("qty") + "\n\n");
                    }   
                }

                // Updating a book
                else if (option.equals("2")){
                    while (true){                    
                        int bookToEdit =  getValidIdFromUser(results, statement, input);

                        results = statement.executeQuery("SELECT * FROM books WHERE id=" + bookToEdit);
                        Book bookToUpdate = null;
                        while (results.next()){
                            bookToUpdate = new Book(results.getInt("id"), results.getString("title"),  results.getString("author"), results.getInt("qty")); 
                        }  

                        if(bookToUpdate!=null){
                            Boolean bookChanged = bookToUpdate.updatingDetails(input);
                            if (bookChanged == true){
                                rowsAffected = statement.executeUpdate("UPDATE books SET title='" + bookToUpdate.getTitle() + "', author='" + bookToUpdate.getAuthor() + "', qty=" + bookToUpdate.getQty() + " WHERE id=" + bookToUpdate.getId());
                                System.out.println("\nquery complete, " + rowsAffected + " rows update.\n");
                                break;
                            }
                            else {
                                System.out.println("\nNo changes saved\n");
                                break;
                            }                                                    
                        }
                    }
                }

                // Deleting a book
                else if (option.equals("3")){
                    int toDelete = getValidIdFromUser(results, statement, input);

                    rowsAffected = statement.executeUpdate("DELETE FROM books WHERE id=" + toDelete);
                    System.out.println("\nquery complete, " + rowsAffected + " rows removed.\n");
                }
                
                // Displaying a book through calling the id
                else if (option.equals("4")){
                    int bookToDisplay = getValidIdFromUser(results, statement, input);

                    results = statement.executeQuery("SELECT * FROM books WHERE id=" + bookToDisplay);

                    while (results.next()){
                        System.out.println("\nBook id: " + results.getInt("id") + "\nTitle: " + results.getString("title") + "\nAuthor: " + results.getString("author") + "\nQuantity: " + results.getInt("qty") + "\n");
                    }
                }

                // Displaying all books
                else if (option.equals("5")){
                    printAllFromTable(statement);
                }
                
                // Invalid option
                else{
                    System.out.println("\nInvalid option!");
                    continue;
                }
            }

            results.close();
            statement.close();
            connection.close();     
        }
        catch (SQLException e){
            e.printStackTrace();
        }       
    }
}
