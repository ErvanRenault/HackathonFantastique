package com.mitic.ervan.hackathonfantastique.evenement;

/**
 * Created by ce on 29/11/16.
 */

public class Evenement {
    public Geometry geometry;
    public Field fields;
    public String datasetid;
    public String record_timestamp;
    public String recordid;

    public String toString () {
        return "\nEvenement: "
                + "\ndatasetid: " + datasetid
                + ", \nrecord_timestamp: " + record_timestamp
                + ", \nrecordid: " + recordid
                + ", \n"+geometry.toString()
                + ", \n"+fields.toString();
    }
}
