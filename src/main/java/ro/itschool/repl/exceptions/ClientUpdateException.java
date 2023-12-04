package ro.itschool.repl.exceptions;

public class ClientUpdateException extends RuntimeException{

    public ClientUpdateException(String message, Throwable cause){
        super(message, cause);
    }
}
