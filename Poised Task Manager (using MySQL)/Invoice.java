import java.sql.*;

public class Invoice {
    
    // Creating attributes
    String customerName;
    String completionDate;
    Double outstandingAmount;

    // Constructor
    public Invoice(String customerName, String completionDate, Double outstandingAmount){
        this.customerName = customerName;
        this.completionDate = completionDate;
        this.outstandingAmount = outstandingAmount;
    }

    // This method writes the invoice object to the database
    public void writeInvoiceToDatabase(Statement statement){
        try{
            int rowsAffected;
      
            String sqlQuery2 = "INSERT INTO invoice  (customer_name, completion_date, outstanding_amount) VALUES ('" + customerName + "', '" + completionDate + "', " + outstandingAmount + ")";
            
            rowsAffected = statement.executeUpdate(sqlQuery2);
            System.out.println("Query complete, " + rowsAffected + " rows added");    
        }
        catch (SQLException e){
            e.printStackTrace();  
        }
    }
}
