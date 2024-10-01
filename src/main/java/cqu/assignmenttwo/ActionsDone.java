/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cqu.assignmenttwo;

import java.util.StringJoiner;

/**
 *
 * @author AndresPinilla
 *
 * This class is to create the Actions Done objects. It has getters and setters
 * to access the different types of data. It has a string joiner method to
 * format the data to save it in the csv file. It has a method to set the header
 * of the data saved in the csv file.
 */
public class ActionsDone {

    //Attributes
    private String disasterId;
    private ResponderAuthority authorityRequired;
    private String actionsDone;
    private String actionsDoneReview;
    private String additionalActions;

    //Constructor
    public ActionsDone(String disasterId, ResponderAuthority authorityRequired,
                       String actionsDone, String actionsDoneReview,
                       String additionalActions) {
        this.disasterId = disasterId;
        this.authorityRequired = authorityRequired;
        this.actionsDone = actionsDone;
        this.actionsDoneReview = actionsDoneReview;
        this.additionalActions = additionalActions;
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
    public String getDisasterId() {
        return disasterId;
    }

    /**
     * Sets the disaster id associated with the actions done.
     *
     */
    public void setDisasterId(String disasterId) {
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
        joiner.add(getDisasterId())
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
