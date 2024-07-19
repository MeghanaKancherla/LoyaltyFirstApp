package com.example.loyaltyfirst;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MyAdapter3 extends ArrayAdapter<PrizeDescription> {
    public MyAdapter3(@NonNull Context context, int resource, @NonNull List<PrizeDescription> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.prizedetails_row_item, parent, false);
        }

        TextView colp1 = convertView.findViewById(R.id.redempDate);
        TextView colp2 = convertView.findViewById(R.id.center);

        PrizeDescription item = getItem(position);
        colp1.setText(item.getRedemptionDate());
        colp2.setText(item.getCenter());

        if (position == 0){
            convertView.setBackgroundColor(Color.rgb(230, 230, 250));
        }
        else{
            convertView.setBackgroundColor(Color.WHITE);
        }
        return convertView;
    }

}
