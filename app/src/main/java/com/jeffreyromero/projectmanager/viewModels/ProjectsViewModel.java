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

public class ProjectsViewModel extends AndroidViewModel{

    private ProjectRepository projectRepository;

    public ProjectsViewModel(@NonNull Application application) {
        super(application);
        this.projectRepository = new ProjectRepository(application);
    }

    public LiveData<List<Project>> getAllProjects(){
        return projectRepository.getAllProjects();
    }

    public LiveData<Project> getById(int id) {
        return projectRepository.getById(id);
    }

    public LiveData<Project> getByName(String name) {
        return projectRepository.getByName(name);
    }

    public void insert(Project task){
        projectRepository.insert(task);
        projectRepository.registerListener(new ProjectRepository.onAsyncTasksListener() {
            @Override
            public void onInsertPostExecute(Long projectID) {
                Log.i("<<<>>>", "ProjectsViewModel.insert.onInsertPostExecute: projectID= " + projectID);
            }
        });
    }

    public void update(Project task){
        projectRepository.update(task);
    }

    public void delete(Project task) {
        projectRepository.delete(task);
    }

}
