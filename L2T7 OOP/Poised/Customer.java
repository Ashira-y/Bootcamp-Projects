public class Customer extends Person {

    public Customer(){
        super("Customer");
    }

    public Customer (String type, String firstName, String lastName, String phoneNum, String email, String address){
        super("Customer", firstName, lastName , phoneNum, email, address);
    }
}
