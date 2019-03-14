package com.jeffreyromero.projectmanager.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

/**
 * Matches an ItemType with its list of Materials or
 * Matches a Material with different itemTypes.
 * <p>
 * Usage:
 * itemTypeId = insert item type;
 * materialId = insert material;
 * insert ItemTypeMaterialJoin(itemTypeId, materialId);
 */
@Entity(tableName = "item_type_material_join_table",
        primaryKeys = {"itemTypeId", "materialId"},
        foreignKeys = {
                @ForeignKey(entity = ItemType.class,
                        parentColumns = "id",
                        childColumns = "itemTypeId"),
                @ForeignKey(entity = Material.class,
                        parentColumns = "id",
                        childColumns = "materialId")
        },
        indices = {
                @Index(value = "itemTypeId"),
                @Index(value = "materialId")
        }
)
public class ItemTypeMaterialJoin {
    private int itemTypeId;
    private int materialId;

    public ItemTypeMaterialJoin(int itemTypeId, int materialId) {
        this.itemTypeId = itemTypeId;
        this.materialId = materialId;
    }

    public int getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(int itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }
}
