package com.devfill.checkman_mvp.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.devfill.checkman_mvp.util.ObjectSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Preferences {

    final static String FILE_NAME = "preferences";

    private SharedPreferences preferences;

    public Preferences(Context context) {
        preferences = context.getSharedPreferences(FILE_NAME, 0);
    }

    public void saveSuggestionList(ArrayList<String> stringList){

        SharedPreferences.Editor editor = preferences.edit();
        try {
            editor.putString("LIST", ObjectSerializer.serialize(stringList));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.commit();

    }

    public ArrayList<String> getSuggestionList(){

        try {
            return (ArrayList<String>) ObjectSerializer.deserialize(preferences.getString("LIST", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }

    }
}
