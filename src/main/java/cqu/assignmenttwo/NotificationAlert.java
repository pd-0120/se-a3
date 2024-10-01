/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cqu.assignmenttwo;

import java.time.LocalDate;
import java.util.StringJoiner;

/**
 *
 * @author AndresPinilla
 *
 * This class is to create the Notification Alert objects. It has getters and setters
 * to access the different types of data. It has a string joiner method to
 * format the data to save it in the csv file. It has a method to set the header
 * of the data saved in the csv file.
 */
public class NotificationAlert {
    
    //Attributes
    private String disasterId;
    private String disasterDate;
    private String typeOfDisaster;
    private String disasterLocation;
    private String disasterDescription;
    private String levelOfPriority;

    //Constructor
    public NotificationAlert(String disasterId, String disasterDate, 
                             String typeOfDisaster, String disasterLocation, 
                             String disasterDescription, String levelOfPriority) {
        this.disasterId = disasterId;
        this.disasterDate = disasterDate;
        this.typeOfDisaster = typeOfDisaster;
        this.disasterLocation = disasterLocation;
        this.disasterDescription = disasterDescription;
        this.levelOfPriority = levelOfPriority;
    }

    // Getters and Setters
    /**
     * Gets the disaster id associated with the notification alert.
     *
     * @return The disaster id in String format.
     */
    public String getDisasterId() {
        return disasterId;
    }

    /**
     * Sets the disaster id associated with the notification alert.
     *
     */
    public void setDisasterId(String disasterId) {
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
        joiner.add(getDisasterId())
                .add(getDisasterDate())
                .add(getTypeOfDisaster())
                .add(getDisasterLocation())
                .add(getDisasterDescription())
                .add(getLevelOfPriority());
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
