/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.sc.db;

import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.util.JSON;
import java.util.List;
import java.util.Set;
import org.bson.BasicBSONObject;
import org.bson.types.BasicBSONList;
import my.sc.models.Event;
import my.sc.models.Points.Point;
import my.sc.models.Result;
import my.sc.models.Target;

/**
 *
 * @author myuceel
 */
public class MongoDbOperations {

    DBConnectionManager dbConManager = null;
    Gson gson = new Gson();

    public List<String> getAllDbNames() {

        try {
            dbConManager = new DBConnectionManager();
            List<String> databaseNames = dbConManager.getMongo().getDatabaseNames();
            return databaseNames;
        } catch (MongoException ex) {
            // System.out.println(ex);
            return null;
        }
    }

    public Result addEvent(String dbName, String collectionName, Target target, Event event, String messageText) {

        Result result = new Result();

        result.setSuccess(true);
        result.setResultCode("RSLT.003");
        result.setResultExplanation("Unidentified Command");

        try {
            dbConManager = new DBConnectionManager();
            DBCollection collection = dbConManager.getCollection(dbName, collectionName);
//            collection.ensureIndex(new BasicDBObject("tags", 1).append("unique", true));
            String eventAsJson = gson.toJson(event);
            DBObject eventPart = (DBObject) JSON.parse(eventAsJson);

            boolean Q = target.getmType().equalsIgnoreCase("Q");
            boolean A = target.getmType().equalsIgnoreCase("A");
            boolean C = target.getmType().equalsIgnoreCase("C");


            boolean eventAdd = event.getEventName().equalsIgnoreCase("Add");
            boolean eventComment = event.getEventName().equalsIgnoreCase("Comment");
            boolean eventAnswer = event.getEventName().equalsIgnoreCase("Answer");
            boolean eventVote = event.getEventName().equalsIgnoreCase("Vote");
            boolean eventFavorit = event.getEventName().equalsIgnoreCase("Favorit");

            boolean eventAccept = event.getEventName().equalsIgnoreCase("Accept");


            boolean detailRemoved = event.getDetail().equalsIgnoreCase("remove");
            boolean detailUpped = event.getDetail().equalsIgnoreCase("up");
            boolean detailDowned = event.getDetail().equalsIgnoreCase("down");
            boolean detailFavorited = event.getDetail().equalsIgnoreCase("great");

            int authorPoint = 0;
            int extractFromAuthorPoint = 0;

            // int messagePoint = 0;

            boolean allowMultipleSameTypeEvent = eventAnswer || eventComment;
            boolean justRemoveEvent = detailRemoved;

            if (eventAdd) {

                if (Q) {
                    authorPoint = Point.Q_ADDED.actorPoint();
                    //      messagePoint = Point.Q_ADDED.ownerPoint();
                } else if (A) {
                    authorPoint = Point.A_ADDED.actorPoint();
                    //      messagePoint = Point.A_ADDED.ownerPoint();
                } else if (C) {
                    authorPoint = Point.C_ADDED.actorPoint();
                    //       messagePoint = Point.C_ADDED.ownerPoint();
                }

                DBObject find = new BasicDBObject();

                find.put("_id", target.getAuthorId());

                DBObject person = new BasicDBObject();

                Event existingEvent = getExistingEvent(dbName, collectionName, target, event, allowMultipleSameTypeEvent);

                if (existingEvent != null) {
                    deleteExistingEvent(dbName, collectionName, target, event, allowMultipleSameTypeEvent, authorPoint);
                }

                person.put("$set", new BasicDBObject().append(target.getmId() + ".messageType", target.getmType()).append(target.getmId() + ".messageDetail", messageText));
                person.put("$addToSet", new BasicDBObject(target.getmId() + ".messageEvents", eventPart));
                person.put("$inc", new BasicDBObject("authorPoint", authorPoint).append(target.getmId() + ".messagePoint", authorPoint));

                //System.out.println(person.toString());

                collection.update(find, person, true, false);

                result.setSuccess(true);
                result.setResultCode("RSLT.001");
                result.setResultExplanation("Successful");

            } else if (eventComment || eventAnswer || eventVote || eventFavorit || eventAccept) {


                Event existingEvent = getExistingEvent(dbName, collectionName, target, event, allowMultipleSameTypeEvent);

                if (Q) {
                    if (eventComment) {
                        authorPoint = Point.Q_COMMENTED.actorPoint();
                        extractFromAuthorPoint = authorPoint;
                        //          messagePoint = Point.Q_COMMENTED.ownerPoint();
                    } else if (eventAnswer) {
                        authorPoint = Point.Q_ANSWERED.actorPoint();
                        extractFromAuthorPoint = authorPoint;
                        //          messagePoint = Point.Q_ANSWERED.ownerPoint();
                    } else if (eventVote) {
                        if (detailUpped) {
                            authorPoint = Point.Q_VOTED_UP.actorPoint();

                            if (existingEvent != null && existingEvent.getDetail().equalsIgnoreCase("down")) {
                                extractFromAuthorPoint = Point.Q_VOTED_DOWN.actorPoint();
                            } else if (existingEvent != null && existingEvent.getDetail().equalsIgnoreCase("up")) {
                                extractFromAuthorPoint = Point.Q_VOTED_UP.actorPoint();
                            }

                            //             messagePoint = Point.Q_VOTED_UP.ownerPoint(); 
                        } else if (detailDowned) {
                            authorPoint = Point.Q_VOTED_DOWN.actorPoint();

                            if (existingEvent != null && existingEvent.getDetail().equalsIgnoreCase("up")) {
                                extractFromAuthorPoint = Point.Q_VOTED_UP.actorPoint();
                            } else if (existingEvent != null && existingEvent.getDetail().equalsIgnoreCase("down")) {
                                extractFromAuthorPoint = Point.Q_VOTED_DOWN.actorPoint();
                            }

                            //            messagePoint = Point.Q_VOTED_DOWN.ownerPoint();
                        } else if (justRemoveEvent) {
                            if (existingEvent != null && existingEvent.getDetail().equalsIgnoreCase("up")) {
                                extractFromAuthorPoint = Point.Q_VOTED_UP.actorPoint();
                            } else if (existingEvent != null && existingEvent.getDetail().equalsIgnoreCase("down")) {
                                extractFromAuthorPoint = Point.Q_VOTED_DOWN.actorPoint();
                            }
                        }
                    } else if (eventFavorit) {
                        authorPoint = Point.Q_FAVED.actorPoint();
                        extractFromAuthorPoint = authorPoint;
                        //       messagePoint = Point.Q_FAVED.ownerPoint();
                    }

                }
                if (A) {
                    if (eventComment) {
                        authorPoint = Point.A_COMMENTED.actorPoint();
                        extractFromAuthorPoint = authorPoint;
                        //      messagePoint = Point.A_COMMENTED.ownerPoint();
                    } else if (eventAccept) {
                        authorPoint = Point.A_ACCEPTED.actorPoint();
                        extractFromAuthorPoint = authorPoint;
                        //        messagePoint = Point.A_ACCEPTED.ownerPoint();
                    } else if (eventVote) {
                        if (detailUpped) {
                            authorPoint = Point.A_VOTED_UP.actorPoint();
                            //        messagePoint = Point.A_VOTED_UP.ownerPoint();
                        } else if (detailDowned) {
                            authorPoint = Point.A_VOTED_DOWN.actorPoint();
                            //        messagePoint = Point.A_VOTED_DOWN.ownerPoint();
                        }

                    }
                }
                if (C) {
                    if (eventFavorit) {
                        authorPoint = Point.C_FAVED.actorPoint();
                        extractFromAuthorPoint = authorPoint;
                        //         messagePoint = Point.C_FAVED.ownerPoint();
                    }
                }


                if (existingEvent != null) {
                    deleteExistingEvent(dbName, collectionName, target, event, allowMultipleSameTypeEvent, extractFromAuthorPoint);
                }

                if (!justRemoveEvent) {

                    DBObject find = new BasicDBObject();

                    find.put("_id", target.getAuthorId());

                    DBObject update = new BasicDBObject();
                    DBObject where = new BasicDBObject();

                    update.put("$addToSet", where);
                    where.put(target.getmId() + ".messageEvents", eventPart);

                    WriteResult writeResult = collection.update(find, update);
                    CommandResult cr = writeResult.getLastError();

                    boolean updatePointResult = updatePoint(collection, target.getAuthorId(), target.getmId(), authorPoint);

                }

                result.setSuccess(true);
                result.setResultCode("RSLT.001");
                result.setResultExplanation("Successful");

            }


        } catch (MongoException e) {

            result.setSuccess(false);
            result.setResultCode("RSLT.002");
            result.setResultExplanation("MongoDB Error");

            int errorCode = e.getCode();
            if (errorCode == 11000) { //11000 -  duplicate key error index, collection.save() buraya düşmez.
                result.setResultCode("RSLT.003");
                result.setResultExplanation("Record Already Exists");
            }
        }

        return result;
    }

