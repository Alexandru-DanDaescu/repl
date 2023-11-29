package ro.itschool.repl.exceptions;

public class PropertyNotFoundException extends RuntimeException{

    public PropertyNotFoundException(String message){
        super(message);
    }
}
