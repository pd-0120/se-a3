package cqu.assignmenttwo;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.NamedQuery;
import java.time.LocalDateTime;
import java.util.StringJoiner;

/**
 *
 * @author PJ - Andres Pinilla
 * 
 * * This class is to create the Action Plan objects. It has getters and setters
 * to access the different types of data. It validates the data to prevent 
 * duplicities and persist the objects to store the information in the database.
 */
@Entity
@NamedQuery(name = "getAllActionPlans", 
            query = "SELECT a from ActionPlans a")
@NamedQuery(name = "findRegisteredActionPlans", 
            query = "SELECT a FROM ActionPlans a WHERE a.disasterId = :disasterId")
public class ActionPlans implements Serializable {

    private static final long serialVersionUID = 1L;
    @jakarta.persistence.Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Long id;

    //Attributes
    private Long disasterId;
    private String levelOfPriority;
    @Enumerated(EnumType.STRING)
    private ResponderAuthority authorityRequired;
    private String actionsRequired;
    private String planReview;
    private String planChanges;
    private LocalDateTime timeStamping;
    private Staff createdBy;

    //Constructors
    public ActionPlans() {
    }
    
    public ActionPlans(Long disasterId, String levelOfPriority,
            ResponderAuthority authorityRequired,
            String actionsRequired, String planReview,
            String planChanges, LocalDateTime timeStamping, Staff createdBy) {
        this.disasterId = disasterId;
        this.levelOfPriority = levelOfPriority;
        this.authorityRequired = authorityRequired;
        this.actionsRequired = actionsRequired;
        this.planReview = planReview;
        this.planChanges = planChanges;
        this.timeStamping = LocalDateTime.now();
        this.createdBy = createdBy;
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Gets the changes required by the disaster manager.
     *
     * @return The changes required by the manager in String format.
     */
    public String getPlanChanges() {
        return planChanges;
    }

    /**
     * Sets the changes required by the disaster manager.
     *
     */
    public void setPlanChanges(String planChanges) {
        this.planChanges = planChanges;
    }

    /**
     * Gets the disaster id associated with the action plan.
     *
     * @return The disaster id in String format.
     */
    public Long getDisasterId() {
        return disasterId;
    }

    /**
     * Sets the disaster id associated with the action plan.
     *
     */
    public void setDisasterId(Long disasterId) {
        this.disasterId = disasterId;
    }

    /**
     * Gets the level of priority determined by the disaster assistant.
     *
     * @return The level of priority in String format.
     */
    public String getLevelOfPriority() {
        return levelOfPriority;
    }

    /**
     * Sets the level of priority in the action plan.
     *
     */
    public void setLevelOfPriority(String levelOfPriority) {
        this.levelOfPriority = levelOfPriority;
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
     * Sets the authority required in the action plan.
     *
     */
    public void setAuthorityRequired(ResponderAuthority authorityRequired) {
        this.authorityRequired = authorityRequired;
    }

    /**
     * Gets the actions required by the disaster assistant.
     *
     * @return The actions required in String format.
     */
    public String getActionsRequired() {
        return actionsRequired;
    }

    /**
     * Sets the actions required in the action plan.
     *
     */
    public void setActionsRequired(String actionsRequired) {
        this.actionsRequired = actionsRequired;
    }

    /**
     * Gets the decision review by the disaster manager.
     *
     * @return The decision review in String format.
     */
    public String getPlanReview() {
        return planReview;
    }

    /**
     * Sets the decision review for the action plan.
     *
     */
    public void setPlanReview(String planReview) {
        this.planReview = planReview;
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
