package com.example.olgac.tutor_temp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

/**
 * Created by olgac on 3/28/2018.
 */
@Entity (primaryKeys={"IDCampus","IDSubject"},
    foreignKeys = {
            @ForeignKey(entity = Campus.class,
                    parentColumns = "IDCampus",//Name in the table
                    childColumns = "IDCampus"),//Name in the join table
            @ForeignKey(entity = Subjects.class,
                    parentColumns = "IDSubject",//Name in the table
                    childColumns = "IDSubject")//Name in the join table
    })
public class CampusSubject {

    private int IDCampus;
    private int IDSubject;

    public CampusSubject(int IDCampus, int IDSubject) {
        this.IDCampus = IDCampus;
        this.IDSubject = IDSubject;
    }

    public int getIDCampus() {
        return IDCampus;
    }

    public void setIDCampus(int IDCampus) {
        this.IDCampus = IDCampus;
    }

    public int getIDSubject() {
        return IDSubject;
    }

    public void setIDSubject(int IDSubject) {
        this.IDSubject = IDSubject;
    }

    @Override
    public String toString() {
        return "CampusSubject{" +
                "IDCampus=" + IDCampus +
                ", IDSubject=" + IDSubject +
                '}';
    }
}
