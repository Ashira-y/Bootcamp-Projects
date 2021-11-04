import java.util.Scanner;
import java.util.Date;
import java.sql.*;

enum ProjectResultType{
    NoResults,
    ProjectNum,
    ProjectName    
 }
 
 enum PersonType{
     Contractor,
     Architect,
     Customer
 }

public class Poised {    

    // This method marks the project as complete
    private static void markingProjectFinalised(ProjectDetails project){
        System.out.println("\nProject has been marked as completed");
        project.setfinalised(true);  
        project.updateProjectOnDatabase();       
    }
   
    // This method generates an invoice if the amount hasn't been settled
    private static void genertatingInvoice(ProjectDetails project, Customer customer, Scanner input, Statement statement){ 
        if (project.finalised.equals(true)){
            if (project.amountPaid.equals(project.projectFee)){
                System.out.println("\nProject fee has been settled");
            }
            else{
                System.out.println("\nEnter the completion date of the project:");

                Date completionDate = project.isDateFormat("completion date", input);
                String dealineC = project.getDeadlineAsString(completionDate);
                Double outstandingAmount = project.projectFee - project.amountPaid;

                Invoice newInvoice = new Invoice(customer.getFullName(), dealineC, outstandingAmount);
                newInvoice.writeInvoiceToDatabase(statement);
            }
        }
    }
   
    // This method displays a menu for updating the project details
    private static void updatingDetailsMenu(ProjectDetails currentProject, Scanner input, ResultSet results, Statement statement){
        
        while (true){    
            System.out.println("\nUpdating details: \n1. Deadline \n2. Amount Paid \n3. Contractors Details \n4. Architects Details \n5. Customer Details \n6. Marking the project complete \nPress 'E' to exit.");
            String option = input.nextLine();

            // Updating the deadline of the project.
            if (option.equals("1")){               
                Date newDealine = currentProject.isDateFormat("new deadline", input);

                currentProject.setdeadline(currentProject.getDeadlineAsString(newDealine));
                currentProject.updateProjectOnDatabase();
            }

            // Updating the total amount 
            else if (option.equals("2")){
                currentProject.setamountPaid(currentProject.getAmountPaid(input)); 
                currentProject.updateProjectOnDatabase();             
            }

            // Updating the contracotrs details.
            else if (option.equals("3")){
                System.out.println("\nUpdating the contractors details: ");
                
                Contractor contractorToUpdate = new Contractor(currentProject.getContractorId(), results, statement);
                updatingPersonDetails(contractorToUpdate, input, statement);
                contractorToUpdate.updatingDetailsToDatabase(statement);
            }

            // Updating the architectects details.
            else if (option.equals("4")){
                System.out.println("\nUpdating the Architects details: ");

                Architect architectToUpdate = new Architect(currentProject.getArchitectId(), results, statement);
                updatingPersonDetails(architectToUpdate, input, statement);
                architectToUpdate.updatingDetailsToDatabase(statement);
            }

            // Updating the customers details.
            else if (option.equals("5")){
                System.out.println("\nUpdating the Customers details: ");

                Customer customerToUpdate = new Customer(currentProject.getCustomerId(), results, statement);
                updatingPersonDetails(customerToUpdate, input, statement);
                customerToUpdate.updatingDetailsToDatabase(statement);
            }

            // Marking the project as finalised and breaking out of the loop.
            else if (option.equals("6")){
                if (currentProject.finalised.equals(false)){
                    markingProjectFinalised(currentProject);
                    Customer customerDetails = new Customer(currentProject.getCustomerId(), results, statement);
                    genertatingInvoice(currentProject, customerDetails, input, statement);
                    break;
                }
                // Project has already been marked as finalised
                else{
                    System.out.println("\nProject is already finalised\n");
                    break;
                }
            }    

            // Breaking out of the loop
            else if (option.toLowerCase().equals("e")){
                break;  
            }

            // Invalid option
            else{
                System.out.println("\nPlease select a valid option!");
            }
        }
    }
    
