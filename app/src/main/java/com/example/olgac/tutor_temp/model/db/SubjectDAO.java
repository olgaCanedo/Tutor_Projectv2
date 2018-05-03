package com.example.olgac.tutor_temp.model.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.olgac.tutor_temp.model.Campus;
import com.example.olgac.tutor_temp.model.CampusSubject;
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

    @Query("SELECT * FROM Subjects " +
            " INNER JOIN CampusSubject ON Subjects.IDSubject = CampusSubject.IDSubject" +
            " INNER JOIN Campus ON Campus.IDCampus = CampusSubject.IDCampus" +
            " WHERE Campus.IDCampus = :campusID")
    List<Subjects> loadSubjectsByCampusID(int campusID);

    @Query("SELECT * FROM Campus " +
            " INNER JOIN CampusSubject ON Campus.IDCampus = CampusSubject.IDCampus" +
            " INNER JOIN Subjects ON Subjects.IDSubject = CampusSubject.IDSubject" +
            " WHERE Subjects.IDSubject = :idSubject ")
    List<Campus> loadCampusBySubjectID(int idSubject);

    @Query("DELETE FROM Subjects WHERE IDSubject = :idSubject")
    void deleteSubject(int idSubject);

    @Delete
    void deleteSubjects(Subjects subjects);

    @Query("DELETE FROM Subjects")
    void deleteAll();

    @Insert(onConflict = IGNORE)
    void insertSubjects(Subjects subjects);

    @Insert(onConflict = IGNORE)
    void insertCampusSubjects(CampusSubject campusSubject);

    @Update(onConflict = REPLACE)
    void updateSubjects(Subjects subjects);
}

