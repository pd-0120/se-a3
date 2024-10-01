package cqu.assignmenttwo;

import jakarta.persistence.Entity;
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
@NamedQuery(name = "getAllNotifications", query = "SELECT n from NotificationAlert n")
public class NotificationAlert implements Serializable {

    //Attributes
    private Long disasterId;
    private String disasterDate;
    private String typeOfDisaster;
    private String disasterLocation;
    private String disasterDescription;
    private String levelOfPriority;

    //Constructor
    public NotificationAlert(Long disasterId, String disasterDate,
            String typeOfDisaster, String disasterLocation,
            String disasterDescription, String levelOfPriority) {
        this.disasterId = disasterId;
        this.disasterDate = disasterDate;
        this.typeOfDisaster = typeOfDisaster;
        this.disasterLocation = disasterLocation;
        this.disasterDescription = disasterDescription;
        this.levelOfPriority = levelOfPriority;
    }

    public NotificationAlert() {
    }
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
     * Converts the notification alert details to a CSV formatted string.
     *
     * @return a new string for each new notification alert.
     */
    public String toCsvStringNotificationAlert() {
        StringJoiner joiner = new StringJoiner(",");
        // joiner.add(getDisasterId())
        //         .add(getDisasterDate())
        //         .add(getTypeOfDisaster())
        //         .add(getDisasterLocation())
        //         .add(getDisasterDescription())
        //         .add(getLevelOfPriority());
        return joiner.toString();
    }

    /**
     * Gets the CSV header for the notification alert information.
     *
     * @return The CSV header.
     */
    public String getCsvNotificationAlert() {

        return "DisasterId,DisasterDate,TypeOfDisaster,DisasterLocation,"
                + "DisasterDescription,LevelOfPriority";
    }

}
