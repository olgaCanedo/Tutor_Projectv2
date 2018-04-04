package com.example.olgac.tutor_temp.model.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.olgac.tutor_temp.model.Lab;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by olgac on 4/4/2018.
 */
@Dao
public interface LabDAO {
    @Query("SELECT * FROM Lab")
    List<Lab> findAllLabsSync();

    @Query("SELECT * FROM Lab WHERE labID = :ID")
    Lab loadLabByID(int ID);

    @Query("DELETE FROM Lab")
    void deleteAll();

    @Insert(onConflict = IGNORE)
    void insertLab(Lab lab);

    @Update(onConflict = REPLACE)
    void updateLab(Lab lab);
}
