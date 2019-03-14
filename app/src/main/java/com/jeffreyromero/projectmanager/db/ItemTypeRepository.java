package com.jeffreyromero.projectmanager.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.jeffreyromero.projectmanager.models.ItemType;

import java.util.List;


public class ItemTypeRepository {

    private ItemTypeDao itemTypeDao;

    public ItemTypeRepository(Application application) {
        // Create the ItemType database
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        this.itemTypeDao = appDatabase.itemTypeDao();
    }

    public LiveData<List<ItemType>> getAll(){
        return itemTypeDao.getAll();
    }

    public LiveData<ItemType> getById(int id) {
        return itemTypeDao.getById(id);
    }

    public void insert(ItemType itemType){
        new InsertItemTypeAsyncTask(itemTypeDao).execute(itemType);
    }

    public void update(ItemType itemType){
        new UpdateItemTypeAsyncTask(itemTypeDao).execute(itemType);
    }

    public void delete(ItemType itemType) {
        new DeleteItemTypeAsyncTask(itemTypeDao).execute(itemType);
    }

    /**
     * Room would execute the methods that return the live data on the background thread.
     * All non LiveData methods that may take time must be done async.
     * Must be static to avoid memory leak by holding an instance of ItemTypeRepository.
     */

    private static class InsertItemTypeAsyncTask extends AsyncTask<ItemType, Void, Long> {
        private ItemTypeDao itemTypeDao;

        private InsertItemTypeAsyncTask(ItemTypeDao itemTypeDao) {
            this.itemTypeDao = itemTypeDao;
        }

        @Override
        protected Long doInBackground(ItemType... itemTypes) {
            return itemTypeDao.insert(itemTypes[0]);
        }
    }

    private static class UpdateItemTypeAsyncTask extends AsyncTask<ItemType, Void, Void> {
        private ItemTypeDao itemTypeDao;

        private UpdateItemTypeAsyncTask(ItemTypeDao itemTypeDao) {
            this.itemTypeDao = itemTypeDao;
        }

        @Override
        protected Void doInBackground(ItemType... itemTypes) {
            itemTypeDao.update(itemTypes[0]);
            return null;
        }
    }

    private static class DeleteItemTypeAsyncTask extends AsyncTask<ItemType, Void, Void> {
        private ItemTypeDao itemTypeDao;

        private DeleteItemTypeAsyncTask(ItemTypeDao itemTypeDao) {
            this.itemTypeDao = itemTypeDao;
        }

        @Override
        protected Void doInBackground(ItemType... itemTypes) {
            itemTypeDao.delete(itemTypes[0]);
            return null;
        }
    }


}
