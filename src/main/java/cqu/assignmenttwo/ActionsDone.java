package cqu.assignmenttwo;

import java.io.Serializable;
import java.util.StringJoiner;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.NamedQuery;
import java.time.LocalDateTime;


/**
 *
 * @author PJ - Andres Pinilla
 * 
 * This class is to create the Actions Done objects. It has getters and setters
 * to access the different types of data. It validates the data to prevent 
 * duplicities and persist the objects to store the information in the database.
 */
@Entity
@NamedQuery(name = "getAllActionsDone", 
            query = "SELECT ac from ActionsDone ac")
@NamedQuery(name = "findRegisteredActionsDone", 
            query = "SELECT ac FROM ActionsDone ac WHERE ac.disasterId = :disasterId")
public class ActionsDone implements Serializable {

    private static final long serialVersionUID = 1L;
    @jakarta.persistence.Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Long id;
    //Attributes
    private Long disasterId;
    @Enumerated(EnumType.STRING)
    private ResponderAuthority authorityRequired;
    private String actionsDone;
    private String actionsDoneReview;
    private String additionalActions;
    private LocalDateTime timeStamping;
    private Staff createdBy;

    public ActionsDone() {
    }
    
    //Constructor
    public ActionsDone(Long disasterId, ResponderAuthority authorityRequired,
            String actionsDone, String actionsDoneReview,
            String additionalActions, LocalDateTime timeStamping, Staff createdBy) {
        this.disasterId = disasterId;
        this.authorityRequired = authorityRequired;
        this.actionsDone = actionsDone;
        this.actionsDoneReview = actionsDoneReview;
        this.additionalActions = additionalActions;
        this.timeStamping = LocalDateTime.now();
        this.createdBy = createdBy;
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
        if (!(object instanceof ActionsDone)) {
            return false;
        }
        ActionsDone other = (ActionsDone) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Gets the decision review by the disaster manager.
     *
     * @return The decision review by the manager in String format.
     */
    public String getActionsDoneReview() {
        return actionsDoneReview;
    }

    public void setActionsDoneReview(String actionsDoneReview) {
        this.actionsDoneReview = actionsDoneReview;
    }

    /**
     * Gets the disaster id associated with the actions done.
     *
     * @return The disaster id in String format.
     */
    public Long getDisasterId() {
        return disasterId;
    }

    /**
     * Sets the disaster id associated with the actions done.
     *
     */
    public void setDisasterId(Long disasterId) {
        this.disasterId = disasterId;
    }

    /**
     * Gets the authority required by the disaster assistant.
     *
     * @return The authority required in String format.
     */
    public String getAuthorityRequired() {
        return authorityRequired.toString();
    }

    /**
     * Sets the authority required in the actions done report.
     *
     */
    public void setAuthorityRequired(ResponderAuthority authorityRequired) {
        this.authorityRequired = authorityRequired;
    }

    /**
     * Gets the actions done by the emergency responder.
     *
     * @return The actions done in String format.
     */
    public String getActionsDone() {
        return actionsDone;
    }

    /**
     * Sets the actions done in the actions done report.
     *
     */
    public void setActionsDone(String actionsDone) {
        this.actionsDone = actionsDone;
    }

    /**
     * Gets the additional actions required by the disaster manager.
     *
     * @return The additional actions required by the manager in String format.
     */
    public String getAdditionalActions() {
        return additionalActions;
    }

    /**
     * Sets the additional actions required by the disaster manager.
     *
     */
    public void setAdditionalActions(String additionalActions) {
        this.additionalActions = additionalActions;
    }

    /**
     * Gets the date and time when the action plan was registered.
     *
     * @return The date and time when the action plan was registered in String format.
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
