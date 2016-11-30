package com.mitic.ervan.hackathonfantastique.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoan on 29/11/16.
 */

public class Parcours {


    private List<Evenement> evenements;
    private int courant;
    private String id;

    public Parcours(String id, List<Evenement> evenements) {
        this.id = id;
        this.evenements = evenements;
        courant = 0;
    }

    public String getId() {
        return id;
    }

    public void evenementSuivant() {
        if (courant < evenements.size() - 1) ;
        courant++;
    }

    public boolean estTermine() {
        return courant > evenements.size() - 1;
    }

    public Evenement getCourant() {
        return evenements.get(courant);
    }

    public List<Evenement> getParcoursVisite() {
        List<Evenement> res = new ArrayList<Evenement>();
        for (int i = 0; i < courant; i++)
            res.add(evenements.get(i));
        return res;
    }

    public List<Evenement> getParcoursRestant() {
        List<Evenement> res = new ArrayList<Evenement>();
        for (int i = courant; i < evenements.size(); i++)
            res.add(evenements.get(i));
        return res;
    }

    public String toString () {
        String res = "";
        for (Evenement event : evenements)
            res += event.fields.titre_fr + ", ";
        return res;
    }
    
}
