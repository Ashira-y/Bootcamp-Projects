public class Architect extends Person {

    public Architect(){
        super("Architect");
    }

    public Architect (String type, String firstName, String lastName, String phoneNum, String email, String address){
        // Calling the super() method to invoke the base class' constructor
        super("Architect", firstName, lastName , phoneNum, email, address);
    }   
}
