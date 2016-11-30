package com.mitic.ervan.hackathonfantastique.data;

import android.util.Log;

import com.mitic.ervan.hackathonfantastique.data.Evenement;
import com.mitic.ervan.hackathonfantastique.data.Parcours;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by yoan on 29/11/16.
 */

public class Data {

    private HashMap<String, Evenement> evenements = new HashMap<String, Evenement>();
    private HashMap<String, Parcours> parcours = new HashMap<String, Parcours>();
    private HashMap<String, List<Evenement>> villesEvenements = new HashMap<String, List<Evenement>>();

    // Evenements
    public void AddEvenement (String id, Evenement evenement) {
        evenements.put(id, evenement);
    }

    public Evenement getById (String id) {
        return evenements.get(id);
    }

    public int getSize () {
        return evenements.size();
    }

    public List<Evenement> getAllEvents(){
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

    public void initializeCities () {
        for (String id : evenements.keySet()) {
            String ville = evenements.get(id).fields.ville;
            if (ville == null) ville = "Autres";
            if (!villesEvenements.keySet().contains(ville))
                villesEvenements.put(ville, new ArrayList<Evenement>());
            villesEvenements.get(ville).add(evenements.get(id));
        }
    }

    public String[] getVilles () {
        String[] temp = new String[villesEvenements.size()];
        String[] res = villesEvenements.keySet().toArray(temp);
        return res;
    }

    public String[] getEvenementsVille () {
        return null;
    }
    //

}
