package com.example.olgac.tutor_temp.model.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.example.olgac.tutor_temp.model.Campus;
import com.example.olgac.tutor_temp.model.CampusSubject;
import com.example.olgac.tutor_temp.model.Schedules;
import com.example.olgac.tutor_temp.model.Skills;
import com.example.olgac.tutor_temp.model.Subjects;
import com.example.olgac.tutor_temp.model.Tutor;
import com.example.olgac.tutor_temp.model.TutorsSkill;
import com.example.olgac.tutor_temp.model.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


@Database(entities = {
        Tutor.class,
        User.class,
        Campus.class,
        Subjects.class,
        Skills.class,
        Schedules.class,
        CampusSubject.class,
        TutorsSkill.class},
        version = 13,
        exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {
    private static final String TAG = "--AppDatabase";
    private static final String DATABASE_NAME = "TutorsDB.db";

    private static AppDatabase INSTANCE;

    public abstract SkillsDAO skillsModel();

    public abstract SubjectDAO subjectModel();

    public abstract CampusDAO campusModel();

    public abstract TutorDAO tutorModel();

    public abstract UserDAO userModel();

    public abstract CampusSubjectDAO campusSubjectModel();

    public abstract TutorsSkillDAO tutorsSkillModel();

    public abstract ScheduleDAO scheduleModel();


    public static AppDatabase getInstance(Context context){
        if (INSTANCE == null){

            final File dbPath = context.getDatabasePath(DATABASE_NAME);

            if (!dbPath.exists()) {// If the database file does not exist

                // Make sure we have a path to the file
                dbPath.getParentFile().mkdirs();
                copyPrePopulatedDb(dbPath, DATABASE_NAME, context);
            }

            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();

        }
        return INSTANCE;
    }

    public static void destroy(){
        INSTANCE = null;
    }

    private static void copyPrePopulatedDb(File dbPath, String databaseName, Context context){

        try {
            final InputStream inputStream = context.getAssets().open(databaseName);
            final OutputStream output = new FileOutputStream(dbPath);

            byte[] buffer = new byte[8192];
            int length;

            while ((length = inputStream.read(buffer, 0, 8192)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            inputStream.close();
        }
        catch (IOException e) {
            Log.d(TAG, "Failed to open file", e);
            e.printStackTrace();
        }

    }


}
