/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cqu.assignmenttwo;

import java.util.StringJoiner;

/**
 *
 * @author AndresPinilla 12243141
 *
 * This class is to create the Action Plan objects. It has getters and setters
 * to access the different types of data. It has a string joiner method to
 * format the data to save it in the csv file. It has a method to set the header
 * of the data saved in the csv file.
 */
public class ActionPlans {

    //Attributes
    private String disasterId;
    private String levelOfPriority;
    private ResponderAuthority authorityRequired;
    private String actionsRequired;
    private String planReview;
    private String planChanges;

    //Constructor
    public ActionPlans(String disasterId, String levelOfPriority,
                      ResponderAuthority authorityRequired,
                      String actionsRequired, String planReview,
                      String planChanges) {
        this.disasterId = disasterId;
        this.levelOfPriority = levelOfPriority;
        this.authorityRequired = authorityRequired;
        this.actionsRequired = actionsRequired;
        this.planReview = planReview;
        this.planChanges = planChanges;
    }

    //Getters and Setters
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
    public String getDisasterId() {
        return disasterId;
    }

    /**
     * Sets the disaster id associated with the action plan.
     *
     */
    public void setDisasterId(String disasterId) {
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
     * Converts the Action Plan details to a CSV formatted string.
     *
     * @return a new string for each new action plan.
     */
    public String toCsvStringActionPlan() {
        StringJoiner joiner = new StringJoiner(",");
        joiner.add(getDisasterId())
                .add(getLevelOfPriority())
                .add(getAuthorityRequired())
                .add(getActionsRequired())
                .add(getPlanReview())
                .add(getPlanChanges());
        return joiner.toString();
    }

    /**
     * Gets the CSV header for the action plan information.
     *
     * @return The CSV header.
     */
    public String getCsvActionPlan() {

        return "DisasterId,LevelOfPriority,AuthorityRequired,ActionsRequired,"
                + "ManagerReview,ChangesRequired";
    }
}
