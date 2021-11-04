import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.*;

public class ProjectDetails {

    // Attributes: 
    int projectNumber;
    String projectName;
    String buildingType;
    String address;
    String erfNumber;
    Double projectFee;
    Double amountPaid;
    String deadline;
    Boolean finalised;

    int architect_id;
    int contractor_id;
    int customer_id;

    Architect architectDetails;
    Contractor contractorDetails;
    Customer customerDetails;   

    // Constructor: 
  

    public ProjectDetails(int architect_id, int contractor_id,  int customer_id, Scanner input){
        this.architect_id = architect_id;
        this.contractor_id = contractor_id;
        this.customer_id = customer_id;

        // Calling creatingNewProjectNumber() to create a new project number
        this.projectNumber = creatingNewProjectNumber();
        // Calling populate method to get user input for the other details
        this.populateDetails(input);
    }

     public ProjectDetails(Architect architectDetails, Contractor contractorDetails,  Customer customerDetails, Scanner input){
        this.architectDetails = architectDetails;
        this.contractorDetails = contractorDetails;
        this.customerDetails = customerDetails;
        this.projectNumber = creatingNewProjectNumber();
        this.populateDetails(input);
    }    

    public ProjectDetails(int projectNumber){
        this.projectNumber = projectNumber;
        try{
            // Connect to the library_db database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedpms?useSSL=true", "otheruser", "swordfish");
            // Create a direct line to the database for running our queries
            Statement statement = connection.createStatement();

            ResultSet results = statement.executeQuery("SELECT * FROM project_details WHERE project_num=" + projectNumber);
            System.out.println();
            // Itteravting through each row and setting the different attributes to the values
            while (results.next()) {
                this.projectName = results.getString("project_name");
                this.buildingType = results.getString("building_type");
                this.address = results.getString("address");
                this.erfNumber = results.getString("erf_number");
                this.projectFee = results.getDouble("project_fee");
                this.amountPaid = results.getDouble("amount_paid");
                this.deadline = results.getString("deadline");
                this.finalised = results.getBoolean("finalised");
                this.architect_id =  results.getInt("architect_id");
                this.contractor_id = results.getInt("contractor_id");
                this.customer_id = results.getInt("customer_id");
            }
           // Close up connections
           results.close();
           statement.close();
           connection.close(); 
       }
       catch (SQLException e){
           // We only want to catch a SQLException - anything else is off-limits for now.
           e.printStackTrace();
       }  
    }

    public ProjectDetails(String projectName){
        this.projectName = projectName;
        try{
            // Connect to the library_db database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedpms?useSSL=true", "otheruser", "swordfish");
            
            // Create a direct line to the database for running our queries
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM project_details WHERE project_num=" + projectName);
            System.out.println();
            // Itteravting through each row and setting the different attributes to the values
            while (results.next()) {
                this.projectNumber = results.getInt("project_num");
                this.buildingType = results.getString("building_type");
                this.address = results.getString("address");
                this.erfNumber = results.getString("erf_number");
                this.projectFee = results.getDouble("project_fee");
                this.amountPaid = results.getDouble("amount_paid");
                this.deadline = results.getString("deadline");
                this.finalised = results.getBoolean("finalised");
                this.architect_id =  results.getInt("architect_id");
                this.contractor_id = results.getInt("contractor_id");
                this.customer_id = results.getInt("customer_id");
            }
           // Close up connections
           results.close();
           statement.close();
           connection.close(); 
       }
       catch (SQLException e){
           // We only want to catch a SQLException - anything else is off-limits for now.
           e.printStackTrace();
       }  
    }

    // Creating Getters and Setters   
    public int getProjectNumber(){
        return projectNumber;
    }
    public String getprojectName() {
        return projectName;
    }
    public int getContractorId(){
        return contractor_id;
    }
    public int getArchitectId(){
        return architect_id;
    }
    public int getCustomerId(){
        return customer_id;
    }
    public Double getprojectFee(){
        return projectFee;
    }
    public Double getamountPaid(){
        return amountPaid;
    }
    public void setamountPaid(Double newAmountPaid){
        amountPaid = newAmountPaid;
    }
    public String getdeadline(){
        return deadline;
    }
    public void setdeadline(String newDeadline){
        deadline = newDeadline;
    }
    public Boolean getfinalised(){
        return finalised;
    }
    public void setfinalised(Boolean isFinished){
        finalised = isFinished;
    }
    public Contractor getContractorDetails(){
        return contractorDetails;
    }
    public Customer getCustomerDetails(){
        return customerDetails;
    }
    public Architect getArchitectDetails(){
        return architectDetails;
    }

    private void populateDetails(Scanner input){  

        System.out.println("\nEnter the project name or 'none' if there is no name: ");
        this.projectName = input.nextLine(); 
        
        System.out.println("\nEnter the building type: ");
        this.buildingType = input.nextLine();
        
        System.out.println("\nEnter the address: ");
        this.address = input.nextLine();
        
        System.out.println("\nEnter the ERF number: ");
        this.erfNumber = input.nextLine();

        this.projectFee = getProjectFee(input);
        
        this.amountPaid = getAmountPaid(input);

        Date isDeadline = isDateFormat("due date", input);
        this.deadline = getDeadlineAsString(isDeadline);
        
        this.finalised = false;     
        
        if (this.projectName.equalsIgnoreCase("none")){   
            this.projectName = this.buildingType + " " + this.customerDetails.lastName;
        } 
    }

