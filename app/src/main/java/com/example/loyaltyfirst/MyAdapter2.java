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

public class MyAdapter2 extends ArrayAdapter<TransactionDetails> {
    public MyAdapter2(@NonNull Context context, int resource, @NonNull List<TransactionDetails> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.txndetails_row_item, parent, false);
        }

        TextView colt1 = convertView.findViewById(R.id.prodName);
        TextView colt2 = convertView.findViewById(R.id.prodQuantity);
        TextView colt3 = convertView.findViewById(R.id.prodPoints);

        TransactionDetails item = getItem(position);
        colt1.setText(item.getProductName());
        colt2.setText(item.getQuantity());
        colt3.setText(item.getPoints());

        if (position == 0){
            convertView.setBackgroundColor(Color.rgb(230, 230, 250));
        }
        else{
            convertView.setBackgroundColor(Color.WHITE);
        }
        return convertView;
    }
}
