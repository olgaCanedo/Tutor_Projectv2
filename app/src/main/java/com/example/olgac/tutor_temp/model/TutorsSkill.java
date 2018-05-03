package com.example.olgac.tutor_temp.model;

import android.arch.persistence.room.Entity;

/**
 * Created by olgac on 3/28/2018.
 */
@Entity (primaryKeys={"IDTutor","IDSkill"})
public class TutorsSkill {

    private long IDTutor;
    private int IDSkill;

    public TutorsSkill(long IDTutor, int IDSkill) {
        this.IDTutor = IDTutor;
        this.IDSkill = IDSkill;
    }

    public long getIDTutor() {
        return IDTutor;
    }

    public void setIDTutor(long IDTutor) {
        this.IDTutor = IDTutor;
    }

    public int getIDSkill() {
        return IDSkill;
    }

    public void setIDSkill(int IDSkill) {
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
