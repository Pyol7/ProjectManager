package com.jeffreyromero.projectmanager.db;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.jeffreyromero.projectmanager.models.Item;
import com.jeffreyromero.projectmanager.models.ItemType;
import com.jeffreyromero.projectmanager.models.ItemTypeMaterialJoin;
import com.jeffreyromero.projectmanager.models.Material;
import com.jeffreyromero.projectmanager.models.Project;
import com.jeffreyromero.projectmanager.models.materials.CeilingTile;
import com.jeffreyromero.projectmanager.models.materials.Hanger;
import com.jeffreyromero.projectmanager.models.materials.WallAngle;
import com.jeffreyromero.projectmanager.utilities.Converters;

@Database(entities = {
        Project.class,
        Item.class,
        ItemType.class,
        Material.class,
        ItemTypeMaterialJoin.class
}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = "AppDatabase";
    // Room generates the code of these methods when the database builder is executed.
    public abstract ProjectDao projectDao();
    public abstract ItemDao itemDao();
    public abstract ItemTypeDao itemTypeDao();
    public abstract MaterialDao materialDao();
    public abstract ItemTypeMaterialJoinDao itemTypeMaterialJoinDao();

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
        private MaterialDao materialDao;
        private ItemTypeMaterialJoinDao itemTypeMaterialJoinDao;

        private PopulateDBAsyncTask(AppDatabase appDatabase) {
            this.projectDao = appDatabase.projectDao();
            this.itemDao = appDatabase.itemDao();
            this.itemTypeDao = appDatabase.itemTypeDao();
            this.materialDao = appDatabase.materialDao();
            this.itemTypeMaterialJoinDao = appDatabase.itemTypeMaterialJoinDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            // Add all item types to the itemTypes table
            int droppedCeilingId = (int) itemTypeDao.insert(new ItemType("Dropped Ceiling"));
            int DrywallCeilingId = (int) itemTypeDao.insert(new ItemType("Drywall Ceiling"));
            int drywallPartitionId = (int) itemTypeDao.insert(new ItemType("Drywall Partition"));

            // Add all Materials to the materials table.
            Material ceilingTile = new CeilingTile();
            ceilingTile.setName("Generic Ceiling Tile");
            ceilingTile.setWidth(2);
            ceilingTile.setLength(2);
            ceilingTile.setUnitPrice(11.50);
            ceilingTile.setQuantity(30);

            Material wallAngle = new WallAngle();
            wallAngle.setName("Wall Angles (Donn)");
            wallAngle.setLength(10);
            wallAngle.setUnitPrice(6.50);
            wallAngle.setQuantity(22);

            Material hanger = new Hanger();
            hanger.setName("Hit-it Fasteners");
            hanger.setUnitPrice(1.50);
            hanger.setQuantity(100);

            int materialId1 = (int) materialDao.insert(ceilingTile);
            int materialId2 = (int) materialDao.insert(wallAngle);
            int materialId3 = (int) materialDao.insert(hanger);

            // Build DroppedCeiling material list by associating item type with material and
            // adding those entries to the ItemTypeMaterialJoin pivot table
            int joinId1 = (int) itemTypeMaterialJoinDao
                    .insert(new ItemTypeMaterialJoin(droppedCeilingId, materialId1));
            int joinId2 = (int) itemTypeMaterialJoinDao
                    .insert(new ItemTypeMaterialJoin(droppedCeilingId, materialId2));
            int joinId3 = (int) itemTypeMaterialJoinDao
                    .insert(new ItemTypeMaterialJoin(droppedCeilingId, materialId3));

            return null;
        }
    }
}
