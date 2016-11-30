package com.mitic.ervan.hackathonfantastique.parcours;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mitic.ervan.hackathonfantastique.R;
import com.mitic.ervan.hackathonfantastique.data.Data;
import com.mitic.ervan.hackathonfantastique.data.Evenement;
import com.mitic.ervan.hackathonfantastique.data.Field;
import com.mitic.ervan.hackathonfantastique.parcours.MyEventParcoursRecyclerViewAdapter;

import java.util.ArrayList;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Parcours.OnListFragmentInteractionListener}
 * interface.
 */
public class Parcours extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private Parcours.OnListFragmentInteractionListener mListener;
    private static com.mitic.ervan.hackathonfantastique.data.Parcours parcours;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public Parcours() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static Parcours newInstance(int columnCount, com.mitic.ervan.hackathonfantastique.data.Parcours donnee) {
        Parcours fragment = new Parcours();
        parcours = donnee;
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parcours, container, false);
        RecyclerView recycler=(RecyclerView)((LinearLayout)((FrameLayout)view).getChildAt(0)).getChildAt(1);
        // Set the adapter
        if (recycler instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = recycler;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyEventParcoursRecyclerViewAdapter(parcours.getEvenements(), mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Parcours.OnListFragmentInteractionListener) {
            mListener = (Parcours.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Evenement event);
    }
}