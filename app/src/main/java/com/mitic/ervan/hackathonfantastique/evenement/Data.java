package com.mitic.ervan.hackathonfantastique.evenement;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public List<Evenement> getAllEventsBy100(){
        List<Evenement> res = new ArrayList<Evenement>();
        for (String key : evenements.keySet())
            res.add(evenements.get(key));
        return res;
    }

    public void print () {
        String res = evenements.size() + " évènements: \n";
        for (String key : evenements.keySet())
            Log.d("ADIEU: ", evenements.get(key).toString());
    }
}
