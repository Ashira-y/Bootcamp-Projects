import java.util.Scanner;

public class Poised {
    public static void newDeadline(ProjectDetails project){
        Scanner input = new Scanner(System.in);

        System.out.println("\nWould you like to change the due-date of the project (yes or no)");
        String changeDueDate = input.nextLine();
        if (changeDueDate.toLowerCase().equals("yes")){
            System.out.println("\nEnter the new due-date:");
            String newDeadline = input.nextLine();
            project.setdeadline(newDeadline);
        }
    }
    public static void newAmountPaid(ProjectDetails project){
        Scanner input = new Scanner(System.in);

        System.out.println("\nWould you like to update the total amount of the fee paid (yes or no):");
        String updateAmountPaid = input.nextLine();
        if (updateAmountPaid.toLowerCase().equals("yes")){
            System.out.println("\nEnter the updated amount paid: ");
            Double newAmount = input.nextDouble();
            project.setamountPaid(newAmount);
        }
    }
    public static void updatingCotractorsDetails (ProjectDetails project){
        Scanner input = new Scanner(System.in);

        System.out.println("\nWould you like to update the contractor's details (yes or no):");
        String updateDetails = input.nextLine();
        if (updateDetails.toLowerCase().equals("yes")){
            project.contractorDetails.populate();
        }
    }
    public static void markingProjectFinalised(ProjectDetails project){
        Scanner input = new Scanner(System.in);

        System.out.println("\nWould you like to mark the project as complete (yes or no):");
        String finished = input.nextLine();
        if (finished.toLowerCase().equals("yes"));{
            project.setfinalised(true);
        }
    }
    public static void genertatingInvoice(ProjectDetails project, Customer customer){
        Scanner input = new Scanner(System.in);

        System.out.println("\nEnter the completion date of the project:");
        String completionDate = input.nextLine();

        if (project.finalised.equals(true)){
            if (project.amountPaid.equals(project.projectFee)){
                System.out.println("P\nroject fee has been settled");
            }
            else{
                System.out.println("\n\t\tINVOICE:");
                System.out.print("\n" + customer.firstName + " " + customer.lastName);
                System.out.print("\t\tProject Completed: " + completionDate);
                Double outstandingAmount = project.projectFee - project.amountPaid;
                System.out.println("\nAmount due: \t\t" + "R" + project.projectFee + " - " + "R" + project.amountPaid  + " = R" + outstandingAmount);
            }
        }

    }
    public static void main(String[] args) throws Exception {
        // If I close the scanner the program breaks. Not too sure why. Please can you tell me why it does this.
        Scanner input = new Scanner(System.in);
        
        // The attributes in the case will be created through user input.
        /*
        Customer customerDetails = new Customer();
        Contractor contractorDetails = new Contractor();
        Architect architectDetails = new Architect();
        */

        // The attributes can also be popultaed through entering in the detail when creating the object.
        Customer customerDetails = new Customer("Customer", "Ashleigh", "Green", "0794518765", "ashleigh@email.coom", "12 New Road, Cape Town");
        Contractor contractorDetails = new Contractor("Contractor", "Caleb", "Star", "0852918756", "caleb@email.com", "98 Caleb Road, Rosebank");
        Architect architectDetails = new Architect("Architect", "Cayley", "Blue", "0897653412", "cayley@email.com", "49 Cayley Road, Cape Town");
        ProjectDetails currentProject = new ProjectDetails(architectDetails, contractorDetails, customerDetails);

        // I've added in getters and setters in order to update various aspects of the project at different points.

        System.out.println(currentProject);

        // Changing the due date of the project
        newDeadline(currentProject);

        // Updating the total amount of the fee paid.
        newAmountPaid(currentProject);
    
         // Updating the contractors contact details.
         updatingCotractorsDetails (currentProject);

        // Marking the project as finalised
        markingProjectFinalised(currentProject);

        System.out.println("\nUpdated Details: ");

        System.out.println("\n" + currentProject);

        // If the project is finalised generate a report
        // If the project fee has not been settled an invoice should be generated
        genertatingInvoice(currentProject, customerDetails); 
    }
}
