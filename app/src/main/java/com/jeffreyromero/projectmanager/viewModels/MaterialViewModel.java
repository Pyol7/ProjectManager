package com.jeffreyromero.projectmanager.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.jeffreyromero.projectmanager.db.MaterialDao;
import com.jeffreyromero.projectmanager.db.MaterialRepository;
import com.jeffreyromero.projectmanager.models.Material;

import java.util.List;

public class MaterialViewModel extends AndroidViewModel {

    private MaterialRepository repo;

    public MaterialViewModel(@NonNull Application application) {
        super(application);
        this.repo = new MaterialRepository(application);
    }

    public LiveData<List<Material>> getAll(){
        return repo.getAll();
    }

    public LiveData<Material> getById(int id) {
        return repo.getById(id);
    }

    public void insert(Material material){
        repo.insert(material);
    }

    public void update(Material material){
        repo.update(material);
    }

    public void delete(Material material) {
        repo.delete(material);
    }

}
