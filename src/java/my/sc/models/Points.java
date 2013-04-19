/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.sc.models;

/**
 *
 * @author myuceel
 */
public class Points {

    public enum Point {

        Q_ADDED(3,0),
        Q_FAVED(2,0),
        Q_VOTED_UP(5,1),
        Q_VOTED_DOWN(-5,1),
        Q_ANSWERED(4,2),
        Q_COMMENTED(2,0),
        
        A_ADDED(3,0),
        A_ACCEPTED(2,10),
        A_VOTED_UP(6,1),
        A_VOTED_DOWN(-6,1),
        A_COMMENTED(2,0),
        
        C_ADDED(3,0),
        C_FAVED(3,0);
        
        
        private final int actorPoint;
        private final int ownerPoint;
        

        Point(int actorPoint,int ownerPoint) {
            this.actorPoint = actorPoint;
            this.ownerPoint=ownerPoint;
        }

        public int actorPoint() {
            return this.actorPoint;
        }
        public int ownerPoint() {
            return this.ownerPoint;
        }
        
    }
    
}
