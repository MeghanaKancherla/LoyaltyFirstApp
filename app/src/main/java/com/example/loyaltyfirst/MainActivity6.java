package com.example.loyaltyfirst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class MainActivity6 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        Spinner txnSpinner = findViewById(R.id.txnSpinner);
        TextView points = findViewById(R.id.txnPointsTV);
        TextView famId = findViewById(R.id.familyIdTV);
        TextView famPercent = findViewById(R.id.familyPercentTV);
        Button addPercentage = findViewById(R.id.addPointsBtn);

        Intent i = getIntent();
        String cid = i.getStringExtra("cidFam");
        final String[] fid = {""};
        final String[] addPoints = {""};
        ArrayList<String> trefs = new ArrayList<String>();

        RequestQueue queue = Volley.newRequestQueue(MainActivity6.this);
        String url = "http://10.0.2.2:8080/loyaltyfirst/Transactions.jsp?cid="+cid;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String response = s.trim();
                String rows[] = response.split("<BR>");
                for(int i=0; i<rows.length; i++){
                    String cols[] = rows[i].split(",");
                    trefs.add(cols[0]);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity6.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, trefs);
                txnSpinner.setAdapter(adapter);
                txnSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String tref = parent.getSelectedItem().toString();
                        String url1 = "http://10.0.2.2:8080/loyaltyfirst/SupportFamilyIncrease.jsp?cid="+cid+"&tref='"+tref+"'";
                        StringRequest request1 = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                String response1 = s.trim();
                                String famRows[] = response1.split("<BR>");
                                for(int j=0;j<famRows.length; j++){
                                    String famCols[] = famRows[j].split(",");
                                    fid[0] = famCols[0];
                                    addPoints[0] = famCols[2];

                                    famId.setText(famCols[0]);
                                    famPercent.setText(famCols[1]);
                                    points.setText(famCols[2]);
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(MainActivity6.this, "Some error occured", Toast.LENGTH_LONG).show();
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
                Toast.makeText(MainActivity6.this, "Some error occured", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request);

        addPercentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity6.this, fid[0]+addPoints[0] , Toast.LENGTH_LONG).show();
                String url2 = "http://10.0.2.2:8080/loyaltyfirst/FamilyIncrease.jsp?fid="+fid[0]+"&cid="+cid+"&npoints="+addPoints[0];
                StringRequest request2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(MainActivity6.this, s , Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(MainActivity6.this, "Some error occured", Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(request2);
            }
        });
    }
}