package cqu.assignmenttwo;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQuery;
import java.io.Serializable;
import java.util.StringJoiner;

/**
 *
 * @author PJ - Andres Pinilla 
 * 
 * This class is to create the Staff objects. It has getters and setters
 * to access the different types of data and queries to process the user request
 * with the database.
 */
@Entity  

@NamedQuery(name = "findByEmailId", 
            query = "SELECT s FROM Staff s WHERE s.emailAddress = :emailAddress") 
@NamedQuery(name = "findByEmailIdAndPassword", 
            query = "SELECT s FROM Staff s WHERE s.emailAddress = :emailAddress and s.password = :password") 
public class Staff implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //Atributes
    @Enumerated(EnumType.STRING)
    private Role role;
    private String name;
    private String emailAddress;
    private String password;
    
    @Lob // Large object for storing the salt (binary data)
    private byte[] salt;

    //Constructor
    public Staff(Role role, String name, String emailAddress,
            String password, byte[] salt) {
//        this.idStaff = idStaff;
        this.name = name;
        this.emailAddress = emailAddress;
        this.role = role;
        this.password = password;
        this.salt = salt;
    }

    public Staff() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

     /**
     * Gets the name associated with the staff member.
     *
     * @return The name in String format.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name associated with the staff member.
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email address associated with the staff member.
     *
     * @return The email address in String format.
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the email address associated with the staff member.
     *
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Gets the role associated with the staff member.
     *
     * @return The role in String format.
     */
    public String getRoleAsString() {
        return role.toString();
    }

    /**
     * Sets the role associated with the staff member.
     *
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Gets the password associated with the staff member.
     *
     * @return The password in String format.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password associated with the staff member.
     *
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the role associated with the staff member.
     *
     * @return The role in enum format.
     */
    public Role getRole() {
        return role;
    }
    
    /**
     * Gets the password salt associated with the staff member.
     *
     * @return The salt in byte format.
     */
    public byte[] getSalt() {
        return salt;
    }

    /**
     * Sets the salt password associated with the staff member.
     *
     */
    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
}