    // This method displays a menu for updating the persons details
    private static void updatingPersonDetails (Person personDetails, Scanner input, Statement statement){
         
        while (true){
            System.out.println("\n1. Email Address \n2. Phone Number \n3.Address \nPress 'm' to exit to previous menu");
            String detailsToUpdate = input.nextLine();
            
            // Updating the email address
            if (detailsToUpdate.equals("1")){
                System.out.println("\nEnter the updated email address:");
                String updatedEmail = input.nextLine();                
                personDetails.setemail(updatedEmail);        
                personDetails.updatingDetailsToDatabase(statement); 
                continue;
            }

            // Updating the phone number
            else if (detailsToUpdate.equals("2")){
                System.out.println("\nEnter the updated phone number:");
                String updatedNumber = input.nextLine();
                personDetails.setphoneNum(updatedNumber);    
                personDetails.updatingDetailsToDatabase(statement);  
                continue;
            }

            // Updating the address
            else if (detailsToUpdate.equals("3")){
                System.out.println("\nEnter the updated address:");
                String updatedAddress = input.nextLine();                
                personDetails.setaddress(updatedAddress);    
                personDetails.updatingDetailsToDatabase(statement);   
                continue;
            }

            // Breaking out of the loop if 'm' has been enterd
            else if (detailsToUpdate.toLowerCase().equals("m")){
                break;
            }

            // Displaying an error message if the user has not entered a valid option.
            else{
            System.out.println("\nPlease select a valid option!");
            } 
        }
    }

    // This method prints all projects
    public static void printAllFromProjectDetails(Statement statement) throws SQLException{
        ResultSet results = statement.executeQuery("SELECT * FROM project_details");
        System.out.println();
        whileLoopToDisplayProjects(results);    
    }  

    // This method prints all people
    public static void printAllFromPersonTable(Statement statement, String type) throws SQLException{
        ResultSet results = statement.executeQuery("SELECT * FROM " + type) ;
        System.out.println();

        while (results.next()) {
            System.out.println(type + " id: " + results.getInt("id") + "\nName: " + results.getString("first_name") + " " + results.getString("last_name") + "\nPhone number: " 
                                + results.getString("phone_num") + "\nEmail: " + results.getString("email") + "\nAddress: " + results.getString("address") + "\n");
        }
        System.out.println();
    } 

    // This method displays all invoices
    public static void displayAllInvoices(Statement statement) throws SQLException{
        ResultSet results = statement.executeQuery("SELECT * FROM invoice" ) ;
        System.out.println();

        while (results.next()) {
            System.out.println("INVOICE: " + results.getInt("id") + "\nCustomer name: " + results.getString("customer_name") + "\nCompletion Date: " 
                                    + results.getString("completion_date") + "\nOutstanding Amount: " + results.getDouble("outstanding_amount") + "\n");
        }
        System.out.println();
    } 
 
    // This method displays all the projects
    public static void whileLoopToDisplayProjects(ResultSet results) throws SQLException{
        System.out.println();
        while (results.next()) {
            System.out.println("Project number: " + results.getString("project_num") + "\nProject name: " + results.getString("project_name") + "\nBuilding type: " 
                                + results.getString("building_type") + "\nAddress: " + results.getString("address") + "\nErf number: " + results.getString("erf_number") 
                                + "\nProject fee: " + results.getDouble("project_fee") + "\nAmount paid: " + results.getDouble("amount_paid") + "\nDeadline: " 
                                + results.getDate("deadline") + "\nFinalised: " + results.getBoolean("finalised") + "\nArchitect id: " + results.getInt("architect_id") 
                                + "\nContractor id: " + results.getInt("contractor_id") + "\nCustomer id: " + results.getInt("customer_id") + "\n");
        }
        System.out.println();
    }

    // This method checks if a number has been entered
    private static Boolean isNumber(String toCheck){
        try{
            Integer.parseInt(toCheck);
            return true;      
        }
        catch (Exception e){
            return false;
        }       
    }

