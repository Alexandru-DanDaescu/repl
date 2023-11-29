package ro.itschool.repl.exceptions;

public class PropertyUpdateException extends RuntimeException{
    public PropertyUpdateException(String message, Throwable cause){
        super(message, cause);
    }
}
