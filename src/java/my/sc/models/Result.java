/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.sc.models;

/**
 *
 * @author myuceel
 */
public class Result {
    
    boolean success;
    String resultCode;
    String resultExplanation;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public void setResultExplanation(String resultExplanation) {
        this.resultExplanation = resultExplanation;
    }

    
    
    public String getResultCode() {
        return resultCode;
    }

    public String getResultExplanation() {
        return resultExplanation;
    }
       
}
