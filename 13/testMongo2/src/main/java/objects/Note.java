package objects;

public class Note {

    private String header;
    private String Inf;

    public Note(String header, String inf) {
        this.header = header;
        Inf = inf;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getInf() {
        return Inf;
    }

    public void setInf(String inf) {
        Inf = inf;
    }


}
