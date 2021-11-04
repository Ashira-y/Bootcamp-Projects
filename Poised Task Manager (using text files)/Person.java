import java.util.Scanner;

public class Person {

    // Attributes:
    String type;
    String firstName;
    String lastName;
    String phoneNum;
    String email;
    String address;

    // Constructors: 
    public Person(String type, Scanner input){
        this.type = type;
        this.populate(input);
    }
    
    public Person (String type, String firstName, String lastName, String phoneNum, String email, String address){
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.email = email;
        this.address = address;
    }

    // Creating getters and setter to update the contact details
    public String getemail(){
        return email;
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

    // Creating a Populate method to fill in the different attributes
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

    // This method formats the different elements in the constructor into a string which can be easily read once printed.
    public String toString(){
        String createdString = type + " Name: "  + firstName + " " + lastName + "\n" + type + " Phone Number: " + phoneNum + "\n" + type + " Email Adress: " + email + "\n" + type + " Address: " + address;
        return createdString;
    }

    // This method formats the different elements in the constructor into a string which can be easily written to a file
    public String toFile(){
        String createdString = firstName + " " + lastName + "," + phoneNum + "," + email + "," + address;
        return createdString;
    }
}
