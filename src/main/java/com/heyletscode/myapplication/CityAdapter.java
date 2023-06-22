package com.heyletscode.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CityAdapter extends ArrayAdapter<City> {
    private Context context;
    private int resource;

    public CityAdapter(@NonNull Context context, int resource, @NonNull ArrayList<City> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        convertView = layoutInflater.inflate(resource, parent, false);

        ImageView imageView = convertView.findViewById(R.id.image);

        TextView cityName = convertView.findViewById(R.id.cityName);

        imageView.setImageResource(getItem(position).getImage());

        cityName.setText(getItem(position).getName());

        return convertView;

    }
}
