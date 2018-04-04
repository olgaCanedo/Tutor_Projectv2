package com.example.olgac.tutor_temp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by olgac on 4/4/2018.
 */
@Entity
public class Lab {

    @PrimaryKey (autoGenerate = true)
    private int labID;
    private String labName;
    private int campusID;
    private String labLocation;
    private String phoneNumber;
    private int IDSubject;

    public Lab(int labID, String labName, int campusID, String labLocation, String phoneNumber, int IDSubject) {
        this.labID = labID;
        this.labName = labName;
        this.campusID = campusID;
        this.labLocation = labLocation;
        this.phoneNumber = phoneNumber;
        this.IDSubject = IDSubject;
    }

    public int getLabID() {
        return labID;
    }

    public void setLabID(int labID) {
        this.labID = labID;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public int getCampusID() {
        return campusID;
    }

    public void setCampusID(int campusID) {
        this.campusID = campusID;
    }

    public String getLabLocation() {
        return labLocation;
    }

    public void setLabLocation(String labLocation) {
        this.labLocation = labLocation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getIDSubject() {
        return IDSubject;
    }

    public void setIDSubject(int IDSubject) {
        this.IDSubject = IDSubject;
    }

    @Override
    public String toString() {
        return "Lab{" +
                "labID=" + labID +
                ", labName='" + labName + '\'' +
                ", campusID=" + campusID +
                ", labLocation='" + labLocation + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", IDSubject=" + IDSubject +
                '}';
    }
}
