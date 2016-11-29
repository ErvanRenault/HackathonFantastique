package com.mitic.ervan.hackathonfantastique;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mitic.ervan.hackathonfantastique.gestion.GestionEvenement;
import com.mitic.ervan.hackathonfantastique.parcours.CreerParcours;
import com.mitic.ervan.hackathonfantastique.parcours.RechercheParcours;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CreerParcours.OnFragmentInteractionListener,GestionEvenement.OnFragmentInteractionListener, RechercheParcours.OnFragmentInteractionListener,Accueil.OnFragmentInteractionListener {

    private List<Object> lesevents = new ArrayList<Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("");

        // Read from the database
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Object value = dataSnapshot.getValue(Object.class);
                lesevents.add(value);
                Log.d("TEST", "Number : " + lesevents.size());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        Accueil accueil=Accueil.newInstance("param1","param2");
        FragmentTransaction fragmentManager =  getSupportFragmentManager().beginTransaction();
        fragmentManager.add(R.id.activity_main,accueil).commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.gestionevenement:
                gererEvent();
                return true;
            case R.id.creationparcours:
                createParcours();
                return true;
            case R.id.rechercheparcours:
                searchParcours();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    private void gererEvent(){
        FragmentTransaction fragmentManager =  getSupportFragmentManager().beginTransaction();
        Fragment gestionEvenement=GestionEvenement.newInstance("param1","param2");
        fragmentManager.replace(R.id.activity_main,gestionEvenement).addToBackStack(null).commit();
    }
    private void  createParcours(){
        FragmentTransaction fragmentManager =  getSupportFragmentManager().beginTransaction();
        Fragment creerParcours= CreerParcours.newInstance("param1","param2");
        fragmentManager.replace(R.id.activity_main,creerParcours).addToBackStack(null).commit();
    }
    private void searchParcours(){
        FragmentTransaction fragmentManager =  getSupportFragmentManager().beginTransaction();
        Fragment rechercheParcours= RechercheParcours.newInstance("param1","param2");
        fragmentManager.replace(R.id.activity_main,rechercheParcours).addToBackStack(null).commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
