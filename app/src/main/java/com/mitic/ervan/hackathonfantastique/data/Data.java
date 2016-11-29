package com.mitic.ervan.hackathonfantastique.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yoan on 29/11/16.
 */

public class Data {

    private HashMap<String, Evenement> evenements = new HashMap<String, Evenement>();
    private HashMap<String, Parcours> parcours = new HashMap<String, Parcours>();

    // Evenements

    public void AddEvenement (String id, Evenement evenement) {
        evenements.put(id, evenement);
    }

    public Evenement getById (String id) {
        return evenements.get(id);
    }

    public List<Evenement> getAllEventsBy100(){
        List<Evenement> res = new ArrayList<Evenement>();
        for (String key : evenements.keySet())
            res.add(evenements.get(key));
        return res;
    }

    //

    // Parcours

    public boolean idParcoursUtilise (String id) {
        return parcours.keySet().contains(id);
    }

    public void addParcours (String id, List<Evenement> evenements) {
        parcours.put(id, new Parcours(id, evenements));
    }

    public Parcours getParcoursById (String id) {
        return parcours.get(id);
    }

    //

}
