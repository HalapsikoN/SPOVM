package workWithObjects;

import com.google.gson.Gson;
import inputExceptionsMongo.AlreadyHasHeaderException;
import inputExceptionsMongo.NoHeaderEcxeption;
import inputExceptionsMongo.NoSuchElementInDBException;
import inputExceptionsMongo.ParameterException;
import mongo.MongoWorkNote;
import objects.Note;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.List;

public class NoteWork {

    private HttpServletResponse resp;

    public NoteWork(HttpServletResponse resp){
        this.resp=resp;
    }

    private String getFromBodyListByName(List<String> list, String name) throws ParameterException {

        if(!list.contains(name)){
            throw new ParameterException();
        }
        int index=0;
        index=list.indexOf(name);
        index++;
        String result=list.get(index);
        return result;
    }

    public void addNote(List<String> list) throws IOException, ParameterException, NoHeaderEcxeption, AlreadyHasHeaderException {

        PrintWriter printWriter = resp.getWriter();
        String header = getFromBodyListByName(list, Note.HEADER);
        String inf = getFromBodyListByName(list, Note.INF);
        Note note = new Note(header, inf);

        MongoWorkNote mongo = new MongoWorkNote();
        mongo.add(note);
        String j = new Gson().toJson(note);
        resp.setStatus(HttpServletResponse.SC_OK);
        printWriter.write("added: " + j);
    }

    public void deleteNote(List<String> list) throws IOException, ParameterException, NoHeaderEcxeption, NoSuchElementInDBException {
        PrintWriter printWriter = resp.getWriter();

        String header = getFromBodyListByName(list, Note.HEADER);
        Note note = new Note(header, Note.INF);

        MongoWorkNote mongo = new MongoWorkNote();
        mongo.delete(note);

        String j = new Gson().toJson(note);
        resp.setStatus(HttpServletResponse.SC_OK);
        printWriter.write("deleted: " + j);

    }

    public void updateNote(List<String> list) throws IOException, NoHeaderEcxeption, NoSuchElementInDBException, ParameterException {

        PrintWriter printWriter = resp.getWriter();

        String header = getFromBodyListByName(list, Note.HEADER);
        String inf = getFromBodyListByName(list, Note.INF);
        Note note = new Note(header, inf);

        MongoWorkNote mongo = new MongoWorkNote();
        mongo.update(note);

        String j = new Gson().toJson(note);
        resp.setStatus(HttpServletResponse.SC_OK);
        printWriter.write("updated: " + j);

    }

    public Note getNoteFromBD(String header) throws IOException, NoHeaderEcxeption, NoSuchElementInDBException {

        MongoWorkNote mongo = new MongoWorkNote();
        Note note = mongo.get(header);

        String string = new Gson().toJson(note);
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(string);
        return note;

    }
}
