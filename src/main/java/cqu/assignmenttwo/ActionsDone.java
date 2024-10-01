package cqu.assignmenttwo;

import java.io.Serializable;
import java.util.StringJoiner;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.NamedQuery;


/**
 *
 * @author PJ
 */
@Entity
public class ActionsDone implements Serializable {

    private static final long serialVersionUID = 1L;
    @jakarta.persistence.Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Long id;
    //Attributes
    private Long disasterId;
    private ResponderAuthority authorityRequired;
    private String actionsDone;
    private String actionsDoneReview;
    private String additionalActions;

    public ActionsDone() {
    }
    
    //Constructor
    public ActionsDone(Long disasterId, ResponderAuthority authorityRequired,
            String actionsDone, String actionsDoneReview,
            String additionalActions) {
        this.disasterId = disasterId;
        this.authorityRequired = authorityRequired;
        this.actionsDone = actionsDone;
        this.actionsDoneReview = actionsDoneReview;
        this.additionalActions = additionalActions;
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
     * Converts the Actions Done details to a CSV formatted string.
     *
     * @return a new string for each new actions done report.
     */
    public String toCsvStringActionDone() {
        StringJoiner joiner = new StringJoiner(",");
        joiner.add(getDisasterId().toString())
                .add(getAuthorityRequired())
                .add(getActionsDone())
                .add(getActionsDoneReview())
                .add(getAdditionalActions());
        return joiner.toString();
    }

    /**
     * Gets the CSV header for the actions done information.
     *
     * @return The CSV header.
     */
    public String getCsvActionDone() {

        return "DisasterId,AuthorityRequired,ActionsDone,ManagerReview,"
                + "AdditionalActionsRequired";
    }

}
