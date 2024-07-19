package com.example.loyaltyfirst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        TextView prizeDescription = findViewById(R.id.prizeDesc);
        TextView pointsNeed = findViewById(R.id.pointsNeeded);
        ListView prizeList = findViewById(R.id.prizeList);
        Spinner prizeSpinner = findViewById(R.id.prizeSpinner);

        Intent i = getIntent();
        String cid = i.getStringExtra("cidPrize");

        RequestQueue queue = Volley.newRequestQueue(MainActivity5.this);
        String url = "http://10.0.2.2:8080/loyaltyfirst/PrizeIds.jsp?cid="+cid;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                ArrayList<PrizeDescription> pdList = new ArrayList<>();
                String response = s.trim();
                String rows[] = response.split("<BR>");
                ArrayList<String> ids = new ArrayList<String>(Arrays.asList(rows));
                ArrayAdapter<String> prizeIds = new ArrayAdapter<String>(MainActivity5.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, ids);
                prizeSpinner.setAdapter(prizeIds);

                prizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        pdList.clear();
                        pdList.add(new PrizeDescription("Redemption Date", "Exchange Center"));
                        String selectedPrizeId = parent.getSelectedItem().toString();
                        String url1 = "http://10.0.2.2:8080/loyaltyfirst/RedemptionDetails.jsp?prizeid="+selectedPrizeId+"&cid="+cid;
                        StringRequest request1 = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                String prizeDetails = s.trim();
                                String prizeDetailsRows[] = prizeDetails.split("<BR>");
                                for(int i=0; i<prizeDetailsRows.length; i++){
                                    String prizeDetailsColumns[] = prizeDetailsRows[i].split(",");
                                    prizeDescription.setText(prizeDetailsColumns[0]);
                                    pointsNeed.setText(prizeDetailsColumns[1]);
                                    String redeemDate[] = prizeDetailsColumns[2].split(" ");
                                    pdList.add(new PrizeDescription(redeemDate[0],prizeDetailsColumns[3]));

                                    MyAdapter3 adapter3 = new MyAdapter3(MainActivity5.this, R.layout.prizedetails_row_item, pdList);
                                    prizeList.setAdapter(adapter3);
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(MainActivity5.this, "Some error occured", Toast.LENGTH_LONG).show();
                            }
                        });
                        queue.add(request1);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity5.this, "Some error occured", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request);
    }
}