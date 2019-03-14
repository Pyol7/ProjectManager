package com.jeffreyromero.projectmanager.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.jeffreyromero.projectmanager.models.ItemTypeMaterialJoin;
import com.jeffreyromero.projectmanager.models.Material;

import java.util.List;


public class ItemTypeMaterialJoinRepository {

    private ItemTypeMaterialJoinDao itemTypeMaterialJoinDao;

    public ItemTypeMaterialJoinRepository(Application application) {
        // Create the Item database
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        this.itemTypeMaterialJoinDao = appDatabase.itemTypeMaterialJoinDao();
    }

    public LiveData<List<Material>> getMaterialsForItemType(int itemTypeId) {
        return itemTypeMaterialJoinDao.getMaterialsForItemType(itemTypeId);
    }

    public void insert(ItemTypeMaterialJoin itemTypeMaterialJoin){
        new InsertItemAsyncTask(itemTypeMaterialJoinDao).execute(itemTypeMaterialJoin);
    }

    /**
     * Room would execute the methods that return the live data on the background thread.
     * All non LiveData methods that may take time must be done async.
     * Must be static to avoid memory leak by holding an instance of ItemRepository.
     */

    private static class InsertItemAsyncTask extends AsyncTask<ItemTypeMaterialJoin, Void, Long> {

        // todo - add async for getMaterialsForItemType()

        private ItemTypeMaterialJoinDao itemTypeMaterialJoinDao;

        private InsertItemAsyncTask(ItemTypeMaterialJoinDao itemTypeMaterialJoinDao) {
            this.itemTypeMaterialJoinDao = itemTypeMaterialJoinDao;
        }

        @Override
        protected Long doInBackground(ItemTypeMaterialJoin... itemTypeMaterialJoins) {
            return itemTypeMaterialJoinDao.insert(itemTypeMaterialJoins[0]);
        }
    }
}
