package inputExceptionsMongo;

public class AlreadyHasHeaderException extends Exception{

    public AlreadyHasHeaderException(){
        super("Header with such name is already exists");
    }
}
