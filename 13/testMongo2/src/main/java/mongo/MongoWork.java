package mongo;

import com.mongodb.*;


import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;


public class MongoWork {

    private final String HEADER="header";
    private final String INF="inf";
    private Mongo mongo;
    private DB db;
    private DBCollection collection;

    public MongoWork() throws UnknownHostException {
        mongo=new Mongo("localhost", 27017);
        db= mongo.getDB("test");
        collection=db.getCollection("some");
    }

    public void add() {
        //BasicDBObject dbObject=new BasicDBObject();
        String result="bo";
        BasicDBObject query=new BasicDBObject();
        query.put("name", "James");
        try {
            DBObject findElement = collection.findOne(query);
            if(findElement==null)
            {
                throw new IOException();
            }
            result= String.valueOf(findElement.get("gender"));
        }catch (IOException e)
        {
            System.out.println("yes");
        }
        System.out.println(result);
       // collection.insert(dbObject);
    }




}
