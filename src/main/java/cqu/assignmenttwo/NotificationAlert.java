package cqu.assignmenttwo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.StringJoiner;

/**
 *
 * @author PJ - Andres Pinilla
 * 
 * This class is to create the Notification Alert objects. It has getters and setters
 * to access the different types of data. It validates the data to prevent 
 * duplicities and persist the objects to store the information in the database.
 */
@Entity
@NamedQuery(name = "getAllNotifications", 
            query = "SELECT n from NotificationAlert n")
@NamedQuery(name = "findRegisteredNotifications", 
            query = "SELECT n FROM NotificationAlert n WHERE n.disasterId = :disasterId")
public class NotificationAlert implements Serializable {

    //Attributes
    private String reporterName;
    private Long disasterId;
    private String disasterDate;
    private String typeOfDisaster;
    private String disasterLocation;
    private String disasterDescription;
    private String levelOfPriority;
    private LocalDateTime timeStamping;
    private Staff createdBy;

    //Constructor
    public NotificationAlert(Long disasterId, String reporterName, 
            String disasterDate, String typeOfDisaster, String disasterLocation,
            String disasterDescription, String levelOfPriority,
            LocalDateTime timeStamping, Staff createdBy) {
        this.disasterId = disasterId;
        this.reporterName = reporterName;
        this.disasterDate = disasterDate;
        this.typeOfDisaster = typeOfDisaster;
        this.disasterLocation = disasterLocation;
        this.disasterDescription = disasterDescription;
        this.levelOfPriority = levelOfPriority;
        this.timeStamping = LocalDateTime.now();
        this.createdBy = createdBy;
    }

    public NotificationAlert() {
    }
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotificationAlert)) {
            return false;
        }
        NotificationAlert other = (NotificationAlert) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Gets the disaster id associated with the notification alert.
     *
     * @return The disaster id in String format.
     */
    public Long getDisasterId() {
        return disasterId;
    }

    /**
     * Sets the disaster id associated with the notification alert.
     *
     */
    public void setDisasterId(Long disasterId) {
        this.disasterId = disasterId;
    }
    
    /**
     * Gets the reporter name associated with the disaster event.
     *
     * @return The reporter name in String format.
     */
    public String getReporterName() {
        return reporterName;
    }

    /**
     * Sets the reporter name associated with the disaster event.
     *
     */
    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    /**
     * Gets the disaster date as a string.
     *
     * @return The incident date in string format.
     */
    public String getDisasterDate() {
        return disasterDate;
    }

    /**
     * Sets the disaster date associated with the disaster event.
     *
     */
    public void setDisasterDate(String disasterDate) {
        this.disasterDate = disasterDate;
    }

    /**
     * Gets the type of disaster associated with the notification alert.
     *
     * @return The type of disaster in String format.
     */
    public String getTypeOfDisaster() {
        return typeOfDisaster;
    }

    /**
     * Sets the type of disaster associated with the notification alert.
     *
     */
    public void setTypeOfDisaster(String typeOfDisaster) {
        this.typeOfDisaster = typeOfDisaster;
    }

    /**
     * Gets the disaster location associated with the notification alert.
     *
     * @return The disaster location in String format.
     */
    public String getDisasterLocation() {
        return disasterLocation;
    }

    /**
     * Sets the disaster location associated with the notification alert.
     *
     */
    public void setDisasterLocation(String disasterLocation) {
        this.disasterLocation = disasterLocation;
    }

    /**
     * Gets the disaster description associated with the notification alert.
     *
     * @return The disaster description in String format.
     */
    public String getDisasterDescription() {
        return disasterDescription;
    }

    /**
     * Sets the disaster description associated with the notification alert.
     *
     */
    public void setDisasterDescription(String disasterDescription) {
        this.disasterDescription = disasterDescription;
    }

    /**
     * Gets the level of priority associated with the notification alert.
     *
     * @return The level of priority in String format.
     */
    public String getLevelOfPriority() {
        return levelOfPriority;
    }

    /**
     * Sets the level of priority associated with the notification alert.
     *
     */
    public void setLevelOfPriority(String levelOfPriority) {
        this.levelOfPriority = levelOfPriority;
    }

    /**
     * Gets the date and time when the notification was registered.
     *
     * @return The date and time when the notification was registered in String format.
     */
    public String getTimeStamping() {
        return timeStamping.toString(); // Returns date and time as a string
    }
    
    /**
     * Gets the staff member logged to identify who creates or modifies the report.
     * 
     * @return the staff id
     */
    public Staff getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the staff member who creates or modifies the report.
     *
     */
    public void setCreatedBy(Staff createdBy) {
        this.createdBy = createdBy;
    }
}
