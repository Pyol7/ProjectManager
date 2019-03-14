package com.jeffreyromero.projectmanager.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "item_table",
        foreignKeys = {
                @ForeignKey(
                        entity = Project.class,
                        parentColumns = "id",
                        childColumns = "projectId",
                        onDelete = CASCADE
                ),
                @ForeignKey(
                        entity = ItemType.class,
                        parentColumns = "id",
                        childColumns = "itemTypeId",
                        onDelete = CASCADE
                )},
        indices = {
                @Index(value = "projectId"),
                @Index(value = "itemTypeId")
        }
)
public class Item {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private double width;
    private double length;
    private double height;
    // Each item belongs to a project
    private int projectId;
    // Each item has an itemType
    private int itemTypeId;

    public Item(int itemTypeId, int projectId) {
        this.itemTypeId = itemTypeId;
        this.projectId = projectId;
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

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(int itemTypeId) {
        this.itemTypeId = itemTypeId;
    }
}
