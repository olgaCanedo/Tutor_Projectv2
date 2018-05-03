package com.example.olgac.tutor_temp.model;

/**
 * Created by olgac on 3/28/2018.
 */

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Campus {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int IDCampus;
    private String nameC;
    private String phoneC;
    private String roomC;
    private String locationC;

    public Campus(String nameC, String phoneC, String roomC, String locationC) {
        this.nameC = nameC;
        this.phoneC = phoneC;
        this.roomC = roomC;
        this.locationC = locationC;
    }

    @NonNull
    public int getIDCampus() {
        return IDCampus;
    }

    public void setIDCampus(@NonNull int IDCampus) {
        this.IDCampus = IDCampus;
    }

    public String getNameC() {
        return nameC;
    }

    public void setNameC(String nameC) {
        this.nameC = nameC;
    }

    public String getPhoneC() {
        return phoneC;
    }

    public void setPhoneC(String phoneC) {
        this.phoneC = phoneC;
    }

    public String getRoomC() {
        return roomC;
    }

    public void setRoomC(String roomC) {
        this.roomC = roomC;
    }

    public String getLocationC() {
        return locationC;
    }

    public void setLocationC(String locationC) {
        this.locationC = locationC;
    }

    @Override
    public String toString() {
        return "Campus{" +
                "IDCampus=" + IDCampus +
                ", nameC='" + nameC + '\'' +
                ", phoneC='" + phoneC + '\'' +
                ", roomC='" + roomC + '\'' +
                ", locationC='" + locationC + '\'' +
                '}';
    }
}
