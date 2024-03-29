import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.mongodb.*;
import com.mongodb.util.JSON;
import mongo.MongoWork;
import objects.Note;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Set;

public class MongoDb{


    public static void main(String[] args) throws UnknownHostException {

        System.out.println("Start");

        try {
            Mongo m = new Mongo("localhost", 27017);
            DB db = m.getDB("test");

            DBCollection coll = db.getCollection("some");

            //добавление
            //coll.insert(makeDocument(10, "James", "male"));
            //DBObject myDoc=coll.findOne();
            //System.out.println(coll.getCount());

            //вывод всего
            /*DBCursor cur=coll.find();
            while(cur.hasNext()){
                System.out.println(cur.next());
            }*/

            //поиск по параметру
           /* BasicDBObject query=new BasicDBObject();
            int[] arr=new int[2];
            arr[0]=40;
            arr[1]=41;
            query.put("age", new BasicDBObject("$in",arr)); //здесь можно задавать параметры поиска как в монге
            DBCursor cur=coll.find(query);
            while(cur.hasNext()){
                System.out.println(cur.next());
            }*/

           //изменение
            /*BasicDBObject newData=new BasicDBObject();
            newData.put("name", "Tom");
            BasicDBObject searchQuery=new BasicDBObject().append("name","James");
            coll.update(searchQuery, newData);*/

            /*String result="bo";
            BasicDBObject query=new BasicDBObject();
            query.put("name", "Tom");
            try {
                DBObject findElement = coll.findOne(query);
                if(findElement==null)
                {
                    throw new IOException();
                }
                result= String.valueOf(findElement.get("gender"));
            }catch (IOException e)
            {
                System.out.println("yes");
            }*/

            //System.out.println(result);
            //удаление
            /*BasicDBObject query=new BasicDBObject();
            query.put("name", "Tom");
            coll.remove(query);*/


            //MongoWork mongo=new MongoWork();
            //mongo.add();


            /*System.out.println();
            cur=coll.find();
            while(cur.hasNext()){
                System.out.println(cur.next());
            }*/
            //System.out.println("Finish");

            Note note=new Note("asd", "ssssssss");

            String str=new Gson().toJson(note);

            BasicDBObject ob= (BasicDBObject) JSON.parse(str);

            coll.insert(ob);
            /*String result = null;
            BasicDBObject query = new BasicDBObject();

            query.put("header", "asd");
            DBObject findElement = coll.findOne(query);
            if (findElement == null) {
                return;
            }
            String str=findElement.toString();
            System.out.println(str);


            Note note1=new Gson().fromJson(str, Note.class);
            System.out.println(note1.getHeader()+"\n"+note1.getInf());*/

            //coll.insert(dbObject);


        }
        catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
        catch (MongoException ex) {
            ex.printStackTrace();
        }
    }

    public static BasicDBObject makeDocument(int id, String name, String gender) {
        BasicDBObject doc = new BasicDBObject();
        doc.put("id", id);
        doc.put("name", name);
        doc.put("gender", gender);
        return doc;
    }
}