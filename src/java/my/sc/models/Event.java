/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.sc.models;

/**
 *
 * @author myuceel
 */
public class Event {
    
    String eventId;
    String eventName;
    String actorId;
    String detail;

    long time;

    public Event(String eventId,String eventName, String actorId, String detail, long time) {
        this.eventName = eventName;
        this.actorId = actorId;
        this.detail = detail;
        this.eventId = eventId;
        this.time = time;

        
        
    }


    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    
    
    

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
    

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    
    
    
    
    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    
    
    
}
