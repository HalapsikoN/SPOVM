package inputExceptionsMongo;

public class NoSuchElementInDBException extends Exception{

    public NoSuchElementInDBException(){
        super("There is no such Header on server");
    }
}
