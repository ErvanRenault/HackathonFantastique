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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mitic.ervan.hackathonfantastique.gestion.GestionEvenement;
import com.mitic.ervan.hackathonfantastique.parcours.CreerParcours;
import com.mitic.ervan.hackathonfantastique.parcours.RechercheParcours;

public class MainActivity extends AppCompatActivity implements CreerParcours.OnFragmentInteractionListener,GestionEvenement.OnFragmentInteractionListener, RechercheParcours.OnFragmentInteractionListener,Accueil.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("TEST", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TEST", "Failed to read value.", error.toException());
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
