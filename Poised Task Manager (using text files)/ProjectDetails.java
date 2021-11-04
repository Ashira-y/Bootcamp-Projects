import java.util.*;
import java.text.SimpleDateFormat;

public class ProjectDetails {

    // Attributes: 
    String projectNumber;
    String projectName;
    String buildingType;
    String address;
    String erfNumber;
    Double projectFee;
    Double amountPaid;
    Date deadline; 
    Boolean finalised;
    Architect architectDetails;
    Contractor contractorDetails;
    Customer customerDetails;   

    public ProjectDetails (String projectNumber, String projectName, String buildingType, String address,  String erfNumber, Double projectFee, Double amountPaid, Date deadline, Boolean finalised, Architect architectDetails, Contractor contractorDetails,  Customer customerDetails ){
        this.architectDetails = architectDetails;
        this.contractorDetails = contractorDetails;
        this.customerDetails = customerDetails;
        this.projectNumber = projectNumber;
        this.projectName = projectName;
        this.buildingType = buildingType;
        this.address = address;
        this.erfNumber = erfNumber;
        this.projectFee = projectFee;
        this.amountPaid = amountPaid;
        this.deadline = deadline;
        this.finalised = finalised;

        if (this.projectName.equals("none")){
            this.projectName = this.buildingType + " " + this.customerDetails.lastName;
        } 
    }


    // This constructor calls the .populateDetails() method which gets user input to fill in the different details of the project.
    public ProjectDetails(Architect architectDetails, Contractor contractorDetails,  Customer customerDetails, Scanner input){
        this.architectDetails = architectDetails;
        this.contractorDetails = contractorDetails;
        this.customerDetails = customerDetails;
        this.populateDetails(input);
    }

 
    public String getProjectNumber(){
        return projectNumber;
    }
    public String getprojectName() {
        return projectName;
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
    public Date getdeadline(){
        return deadline;
    }
    public void setdeadline(Date newDeadline){
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

    // Creating a method to populate the different attributes in the constructor.
    private void populateDetails(Scanner input){
        
        System.out.println("\nEnter the project number: ");
        this.projectNumber = input.nextLine();

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
        
        this.deadline = isDateFormat(input);
        
        this.finalised = false;     
        
        if (this.projectName.equalsIgnoreCase("none")){   
            this.projectName = this.buildingType + " " + this.customerDetails.lastName;
        } 
    }

  
    // This method is used to check if the date format (for the deadline) is corect and if a valid date has been entered.
    public Date isDateFormat(Scanner input){

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        
        while (true){
            System.out.println("\nEnter the due-date (dd-mm-yyyy): ");
            String deadlineEntered = input.nextLine();

            try{
                Date deadline = dateFormat.parse(deadlineEntered);
                return deadline;
            }
            catch(Exception e){
                System.out.println("Incorrect date format.");
                continue;
            }
        } 
    }

    // This method is used to format the date format into a string.
    public String getDeadline(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDeadline = dateFormat.format(deadline);
        return formattedDeadline;
    }

  
    // This method is used to check if a valid amount has been entered
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

    public static Double getProjectFee(Scanner input){

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

    
    // This method formats the different elements in the constructor into a string which can be easily read once printed.
    public String toString(){
        String createdString = "\nPrject Number: " + projectNumber + "\nProject Name: " + projectName + "\nBuilding Type: " + buildingType + "\nAddress: " + address + "\nERF Number: " + erfNumber + "\nProject Fee: " + projectFee +  "\nAmount Paid: " + amountPaid + "\nDeadline: " + deadline  + "\nFinalised:  " + finalised + "\n" + architectDetails + "\n" + contractorDetails + "\n" + customerDetails + "\n";
        return createdString;
    }

    
    // This method formats the different elements in the constructor into a string which can be easily written to a file
    public String toFile(){
        String forFile = projectNumber + "," + projectName + "," + buildingType + "," + address + "," + erfNumber + "," + projectFee +  "," + amountPaid + "," + getDeadline()  + "," + finalised + "," + architectDetails.toFile() + "," + contractorDetails.toFile() + "," + customerDetails.toFile() + "\n";
        return forFile;
    }
}
