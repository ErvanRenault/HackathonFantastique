package com.mitic.ervan.hackathonfantastique.evenement;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by yoan on 29/11/16.
 */

public class EvenementFactory {

    private Data data;

    public EvenementFactory (Data data) {
        this.data = data;
    }

    public void ParseEvenement (DataSnapshot dataSnapshot) {
        Evenement res = new Evenement();
        for (DataSnapshot row : dataSnapshot.getChildren()) {
            switch (row.getKey()) {
                case "datasetid": res.datasetid = row.getValue(String.class); break;
                case "record_timestamp":res.record_timestamp = row.getValue(String.class); break;
                case "recordid": res.recordid = row.getValue(String.class); break;
                case "geometry": res.geometry = parseGeometry(row); break;
                case "fields": res.fields = parseFields(row); break;
                default:
            }
        }
        data.AddEvenement(dataSnapshot.getKey(), res);
    }

    private Geometry parseGeometry (DataSnapshot dataSnapshot) {
        Geometry res = new Geometry();
        for (DataSnapshot row : dataSnapshot.getChildren()) {
            if (row.getKey().equals("type")) res.type = row.getValue(String.class);
            else if (row.getKey().equals("coordinates")) {
                for (DataSnapshot row2 : row.getChildren()) {
                    if (row2.getKey().equals("0")) res.zero = row2.getValue(float.class);
                    else if (row2.getKey().equals("1")) res.one = row2.getValue(float.class);
                }
            }
        }
        return res;
    }

    private Field parseFields (DataSnapshot dataSnapshot) {
        Field res = new Field();

        for (DataSnapshot row : dataSnapshot.getChildren()) {
            switch (row.getKey()) {
                case "nom_du_lieu": res.nom_du_lieu = row.getValue(String.class); break;
                case "adresse": res.adresse = row.getValue(String.class); break;
                case "telephone_du_lieu": res.telephone_du_lieu = row.getValue(String.class); break;
                case "dates": res.dates = row.getValue(String.class); break;
                case "image": res.image = row.getValue(String.class); break;
                default:
            }
        }
        return res;
    }
}
