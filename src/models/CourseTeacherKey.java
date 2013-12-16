/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author Daan
 */
@Embeddable
public class CourseTeacherKey implements Serializable{
    
    private int COURSEID;
    private int USERID;

    public CourseTeacherKey() {
    }

    public CourseTeacherKey(int COURSEID, int USERID) {
        this.COURSEID = COURSEID;
        this.USERID = USERID;
    }

    public int getCOURSEID() {
        return COURSEID;
    }

    public void setCOURSEID(int COURSEID) {
        this.COURSEID = COURSEID;
    }

    public int getUSERID() {
        return USERID;
    }

    public void setUSERID(int USERID) {
        this.USERID = USERID;
    }
}
