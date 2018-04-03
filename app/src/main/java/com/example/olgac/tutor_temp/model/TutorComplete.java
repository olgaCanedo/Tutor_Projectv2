package com.example.olgac.tutor_temp.model;

/**
 * Created by olgac on 3/28/2018.
 */

public class TutorComplete {
    private long tutorId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private int campusID;
    private int subjectID;
    private String skill;

    private int IDCampus;
    private String nameC;

    private int IDSubject;
    private  String nameS;

    public TutorComplete(long tutorId, String firstName, String lastName, String email, String phone, int campusID, int subjectID, String skill, int IDCampus, String nameC, int IDSubject, String nameS) {
        this.tutorId = tutorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.campusID = campusID;
        this.subjectID = subjectID;
        this.skill = skill;
        this.IDCampus = IDCampus;
        this.nameC = nameC;
        this.IDSubject = IDSubject;
        this.nameS = nameS;
    }

    public long getTutorId() {
        return tutorId;
    }

    public void setTutorId(long tutorId) {
        this.tutorId = tutorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCampusID() {
        return campusID;
    }

    public void setCampusID(int campusID) {
        this.campusID = campusID;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public int getIDCampus() {
        return IDCampus;
    }

    public void setIDCampus(int IDCampus) {
        this.IDCampus = IDCampus;
    }

    public String getNameC() {
        return nameC;
    }

    public void setNameC(String nameC) {
        this.nameC = nameC;
    }

    public int getIDSubject() {
        return IDSubject;
    }

    public void setIDSubject(int IDSubject) {
        this.IDSubject = IDSubject;
    }

    public String getNameS() {
        return nameS;
    }

    public void setNameS(String nameS) {
        this.nameS = nameS;
    }

    @Override
    public String toString() {
        return "TutorComplete{" +
                "tutorId=" + tutorId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", campusID=" + campusID +
                ", subjectID=" + subjectID +
                ", skill='" + skill + '\'' +
                ", IDCampus=" + IDCampus +
                ", nameC='" + nameC + '\'' +
                ", IDSubject=" + IDSubject +
                ", nameS='" + nameS + '\'' +
                '}';
    }
}
