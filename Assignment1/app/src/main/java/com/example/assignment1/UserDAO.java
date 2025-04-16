package com.example.assignment1;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    void insert(User user);

    @Query("SELECT * FROM User")
    List<User> getAllUsers();




    @Query("SELECT * FROM User WHERE username = :username LIMIT 1")
    User findByUsername(String username);

    @Delete
    void delete(User user);

}
