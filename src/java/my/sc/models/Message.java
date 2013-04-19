/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.sc.models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author myuceel
 */
public class Message {
    
    String _id;
    String messageType;
    String messageAuthorId;
    int messagePoint;
    ArrayList<Event> messageEvents;
    

    
    
    public String getId() {
        return _id;
    }
    
    public void setId(String _id) {
        this._id = _id;
    }
    
    public String getMessageAuthorId() {
        return messageAuthorId;
    }
    
    public void setMessageAuthorId(String messageAuthorId) {
        this.messageAuthorId = messageAuthorId;
    }
    
    public ArrayList<Event> getMessageEvents() {
        return messageEvents;
    }
    
    public void setMessageEvents(ArrayList<Event> messageEvents) {
        this.messageEvents = messageEvents;
    }
    
    public int getMessagePoint() {
        return messagePoint;
    }
    
    public void setMessagePoint(int messagePoint) {
        this.messagePoint = messagePoint;
    }
    
    public String getMessageType() {
        return messageType;
    }
    
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
