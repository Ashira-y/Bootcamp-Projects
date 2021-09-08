import java.util.Scanner;

public class Person {
    // Using inheritance to create a base class (Person Class) and a derived/sub-class' (Contractor, Customer and Architect Class) to inherit from the base class.
    // I read up about inheritance from: https://www.mygreatlearning.com/blog/inheritance-in-java/

    // Attributes:
    String type;
    String firstName;
    String lastName;
    String phoneNum;
    String email;
    String address;

    // Constructor
    public Person(String type){
        this.type = type;

        // Calling the populate method 
        this.populate();
    }
    
    public Person (String type, String firstName, String lastName, String phoneNum, String email, String address){
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.email = email;
        this.address = address;
    }

    // Creating getters and setter to update the contractors contact details
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
    public void populate(){
        Scanner input = new Scanner(System.in);

        System.out.println("\n" + type + " details ");
        System.out.println("\nEnter the " + type + "'s first name:  ");
        this.firstName = input.next();
        System.out.println("\nEnter the " + type + "'s last name:  ");
        this.lastName = input.next();
        System.out.println("\nEnter the " + type + "'s phone number: ");
        this.phoneNum = input.next();
        System.out.println("\nEnter the "+ type + "'s email address: ");
        this.email = input.next();
        System.out.println("\nEnter the " + type + "'s address: ");
        this.address = input.next();

        // If I close the scanner the program breaks. Not too sure why. Please can you tell me why it does this.
        //input.close();
        }

    // toString method
    public String toString(){
        String createdString = "\n" + type + " details:" + "\nName: " + firstName + " " + lastName + "\nPhone Number: " + phoneNum + "\nEmail Adress: " + email + "\nAddress: " + address;
        return createdString;
    }
    
}
