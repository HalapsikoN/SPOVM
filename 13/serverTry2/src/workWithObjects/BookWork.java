package workWithObjects;

import com.google.gson.Gson;
import inputExceptionsMongo.AlreadyHasHeaderException;
import inputExceptionsMongo.NoHeaderEcxeption;
import inputExceptionsMongo.NoSuchElementInDBException;
import inputExceptionsMongo.ParameterException;
import mongo.MongoWorkBook;
import objects.Book;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class BookWork {

    private HttpServletResponse resp;

    public BookWork(HttpServletResponse resp){
        this.resp=resp;
    }

    public String getFromBodyListByName(List<String> list, String name) throws ParameterException {

        if(!list.contains(name)){
            throw new ParameterException();
        }
        int index=0;
        index=list.indexOf(name);
        index++;
        String result=list.get(index);
        return result;
    }

    public void addBook(List<String> list) throws IOException, ParameterException, NoHeaderEcxeption, AlreadyHasHeaderException {

        PrintWriter printWriter = resp.getWriter();
        String author = getFromBodyListByName(list, Book.AUTHOR);
        String name = getFromBodyListByName(list, Book.NAME);
        Book book = new Book(author, name);

        MongoWorkBook mongo = new MongoWorkBook();
        mongo.add(book);
        String j = new Gson().toJson(book);
        resp.setStatus(HttpServletResponse.SC_OK);
        printWriter.write("added: " + j);
    }

    public void deleteBook(List<String> list) throws IOException, ParameterException, NoHeaderEcxeption, NoSuchElementInDBException {
        PrintWriter printWriter = resp.getWriter();

        String author = getFromBodyListByName(list, Book.AUTHOR);
        Book book = new Book(author, Book.NAME);

        MongoWorkBook mongo = new MongoWorkBook();
        mongo.delete(book);

        String j = new Gson().toJson(book);
        resp.setStatus(HttpServletResponse.SC_OK);
        printWriter.write("deleted: " + j);

    }

    public void updateBook(List<String> list) throws IOException, NoHeaderEcxeption, NoSuchElementInDBException, ParameterException {

        PrintWriter printWriter = resp.getWriter();

        String author = getFromBodyListByName(list, Book.AUTHOR);
        String name = getFromBodyListByName(list, Book.NAME);
        Book book = new Book(author, name);

        MongoWorkBook mongo = new MongoWorkBook();
        mongo.update(book);

        String j = new Gson().toJson(book);
        resp.setStatus(HttpServletResponse.SC_OK);
        printWriter.write("updated: " + j);

    }

    public Book getBookFromBD(String author) throws IOException, NoHeaderEcxeption, NoSuchElementInDBException {

        MongoWorkBook mongo = new MongoWorkBook();
        Book book = mongo.get(author);

        String string = new Gson().toJson(book);
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(string);
        return book;

    }
}
