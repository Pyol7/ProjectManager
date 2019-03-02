package com.jeffreyromero.projectmanager.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jeffreyromero.projectmanager.db.ItemTypeRepository;
import com.jeffreyromero.projectmanager.models.itemTypes.ItemType;

import java.util.List;

public class ItemTypesViewModel extends AndroidViewModel {

    private ItemTypeRepository itemTypeRepository;

    public ItemTypesViewModel(@NonNull Application application) {
        super(application);
        this.itemTypeRepository = new ItemTypeRepository(application);
    }

    public LiveData<List<ItemType>> getAllItemTypes(){
        return itemTypeRepository.getAllItemTypes();
    }

    public LiveData<ItemType> getById(int id) {
        return itemTypeRepository.getById(id);
    }

    public void insert(ItemType task){
        itemTypeRepository.insert(task);
    }

    public void update(ItemType task){
        itemTypeRepository.update(task);
    }

    public void delete(ItemType task) {
        itemTypeRepository.delete(task);
    }

}
