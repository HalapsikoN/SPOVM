package server;


import inputExceptionsMongo.*;
import objects.Book;
import objects.Note;
import workWithObjects.BookWork;
import workWithObjects.NoteWork;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="server", displayName="Notification Servlet", urlPatterns = {"/server"})
public class Main extends HttpServlet {

    private String getBody(HttpServletRequest req) throws IOException {

        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
            buffer.append('\0');
        }
        String data = buffer.toString();
        return data;
    }

    private List<String> getBodyList(String string){

        char[] stringChar=string.toCharArray();
       // System.out.println(stringChar.length);
        List<String> list=new ArrayList<String>();
        int i=0;
        while(i<stringChar.length){
            StringBuffer buffer=new StringBuffer();
            while (isGood(stringChar[i])){
                buffer.append(stringChar[i]);
                i++;
            }
            String string1=buffer.toString();
            //System.out.println(string1);
            list.add(string1);
            i++;
        }
        return list;
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

    private boolean isGood(char symbol){
        return ((symbol>='a'&&symbol<='z') || (symbol>='A'&&symbol<='Z') || (symbol>= '0'&&symbol<='9'));
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String header=req.getParameter(Note.HEADER);
        String author=req.getParameter(Book.AUTHOR);
        //
        try {
            if (header!=null){
                NoteWork noteWork=new NoteWork(resp);
                noteWork.getNoteFromBD(header);
            }
            if(author!=null){
                BookWork bookWork=new BookWork(resp);
                bookWork.getBookFromBD(author);
            }
            /*MongoWorkNote mongo = new MongoWorkNote();
            Note note = mongo.get(header);

            String string = new Gson().toJson(note);
            PrintWriter printWriter = resp.getWriter();
            printWriter.write(string);*/
        }catch (NoHeaderEcxeption | NoSuchElementInDBException ex) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String string=getBody(req);
        List<String> list= getBodyList(string);
        //
        PrintWriter printWriter=resp.getWriter();
        try {
            if (list.contains(Note.HEADER)){
                NoteWork noteWork=new NoteWork(resp);
                noteWork.addNote(list);
            }
            if(list.contains(Book.AUTHOR)){
                BookWork bookWork=new BookWork(resp);
                bookWork.addBook(list);
            }

            /*String header = getFromBodyListByName(list, Note.HEADER);
            String inf = getFromBodyListByName(list, Note.INF);
            Note note = new Note(header, inf);

            MongoWorkNote mongo = new MongoWorkNote();
            mongo.add(note);
            String j = new Gson().toJson(note);
            resp.setStatus(HttpServletResponse.SC_OK);
            printWriter.write("added: "+j);*/
        }catch (ParameterException | NoHeaderEcxeption | AlreadyHasHeaderException ex){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String string=getBody(req);
        List<String> list= getBodyList(string);
        //
        PrintWriter printWriter=resp.getWriter();
        try {
            if (list.contains(Note.HEADER)){
                NoteWork noteWork=new NoteWork(resp);
                noteWork.updateNote(list);
            }
            if(list.contains(Book.AUTHOR)){
                BookWork bookWork=new BookWork(resp);
                bookWork.updateBook(list);
            }

            /*String header = getFromBodyListByName(list, Note.HEADER);
            String inf = getFromBodyListByName(list, Note.INF);
            Note note=new Note(header, inf);

            MongoWorkNote mongo=new MongoWorkNote();
            mongo.update(note);

            String j = new Gson().toJson(note);
            resp.setStatus(HttpServletResponse.SC_OK);
            printWriter.write("updated: " +j);*/
        }catch (ParameterException | NoSuchElementInDBException | NoHeaderEcxeption ex) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String string=getBody(req);
        List<String> list= getBodyList(string);
        //
        PrintWriter printWriter=resp.getWriter();
        try{
            if (list.contains(Note.HEADER)){
                NoteWork noteWork=new NoteWork(resp);
                noteWork.deleteNote(list);
            }
            if(list.contains(Book.AUTHOR)){
                BookWork bookWork=new BookWork(resp);
                bookWork.deleteBook(list);
            }
           /* String header = getFromBodyListByName(list, Note.HEADER);
            Note note=new Note(header, Note.INF);

            MongoWorkNote mongo=new MongoWorkNote();
            mongo.delete(note);

            String j = new Gson().toJson(note);
            resp.setStatus(HttpServletResponse.SC_OK);
            printWriter.write("deleted: " +j);*/
        } catch (ParameterException | NoSuchElementInDBException | NoHeaderEcxeption ex) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        }
    }
}
