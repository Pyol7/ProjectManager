package com.jeffreyromero.projectmanager.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "item_type_table")
public class ItemType {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

    public ItemType(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
