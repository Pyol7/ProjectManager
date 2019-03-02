package com.jeffreyromero.projectmanager.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.jeffreyromero.projectmanager.models.itemTypes.ItemType;

import java.util.List;


public class ItemTypeRepository implements ItemTypeDao {

    private ItemTypeDao itemTypeDao;

    public ItemTypeRepository(Application application) {
        // Create the Item database
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        this.itemTypeDao = appDatabase.itemTypeDao();
    }

    @Override
    public LiveData<List<ItemType>> getAllItemTypes(){
        return itemTypeDao.getAllItemTypes();
    }

    @Override
    public LiveData<ItemType> getById(int id) {
        return itemTypeDao.getById(id);
    }

    @Override
    public void insert(ItemType item){
        new InsertItemTypeAsyncTask(itemTypeDao).execute(item);
    }

    @Override
    public void update(ItemType item){
        new UpdateItemTypeAsyncTask(itemTypeDao).execute(item);
    }

    @Override
    public void delete(ItemType item) {
        new DeleteItemTypeAsyncTask(itemTypeDao).execute(item);
    }

    /**
     * Room would execute the methods that return the live data on the background thread.
     * All non LiveData methods that may take time must be done async.
     * Must be static to avoid memory leak by holding an instance of ItemTypeRepository.
     */

    private static class InsertItemTypeAsyncTask extends AsyncTask<ItemType, Void, Void> {
        private ItemTypeDao itemTypeDao;

        private InsertItemTypeAsyncTask(ItemTypeDao itemTypeDao) {
            this.itemTypeDao = itemTypeDao;
        }

        @Override
        protected Void doInBackground(ItemType... items) {
            itemTypeDao.insert(items[0]);
            return null;
        }
    }

    private static class UpdateItemTypeAsyncTask extends AsyncTask<ItemType, Void, Void> {
        private ItemTypeDao itemTypeDao;

        private UpdateItemTypeAsyncTask(ItemTypeDao itemTypeDao) {
            this.itemTypeDao = itemTypeDao;
        }

        @Override
        protected Void doInBackground(ItemType... items) {
            itemTypeDao.update(items[0]);
            return null;
        }
    }

    private static class DeleteItemTypeAsyncTask extends AsyncTask<ItemType, Void, Void> {
        private ItemTypeDao itemTypeDao;

        private DeleteItemTypeAsyncTask(ItemTypeDao itemTypeDao) {
            this.itemTypeDao = itemTypeDao;
        }

        @Override
        protected Void doInBackground(ItemType... items) {
            itemTypeDao.delete(items[0]);
            return null;
        }
    }


}
