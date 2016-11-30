package com.mitic.ervan.hackathonfantastique.data;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mitic.ervan.hackathonfantastique.event.MyEvenementRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


/**
 * Created by yoan on 29/11/16.
 */

public class Data {
    private int marqueur;
    private DatabaseReference myRef;
    private HashMap<String, Evenement> evenements = new HashMap<String, Evenement>();
    private HashMap<String, Parcours> parcours = new HashMap<String, Parcours>();
    private HashMap<String, List<Evenement>> villesEvenements = new HashMap<String, List<Evenement>>();
    private List<Evenement> events = new ArrayList<Evenement>();
    private EvenementFactory evenementFactory;

    public Data(){
        super();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("");
        myRef.orderByKey();
        evenementFactory = new EvenementFactory(this);
        marqueur = 0;
    }

    public void chargerLaSuite(){
        myRef.startAt(null, String.valueOf(marqueur)).limitToFirst(100).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                evenementFactory.ParseEvenement(dataSnapshot);
                marqueur++;
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    // Evenements
    public void AddEvenement (String id, Evenement evenement) {
        evenements.put(id, evenement);
        events.add(evenement);
    }

    public Evenement getById (String id) {
        return evenements.get(id);
    }

    public int getSize () {
        return evenements.size();
    }

    public List<Evenement> getAllEvents(){
        return events;
    }
    //

    // Parcours
    public boolean idParcoursUtilise (String id) {
        return parcours.keySet().contains(id);
    }

    public void addParcours (String id, List<Evenement> evenements) {
        parcours.put(id, new Parcours(id, evenements));
        Log.d("SIZE: ", parcours.size()+"");
        for (String s : parcours.keySet())
            Log.d(s, parcours.get(s).toString());
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
        return villesEvenements.keySet().toArray(temp);
    }

    public Evenement[] getEvenementsVille (String ville) {
        Evenement[] res = new Evenement[villesEvenements.get(ville).size()];
        return villesEvenements.get(ville).toArray(res);
    }
    //

}
