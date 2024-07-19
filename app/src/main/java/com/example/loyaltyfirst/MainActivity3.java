package com.example.loyaltyfirst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        ListView lv = findViewById(R.id.listView);
        ArrayList<Transaction> txns = new ArrayList<Transaction>();

        Intent i = getIntent();
        String cid = i.getStringExtra("cid");

        RequestQueue queue = Volley.newRequestQueue(MainActivity3.this);
        String url = "http://10.0.2.2:8080/loyaltyfirst/Transactions.jsp?cid="+cid;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String response = s.trim();
                String rows[] = response.split("<BR>");
                txns.add(new Transaction("TREF", "DATE", "POINTS", "AMOUNT"));
                for(int i=0; i<rows.length; i++){
                    String cols[] = rows[i].split(",");
                    String date[] = cols[1].split(" ");
                    txns.add(new Transaction(cols[0], date[0], cols[2], cols[3]));
                }
                lv.setAdapter(null);
                MyAdapter adapter = new MyAdapter(MainActivity3.this, R.layout.transaction_row_item, txns);
                lv.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity3.this, "Some error occured", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request);

    }
}