    // This method determins if a valid project number or name has been entered
    private static ProjectResultType isValidProject(ResultSet results,  Statement statement, String prpjectToCheck){
        if (isNumber(prpjectToCheck) == true){
            try{
                results = statement.executeQuery("SELECT * FROM project_details where project_num=" + prpjectToCheck);
                if(results.next()) {
                    return ProjectResultType.ProjectNum;        
                }
                else{
                    return ProjectResultType.NoResults;         
                }    
            }
            catch (Exception e){
                System.out.println("Error!");
                return ProjectResultType.NoResults;           
            }
        }
        else{
            try{
                results = statement.executeQuery("SELECT * FROM project_details where project_name='" + prpjectToCheck+"'");
                if(results.next()) {
                    return ProjectResultType.ProjectName;   
                }
                else{
                    return ProjectResultType.NoResults;    
                }    
            }
            catch (Exception e){
                System.out.println("Error!");
                return ProjectResultType.NoResults;     
            }
        }
    }

    // This method determins whether a valid person ID has been entered
    public static Boolean isValidPersonId(ResultSet results,  Statement statement, String idToCheck, PersonType personType){
        if (isNumber(idToCheck) == true){
            try{
                results = statement.executeQuery("SELECT * FROM " + personType + " where id=" + idToCheck);
                if(results.next()) {
                    return true;        
                }
                else{
                    return false;       
                }    
            }
            catch (Exception e){
                System.out.println("Error!");
                return false;
            }
        }
        else{
            return false;          
        }
    }

    // This method asks the user which project they want to update
    private static void updatingProject(Scanner input, Statement statement, ResultSet results) {
        
        while (true){
            System.out.println("\nSelect a project name or number to update/finalize or 'r' to return to the menu: ");
            String toUpdate = input.nextLine();
            
            // If 'r' is selected - break out of the loop
            if (toUpdate.equalsIgnoreCase("r")){
                break;
            }

            // Check if the project name was entered and create a new ProjectDetails object
            else if (isValidProject(results, statement,  toUpdate) == ProjectResultType.ProjectName){
                ProjectDetails projectToUpdate = new ProjectDetails(toUpdate);   
                updatingDetailsMenu(projectToUpdate, input, results, statement);   
                break;                      
            }   

            // Check if the project number was entered and create a new ProjectDetails object
            else if (isValidProject(results, statement,  toUpdate) == ProjectResultType.ProjectNum){
                ProjectDetails projectToUpdate = new ProjectDetails(toUpdate);
                updatingDetailsMenu(projectToUpdate, input, results, statement); 
                break;
            }   

            // Invlid entry
            else{
                System.out.println("\nEnter a valid Project number or name!");
            }    
        }
    }

    // This methods adds and creates a new project to the database
    private static void addNewProject(ResultSet results, Statement statement, Scanner input) {
        System.out.println("\nPlease enter the following details to create a new project: \n");

        Architect newArchitect = (Architect)createNewPersonOrUseExisting(results, statement, input, PersonType.Architect);
        Contractor newContractor = (Contractor)createNewPersonOrUseExisting(results, statement, input, PersonType.Contractor);
        Customer newCustomer = (Customer)createNewPersonOrUseExisting(results, statement, input, PersonType.Customer);
        
        ProjectDetails newProject = new ProjectDetails(newArchitect, newContractor, newCustomer, input);
        
        newProject.getIdToWriteProjectToDatabase(newArchitect.getId(), newContractor.getId(), newCustomer.getId());
    }

