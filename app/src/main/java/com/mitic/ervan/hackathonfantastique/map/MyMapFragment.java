package com.mitic.ervan.hackathonfantastique.map;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mitic.ervan.hackathonfantastique.R;


/**
 * Created by ervan on 29/11/16.
 */

public class MyMapFragment extends Fragment implements OnMapReadyCallback {

    private float lat;
    private float lng;
    private String adresse;

    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;


    public static MyMapFragment newInstance(float lat, float lng, String adresse) {

        MyMapFragment fragment = new MyMapFragment();
        Bundle args = new Bundle();
        args.putFloat("latitude", lat);
        args.putFloat("longitude", lng);
        args.putString("adresse", adresse);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            lat = getArguments().getFloat("latitude");
            lng = getArguments().getFloat("longitude");
            adresse = getArguments().getString("adresse");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_map_recherche_event, container, false);

            mMapView = (MapView) mView.findViewById(R.id.map);
            mMapView.onCreate(savedInstanceState);
            mMapView.onResume();
            mMapView.getMapAsync(this);

        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng position = new LatLng(this.lat, this.lng);
        MarkerOptions marker = new MarkerOptions().position(position).title("Evenement Cité de la Science")
                .snippet(this.adresse);
        googleMap.addMarker(marker);
        //Zoom in and animate the camera.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 14));


        /**mGoogleMap = googleMap;
         googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

         googleMap.addMarker(new MarkerOptions().position(new LatLng(40.689247, -74.044502)).title("Statue de la liberté"));
         CameraPosition Liberty = CameraPosition.builder().target(new LatLng(40.689247, -74.044502)).zoom(16).bearing(0).tilt(45).build();
         googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));**/
    }

}
