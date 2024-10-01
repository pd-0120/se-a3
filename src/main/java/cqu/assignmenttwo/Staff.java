package cqu.assignmenttwo;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import java.io.Serializable;
import java.util.StringJoiner;

/**
 *
 * @author PJ
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
    private String idStaff;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String name;
    private String emailAddress;
    private String password;

    //Constructor
    public Staff(String idStaff, Role role, String name, String emailAddress,
            String password) {
        this.idStaff = idStaff;
        this.name = name;
        this.emailAddress = emailAddress;
        this.role = role;
        this.password = password;
    }

    public Staff() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //Getters and Setters
    /**
     * Gets the staff id associated with the staff member.
     *
     * @return The staff id in String format.
     */
    public String getIdStaff() {
        return idStaff;
    }

    /**
     * Sets the staff id associated with the staff member.
     *
     */
    public void setIdStaff(String idStaff) {
        this.idStaff = idStaff;
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
     * Converts the staff details to a CSV formatted string.
     *
     * @return a new string for each new staff.
     */
    public String toCsvStringStaff() {
        StringJoiner joiner = new StringJoiner(",");
        joiner.add(getIdStaff())
                .add(getRoleAsString())
                .add(getName())
                .add(getEmailAddress())
                .add(getPassword());
        return joiner.toString();
    }

    /**
     * Gets the CSV header for the staff information.
     *
     * @return The CSV header.
     */
    public String getCsvStaffHeader() {

        return "IdStaff,Role,Name,EmailAddress,Password";
    }
}
