package mongo;

import com.mongodb.*;

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
        db= mongo.getDB("server");
        collection=db.getCollection("articles");
    }

    public void add(List<String> parametersList, Map<String,String> parametersMap){

        BasicDBObject dbObject=new BasicDBObject();
        for(String parameter:parametersList){
            String key=parameter;
            String value=parametersMap.get(parameter);
            dbObject.put(key,value);
        }
        collection.insert(dbObject);
    }

    public String getByHeader(String searchHeader){

        String result=null;
        BasicDBObject query=new BasicDBObject();
        query.put(HEADER, searchHeader);
        DBObject findElement= (DBObject) collection.find(query);
        result= String.valueOf(findElement.get(HEADER));
        return result;
    }

    public void updateByHeader(String searchHeader, String newInf){

        BasicDBObject newDBObject=new BasicDBObject();
        newDBObject.put(searchHeader, newInf);
        BasicDBObject oldDBObject=new BasicDBObject().append(searchHeader, getByHeader(searchHeader));
        collection.update(oldDBObject, newDBObject);
    }

    public void deleteByHeader(String searchHeader){

        BasicDBObject query=new BasicDBObject();
        query.put(searchHeader, getByHeader(searchHeader));
        collection.remove(query);
    }
}
