package com.jeffreyromero.projectmanager.utilities;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jeffreyromero.projectmanager.models.Item;
import com.jeffreyromero.projectmanager.models.itemTypes.DroppedCeiling;
import com.jeffreyromero.projectmanager.models.itemTypes.DrywallCeiling;
import com.jeffreyromero.projectmanager.models.itemTypes.DrywallPartition;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class Converters {
    private static Gson gson;

    static {
        // Item
        GsonRuntimeTypeAdapterFactory<Item> itemTypeAdapter = GsonRuntimeTypeAdapterFactory
                .of(Item.class, "subType")
                .registerSubtype(DroppedCeiling.class, "DroppedCeiling")
                .registerSubtype(DrywallCeiling.class, "DrywallCeiling")
                .registerSubtype(DrywallPartition.class, "DrywallPartition");

        gson = new GsonBuilder()
                .registerTypeAdapterFactory(itemTypeAdapter)
                .create();
    }

    @TypeConverter
    public static String toGson (List<Item> items) {
        return gson.toJson(items);
    }

    @TypeConverter
    public static List<Item> toItemList(String json) {
        Type type = new TypeToken<List<Item>>(){}.getType();
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static Date timestampToDate(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}
