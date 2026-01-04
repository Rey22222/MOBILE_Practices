package ru.mirea.khasanova.succuforest.data.storage.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class ClientPrefs {
    private final SharedPreferences prefs;

    public ClientPrefs(Context context) {
        prefs = context.getSharedPreferences("client_prefs", Context.MODE_PRIVATE);
    }

    public void saveUser(String email) {
        prefs.edit().putString("EMAIL", email).apply();
    }
}