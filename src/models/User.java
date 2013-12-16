package models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue
    private Integer USERID;
    private int ACCESSLEVEL;
    private String FIRSTNAME;
    private String MAIL;
    private String ADDRESS;
    private String NATIONALITY;
    private String LASTNAME;
    private String PASSWORD;
    private Boolean ACTIVE;
    private String SESSIONKEY;
    private Boolean HASPIC;
    private String POSTAL_CODE;
    private String CITY;
    
    
    @OneToMany(mappedBy="user")
    private List<CourseTeacher> teaches;

    public User(Integer USERID, int ACCESSLEVEL, String FIRSTNAME, String MAIL, String ADDRESS, String NATIONALITY, String LASTNAME, String PASSWORD, Boolean ACTIVE, String SESSIONKEY, Boolean HASPIC, String POSTAL_CODE, String CITY, List<CourseTeacher> teaches) {
        this.USERID = USERID;
        this.ACCESSLEVEL = ACCESSLEVEL;
        this.FIRSTNAME = FIRSTNAME;
        this.MAIL = MAIL;
        this.ADDRESS = ADDRESS;
        this.NATIONALITY = NATIONALITY;
        this.LASTNAME = LASTNAME;
        this.PASSWORD = PASSWORD;
        this.ACTIVE = ACTIVE;
        this.SESSIONKEY = SESSIONKEY;
        this.HASPIC = HASPIC;
        this.POSTAL_CODE = POSTAL_CODE;
        this.CITY = CITY;
        this.teaches = teaches;
    }
    
   
    public User() {
    }

    public Integer getUSERID() {
        return USERID;
    }

    public void setUSERID(Integer USERID) {
        this.USERID = USERID;
    }

    public int getACCESSLEVEL() {
        return ACCESSLEVEL;
    }

    public void setACCESSLEVEL(int ACCESSLEVEL) {
        this.ACCESSLEVEL = ACCESSLEVEL;
    }

    public String getFIRSTNAME() {
        return FIRSTNAME;
    }

    public void setFIRSTNAME(String FIRSTNAME) {
        this.FIRSTNAME = FIRSTNAME;
    }

    public String getMAIL() {
        return MAIL;
    }

    public void setMAIL(String MAIL) {
        this.MAIL = MAIL;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getNATIONALITY() {
        return NATIONALITY;
    }

    public void setNATIONALITY(String NATIONALITY) {
        this.NATIONALITY = NATIONALITY;
    }

    public String getLASTNAME() {
        return LASTNAME;
    }

    public void setLASTNAME(String LASTNAME) {
        this.LASTNAME = LASTNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public Boolean getACTIVE() {
        return ACTIVE;
    }

    public void setACTIVE(Boolean ACTIVE) {
        this.ACTIVE = ACTIVE;
    }

    public String getSESSIONKEY() {
        return SESSIONKEY;
    }

    public void setSESSIONKEY(String SESSIONKEY) {
        this.SESSIONKEY = SESSIONKEY;
    }

    public Boolean getHASPIC() {
        return HASPIC;
    }

    public void setHASPIC(Boolean HASPIC) {
        this.HASPIC = HASPIC;
    }

    public String getPOSTAL_CODE() {
        return POSTAL_CODE;
    }

    public void setPOSTAL_CODE(String POSTAL_CODE) {
        this.POSTAL_CODE = POSTAL_CODE;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public List<CourseTeacher> getTeaches() {
        return teaches;
    }

    public void setTeaches(List<CourseTeacher> teaches) {
        this.teaches = teaches;
    }
    
    public static boolean isAdmin(User user) {
        if (user.getACCESSLEVEL() == 3) {
            return true;
        }
        return false;
    }
    
    
    public static String sha1(String input) throws NoSuchAlgorithmException { // deze exception zal niet optreden want SHA1 is een valid algo
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    public static boolean isValidLogin(String email, String password) throws NoSuchAlgorithmException { // deze exception zal niet optreden want SHA1 is een valid algo
        User user = getUserByEmail(email);
        if (user != null) {
            if (user.getPASSWORD().equals(sha1(password)) && user.getACCESSLEVEL() >= 2) {
                return true;
            }
        }
        return false;
    }

    public static User getUserByEmail(String email) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        tx.begin();
        Criteria criteria = session.createCriteria(User.class).add(Restrictions.eq("MAIL", email));
        User user = (User)criteria.uniqueResult();
        tx.commit();
        return user;
    }    
    

}