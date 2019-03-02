package com.jeffreyromero.projectmanager.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jeffreyromero.projectmanager.models.Item;

import java.util.List;

/**
 * Room generates the code for all the methods.
 */
@Dao
public interface ItemDao {

    @Query("SELECT * FROM item_table ORDER BY id ASC")
    LiveData<List<Item>> getAllItems();

    @Query("SELECT * FROM item_table WHERE id = :id")
    LiveData<Item> getById(int id);

    @Query("SELECT * FROM item_table WHERE projectID = :projectID")
    LiveData<List<Item>> getItemsForProject(int projectID);

    @Insert
    void insert(Item item);

    @Update
    void update(Item item);

    @Delete
    void delete(Item item);

}

