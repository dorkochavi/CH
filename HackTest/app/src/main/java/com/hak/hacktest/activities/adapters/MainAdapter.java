package com.hak.hacktest.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hak.hacktest.R;
import com.hak.hacktest.activities.objects.Project;
import com.hak.hacktest.activities.retroFit.API;
import com.hak.hacktest.activities.util.AppClass;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainAdapter extends ArrayAdapter<Project> {

    private Context context;
    private List<Project> myListAdapter;

    public MainAdapter(Context context , List<Project> values) {
        super(AppClass.getAppContext(), R.layout.card, values);
        this.context = context;
        myListAdapter = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = LayoutInflater.from(context).inflate(R.layout.card, parent, false);
        final Project item = myListAdapter.get(position);
        TextView user_name = (TextView)v.findViewById(R.id.name);
        TextView description = (TextView)v.findViewById(R.id.description);
        TextView price = (TextView)v.findViewById(R.id.price);
        ImageView preImage = (ImageView)v.findViewById(R.id.choose_image);
        user_name.setText(item.getUser_name());
        description.setText(item.getDescription());
        String priceString = AppClass.getAppContext().getString(R.string.nis)  + item.getPrice();
        price.setText(priceString);
        Picasso.with(context)
                .load(API.BASE_URL + item.getPath())
                .fit()
                .into(preImage);

        return v;
    }
}