    public Date isDateFormat(String dueDate, Scanner input){
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        
        // Using a loop to ask the user to enter in a date until a valid date has been entered
        while (true){
            System.out.println("\nEnter the " + dueDate + " (yyyy-MM-dd): ");
            String deadlineEntered = input.nextLine();

            // Using a try catch to see if a valid date has been entered - the string is parsed to a Date.
            try{
                Date deadline = dateFormat.parse(deadlineEntered);
                return deadline;
            }
            // Displaying an appropriate message if an incorrect date has been entered
            catch(Exception e){
                System.out.println("Incorrect date format.");
                continue;
            }
        } 
    }

    public String getDeadlineAsString(Date enteredDeadline){
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String formattedDeadline = dateFormat.format(enteredDeadline);
        return formattedDeadline;

    }

    public Double getAmountPaid(Scanner input){
        while (true){
            System.out.println("\nEnter the amount paid so far: ");
            String stringPaid = input.nextLine();

            try{
                Double  amountPaid = Double.parseDouble(stringPaid);
                return amountPaid;
            }
            catch(Exception e){
                System.out.println("Enter a number.");
                continue;
            }
        }
    }

    public Double getProjectFee(Scanner input){

        while (true){
            System.out.println("\nEnter the project fee: ");
            String stringPaid = input.nextLine();

            try{
                Double  amountPaid = Double.parseDouble(stringPaid);
                return amountPaid;
            }
            catch(Exception e){
                System.out.println("Enter a number.");
                continue;
            }
        }
    }

    public void getIdToWriteProjectToDatabase(int architect_id, int contractor_id, int  customer_id){
        try{
            // Connect to the library_db database, via the jdbc:mysql: channel on localhost (this PC) - username "otheruser", password "swordfish".
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedPMS?useSSL=true", "otheruser", "swordfish");
            
            // Create a direct line to the database for running queries
            Statement statement = connection.createStatement();
            int rowsAffected;

            // Adding in a new project - calling in the different attributes values       
            String sqlQuery2 = "INSERT INTO project_details VALUES (" + projectNumber + ", '" + projectName + "', '" + buildingType + "', '" + address + "', '"  + erfNumber 
            + "', " + projectFee + ", "  +  amountPaid + ", '" + deadline + "', "  + finalised + ", " + architect_id  + ", " + contractor_id  + ", " + customer_id +")";
            
            rowsAffected = statement.executeUpdate(sqlQuery2);
            System.out.println("Query complete, " + rowsAffected + " rows added"); 
            
            // Close up connections
            statement.close();
            connection.close();     
        }
        catch (SQLException e){
            e.printStackTrace(); 
        }
    }

    public void updateProjectOnDatabase(){
        try{
            // Connect to the library_db database, via the jdbc:mysql: channel on localhost (this PC) - username "otheruser", password "swordfish".
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedPMS?useSSL=true", "otheruser", "swordfish");
            // Create a direct line to the database for running queries
            Statement statement = connection.createStatement();
            int rowsAffected;
  
            // Updating the project in the different attributes values          
            String sqlQuery2 = "UPDATE  project_details SET deadline='" + deadline + "', amount_paid="  + amountPaid + ", finalised=" + finalised + " WHERE project_num=" + projectNumber;

            rowsAffected = statement.executeUpdate(sqlQuery2);
            System.out.println("Query complete, " + rowsAffected + " rows added"); 
            
            // Close up connections
            statement.close();
            connection.close();     
        }
        catch (SQLException e){
            e.printStackTrace(); // Only want to catch SQLException - anything else is off-limits for now.
        }
    }

    public int creatingNewProjectNumber(){
        try{
            // Connect to the library_db database, via the jdbc:mysql: channel on localhost (this PC) - username "otheruser", password "swordfish".
            // Create a direct line to the database for running our queries            
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedPMS?useSSL=true", "otheruser", "swordfish");  
            Statement statement = connection.createStatement();
            ResultSet results;

            // Getting the max number from the databas - Using the type variable to search the corresponding table
            String sqlQuery = "SELECT MAX(project_num) as project_num FROM project_details";
            results = statement.executeQuery(sqlQuery);

            // Incrementing the number by 1 and returning the value (this will be used as the new id for the next entry)
            int newProjectNum = 1;
            while(results.next()){
                newProjectNum = results.getInt("project_num") + 1;
            }
            // Close up connections
            results.close();
            statement.close();
            connection.close();     

            // Returning the newId
            return newProjectNum;
        }
        catch (SQLException e){
            e.printStackTrace(); // We only want to catch a SQLException - anything else is off-limits for now.
            return 1;   // Returning 1 - this would mean that there aren't any enteries so far.
            
        }

    }
}
