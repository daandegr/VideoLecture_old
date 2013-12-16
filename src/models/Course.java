package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import service.HibernateUtil;

@Entity
@Table(name="COURSE")
public class Course implements Serializable{

    @Id
    @GeneratedValue
    private int COURSEID;
    private String COURSENAME;
    private String COURSEDESCRIPTION;
    private double PRICE;
    private boolean COURSESETFORDELETION;
    private boolean VIDEOLECTUREACTIVE;
    
   
    
    public Course(){
        
    }

    public Course(int COURSEID, String COURSENAME, String COURSEDESCRIPTION, double PRICE, boolean COURSESETFORDELETION, boolean VIDEOLECTUREACTIVE) {
        this.COURSEID = COURSEID;
        this.COURSENAME = COURSENAME;
        this.COURSEDESCRIPTION = COURSEDESCRIPTION;
        this.PRICE = PRICE;
        this.COURSESETFORDELETION = COURSESETFORDELETION;
        this.VIDEOLECTUREACTIVE = VIDEOLECTUREACTIVE;
    }




    public int getCOURSEID() {
        return COURSEID;
    }

    public void setCOURSEID(int COURSEID) {
        this.COURSEID = COURSEID;
    }

    public String getCOURSENAME() {
        return COURSENAME;
    }

    public void setCOURSENAME(String COURSENAME) {
        this.COURSENAME = COURSENAME;
    }

    public String getCOURSEDESCRIPTION() {
        return COURSEDESCRIPTION;
    }

    public void setCOURSEDESCRIPTION(String COURSEDESCRIPTION) {
        this.COURSEDESCRIPTION = COURSEDESCRIPTION;
    }

    public double getPRICE() {
        return PRICE;
    }

    public void setPRICE(double PRICE) {
        this.PRICE = PRICE;
    }

    public boolean isCOURSESETFORDELETION() {
        return COURSESETFORDELETION;
    }

    public void setCOURSESETFORDELETION(boolean COURSESETFORDELETION) {
        this.COURSESETFORDELETION = COURSESETFORDELETION;
    }

    public boolean isVIDEOLECTUREACTIVE() {
        return VIDEOLECTUREACTIVE;
    }

    public void setVIDEOLECTUREACTIVE(boolean VIDEOLECTUREACTIVE) {
        this.VIDEOLECTUREACTIVE = VIDEOLECTUREACTIVE;
    }
    
    public static List<Course> getAllCourses(Session session) {
        Criteria criteria = session.createCriteria(Course.class);
        return criteria.list();
    }
    
    public static Course getCourseFromName(String name){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        Criteria criteria = session.createCriteria(Course.class).add(Restrictions.eq("COURSENAME", name));
        return (Course)criteria.uniqueResult();
        
    }
    
    public static List<Course> getCoursesFromTeacher(User user) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        if (User.isAdmin(user)) {
            return getAllCourses(session);
        } else {
            Criteria criteria = session.createCriteria(CourseTeacher.class).add(Restrictions.eq("user.USERID", user.getUSERID()));
            List<CourseTeacher> ct = criteria.list();

            List<Course> courses = new ArrayList<Course>();
            for (int i = 0; i < ct.size(); i++) {
                courses.add(ct.get(i).getCourse());
            }

            return courses;
        }

    }
    
    public static void updateCourse(Course course) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        session.update(course);
        tx.commit();
    }
    
    public static Course getActiveCourse() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        List<Course> allCourses = Course.getAllCourses(session);
        Course activeCourse = new Course();

        for (int i = 0; i < allCourses.size(); i++) {
            if (allCourses.get(i).isVIDEOLECTUREACTIVE()) {
                activeCourse = allCourses.get(i);
                break;
            }
        }
        return activeCourse;
    }
    public static boolean activeLecture() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        List<Course> allCourses = Course.getAllCourses(session);

        for (int i = 0; i < allCourses.size(); i++) {
            if (allCourses.get(i).isVIDEOLECTUREACTIVE()) {
                return true;
            }
        }
        return false;
    }

    public void editCourse() {
    }

    public void createModule() {
    }

    public void createAnnouncement() {
    }

    public void startVideoLecture() {
    }
}
