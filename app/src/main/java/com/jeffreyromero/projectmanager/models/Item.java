package com.jeffreyromero.projectmanager.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.jeffreyromero.projectmanager.models.itemTypes.ItemType;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "item_table",
        foreignKeys = {
        @ForeignKey(
                entity = Project.class,
                parentColumns = "id",
                childColumns = "projectID",
                onDelete = CASCADE
        ),
        @ForeignKey(
                entity = ItemType.class,
                parentColumns = "id",
                childColumns = "itemTypeID",
                onDelete = CASCADE
        )},
        indices = {
                @Index(value = "projectID"),
                @Index(value = "itemTypeID")
        }
)
public class Item {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private double width;
    private double length;
    private double height;
    private int itemTypeID;
    private int projectID;

    public Item(int itemTypeID, String name, int  projectID) {
        this.itemTypeID = itemTypeID;
        this.name = name;
        this.projectID = projectID;
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

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getItemTypeID() {
        return itemTypeID;
    }

    public void setItemTypeID(int itemTypeID) {
        this.itemTypeID = itemTypeID;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

}
