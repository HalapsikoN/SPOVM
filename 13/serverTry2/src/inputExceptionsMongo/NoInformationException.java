package inputExceptionsMongo;

public class NoInformationException extends Exception{

    public NoInformationException(){
        super("Please enter some text to the Information field");
    }
}
