package com.example.olgac.tutor_temp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by olgac on 3/28/2018.
 */
@Entity
public class Schedules implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int IDSchedule;
    private long IDTutor;
    private String day;
    private String hourStart;
    private String hourEnd;
    private String minStart;
    private String minEnd;
    private String roomNumber;

    public Schedules(long IDTutor, String day, String hourStart, String minStart, String hourEnd, String minEnd, String roomNumber) {
        this.IDTutor = IDTutor;
        this.day = day;
        this.hourStart = hourStart;
        this.minStart = minStart;
        this.hourEnd = hourEnd;
        this.minEnd = minEnd;
        this.roomNumber = roomNumber;
    }

    public Schedules(){}

    protected Schedules(Parcel in) {
        IDSchedule = in.readInt();
        IDTutor = in.readLong();
        day = in.readString();
        hourStart = in.readString();
        hourEnd = in.readString();
        minStart = in.readString();
        minEnd = in.readString();
        roomNumber = in.readString();
    }

    public static final Creator<Schedules> CREATOR = new Creator<Schedules>() {
        @Override
        public Schedules createFromParcel(Parcel in) {
            return new Schedules(in);
        }

        @Override
        public Schedules[] newArray(int size) {
            return new Schedules[size];
        }
    };

    @NonNull
    public int getIDSchedule() {
        return IDSchedule;
    }

    public void setIDSchedule(@NonNull int IDSchedule) {
        this.IDSchedule = IDSchedule;
    }

    public long getIDTutor() {
        return IDTutor;
    }

    public void setIDTutor(long IDTutor) {
        this.IDTutor = IDTutor;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHourStart() {
        return hourStart;
    }

    public void setHourStart(String hourStart) {
        this.hourStart = hourStart;
    }

    public String getHourEnd() {
        return hourEnd;
    }

    public void setHourEnd(String hourEnd) {
        this.hourEnd = hourEnd;
    }

    public String getMinStart() {
        return minStart;
    }

    public void setMinStart(String minStart) {
        this.minStart = minStart;
    }

    public String getMinEnd() {
        return minEnd;
    }

    public void setMinEnd(String minEnd) {
        this.minEnd = minEnd;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public String toString() {
        return "Schedules{" +
                "IDSchedule=" + IDSchedule +
                ", IDTutor=" + IDTutor +
                ", day='" + day + '\'' +
                ", hourStart='" + hourStart + '\'' +
                ", hourEnd='" + hourEnd + '\'' +
                ", minStart='" + minStart + '\'' +
                ", minEnd='" + minEnd + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(IDSchedule);
        dest.writeLong(IDTutor);
        dest.writeString(day);
        dest.writeString(hourStart);
        dest.writeString(hourEnd);
        dest.writeString(minStart);
        dest.writeString(minEnd);
        dest.writeString(roomNumber);
    }
}