    private boolean updatePoint(DBCollection collection, String authorId, String mId, int authorPoint) {

        boolean result = false;

        DBObject find = new BasicDBObject();
        find.put("_id", authorId);


        BasicDBObject update = new BasicDBObject();

        update.put("$inc", new BasicDBObject("authorPoint", authorPoint).append(mId + ".messagePoint", authorPoint));

        WriteResult writeResult = collection.update(find, update);

        return result;

    }

    private Event getExistingEvent(String dbName, String collectionName, Target target, Event event, boolean allowMultipleSameTypeEvent) {

        Event result = null;

        try {
            dbConManager = new DBConnectionManager();
            DBCollection collection = dbConManager.getCollection(dbName, collectionName);

            DBObject find = new BasicDBObject();

            find.put("_id", target.getAuthorId());
            find.put(target.getmId() + ".messageEvents.eventName", event.getEventName());
            find.put(target.getmId() + ".messageEvents.actorId", event.getActorId());
            if (allowMultipleSameTypeEvent) {
                find.put(target.getmId() + ".messageEvents.eventId", event.getEventId());
            }

            DBCursor cursor = collection.find(find);

            if (cursor.hasNext()) {

                DBObject document = cursor.next();

                System.out.println(document);

                BasicBSONList messagesList = ((BasicBSONList) ((BasicBSONObject) document.get(target.getmId())).get("messageEvents"));

                for (int i = 0; i < messagesList.size(); i++) {

                    String eventName = ((BasicBSONObject) messagesList.get(i)).get("eventName").toString();
                    String actorId = ((BasicBSONObject) messagesList.get(i)).get("actorId").toString();
                    String eventId = ((BasicBSONObject) messagesList.get(i)).get("eventId").toString();

                    if (eventName.equalsIgnoreCase(event.getEventName()) && actorId.equalsIgnoreCase(event.getActorId())) {

                        String detail = ((BasicBSONObject) messagesList.get(i)).get("detail").toString();
                        long time = Long.parseLong(((BasicBSONObject) messagesList.get(i)).get("time").toString());

                        if (allowMultipleSameTypeEvent) {
                            if (eventId.equalsIgnoreCase(event.getEventId())) {
                                result = new Event(eventId, eventName, actorId, detail, time);
                            }
                        } else {
                            result = new Event(eventId, eventName, actorId, detail, time);
                        }
                    }
                }
            }

        } catch (MongoException e) {

            e.printStackTrace();

            result = null;
        }

        return result;
    }

