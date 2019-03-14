package com.jeffreyromero.projectmanager.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.jeffreyromero.projectmanager.models.ItemTypeMaterialJoin;
import com.jeffreyromero.projectmanager.models.Material;

import java.util.List;

@Dao
public interface ItemTypeMaterialJoinDao {
    @Query("SELECT * FROM material_table INNER JOIN item_type_material_join_table ON " +
            "material_table.id = item_type_material_join_table.materialId WHERE " +
            ":itemTypeId = item_type_material_join_table.itemTypeId")
    LiveData<List<Material>> getMaterialsForItemType(final int itemTypeId);

    @Insert
    long insert(ItemTypeMaterialJoin itemTypeMaterialJoin);
}
