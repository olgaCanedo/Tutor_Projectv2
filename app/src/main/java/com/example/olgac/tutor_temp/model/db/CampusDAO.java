package com.example.olgac.tutor_temp.model.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.olgac.tutor_temp.model.Campus;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by olgac on 3/28/2018.
 */
@Dao
public interface CampusDAO {
    @Query("SELECT * FROM Campus")
    List<Campus> findAllCampusSync();

    @Query("SELECT * FROM Campus WHERE IDCampus = :ID")
    Campus loadCampusByID(int ID);

    @Query("DELETE FROM Campus WHERE IDCampus = :idCampus")
    void deleteCampus(int idCampus);

    @Query("DELETE FROM Campus")
    void deleteAll();

    @Insert(onConflict = IGNORE)
    void insertCampus(Campus campus);

    @Update(onConflict = REPLACE)
    void updateCampus(Campus campus);
}
