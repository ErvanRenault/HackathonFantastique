package com.mitic.ervan.hackathonfantastique.evenement;

import android.util.Log;

import java.util.HashMap;

/**
 * Created by yoan on 29/11/16.
 */

public class Data {

    private HashMap<String, Evenement> evenements = new HashMap<String, Evenement>();

    public void AddEvenement (String id, Evenement evenement) {
        evenements.put(id, evenement);
    }

    public Evenement getById (String id) {
        return evenements.get(id);
    }

    public int getSize () {
        return evenements.size();
    }

    public void print () {
        String res = evenements.size() + " évènements: \n";
        for (String key : evenements.keySet())
            Log.d("ADIEU: ", evenements.get(key).toString());
    }
}
