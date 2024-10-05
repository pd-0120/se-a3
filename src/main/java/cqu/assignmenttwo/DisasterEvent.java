package cqu.assignmenttwo;

import java.time.LocalDate;
import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.NamedQuery;
import java.time.LocalDateTime;

/**
 *
 * @author PJ - Andres Pinilla
 * 
 * This class is to stored the disaster events objects. It has getters and setters
 * to access the different types of data stored and queries to interact with the
 * database.
 */
@Entity
@NamedQuery(name = "getAllDisasterEvents", query = "SELECT d from DisasterEvent d")
public class DisasterEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    @jakarta.persistence.Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Long id;
//    @Temporal(TemporalType.DATE)
    private LocalDate disasterDate;
    private String reporterName;
    private int reporterMobile;
    private String reporterAddress;
    private String disasterLocation;
    @Enumerated(EnumType.STRING)
    private TypeOfDisaster typeOfDisaster;
    private String disasterDescription;
    private LocalDateTime timeStamping;

    public DisasterEvent() {
    }

    public DisasterEvent(String reporterName, int reporterMobile,
            String reporterAddress, LocalDate disasterDate, 
            TypeOfDisaster typeOfDisaster, String disasterLocation, 
            String disasterDescription, LocalDateTime timeStamping) {
        this.disasterDate = disasterDate;
        this.reporterName = reporterName;
        this.reporterMobile = reporterMobile;
        this.reporterAddress = reporterAddress;
        this.disasterLocation = disasterLocation;
        this.typeOfDisaster = typeOfDisaster;
        this.disasterDescription = disasterDescription;
        this.timeStamping = LocalDateTime.now();
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
        if (!(object instanceof DisasterEvent)) {
            return false;
        }
        DisasterEvent other = (DisasterEvent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cqu.assignmenttwo.DisasterEvent[ id=" + id + " ]";
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Gets the disaster date as a string.
     *
     * @return The incident date in string format.
     */
    public String getDisasterDate() {
        return disasterDate.toString();
    }

    /**
     * Sets the disaster date associated with the disaster event.
     *
     */
    public void setDisasterDate(LocalDate disasterDate) {
        this.disasterDate = disasterDate;
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
     * Gets the reporter mobile associated with the disaster event.
     *
     * @return The reporter mobile in String format.
     */
    public int getReporterMobile() {
        return reporterMobile;
    }

    /**
     * Sets the reporter mobile associated with the disaster event.
     *
     */
    public void setReporterMobile(int reporterMobile) {
        this.reporterMobile = reporterMobile;
    }

    /**
     * Gets the reporter address associated with the disaster event.
     *
     * @return The reporter address in String format.
     */
    public String getReporterAddress() {
        return reporterAddress;
    }

    /**
     * Sets the reporter address associated with the disaster event.
     *
     */
    public void setReporterAddress(String reporterAddress) {
        this.reporterAddress = reporterAddress;
    }

    /**
     * Gets the disaster location associated with the disaster event.
     *
     * @return The disaster location in String format.
     */
    public String getDisasterLocation() {
        return disasterLocation;
    }

    /**
     * Sets the disaster location associated with the disaster event.
     *
     */
    public void setDisasterLocation(String disasterLocation) {
        this.disasterLocation = disasterLocation;
    }

    /**
     * Gets the type of disaster associated with the disaster event.
     *
     * @return The type of disaster in String format.
     */
    public String getTypeOfDisasterAsString() {
        return typeOfDisaster.toString();
    }

    /**
     * Sets the type of disaster associated with the disaster event.
     *
     */
    public void setTypeOfDisaster(TypeOfDisaster typeOfDisaster) {
        this.typeOfDisaster = typeOfDisaster;
    }

    /**
     * Gets the disaster description associated with the disaster event.
     *
     * @return The disaster description in String format.
     */
    public String getDisasterDescription() {
        return disasterDescription;
    }

    /**
     * Sets the disaster description associated with the disaster event.
     *
     */
    public void setDisasterDescription(String disasterDescription) {
        this.disasterDescription = disasterDescription;
    }
    
    /**
     * Gets the date and time when disaster was registered.
     *
     * @return The date and time when the disaster was registered in String format.
     */
    public String getTimeStamping() {
        return timeStamping.toString(); // Returns date and time as a string
    }
}
