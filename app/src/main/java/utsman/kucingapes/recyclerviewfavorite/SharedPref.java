package utsman.kucingapes.recyclerviewfavorite;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class SharedPref {

    private static final String PREFS_NAME = "FILE_PREFERENCES";
    private static final String FAVORITES = "ITEM_FAVORITE";

    private static final String INDEX = "INDEX_LIST";

    private void saveFavorites(Context context, List<Item> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);
        editor.putString(FAVORITES, jsonFavorites);
        editor.apply();
    }

    public void addFavorite(Context context, Item Item) {
        List<Item> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<>();
        favorites.add(Item);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, int pos) {
        ArrayList<Item> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(pos);
            saveFavorites(context, favorites);
        }
    }

    public ArrayList<Item> getFavorites(Context context) {
        SharedPreferences settings;
        List<Item> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            Item[] favoriteItems = gson.fromJson(jsonFavorites,
                    Item[].class);
            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<>(favorites);
        } else
            return null;

        return (ArrayList<Item>) favorites;
    }

    public void addIndex(Context context, String title) {
        List<String> names;
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String json = preferences.getString(INDEX, null);
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        names = gson.fromJson(json, type);
        if (names == null) {
            names = new ArrayList<>();
        }
        names.add(title);
        SharedPreferences.Editor editor = preferences.edit();

        String listJson = gson.toJson(names);
        editor.putString(INDEX, listJson);
        editor.apply();
    }

    public void removeIndex(Context context, String title) {
        List<String> names;
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String json = preferences.getString(INDEX, null);
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        names = gson.fromJson(json, type);
        if (names == null) {
            names = new ArrayList<>();
        }
        names.remove(title);
        SharedPreferences.Editor editor = preferences.edit();

        String listJson = gson.toJson(names);
        editor.putString(INDEX, listJson);
        editor.apply();
    }

    public int setIndex(Context context, String title) {
        List<String> names;
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String json = preferences.getString(INDEX, null);
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        names = gson.fromJson(json, type);
        int position = 0;
        if (names != null) {
            position = names.indexOf(title);
        }
        return position;
    }
}
