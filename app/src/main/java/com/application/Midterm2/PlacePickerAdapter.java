package com.application.Midterm2;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PlacePickerAdapter extends RecyclerView.Adapter<PlacePickerAdapter.ViewHolder> {

    private Context context;
    private List<FoursquareResults> results;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nme;
        TextView addr;
        TextView rtng;
        TextView dist;
        String id;
        double lati;
        double longi;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            nme = (TextView)v.findViewById(R.id.placePickerItemName);
            addr = (TextView)v.findViewById(R.id.placePickerItemAddress);
            rtng = (TextView)v.findViewById(R.id.placePickerItemRating);
            dist = (TextView)v.findViewById(R.id.placePickerItemDistance);
        }
        @Override
        public void onClick(View v) {
            Context context = nme.getContext();
            Intent i = new Intent(context, MapsActivity.class);
            i.putExtra("name", nme.getText());
            i.putExtra("ID", id);
            i.putExtra("latitude", lati);
            i.putExtra("longitude", longi);
            context.startActivity(i);
        }
    }

    public PlacePickerAdapter(Context context, List<FoursquareResults> results) {
        this.context = context;
        this.results = results;
    }
    @Override
    public PlacePickerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place_picker, parent, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        double ratingRaw = results.get(position).venue.rating;
        if (ratingRaw >= 9.0) {
            holder.rtng.setBackgroundColor(ContextCompat.getColor(context, R.color.FSQKale));
        } else if (ratingRaw >= 8.0) {
            holder.rtng.setBackgroundColor(ContextCompat.getColor(context, R.color.FSQGuacamole));
        } else if (ratingRaw >= 7.0) {
            holder.rtng.setBackgroundColor(ContextCompat.getColor(context, R.color.FSQLime));
        } else if (ratingRaw >= 6.0) {
            holder.rtng.setBackgroundColor(ContextCompat.getColor(context, R.color.FSQBanana));
        } else if (ratingRaw >= 5.0) {
            holder.rtng.setBackgroundColor(ContextCompat.getColor(context, R.color.FSQOrange));
        } else if (ratingRaw >= 4.0) {
            holder.rtng.setBackgroundColor(ContextCompat.getColor(context, R.color.FSQMacCheese));
        } else {
            holder.rtng.setBackgroundColor(ContextCompat.getColor(context, R.color.FSQStrawberry));
        }
        holder.nme.setText(results.get(position).venue.name);
        holder.addr.setText(results.get(position).venue.location.address);
        holder.rtng.setText(Double.toString(ratingRaw));
        holder.dist.setText(Integer.toString(results.get(position).venue.location.distance) + "m");
        holder.id = results.get(position).venue.id;
        holder.lati = results.get(position).venue.location.lat;
        holder.longi = results.get(position).venue.location.lng;
    }
    @Override
    public int getItemCount() {
        return results.size();
    }
}