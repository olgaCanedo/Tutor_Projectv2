package com.example.olgac.tutor_temp.model.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.olgac.tutor_temp.model.Skills;
import com.example.olgac.tutor_temp.model.Tutor;
import com.example.olgac.tutor_temp.model.TutorsSkill;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by olgac on 3/28/2018.
 */
@Dao
public interface TutorDAO {

    @Query("SELECT * FROM Tutor")
    List<Tutor> findAllTutorSync();

    @Query("SELECT * FROM Tutor WHERE subjectID = :idSubject")
    List<Tutor> findTutorBySubject(int idSubject);



    @Query("SELECT * FROM Tutor WHERE tutorId = :tutorID")
    Tutor loadTutorByTutorID(long tutorID);

    @Query("SELECT * FROM Skills " +
            " INNER JOIN TutorsSkill ON Skills.IDSkill = TutorsSkill.IDSkill" +
            " INNER JOIN Tutor ON Tutor.tutorId = TutorsSkill.IDTutor" +
            " WHERE Tutor.tutorId = :tutorID")
    List<Skills> getSkillsByTutorID(long tutorID);

    @Query("DELETE FROM Tutor WHERE tutorId = :tutorID")
    void deleteTutor(long tutorID);

    @Query("DELETE FROM Tutor")
    void deleteAll();

    @Insert(onConflict = IGNORE)
    void insertTutor(Tutor tutor);

    @Insert(onConflict = IGNORE)
    void insertSkillTutor(TutorsSkill tutorsSkill);

    @Update(onConflict = REPLACE)
    void updateTutor(Tutor tutor);

}
