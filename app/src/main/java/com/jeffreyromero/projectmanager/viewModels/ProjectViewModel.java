package com.jeffreyromero.projectmanager.viewModels;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jeffreyromero.projectmanager.db.ProjectDao;
import com.jeffreyromero.projectmanager.db.ProjectRepository;
import com.jeffreyromero.projectmanager.models.Project;

import java.util.List;

public class ProjectViewModel extends AndroidViewModel{

    private ProjectRepository projectRepository;

    public ProjectViewModel(@NonNull Application application) {
        super(application);
        this.projectRepository = new ProjectRepository(application);
    }

    public LiveData<List<Project>> getAll(){
        return projectRepository.getAll();
    }

    public LiveData<Project> getById(int id) {
        return projectRepository.getById(id);
    }

    public void insert(Project project){
        projectRepository.insert(project);
    }

    public void update(Project project){
        projectRepository.update(project);
    }

    public void delete(Project project) {
        projectRepository.delete(project);
    }

}
