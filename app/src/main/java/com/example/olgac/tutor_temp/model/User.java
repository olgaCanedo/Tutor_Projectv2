package com.example.olgac.tutor_temp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by olgac on 3/28/2018.
 */
@Entity (tableName = "user")
public class User implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    //@NonNull
    public int IDUser;
    private String nameU;
    private String password;

    public User( String nameU, String password) {
        //this.IDUser = IDUser;
        this.nameU = nameU;
        this.password = password;
    }

    protected User(Parcel in) {
        IDUser = in.readInt();
        nameU = in.readString();
        password = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @NonNull
    public int getIDUser() {
        return IDUser;
    }

    public void setIDUser(@NonNull int IDUser) {
        this.IDUser = IDUser;
    }

    public String getNameU() {
        return nameU;
    }

    public void setNameU(String nameU) {
        this.nameU = nameU;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "IDUser=" + IDUser +
                ", nameU='" + nameU + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(IDUser);
        dest.writeString(nameU);
        dest.writeString(password);
    }
}