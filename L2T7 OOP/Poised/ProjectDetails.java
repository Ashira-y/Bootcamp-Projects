import java.util.Scanner;

public class ProjectDetails {

    // Attributes: 
    String projectNumber;
    String projectName;
    String buildingType;
    String address;
    String erfNumber;
    Double projectFee;
    Double amountPaid;
    String deadline; 
    Boolean finalised;
    Architect architectDetails;
    Contractor contractorDetails;
    Customer customerDetails;   

    // Constructor: 
    // I created two constructors. One which can be set by the user.
    // The other constructor calls the populate() method to fill in the different attributes (except for the architect, customer and contractor).
    public ProjectDetails (String projectNumber, String projectName, String buildingType, String address,  String erfNumber, Double projectFee, Double amountPaid, String deadline, Boolean finalised, Architect architectDetails, Contractor contractorDetails,  Customer customerDetails ){
        this.architectDetails = architectDetails;
        this.contractorDetails = contractorDetails;
        this.customerDetails = customerDetails;
        this.projectNumber = projectNumber;
        this.projectName = projectName;
        
        // If else statement to create a default project name if the name doesn't exist
        if (this.projectName.equals("none")){
            this.projectName = this.buildingType + " " + this.customerDetails.lastName;
         } 

        this.buildingType = buildingType;
        this.address = address;
        this.erfNumber = erfNumber;
        this.projectFee = projectFee;
        this.amountPaid = amountPaid;
        this.deadline = deadline;
        this.finalised = finalised;
       
    }

    public ProjectDetails(Architect architectDetails, Contractor contractorDetails,  Customer customerDetails){
        this.architectDetails = architectDetails;
        this.contractorDetails = contractorDetails;
        this.customerDetails = customerDetails;
        this.populateDetails();
    }

    public ProjectDetails(){
        this.populateDetails();
    }

    // Creating Getters and Setters
    public String getprojectName() {
        return projectName;
    }
    public void setprojectName(String newName) {
        projectName = newName;
    }
    public Double getprojectFee(){
        return projectFee;
    }
    public void setprojectFee(Double newFee){
        projectFee = newFee;
    }
    public Double getamountPaid(){
        return amountPaid;
    }
    public void setamountPaid(Double paid){
        amountPaid = paid;
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

    public void setfinalised(Boolean finished){
        finalised = finished;
    }

    // Creating a method to populate the different attributes in the constructor.
    public void populateDetails(){
        Scanner inputD = new Scanner(System.in);
        
        System.out.println("\nEnter the project number: ");
        this.projectNumber = inputD.nextLine();
        System.out.println("\nEnter the project name or 'none' if there is no name: ");
        this.projectName = inputD.nextLine();
        System.out.println("\nEnter the building type: ");
        this.buildingType = inputD.nextLine();
        System.out.println("\nEnter the address: ");
        this.address = inputD.nextLine();
        System.out.println("\nEnter the ERF number: ");
        this.erfNumber = inputD.nextLine();
        System.out.println("\nEnter the project fee: ");
        this.projectFee = inputD.nextDouble();
        System.out.println("\nEnter the amount paid so far: ");
        this.amountPaid = inputD.nextDouble();
        System.out.println("\nEnter the due-date: ");
        this.deadline = inputD.next();
        this.finalised = false;

        System.out.println(this.projectName);
        // If else satement to set default project name if it doesn't exist.
        if (this.projectName.equals("none")){
            this.projectName = this.buildingType + " " + this.customerDetails.lastName;
         } 

        // If I close the scanner the program breaks. Not too sure why. Please can you tell me why it does this.
        //inputD.close();
    }

    // Creating a toString method
    public String toString(){

        String createdString = "\nPrject Number: " + projectNumber + "\nProject Name: " + projectName + "\nBuilding Type: " + buildingType + "\nAddress: " + address + "\nERF Number: " + erfNumber + "\nProject Fee: " + projectFee +  "\nAmount Paid: " + amountPaid + "\nDeadline: " + deadline  + "\nFinalised:  " + finalised + "\n" + architectDetails + "\n" + contractorDetails + "\n" + customerDetails;

        return createdString;
    }

}
