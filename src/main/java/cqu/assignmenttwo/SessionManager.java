/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cqu.assignmenttwo;

/**
 *
 * @author AndresPinilla - 12243141
 * 
 * This class is to manage the sessions and track the staff members who creates
 * and modifies the reports.
 */
public class SessionManager {
    
    // Attributes
    private static SessionManager instance;
    private Staff loggedInUser;

    private SessionManager() { }

    // Stores the user logged in 
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    //Getters and Setters
    public void setLoggedInUser(Staff user) {
        this.loggedInUser = user;
    }

    public Staff getLoggedInUser() {
        return loggedInUser;
    }

    // Clear the session to track the new user who loggs in.
    public void clearSession() {
        loggedInUser = null;
    }
}

