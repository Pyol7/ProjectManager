package com.jeffreyromero.projectmanager.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.jeffreyromero.projectmanager.db.ItemTypeRepository;
import com.jeffreyromero.projectmanager.models.ItemType;
import java.util.List;

public class ItemTypeViewModel extends AndroidViewModel {

    private ItemTypeRepository itemTypeRepository;

    public ItemTypeViewModel(@NonNull Application application) {
        super(application);
        this.itemTypeRepository = new ItemTypeRepository(application);
    }

    public LiveData<List<ItemType>> getAll(){
        return itemTypeRepository.getAll();
    }

    public LiveData<ItemType> getById(int id) {
        return itemTypeRepository.getById(id);
    }

    public void insert(ItemType itemType){
        itemTypeRepository.insert(itemType);
    }

    public void update(ItemType itemType){
        itemTypeRepository.update(itemType);
    }

    public void delete(ItemType itemType) {
        itemTypeRepository.delete(itemType);
    }

}
