package cqu.assignmenttwo;

import java.time.LocalDate;
import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.NamedQuery;
import java.util.StringJoiner;

/**
 *
 * @author PJ
 */
@Entity
@NamedQuery(name = "getAllDisasterEvents", query = "SELECT d from DisasterEvent d")
public class DisasterEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    @jakarta.persistence.Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Long id;
    private Long disasterId;
//    @Temporal(TemporalType.DATE)
    private LocalDate disasterDate;
    private String reporterName;
    private int reporterMobile;
    private String reporterAddress;
    private String disasterLocation;
    @Enumerated(EnumType.STRING)
    private TypeOfDisaster typeOfDisaster;
    private String disasterDescription;

    public DisasterEvent() {
    }

    public DisasterEvent(String reporterName, int reporterMobile,
            String reporterAddress,
            LocalDate disasterDate, TypeOfDisaster typeOfDisaster,
            String disasterLocation, String disasterDescription) {
        this.disasterDate = disasterDate;
        this.reporterName = reporterName;
        this.reporterMobile = reporterMobile;
        this.reporterAddress = reporterAddress;
        this.disasterLocation = disasterLocation;
        this.typeOfDisaster = typeOfDisaster;
        this.disasterDescription = disasterDescription;
    }

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

    // Getters and Setters
    /**
     * Gets the disaster id associated with the disaster event.
     *
     * @return The disaster id in String format.
     */
    public Long getDisasterId() {
        return id;
    }

    /**
     * Sets the disaster id associated with the disaster event.
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
     * Converts the disaster event details to a CSV formatted string.
     *
     * @return a new string for each new disaster event.
     */
    public String toCsvStringDisasterEvent() {
        StringJoiner joiner = new StringJoiner(",");
        joiner.add(getReporterName())
                .add(Integer.toString(getReporterMobile()))
                .add(getReporterAddress())
                .add(getId().toString())
                .add(getDisasterDate())
                .add(getTypeOfDisasterAsString())
                .add(getDisasterLocation())
                .add(getDisasterDescription());
        return joiner.toString();
    }

    /**
     * Gets the CSV header for the disaster event information.
     *
     * @return The CSV header.
     */
    public String getCsvDisasterHeader() {

        return "ReporterName,ReporterMobile,ReporterAddress,DisasterId,"
                + "DisasterDate,TypeOfDisaster,DisasterLocation,"
                + "DisasterDescription";
    }
}
