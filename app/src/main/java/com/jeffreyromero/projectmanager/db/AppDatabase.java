package com.jeffreyromero.projectmanager.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.jeffreyromero.projectmanager.models.Item;
import com.jeffreyromero.projectmanager.models.itemTypes.DroppedCeiling;
import com.jeffreyromero.projectmanager.models.itemTypes.DrywallPartition;
import com.jeffreyromero.projectmanager.models.Project;
import com.jeffreyromero.projectmanager.models.itemTypes.ItemType;
import com.jeffreyromero.projectmanager.utilities.Converters;

@Database(entities = {Project.class, Item.class, ItemType.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    // Room generates the code of these methods when the database builder is executed.
    public abstract ProjectDao projectDao();
    public abstract ItemDao itemDao();
    public abstract ItemTypeDao itemTypeDao();

    // This holds the database singleton that we can access statically from anywhere.
    private static AppDatabase sInstance;

    // Get a database instance
    public static synchronized AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = create(context);
        }
        return sInstance;
    }

    // Create the database
    private static AppDatabase create(Context context) {
        RoomDatabase.Builder<AppDatabase> builder = Room.databaseBuilder(
                context.getApplicationContext(),
                AppDatabase.class,
                "task_database")
                .fallbackToDestructiveMigration()
                .addCallback(roomCallBack);
        return builder.build();
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        /**
         * Called when the database is created for the first time. This is called after all the
         * tables are created.
         *
         * @param db The database.
         */
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(sInstance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProjectDao projectDao;
        private ItemDao itemDao;
        private ItemTypeDao itemTypeDao;

        private PopulateDBAsyncTask(AppDatabase appDatabase) {
            this.projectDao = appDatabase.projectDao();
            this.itemDao = appDatabase.itemDao();
            this.itemTypeDao = appDatabase.itemTypeDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Populate itemTypes table
            itemTypeDao.insert(new ItemType(1, "Dropped Ceiling"));
            itemTypeDao.insert(new ItemType(2, "Drywall Ceiling"));
            itemTypeDao.insert(new ItemType(3, "Drywall Partition"));

            // Create sample project
            int id = (int) projectDao.insert(new Project("Sample Project"));

            // Add items to sample project
            itemDao.insert(new DroppedCeiling("Living Room", id));
            itemDao.insert(new DrywallPartition("Kitchen", id));
            return null;
        }
    }
}
