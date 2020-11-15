package manuel.de.kuehlschrankinventar.datenbank;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


import java.util.Set;

public class DefPref {

    private SharedPreferences prefs;

    public DefPref(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getString(String key, String defaultValue) {
        return prefs.getString(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return prefs.getBoolean(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return prefs.getInt(key, defValue);
    }

    public Set<String> getStringSet(String key, Set<String> defaultValue) {
        return prefs.getStringSet(key, defaultValue);
    }

    public void setString(String key, String value) {
        prefs.edit().putString(key, value).commit();
    }

    public void setInt(String key, int value) {
        prefs.edit().putInt(key, value).apply();
    }

    public void remove(String key) {
        prefs.edit().remove(key).commit();
    }
}