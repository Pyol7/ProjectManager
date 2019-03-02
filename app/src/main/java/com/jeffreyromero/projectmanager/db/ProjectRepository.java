package com.jeffreyromero.projectmanager.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import com.jeffreyromero.projectmanager.models.Project;

import java.util.List;

public class ProjectRepository {

    private ProjectDao projectDao;
    private static onAsyncTasksListener listener;

    public interface onAsyncTasksListener{
        void onInsertPostExecute(Long projectID);
    }

    public void registerListener(onAsyncTasksListener listener){
        ProjectRepository.listener = listener;
    }

    public ProjectRepository(Application application) {
        // Create the Project database
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        this.projectDao = appDatabase.projectDao();
    }

    public LiveData<List<Project>> getAllProjects(){
        return projectDao.getAllProjects();
    }

    public LiveData<Project> getById(int id) {
        return projectDao.getById(id);
    }

    public LiveData<Project> getByName(String name) {
        return projectDao.getByName(name);
    }

    public void insert(Project project){
        new InsertProjectAsyncTask(projectDao).execute(project);
    }

    public void update(Project project){
        new UpdateProjectAsyncTask(projectDao).execute(project);
    }

    public void delete(Project project) {
        new DeleteProjectAsyncTask(projectDao).execute(project);
    }

    /**
     * Room would execute the methods that return the live data on the background thread.
     * All non LiveData methods that may take time must be done async.
     * Must be static to avoid memory leak by holding an instance of ProjectRepository.
     */

    private static class InsertProjectAsyncTask extends AsyncTask<Project, Void, Long>  {
        private ProjectDao projectDao;


        private InsertProjectAsyncTask(ProjectDao projectDao) {
            this.projectDao = projectDao;
        }

        @Override
        protected Long doInBackground(Project... projects) {
            return this.projectDao.insert(projects[0]);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            listener.onInsertPostExecute(aLong);
        }

    }

    private static class UpdateProjectAsyncTask extends AsyncTask<Project, Void, Void> {
        private ProjectDao projectDao;

        private UpdateProjectAsyncTask(ProjectDao projectDao) {
            this.projectDao = projectDao;
        }

        @Override
        protected Void doInBackground(Project... projects) {
            projectDao.update(projects[0]);
            return null;
        }
    }

    private static class DeleteProjectAsyncTask extends AsyncTask<Project, Void, Void> {
        private ProjectDao projectDao;

        private DeleteProjectAsyncTask(ProjectDao projectDao) {
            this.projectDao = projectDao;
        }

        @Override
        protected Void doInBackground(Project... projects) {
            projectDao.delete(projects[0]);
            return null;
        }
    }


    private static long returnID(long id){
        return id;
    }
}
