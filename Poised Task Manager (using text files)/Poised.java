import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Poised {
  
    // This method sets the project as complete and displays a message stating that the project has been marked complete.
    private static void markingProjectFinalised(ProjectDetails project){
        System.out.println("\nProject has been marked as completed");
        project.setfinalised(true);  
    }
   
    // The method then generates an invoice if the project fee has not been settled.
    private static void genertatingInvoice(ProjectDetails project, Customer customer, Scanner input){

        System.out.println("\nEnter the completion date of the project:");
        Date completionDate = project.isDateFormat(input);

        if (project.finalised.equals(true)){
            if (project.amountPaid.equals(project.projectFee)){
                System.out.println("\nProject fee has been settled");
            }
            else{
                System.out.println("\n\t\tINVOICE:");
                System.out.print("\n" + customer.firstName + " " + customer.lastName);
                System.out.print("\t\tProject Completed: " + completionDate);
                Double outstandingAmount = project.projectFee - project.amountPaid;
                System.out.println("\nAmount due: \t\t" + "R" + project.projectFee + " - " + "R" + project.amountPaid  + " = R" + outstandingAmount);

                try{
                    Formatter invoiceFile = new Formatter("invoice.txt");
                    invoiceFile.format("\n\t\tINVOICE:");
                    invoiceFile.format("\n %s %s", customer.firstName,  customer.lastName);
                    invoiceFile.format("\t\tProject Completed:  %s", completionDate);
                    invoiceFile.format("\nAmount due: \t\t R %s - R %s = R %s",  project.projectFee, project.projectFee, outstandingAmount);
                    invoiceFile.close();
                }
                catch (Exception e){
                    System.out.println("Error");
                }
            }
        }
    }
   
    // This method displays a menu for updating more specific details relating to the project
    private static void updatingDetailsMenu(ProjectDetails currentProject, Contractor contractorDetails, Architect architectDetails, Customer customerDetails, Scanner input){
        String option = "";

        while (!option.toLowerCase().equals("e")){    
            System.out.println("\nUpdating details: \n1. Deadline \n2. Amount Paid \n3. Contractors Details \n4. Architects Details \n5. Customer Details \n6. Marking the project complete \nPress 'E' to exit.");
            option = input.nextLine();

            if (option.equals("1")){
                currentProject.setdeadline(currentProject.isDateFormat(input)); 
            }
            else if (option.equals("2")){
                currentProject.getAmountPaid(input);
            }
            else if (option.equals("3")){
                System.out.println("\nUpdating the contractors details: ");
                updatingPersonDetails (contractorDetails, input);
            }
            else if (option.equals("4")){
                System.out.println("\nUpdating the Architects details: ");
                updatingPersonDetails (architectDetails, input);
            }
            else if (option.equals("5")){
                System.out.println("\nUpdating the Customers details: ");
                updatingPersonDetails (customerDetails, input);
            }
            else if (option.equals("6")){
                markingProjectFinalised(currentProject);
                break;
            }
            else if (option.toLowerCase().equals("e")){
                break;
            }
            else{
                System.out.println("\nPlease select a valid option!");
            }
        }
    }
    
    // This method displays a menu for updating the specific chosen person's details
    private static void updatingPersonDetails (Person personDetails, Scanner input){
        
        while (true){
            System.out.println("\n1. Email Address \n2. Phone Number \n3.Address \nPress 'm' to exit to previous menu");
            String detailsToUpdate = input.nextLine();
            
            if (detailsToUpdate.equals("1")){
                System.out.println("\nEnter the updated email address:");
                String updatedEmail = input.nextLine();
                personDetails.setemail(updatedEmail);
                continue;
            }
            else if (detailsToUpdate.equals("2")){
                System.out.println("\nEnter the updated phone number:");
                String updatedNumber = input.nextLine();
                personDetails.setphoneNum(updatedNumber);
                continue;
            }
            else if (detailsToUpdate.equals("3")){
                System.out.println("\nEnter the updated address:");
                String updatedAddress = input.nextLine();
                personDetails.setaddress(updatedAddress);
                continue;
            }
            else if (detailsToUpdate.toLowerCase().equals("m")){
                break;
            }
            else{
            System.out.println("\nPlease select a valid option!");
            } 
        }
    }
   
    private static ArrayList<String> loadProjectsFromFile(String fileToRead){

        ArrayList<String>listOfProjects = new ArrayList<String>();

        try{
            Scanner fileReader = new Scanner(new File(fileToRead));

            while (fileReader.hasNextLine()){
                String line = fileReader.nextLine();
                listOfProjects.add(line);
            }
        }
        catch (Exception e){
            System.out.println("Error: " + fileToRead + " does not exist.");
        }

        return listOfProjects;
      }   
    
    
    private static ProjectDetails createProjectDetails(String inputLine){

        inputLine.replace("\n", "");

        String[] splitInput = inputLine.split(","); 

        Double projectfee = toDouble(splitInput[5]);
        Double amountPaidSoFar = toDouble(splitInput[6]);
        Date dueDate = toDate(splitInput[7]);

        String[] architectsName = nameArry(splitInput[9]);
        String[] contractorsName = nameArry(splitInput[13]);
        String[] customerName = nameArry(splitInput[17]);

        Boolean isComplete;

        if (splitInput[8].equals("false")){
            isComplete = false;
        }
        else{
            isComplete = true;
        }

        // Creating new Customer, Contractor, Archited and ProjectDetails objects
        Customer customerDetails = new Customer("Customer", customerName[0], customerName[1], splitInput[18],  splitInput[19],  splitInput[20]);
        Contractor contractorDetails = new Contractor("Contractor", contractorsName[0], contractorsName[1],splitInput[14], splitInput[15], splitInput[16]);
        Architect architectDetails = new Architect("Architect", architectsName[0], architectsName[1], splitInput[10], splitInput[11], splitInput[12]);
        ProjectDetails currentProject = new ProjectDetails(splitInput[0], splitInput[1], splitInput[2], splitInput[3], splitInput[4], projectfee, amountPaidSoFar, dueDate , isComplete, architectDetails, contractorDetails, customerDetails);
        
        return currentProject;
    }
    
    private static Double toDouble(String numA){
        try{
            Double  amountPaid = Double.parseDouble(numA);
            return amountPaid;
        }
        catch(Exception e){
            return null;
        }
    }
   
    // Using a try catch - the method parses the string to a date.
    private static Date toDate(String dateA){
         DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
         try{
            Date deadline = dateFormat.parse(dateA);
            return deadline;
        }
        catch(Exception e){
            System.out.println("Incorrect date format.");
            return null;
        }
    }
 
    // This method takes in a string. The string is then split on a " " and the items are stored within an array
    private static String[] nameArry(String toPutInArray){
        String[] nameArray = toPutInArray.split(" ");
        return nameArray;
    }

    // This is the main method for the class where the code is executed in.
    // A menu is displayed - and the user can choose multiple different options.
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);

        ArrayList<String> projectsList = new ArrayList<String>(loadProjectsFromFile("Project_Details.txt"));

        ArrayList<ProjectDetails> listOfProjectDetails = new ArrayList<ProjectDetails>();
        for (int i = 0; i < projectsList.size(); i++){
            listOfProjectDetails.add(createProjectDetails(projectsList.get(i)));
        }

        // Displaying a menu to the user
        while(true){
            System.out.println("Menu: \n1. Display all projects. \n2. Add new project \n3. Update or finalise a project \n4. Display a list of incomplete projects \n5. Display a list of project past their due dates \nQ to exit");
            String menuOption = input.nextLine();
        
            // Display all projects.
            if (menuOption.equals("1")){
                for (int i = 0; i < projectsList.size(); i++){
                    System.out.println(createProjectDetails(projectsList.get(i)).toString());
                }
            }
        
            // Adding a new project
            else if (menuOption.equals("2")){
                System.out.println("\nPlease enter the following details to create a new project: ");
                
                Architect newArchitect = new Architect(input);
                Customer newCustomerA = new Customer(input);
                Contractor newContractor = new Contractor(input);
                ProjectDetails newProject = new ProjectDetails(newArchitect, newContractor, newCustomerA, input);
                
                listOfProjectDetails.add(newProject);
            }
        
            // Updating/Finalising a project
            else if (menuOption.equals("3")){
                while (true){
                    System.out.println("\nSelect a project name or number to update/finalize or 'r' to return to the menu: ");
                    String toUpdate = input.nextLine();

                    if (toUpdate.equalsIgnoreCase("r")){
                        break;
                    }

                    else{
                        for (ProjectDetails project : listOfProjectDetails ){
                            if (project.getProjectNumber().toString().equals(toUpdate) || project.getprojectName().equals(toUpdate)){
                                updatingDetailsMenu(project, project.getContractorDetails(), project.getArchitectDetails(), project.getCustomerDetails(), input);
                                if (project.getfinalised().equals(true)){
                                    genertatingInvoice(project, project.getCustomerDetails(), input); 
                                }
                            }
                        }
                    }        
                }
            }

            // Display list of incomplete projects
            else if (menuOption.equals("4")){
                for (ProjectDetails project : listOfProjectDetails ){
                    if (project.getfinalised().equals(false) ){
                        System.out.println(project);
                        }
                }
            }

             // Display list of incomplete and overdue projects
            else if (menuOption.equals("5")){
                Date todayA = new Date();  
                for (ProjectDetails project : listOfProjectDetails ){
                    if (project.deadline.compareTo(todayA) < 0 && project.getfinalised().equals(false) ){
                        System.out.println(project);
                    }
                }
            }

             // Quit the program and save
            else if (menuOption.equalsIgnoreCase("Q")){

                try{
                    Formatter f = new Formatter("Project_Details.txt");
                    for (ProjectDetails project : listOfProjectDetails ){
                        f.format(project.toFile());
            
                    }
                    f.close();
                }
                catch (Exception e){
                    System.out.println("Error");
                }

                break;
            }

            else{
                System.out.println("\nInvalid option!\n");
            }     
        }

        input.close();
    }
}
