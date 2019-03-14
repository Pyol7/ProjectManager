package com.jeffreyromero.projectmanager.db;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.jeffreyromero.projectmanager.models.Item;

import java.util.List;

public class ItemRepository{

    private static final String TAG = "ItemRepository";
    private ItemDao itemDao;

    public ItemRepository(Application application) {
        // Create the Item database
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        this.itemDao = appDatabase.itemDao();
    }

    public LiveData<List<Item>> getAll(){
        return itemDao.getAll();
    }

    public LiveData<Item> getById(int id) {
        return itemDao.getById(id);
    }

    public LiveData<List<Item>> getByProjectId(int projectId) {
        return itemDao.getByProjectId(projectId);
    }

    public void insert(Item item){
        new InsertItemAsyncTask(itemDao).execute(item);
    }

    public void update(Item item){
        new UpdateItemAsyncTask(itemDao).execute(item);
    }

    public void delete(Item item) {
        new DeleteItemAsyncTask(itemDao).execute(item);
    }

    /**
     * Room would execute the methods that return the live data on the background thread.
     * All non LiveData methods that may take time must be done async.
     * Must be static to avoid memory leak by holding an instance of ItemRepository.
     */

    private static class InsertItemAsyncTask extends AsyncTask<Item, Void, Long> {
        private ItemDao itemDao;

        private InsertItemAsyncTask(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Long doInBackground(Item... items) {
            return itemDao.insert(items[0]);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            Log.d(TAG, "onPostExecute() " + aLong);
        }
    }

    private static class UpdateItemAsyncTask extends AsyncTask<Item, Void, Void> {
        private ItemDao itemDao;

        private UpdateItemAsyncTask(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            itemDao.update(items[0]);
            return null;
        }
    }

    private static class DeleteItemAsyncTask extends AsyncTask<Item, Void, Void> {
        private ItemDao itemDao;

        private DeleteItemAsyncTask(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            itemDao.delete(items[0]);
            return null;
        }
    }


}
