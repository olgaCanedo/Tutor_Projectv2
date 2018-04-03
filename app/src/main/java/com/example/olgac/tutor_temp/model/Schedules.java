package com.example.olgac.tutor_temp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by olgac on 3/28/2018.
 */
@Entity
public class Schedules {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int IDSchedule;
    private int IDTutor;
    private String day;
    private int hourStart;
    private int hourEnd;
    private int minStart;
    private int minEnd;
    private String roomNumber;

    public Schedules(int IDTutor, String day, int hourStart, int hourEnd, int minStart, int minEnd, String roomNumber) {
        this.IDTutor = IDTutor;
        this.day = day;
        this.hourStart = hourStart;
        this.hourEnd = hourEnd;
        this.minStart = minStart;
        this.minEnd = minEnd;
        this.roomNumber = roomNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    @NonNull
    public int getIDSchedule() {
        return IDSchedule;
    }

    public void setIDSchedule(@NonNull int IDSchedule) {
        this.IDSchedule = IDSchedule;
    }

    public int getIDTutor() {
        return IDTutor;
    }

    public void setIDTutor(int IDTutor) {
        this.IDTutor = IDTutor;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getHourStart() {
        return hourStart;
    }

    public void setHourStart(int hourStart) {
        this.hourStart = hourStart;
    }

    public int getHourEnd() {
        return hourEnd;
    }

    public void setHourEnd(int hourEnd) {
        this.hourEnd = hourEnd;
    }

    public int getMinStart() {
        return minStart;
    }

    public void setMinStart(int minStart) {
        this.minStart = minStart;
    }

    public int getMinEnd() {
        return minEnd;
    }

    public void setMinEnd(int minEnd) {
        this.minEnd = minEnd;
    }

    @Override
    public String toString() {
        return "Schedules{" +
                "IDSchedule=" + IDSchedule +
                ", IDTutor=" + IDTutor +
                ", day='" + day + '\'' +
                ", hourStart=" + hourStart +
                ", hourEnd=" + hourEnd +
                ", minStart=" + minStart +
                ", minEnd=" + minEnd +
                ", roomNumber='" + roomNumber + '\'' +
                '}';
    }
}
