package com.jeffreyromero.projectmanager.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jeffreyromero.projectmanager.models.itemTypes.ItemType;

import java.util.List;

/**
 * Room generates the code for all the methods.
 */
@Dao
public interface ItemTypeDao {

    @Query("SELECT * FROM item_type_table ORDER BY id ASC")
    LiveData<List<ItemType>> getAllItemTypes();

    @Query("SELECT * FROM item_type_table WHERE id = :id")
    LiveData<ItemType> getById(int id);

    @Insert
    void insert(ItemType itemType);

    @Update
    void update(ItemType itemType);

    @Delete
    void delete(ItemType itemType);

}

