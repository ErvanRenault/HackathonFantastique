package com.mitic.ervan.hackathonfantastique.data;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Created by yoan on 29/11/16.
 */

public class Data {
    private int marqueur;
    private DatabaseReference myRef;
    private DatabaseReference refParcours;
    private HashMap<String, Evenement> evenements = new HashMap<String, Evenement>();
    private HashMap<String, Parcours> parcours = new HashMap<String, Parcours>();
    private HashMap<String, List<Evenement>> villesEvenements = new HashMap<String, List<Evenement>>();
    private List<Evenement> events = new ArrayList<Evenement>();
    private List<Parcours> listParcours = new ArrayList<>();
    private EvenementFactory evenementFactory;

    public Data(){
        super();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("");
        myRef.orderByKey();
        refParcours = database.getReference("Parcours");
        evenementFactory = new EvenementFactory(this);
        marqueur = 0;
    }

    public void chargerLaSuite(){
        myRef.startAt(null, String.valueOf(marqueur)).limitToFirst(100).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                AddEvenement(dataSnapshot.getKey(), evenementFactory.parseEvenement(dataSnapshot));
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

    public void chargerLesParcours() {

        // Read from the database
        refParcours.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("PARCOURS ADDED (snap)", dataSnapshot.getKey()+"");
                addParcoursFromFirebase(evenementFactory.parseParcours(dataSnapshot));
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

    public void addParcours (Parcours parcours) {
        //this.parcours.put(parcours.getId(), parcours);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference("Parcours").child(parcours.getId()).setValue(parcours);
    }

    public void addParcoursFromFirebase (Parcours parcours) {
        this.parcours.put(parcours.getId(), parcours);
        this.listParcours.add(parcours);

        Log.d("SIZE: ", this.parcours.size()+"");
        for (String s : this.parcours.keySet())
            Log.d(s, this.parcours.get(s).toString());
    }

    public List<Parcours> getListParcours() {
        return listParcours;
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
