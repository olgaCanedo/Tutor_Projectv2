package com.example.olgac.tutor_temp.model.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.olgac.tutor_temp.model.User;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by olgac on 3/28/2018.
 */
@Dao
public interface UserDAO {

    @Query("SELECT * FROM User")
    List<User> findAllUserSync();

    @Query("SELECT * FROM User WHERE IDUser = :ID")
    User loadUserByID(int ID);

    @Query("SELECT * FROM User WHERE nameU LIKE :name")
    User findByName(String name);

    @Query("DELETE FROM User WHERE IDUser LIKE :idUser")
    void deleteUser(int idUser);

    @Query("DELETE FROM User")
    void deleteAll();

    @Insert(onConflict = IGNORE)
    void insertUser(User user);

    @Update(onConflict = REPLACE)
    void updateUser(User user);
}
