public class Contractor extends Person {

    public Contractor(){
        super("Contractor");
    }

    public Contractor(String type, String firstName, String lastName, String phoneNum, String email, String address){
        super("Contractor", firstName, lastName , phoneNum, email, address);
    }
}
