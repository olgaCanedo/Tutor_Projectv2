package com.example.olgac.tutor_temp.model.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.olgac.tutor_temp.model.Skills;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by olgac on 3/28/2018.
 */
@Dao

public interface SkillsDAO {

    @Query("SELECT * FROM Skills")
    List<Skills> findAllSkillsSync();

    @Query("SELECT * FROM Skills WHERE IDSkill = :ID")
    Skills loadSkillsByID(int ID);

    @Query("SELECT * FROM Skills WHERE IDSubject = :idSubject")
    List<Skills> loadSkillsBySubjectID(int idSubject);



    @Query("DELETE FROM Skills")
    void deleteAll();

    @Query("DELETE FROM Skills WHERE IDSkill = :idSkill")
    void deleteSkill(int idSkill);

    @Insert(onConflict = IGNORE)
    void insertSkills(Skills skills);

    @Update(onConflict = REPLACE)
    void updateSkills(Skills skills);
}
