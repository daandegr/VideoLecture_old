/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Daan
 */
@Entity
@Table(name = "COURSETEACHER")
public class CourseTeacher implements Serializable {

    @EmbeddedId
    private CourseTeacherKey id;
    
    @ManyToOne
    @JoinColumn(name="USERID", insertable = false, updatable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name="COURSEID", insertable = false, updatable = false)
    private Course course;

    public CourseTeacher() {
    }

    public CourseTeacher(CourseTeacherKey id, User user, Course course) {
        this.id = id;
        this.user = user;
        this.course = course;
    }

    public CourseTeacherKey getId() {
        return id;
    }

    public void setId(CourseTeacherKey id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    
    
}
