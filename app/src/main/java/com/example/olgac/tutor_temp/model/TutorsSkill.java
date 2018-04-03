package com.example.olgac.tutor_temp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by olgac on 3/28/2018.
 */
@Entity
public class TutorsSkill {
    @PrimaryKey
    @NonNull
    private int IDTutor;
    private int IDSkill;

    public TutorsSkill(@NonNull int IDTutor, @NonNull int IDSkill) {
        this.IDTutor = IDTutor;
        this.IDSkill = IDSkill;
    }

    @NonNull
    public int getIDTutor() {
        return IDTutor;
    }

    public void setIDTutor(@NonNull int IDTutor) {
        this.IDTutor = IDTutor;
    }

    @NonNull
    public int getIDSkill() {
        return IDSkill;
    }

    public void setIDSkill(@NonNull int IDSkill) {
        this.IDSkill = IDSkill;
    }

    @Override
    public String toString() {
        return "TutorsSkill{" +
                "IDTutor=" + IDTutor +
                ", IDSkill=" + IDSkill +
                '}';
    }
}
