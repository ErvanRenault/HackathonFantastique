package com.mitic.ervan.hackathonfantastique.event;


import android.support.v4.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.mitic.ervan.hackathonfantastique.data.Evenement;
import com.mitic.ervan.hackathonfantastique.R;

import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Event.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Event#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Event extends Fragment {

    private View myInflatedView;

    private static final String TITRE_EVENT = "param1";

    // TODO: Rename and change types of parameters
    private static Evenement evenementStatic;
    private OnFragmentInteractionListener mListener;


    public Event() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Event newInstance(Evenement evenement) {
        Event fragment = new Event();
        evenementStatic = evenement;
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //TODO
            //ENLEVER LE STATIC
            // titreEvent = getArguments().getString(TITRE_EVENT);
            // Log.d("-----------------", "onCreate: "+ titreEvent);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myInflatedView = inflater.inflate(R.layout.fragment_event, container, false);
        ((TextView)myInflatedView.findViewById(R.id.nomevent)).setText(evenementStatic.fields.titre_fr);
        ((TextView)myInflatedView.findViewById(R.id.adresseevent)).setText(evenementStatic.fields.adresse);
        ((TextView)myInflatedView.findViewById(R.id.dateevent)).setText(evenementStatic.fields.date_debut);
        ((TextView)myInflatedView.findViewById(R.id.heureevent)).setText(evenementStatic.fields.resume_horaires_fr);
        ((TextView)myInflatedView.findViewById(R.id.telephoneevent)).setText(evenementStatic.fields.telephone_du_lieu);
        if(evenementStatic.fields.image != null) {
            new DownloadImageTask((ImageView) myInflatedView.findViewById(R.id.imageView3)).execute(evenementStatic.fields.image);
        }
        if(evenementStatic.fields.organisateur != null)
            ((TextView)myInflatedView.findViewById(R.id.nomorganisateur)).setText("Organisé par : " + evenementStatic.fields.organisateur);
        else
            ((TextView)myInflatedView.findViewById(R.id.nomorganisateur)).setText("");
        if(evenementStatic.fields.lien_d_inscription != null)
            ((TextView)myInflatedView.findViewById(R.id.adresseEmail)).setText(evenementStatic.fields.lien_d_inscription);
        else
            ((TextView)myInflatedView.findViewById(R.id.adresseEmail)).setText("");


        Button mapButton = (Button) myInflatedView.findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                onButtonPressed(evenementStatic, 1);
            }
        });

        TextView phone = (TextView) myInflatedView.findViewById(R.id.telephoneevent);
        phone.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                onButtonPressed(evenementStatic, 2);
            }
        });

        TextView email = (TextView) myInflatedView.findViewById(R.id.adresseEmail);
        email.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                onButtonPressed(evenementStatic, 3);
            }
        });

        return myInflatedView;
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Evenement event, int action) {
        if (mListener != null) {
            mListener.onFragmentInteraction(event, action);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Evenement event, int action);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