    // This method asks the user if they want to use an existing person object or create new one
    private static Person createNewPersonOrUseExisting(ResultSet results, Statement statement, Scanner input, PersonType personType) {
        while(true){
            System.out.println("Would you like to use an existing " + personType + "? (yes or no)");
            String contractorOption = input.nextLine();
            if (contractorOption.equalsIgnoreCase("yes")){
                while (true){
                    System.out.println("Enter the id of the " + personType + "  you would like to use");
                    String idToCheck = input.nextLine();
                    if (isValidPersonId(results, statement, idToCheck, personType)){
                        int idPerson = Integer.parseInt(idToCheck);
                        if(personType == PersonType.Contractor){
                            return new Contractor(idPerson, results, statement);
                        } 
                        else if(personType == PersonType.Architect){
                            return new Architect(idPerson, results, statement);
                        } 
                        else {
                            return new Customer(idPerson, results, statement);
                        }
                    }
                    else{
                        continue;
                    }
                }
            }
            else if(contractorOption.equalsIgnoreCase("no")){                
                if(personType == PersonType.Contractor){
                    Contractor newContractor = new Contractor(input, results, statement);
                    newContractor.writePersonToDatabase(statement);
                    return newContractor;
                } 
                else if(personType == PersonType.Architect){
                    Architect newArchitect = new Architect(input, results, statement);
                    newArchitect.writePersonToDatabase(statement);
                    return newArchitect;
                } 
                else {
                    Customer newCustomer = new Customer(input, results, statement);
                    newCustomer.writePersonToDatabase(statement);
                    return newCustomer;
                }               
            }
        }
    }

    // Main method - The user is present a menu where thay can choose various options
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);

        try{
            // Connect to the library_db database, via the jdbc:mysql: channel on localhost (this PC) - username "otheruser", password "swordfish".
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedpms?useSSL=true", "otheruser", "swordfish");
            Statement statement = connection.createStatement();
            ResultSet results;
            results = statement.executeQuery("SELECT * FROM project_details");
                        
            // Displaying a menu to the user
            while(true){
                System.out.println("Menu: \n1. Display all projects \n2. Add new project \n3. Update or finalise a project \n4. Display a list of incomplete projects \n5. Display a list of project past their due dates \n6. Display all contractors \n7. Display all architects \n8. Display all customers \n9. Display all invoices\nQ to exit");
                String menuOption = input.nextLine();
                
                // Display all projects - calling  printAllFromProjectDetails() to display all project
                if (menuOption.equals("1")){
                    printAllFromProjectDetails(statement);                    
                }  

                // Adding a new project - calling addNewProject() method
                else if (menuOption.equals("2")){
                    addNewProject(results, statement, input);
                }

                // Updating/Finalising - calling updatingProject() method
                else if (menuOption.equals("3")){
                    updatingProject(input, statement, results);
                }

                // Display list of incomplete projects
                else if (menuOption.equals("4")){
                    results = statement.executeQuery("SELECT * FROM project_details WHERE finalised=false");
                    whileLoopToDisplayProjects(results);
                }

                // Display list of incomplete and overdue projects
                else if (menuOption.equals("5")){
                    results = statement.executeQuery("SELECT * FROM project_details WHERE finalised=false AND deadline < NOW()");
                    whileLoopToDisplayProjects(results);
                }

                // Displaying a list of contractors - calling printAllFromPersonTable() method
                else if (menuOption.equals("6")){
                    System.out.println("\nContractors: ");
                    printAllFromPersonTable(statement, "contractor");
                }

                // Displaying a list of architects - calling printAllFromPersonTable() method
                else if (menuOption.equals("7")){
                    System.out.println("\nArchitects: ");
                    printAllFromPersonTable(statement, "architect");
                }

                // Displaying a list of customers - calling printAllFromPersonTable() method
                else if (menuOption.equals("8")){
                    System.out.println("\nCustomers: ");
                    printAllFromPersonTable(statement, "customer");
                }

                // Displaying all invoices - calling displayAllInvoices() method
                else if (menuOption.equals("9")){
                    displayAllInvoices(statement);
                }

                // Quit the program 
                else if (menuOption.equalsIgnoreCase("Q")){
                    break;
                }

                // Invalid option.
                else{
                    System.out.println("\nInvalid option!\n");
                }     
            }

            results.close();
            statement.close();
            connection.close(); 
            input.close();
        }
        
        catch (SQLException e){           
            e.printStackTrace(); 
        }  
    }

}

