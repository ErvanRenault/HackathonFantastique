package com.mitic.ervan.hackathonfantastique;

import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.mitic.ervan.hackathonfantastique.data.Data;
import com.mitic.ervan.hackathonfantastique.data.Evenement;
import com.mitic.ervan.hackathonfantastique.data.EvenementFactory;
import com.mitic.ervan.hackathonfantastique.event.Event;
import com.mitic.ervan.hackathonfantastique.event.ListEvent;
import com.mitic.ervan.hackathonfantastique.gestion.GestionEvenement;
import com.mitic.ervan.hackathonfantastique.map.MapRechercheEvent;
import com.mitic.ervan.hackathonfantastique.map.MyMapFragment;
import com.mitic.ervan.hackathonfantastique.parcours.CreerParcours;
import com.mitic.ervan.hackathonfantastique.parcours.RechercheParcours;
import com.mitic.ervan.hackathonfantastique.parcours.SpinAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CreerParcours.OnFragmentInteractionListener,GestionEvenement.OnFragmentInteractionListener, RechercheParcours.OnFragmentInteractionListener,Accueil.OnFragmentInteractionListener,MapRechercheEvent.OnFragmentInteractionListener, ListEvent.OnListFragmentInteractionListener,Event.OnFragmentInteractionListener{

    private Data data;
    private EvenementFactory evenementFactory;
    private List<Evenement> parcoursCourant = new ArrayList<Evenement>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = new Data();
        evenementFactory = new EvenementFactory(data);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("");
        Query refQuery = myRef.orderByKey().endAt("99");


        // Read from the database
        refQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                evenementFactory.ParseEvenement(dataSnapshot);
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

    private void createParcours(){

        FragmentTransaction fragmentManager =  getSupportFragmentManager().beginTransaction();
        Fragment creerParcours= CreerParcours.newInstance("param1",data, this);
        fragmentManager.replace(R.id.activity_main,creerParcours).addToBackStack(null).commit();
    }

    private void searchParcours(){
        FragmentTransaction fragmentManager =  getSupportFragmentManager().beginTransaction();
        Fragment rechercheParcours= RechercheParcours.newInstance("param1","param2");
        fragmentManager.replace(R.id.activity_main,rechercheParcours).addToBackStack(null).commit();

    }

    public void ajouterEvenement(View view) {
        ViewGroup layout = (ViewGroup) findViewById(R.id.elementsparcours);
        Spinner spinner = (Spinner) findViewById(R.id.spinnerparcours);

        parcoursCourant.add((Evenement) (spinner.getSelectedItem()));

        final ViewGroup linearLayout = new LinearLayout(MainActivity.this);
        TextView nomEvent = new TextView(MainActivity.this);
        final Button erase = new Button(MainActivity.this);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams paramsElem = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);

        linearLayout.setLayoutParams(params);
        erase.setLayoutParams(paramsElem);
        erase.setText("retirer");
        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ViewGroup) linearLayout.getParent()).removeView(linearLayout);
                Log.d("TAG", "onClick: erase");
            }
        });
        nomEvent.setLayoutParams(paramsElem);
        nomEvent.setText(((Evenement)(spinner.getSelectedItem())).fields.titre_fr);
        linearLayout.addView(nomEvent);
        linearLayout.addView(erase);
        layout.addView(linearLayout);
    }

    public void updateSpinnerParcours () {
        SpinAdapter adapter = new SpinAdapter(
                this, android.R.layout.simple_spinner_item, data.getEvenementsVille(
                ((Spinner) findViewById(R.id.spinnerville)).getSelectedItem().toString()
        ));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.spinnerparcours);
        sItems.setAdapter(adapter);
    }

    public void ajouterParcours (View view) {
        String temp = ((EditText)findViewById(R.id.nomcreationparcours)).getText().toString();
        if (data.getParcoursById(temp) != null)
            Log.d("TOAST: ", "Le nom de parcours \"" + temp + "\" est déjà utilisé.");
        else {
            data.addParcours(temp, cloneParcours());
            clearParcoursCourant();
            createParcours();
        }
    }

    private List<Evenement> cloneParcours () {
        List<Evenement> res = new ArrayList<Evenement>();
        for (Evenement e : parcoursCourant)
            res.add(e);
        return res;
    }

    public void clearParcoursCourant () {
        parcoursCourant.clear();
    }

    public void mapAccueil(View view){
        FragmentTransaction fragmentManager =  getSupportFragmentManager().beginTransaction();
        Fragment mapEvent = MapRechercheEvent.newInstance("param1", "param2");
        fragmentManager.replace(R.id.activity_main,mapEvent).addToBackStack(null).commit();
    }

    public void mapDetail(View view){


    }

    public void listAccueil(View view){
        FragmentTransaction fragmentManager =  getSupportFragmentManager().beginTransaction();
        Fragment listEvent = ListEvent.newInstance(1, data);
        fragmentManager.replace(R.id.activity_main,listEvent).addToBackStack(null).commit();
    }

    @Override
    public void onFragmentInteraction(Evenement event) {
        FragmentTransaction fragmentManager =  getSupportFragmentManager().beginTransaction();
        Fragment mapEvent = MyMapFragment.newInstance(event.geometry.one,event.geometry.zero);
        fragmentManager.replace(R.id.activity_main,mapEvent).addToBackStack(null).commit();
    }

    @Override
    public void onListFragmentInteraction(Evenement event_old) {
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        Fragment event = Event.newInstance(event_old);
        fragmentManager.replace(R.id.activity_main,event).addToBackStack(null).commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
