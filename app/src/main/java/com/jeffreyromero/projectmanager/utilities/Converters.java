package com.jeffreyromero.projectmanager.utilities;


import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jeffreyromero.projectmanager.models.Item;
import com.jeffreyromero.projectmanager.models.Material;
import com.jeffreyromero.projectmanager.models.materials.CeilingTile;
import com.jeffreyromero.projectmanager.models.materials.Hanger;
import com.jeffreyromero.projectmanager.models.materials.Tee;
import com.jeffreyromero.projectmanager.models.materials.WallAngle;
import com.jeffreyromero.projectmanager.models.materials.WallAngleFastener;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class Converters {
    private static Gson gson;

    static {
        // Material
        GsonRuntimeTypeAdapterFactory<Material> materialTypeAdapter = GsonRuntimeTypeAdapterFactory
                .of(Material.class, "subType")
                .registerSubtype(CeilingTile.class, "Ceiling Tile")
                .registerSubtype(Tee.class, "Tee")
                .registerSubtype(WallAngle.class, "Wall Angle")
                .registerSubtype(WallAngleFastener.class, "Wall Angle Fastener")
                .registerSubtype(Hanger.class, "Hanger");
//                .registerSubtype(MainSupport.class)
//                .registerSubtype(MainSupportFastener.class)
//                .registerSubtype(FurringChannel.class)
//                .registerSubtype(Stud.class)
//                .registerSubtype(Track.class)
//                .registerSubtype(TrackFastener.class)
//                .registerSubtype(Panel.class)
//                .registerSubtype(PanelFastener.class)
//                .registerSubtype(JointCompound.class)

        gson = new GsonBuilder()
                .registerTypeAdapterFactory(materialTypeAdapter)
                .create();
    }

    @TypeConverter
    public static Material toMaterial(String json) {
        return gson.fromJson(json, Material.class);
    }

    @TypeConverter
    public static String toString(List<Material> materials){
        return gson.toJson(materials);
    }

    @TypeConverter
    public static List<Material> toMaterialList(String json) {
        Type type = new TypeToken<List<Material>>(){}.getType();
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static Item toItem(String json) {
        return gson.fromJson(json, Item.class);
    }

    public static List<String> toStringList(String json) {
        Type type = new TypeToken<List<String>>(){}.getType();
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
