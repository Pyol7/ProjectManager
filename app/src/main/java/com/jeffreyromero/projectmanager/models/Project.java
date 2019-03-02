package com.jeffreyromero.projectmanager.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "project_table")
public class Project {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private Date dateCreated;

    public Project(String name) {
        this.name = name;
        setDateCreated(new Date());
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public String getDateCreatedAsString() {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        return df.format(getDateCreated());
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

}