    private boolean deleteExistingEvent(String dbName, String collectionName, Target target, Event event, boolean allowMultipleSameTypeEvent, int authorPoint) {

        boolean result = false;

        try {
            dbConManager = new DBConnectionManager();
            DBCollection collection = dbConManager.getCollection(dbName, collectionName);

            DBObject find = new BasicDBObject();

            find.put("_id", target.getAuthorId());


            BasicDBObjectBuilder update = BasicDBObjectBuilder.start();


            DBObject where = new BasicDBObject();
            DBObject evntPart = new BasicDBObject();

            evntPart.put("eventName", event.getEventName());
            evntPart.put("actorId", event.getActorId());
            if (allowMultipleSameTypeEvent) {
                evntPart.put("eventId", event.getEventId());
            }

            where.put(target.getmId() + ".messageEvents", evntPart);
            update.add("$pull", where);


            WriteResult writeResult = collection.update(find, update.get());

            // writeResult.getLastError().get("updatedExisting");

            boolean updatePointResult = updatePoint(collection, target.getAuthorId(), target.getmId(), -authorPoint);

            result = true;

        } catch (MongoException e) {

            result = false;
        }

        return result;
    }

    public Set<String> getAllCollectionNames(String dbName) {

        dbConManager = new DBConnectionManager();
        Set<String> colls = dbConManager.getMongoDB(dbName).getCollectionNames();
        return colls;

    }
}
