package com.example.olgac.tutor_temp.model.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.olgac.tutor_temp.model.Schedules;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface ScheduleDAO {
    @Query("SELECT * FROM Schedules")
    List<Schedules> findAllSchedulesSync();

    @Query("SELECT * FROM Schedules WHERE IDTutor = :ID")
    List<Schedules> loadScheduleByIDTutor(long ID);

    @Insert(onConflict = IGNORE)
    void insertSchedules(Schedules schedules);

    @Query("DELETE FROM Schedules WHERE IDTutor = :tutorID")
    void deleteTutorWithSched(long tutorID);

}
