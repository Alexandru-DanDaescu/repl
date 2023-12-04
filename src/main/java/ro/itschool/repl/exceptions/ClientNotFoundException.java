package ro.itschool.repl.exceptions;

public class ClientNotFoundException extends RuntimeException{

    public ClientNotFoundException(String message){
        super(message);
    }
}
