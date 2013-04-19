/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.sc.models;

/**
 *
 * @author myuceel
 */
public class Target {
    
    String authorId;
    String mType;
    String mId;

    public Target(String authorId, String mType, String mId) {
        this.authorId = authorId;
        this.mType = mType;
        this.mId = mId;
    }
    
    

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

  
    
    
    
    
}
