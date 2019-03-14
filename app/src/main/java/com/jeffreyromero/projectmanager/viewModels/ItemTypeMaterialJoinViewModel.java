package com.jeffreyromero.projectmanager.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jeffreyromero.projectmanager.db.ItemTypeMaterialJoinRepository;
import com.jeffreyromero.projectmanager.models.ItemTypeMaterialJoin;
import com.jeffreyromero.projectmanager.models.Material;

import java.util.List;

public class ItemTypeMaterialJoinViewModel extends AndroidViewModel {

    private ItemTypeMaterialJoinRepository repo;

    public ItemTypeMaterialJoinViewModel(@NonNull Application application) {
        super(application);
        this.repo = new ItemTypeMaterialJoinRepository(application);
    }

    public LiveData<List<Material>> getMaterialsForItemType(int itemTypeId) {
        return repo.getMaterialsForItemType(itemTypeId);
    }

    public void insert(ItemTypeMaterialJoin itemTypeMaterialJoin){
        repo.insert(itemTypeMaterialJoin);
    }
}
