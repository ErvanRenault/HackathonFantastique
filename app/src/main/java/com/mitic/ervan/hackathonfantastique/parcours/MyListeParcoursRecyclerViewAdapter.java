package com.mitic.ervan.hackathonfantastique.parcours;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mitic.ervan.hackathonfantastique.R;
import com.mitic.ervan.hackathonfantastique.data.Parcours;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link com.mitic.ervan.hackathonfantastique.data.Parcours} and makes a call to the
 * specified {@link ListeParcours.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyListeParcoursRecyclerViewAdapter extends RecyclerView.Adapter<MyListeParcoursRecyclerViewAdapter.ViewHolder> {

    private final List<com.mitic.ervan.hackathonfantastique.data.Parcours> mValues;
    private final ListeParcours.OnListFragmentInteractionListener mListener;

    public MyListeParcoursRecyclerViewAdapter(List<Parcours> items, ListeParcours.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public MyListeParcoursRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_evenement, parent, false);
        return new MyListeParcoursRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyListeParcoursRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).getId());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public Parcours mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
