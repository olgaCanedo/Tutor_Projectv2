package com.example.olgac.tutor_temp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by olgac on 3/28/2018.
 */
@Entity
public class CampusSubject {
    @PrimaryKey
    @NonNull
    private int IDCampus;
    private int IDSubject;

    public CampusSubject(@NonNull int IDCampus, @NonNull int IDSubject) {
        this.IDCampus = IDCampus;
        this.IDSubject = IDSubject;
    }

    @NonNull
    public int getIDCampus() {
        return IDCampus;
    }

    public void setIDCampus(@NonNull int IDCampus) {
        this.IDCampus = IDCampus;
    }

    @NonNull
    public int getIDSubject() {
        return IDSubject;
    }

    public void setIDSubject(@NonNull int IDSubject) {
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
