package my.sc.db;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

/**
 * @author mustafa
 */
public class DBConnectionManager {

    private Mongo myMongo;
    private boolean authorized;
    boolean live = false;
    String liveHost = "localhost";
    String liveUserName = "liveUserName";
    String livePassword = "livePassword";

    public Mongo getMongo() {

        try {
            if (myMongo == null) {
                if (live) {
                    myMongo = new Mongo(liveHost, 27017);
                } else {
                    myMongo = new Mongo("localhost", 27017);
                }


            }
            return myMongo;
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

    public DB getMongoDB(String dbName) {
        DB db = getMongo().getDB(dbName);
        if (live) {
            if (!authorized) {
                authorized = db.authenticate(liveUserName, livePassword.toCharArray());
            }
        }
        return db;
    }

    public DBCollection getCollection(String dbName, String collectionName) {
        DBCollection collection = getMongoDB(dbName).getCollection(collectionName);
        return collection;
    }
}