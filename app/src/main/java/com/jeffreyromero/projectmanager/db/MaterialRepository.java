package com.jeffreyromero.projectmanager.db;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.jeffreyromero.projectmanager.models.Material;

import java.util.List;


public class MaterialRepository {

    private static final String TAG = "MaterialRepository";
    private MaterialDao materialDao;

    public MaterialRepository(Application application) {
        // Create the Material database
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        this.materialDao = appDatabase.materialDao();
    }

    public LiveData<List<Material>> getAll(){
        return materialDao.getAll();
    }

    public LiveData<Material> getById(int id) {
        return materialDao.getById(id);
    }

    public void insert(Material material){
        new InsertMaterialAsyncTask(materialDao).execute(material);
    }

    public void update(Material material){
        new UpdateMaterialAsyncTask(materialDao).execute(material);
    }

    public void delete(Material material) {
        new DeleteMaterialAsyncTask(materialDao).execute(material);
    }

    /**
     * Room would execute the methods that return the live data on the background thread.
     * All non LiveData methods that may take time must be done async.
     * Must be static to avoid memory leak by holding an instance of MaterialRepository.
     */

    private static class InsertMaterialAsyncTask extends AsyncTask<Material, Void, Long> {
        private MaterialDao materialDao;

        private InsertMaterialAsyncTask(MaterialDao materialDao) {
            this.materialDao = materialDao;
        }

        @Override
        protected Long doInBackground(Material... materials) {
            return materialDao.insert(materials[0]);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            Log.d(TAG, "onPostExecute() " + aLong);
        }
    }

    private static class UpdateMaterialAsyncTask extends AsyncTask<Material, Void, Void> {
        private MaterialDao materialDao;

        private UpdateMaterialAsyncTask(MaterialDao materialDao) {
            this.materialDao = materialDao;
        }

        @Override
        protected Void doInBackground(Material... materials) {
            materialDao.update(materials[0]);
            return null;
        }
    }

    private static class DeleteMaterialAsyncTask extends AsyncTask<Material, Void, Void> {
        private MaterialDao materialDao;

        private DeleteMaterialAsyncTask(MaterialDao materialDao) {
            this.materialDao = materialDao;
        }

        @Override
        protected Void doInBackground(Material... materials) {
            materialDao.delete(materials[0]);
            return null;
        }
    }


}
