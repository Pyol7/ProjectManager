package com.jeffreyromero.projectmanager.models.itemTypes;

import androidx.room.Entity;

import com.jeffreyromero.projectmanager.models.Item;

@Entity
public class DroppedCeiling extends Item {
    public DroppedCeiling(String name, int projectID) {
        super(1, name, projectID);
    }
}
