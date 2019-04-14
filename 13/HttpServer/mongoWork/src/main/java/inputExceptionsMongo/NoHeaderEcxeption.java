package inputExceptionsMongo;

public class NoHeaderEcxeption extends Exception{

    public NoHeaderEcxeption(){
        super("Please enter some text to the Header field");
    }
}
