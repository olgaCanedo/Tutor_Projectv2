package com.example.olgac.tutor_temp.model.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.olgac.tutor_temp.model.Subjects;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by olgac on 3/28/2018.
 */
@Dao

public interface SubjectDAO {
    @Query("SELECT * FROM Subjects")
    List<Subjects> findAllSubjectsSync();

    @Query("SELECT * FROM Subjects WHERE IDSubject = :ID")
    Subjects loadSubjectsByID(int ID);

    @Query("DELETE FROM Subjects")
    void deleteAll();

    @Insert(onConflict = IGNORE)
    void insertSubjects(Subjects subjects);

    @Update(onConflict = REPLACE)
    void updateSubjects(Subjects subjects);
}

