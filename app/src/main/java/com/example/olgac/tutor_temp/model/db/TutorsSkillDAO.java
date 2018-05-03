package com.example.olgac.tutor_temp.model.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.olgac.tutor_temp.model.TutorsSkill;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface TutorsSkillDAO {
    @Query("SELECT * FROM TutorsSkill WHERE IDTutor = :idTutor")
    List<TutorsSkill> findAllTutorsSync(long idTutor);

    @Query("SELECT * FROM TutorsSkill WHERE IDSkill = :idSkill")
    List<TutorsSkill> findAllTutorsBySkillSync(int idSkill);

    @Insert(onConflict = IGNORE)
    void insertTutorSkills(TutorsSkill tutorsSkill);

    @Query("DELETE FROM TutorsSkill WHERE IDTutor = :tutorID")
    void deleteTutorWithSkill(long tutorID);

}
