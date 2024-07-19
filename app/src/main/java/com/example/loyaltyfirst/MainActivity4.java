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

public class MainActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Spinner trefSpinner = findViewById(R.id.trefSpinner);
        TextView txnDateTV = findViewById(R.id.TxnDate);
        TextView txnPointsTV = findViewById(R.id.txnPoints);
        ListView txnListView = findViewById(R.id.txnList);

        Intent i = getIntent();
        String cid = i.getStringExtra("cidTxn");
        ArrayList<String> trefs = new ArrayList<String>();
        ArrayList<TransactionDetails> trefDetail = new ArrayList<TransactionDetails>();

        RequestQueue queue = Volley.newRequestQueue(MainActivity4.this);
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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity4.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, trefs);
                trefSpinner.setAdapter(adapter);
                trefSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedTref = parent.getSelectedItem().toString();
                        String url2 = "http://10.0.2.2:8080/loyaltyfirst/TransactionDetails.jsp?tref='"+selectedTref+"'";
                        StringRequest request2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                String txnDate = "";
                                String txnPoints = "";
                                String response2 = s.trim();
                                String txnrows[] = response2.split("<BR>");
                                trefDetail.clear();
                                trefDetail.add(new TransactionDetails("PRODUCT NAME", "POINTS", "QUANTITY"));
                                for(int j=0; j< txnrows.length; j++){
                                    String txncols[] = txnrows[j].split(",");
                                    txnDate = txncols[0];
                                    String onlyDate[] = txnDate.split(" ");
                                    txnDate = onlyDate[0];
                                    txnPoints = txncols[1];
                                    trefDetail.add(new TransactionDetails(txncols[2], txncols[3], txncols[4]));
                                }
                                txnDateTV.setText(txnDate);
                                txnPointsTV.setText(txnPoints+" Points");

                                MyAdapter2 adapter2 = new MyAdapter2(MainActivity4.this, R.layout.txndetails_row_item, trefDetail);
                                txnListView.setAdapter(adapter2);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(MainActivity4.this, "Some error occured!", Toast.LENGTH_LONG).show();
                            }
                        });

                        queue.add(request2);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity4.this, "Some error occured", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request);
    }
}