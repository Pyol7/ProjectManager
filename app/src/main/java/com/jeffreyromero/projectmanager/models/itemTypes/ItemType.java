package com.jeffreyromero.projectmanager.models.itemTypes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "item_type_table")
public class ItemType {
    @PrimaryKey
    private int id;
    private String name;

    public ItemType(int id, String name) {
        this.id = id;
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
