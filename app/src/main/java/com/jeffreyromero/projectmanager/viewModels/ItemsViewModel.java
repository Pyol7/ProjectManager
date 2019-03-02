package com.jeffreyromero.projectmanager.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jeffreyromero.projectmanager.db.ItemDao;
import com.jeffreyromero.projectmanager.db.ItemRepository;
import com.jeffreyromero.projectmanager.models.Item;

import java.util.List;

public class ItemsViewModel extends AndroidViewModel implements ItemDao {

    private ItemRepository itemRepository;

    public ItemsViewModel(@NonNull Application application) {
        super(application);
        this.itemRepository = new ItemRepository(application);
    }

    @Override
    public LiveData<List<Item>> getAllItems(){
        return itemRepository.getAllItems();
    }

    @Override
    public LiveData<Item> getById(int id) {
        return itemRepository.getById(id);
    }

    @Override
    public LiveData<List<Item>> getItemsForProject(int project_id) {
        return itemRepository.getItemsForProject(project_id);
    }

    @Override
    public void insert(Item task){
        itemRepository.insert(task);
    }

    @Override
    public void update(Item task){
        itemRepository.update(task);
    }

    @Override
    public void delete(Item task) {
        itemRepository.delete(task);
    }

}
