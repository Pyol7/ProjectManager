package com.jeffreyromero.projectmanager.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jeffreyromero.projectmanager.models.Project;

import java.util.List;

/**
 * Room generates the code for all the methods.
 */
@Dao
public interface ProjectDao {

    @Query("SELECT * FROM project_table ORDER BY dateCreated ASC")
    LiveData<List<Project>> getAllProjects();

    @Query("SELECT * FROM project_table WHERE id = :id")
    LiveData<Project> getById(int id);

    @Query("SELECT * FROM project_table WHERE name = :name")
    LiveData<Project> getByName(String name);

    @Insert
    long insert(Project project);

    @Update
    void update(Project project);

    @Delete
    void delete(Project project);

}

