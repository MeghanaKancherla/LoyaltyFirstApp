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

public class MyAdapter extends ArrayAdapter<Transaction> {

    public MyAdapter(@NonNull Context context, int resource, List<Transaction> items) {
        super(context, resource, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.transaction_row_item, parent, false);
        }

        TextView col1 = convertView.findViewById(R.id.tref);
        TextView col2 = convertView.findViewById(R.id.date);
        TextView col3 = convertView.findViewById(R.id.points);
        TextView col4 = convertView.findViewById(R.id.amount);

        Transaction item = getItem(position);
        col1.setText(item.getTref());
        col2.setText(item.getDate());
        col3.setText(item.getPoints());
        col4.setText(item.getAmount());

        if (position == 0){
            convertView.setBackgroundColor(Color.rgb(230, 230, 250));
        }
        else{
            convertView.setBackgroundColor(Color.WHITE);
        }
        return convertView;
    }
}
