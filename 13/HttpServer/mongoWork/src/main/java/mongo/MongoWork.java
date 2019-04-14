package mongo;

import com.mongodb.*;
import inputExceptionsMongo.*;

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

    public void add(List<String> parametersList, Map<String,String> parametersMap) throws ParameterException, NoHeaderEcxeption, NoInformationException, AlreadyHasHeaderException {

        if(parametersList==null || parametersMap==null || parametersList.isEmpty() || parametersMap.isEmpty()){
            throw new ParameterException();
        }
        for(String parameter:parametersList){
            if (parameter==null || parameter.isEmpty()){
                throw new NoHeaderEcxeption();
            }
            String value=parametersMap.get(parameter);
            if(value==null || value.isEmpty()){
                throw new NoInformationException();
            }
            try{
                String string=getByHeader(parameter);
                if(!(string.isEmpty())) {
                    throw new AlreadyHasHeaderException();
                }
            }catch (NoSuchElementInDBException e) {
            }
        }
        BasicDBObject dbObject=new BasicDBObject();
        for(String parameter:parametersList){
            String key=parameter;
            String value=parametersMap.get(parameter);
            dbObject.put(key,value);
        }
        collection.insert(dbObject);
    }

    public String getByHeader(String searchHeader) throws NoHeaderEcxeption, NoSuchElementInDBException {
        if (searchHeader == null || searchHeader.isEmpty()) {
            throw new NoHeaderEcxeption();
        }
        String result = null;
        BasicDBObject query = new BasicDBObject();

        query.put(HEADER, searchHeader);
        DBObject findElement = collection.findOne(query);
        if (findElement == null) {
            throw new NoSuchElementInDBException();
        }
        result = String.valueOf(findElement.get(HEADER));

        return result;
    }

    public void updateByHeader(String searchHeader, String newInf) throws NoHeaderEcxeption, NoSuchElementInDBException, NoInformationException {
        if(searchHeader==null || searchHeader.isEmpty()){
            throw new NoHeaderEcxeption();
        }
        if(newInf==null || newInf.isEmpty()){
            throw new NoInformationException();
        }
        BasicDBObject newDBObject=new BasicDBObject();
        newDBObject.put(HEADER, searchHeader);
        newDBObject.put(INF, newInf);
        BasicDBObject oldDBObject=new BasicDBObject().append(INF, getByHeader(searchHeader));
        collection.update(oldDBObject, newDBObject);
    }

    public void deleteByHeader(String searchHeader) throws NoHeaderEcxeption, NoSuchElementInDBException {
        if(searchHeader==null || searchHeader.isEmpty()){
            throw new NoHeaderEcxeption();
        }
        BasicDBObject query=new BasicDBObject();
        query.put(INF, getByHeader(searchHeader));
        collection.remove(query);
    }
}
