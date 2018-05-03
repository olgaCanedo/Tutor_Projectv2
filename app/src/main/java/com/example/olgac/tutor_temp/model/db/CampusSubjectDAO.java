package com.example.olgac.tutor_temp.model.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.olgac.tutor_temp.model.CampusSubject;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface CampusSubjectDAO {
    @Query("SELECT * FROM CampusSubject WHERE IDCampus = :IDCam")
    List<CampusSubject> findAllCampusSync(int IDCam);

    @Query("DELETE FROM CampusSubject WHERE IDCampus = :idCampus")
    void deleteCampusSubject(int idCampus);

    @Query("DELETE FROM CampusSubject WHERE IDSubject = :idSubject")
    void deleteAllBySubjectID(int idSubject);

    @Update(onConflict = REPLACE)
    void updateCampusSubjects(CampusSubject campusSubject);

}
