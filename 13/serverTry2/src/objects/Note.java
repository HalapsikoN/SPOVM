package objects;

public class Note {

    public final static String HEADER="header";
    public final static String INF="inf";
    private String header;
    private String inf;

    public Note(String header, String inf) {
        this.header = header;
        this.inf = inf;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getInf() {
        return inf;
    }

    public void setInf(String inf) {
        this.inf = inf;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
