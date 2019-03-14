package com.jeffreyromero.projectmanager.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jeffreyromero.projectmanager.models.Material;

import java.util.List;

/**
 * Room generates the code for all the methods.
 */
@Dao
public interface MaterialDao {

    @Query("SELECT * FROM material_table ORDER BY id ASC")
    LiveData<List<Material>> getAll();

    @Query("SELECT * FROM material_table WHERE id = :id")
    LiveData<Material> getById(int id);

    @Insert
    long insert(Material material);

    @Update
    void update(Material material);

    @Delete
    void delete(Material material);

}

