package com.jeffreyromero.projectmanager.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jeffreyromero.projectmanager.db.ItemDao;
import com.jeffreyromero.projectmanager.db.ItemRepository;
import com.jeffreyromero.projectmanager.models.Item;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {

    private ItemRepository itemRepository;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        this.itemRepository = new ItemRepository(application);
    }

    public LiveData<List<Item>> getAll(){
        return itemRepository.getAll();
    }

    public LiveData<Item> getById(int id) {
        return itemRepository.getById(id);
    }

    public LiveData<List<Item>> getByProjectId(int projectId) {
        return itemRepository.getByProjectId(projectId);
    }

    public void insert(Item item){
        itemRepository.insert(item);
    }

    public void update(Item item){
        itemRepository.update(item);
    }

    public void delete(Item item) {
        itemRepository.delete(item);
    }

}
