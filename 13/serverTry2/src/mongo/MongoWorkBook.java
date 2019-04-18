package mongo;

import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.util.JSON;
import inputExceptionsMongo.AlreadyHasHeaderException;
import inputExceptionsMongo.NoHeaderEcxeption;
import inputExceptionsMongo.NoSuchElementInDBException;
import objects.Book;

import java.net.UnknownHostException;

public class MongoWorkBook {

    private final String HEADER="header";
    private final String INF="inf";
    private Mongo mongo;
    private DB db;
    private DBCollection collection;

    public MongoWorkBook() throws UnknownHostException {
        mongo=new Mongo("localhost", 27017);
        db= mongo.getDB("server");
        collection=db.getCollection("articles");
    }

    public String getByAuthor(String searchAuthor, String field) throws NoHeaderEcxeption, NoSuchElementInDBException {
        if (searchAuthor == null || searchAuthor.isEmpty()) {
            throw new NoHeaderEcxeption();
        }
        String result = null;
        BasicDBObject query = new BasicDBObject();

        query.put(field, searchAuthor);
        DBObject findElement = collection.findOne(query);
        if (findElement == null) {
            throw new NoSuchElementInDBException();
        }
        result = String.valueOf(findElement.get(Book.NAME));

        return result;
    }

    public void add(Book book) throws AlreadyHasHeaderException, NoHeaderEcxeption {

        try{
            String string= getByAuthor(book.getAuthor(), Book.AUTHOR);
            if(string!=null) {
                throw new AlreadyHasHeaderException();
            }
        }catch (NoSuchElementInDBException e) {
        }
        String jSONstring=new Gson().toJson(book);
        BasicDBObject dbObject= (BasicDBObject) JSON.parse(jSONstring);
        collection.insert(dbObject);
    }

    public Book get(String searchAuthor) throws NoHeaderEcxeption, NoSuchElementInDBException {

        return new Book(searchAuthor, getByAuthor(searchAuthor, Book.AUTHOR));
    }

    public void  update(Book book) throws NoHeaderEcxeption, NoSuchElementInDBException {

        String jSONstring=new Gson().toJson(book);
        BasicDBObject newDBObject=(BasicDBObject) JSON.parse(jSONstring);
        BasicDBObject oldDBObject=new BasicDBObject().append(Book.NAME, getByAuthor(book.getAuthor(), Book.AUTHOR));
        collection.update(oldDBObject, newDBObject);
    }

    public void delete(Book book) throws NoHeaderEcxeption, NoSuchElementInDBException {

        book.setName(getByAuthor(book.getAuthor(), Book.AUTHOR));
        String jSONstring=new Gson().toJson(book);
        BasicDBObject query=(BasicDBObject) JSON.parse(jSONstring);
        collection.remove(query);
    }
}
