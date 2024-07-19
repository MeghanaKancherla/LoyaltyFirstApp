package com.example.loyaltyfirst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView nameT = findViewById(R.id.userNameTV);
        TextView pointsT = findViewById(R.id.pointsTV);
        Button txnsB = findViewById(R.id.txnBtn);
        Button txnDetailsB = findViewById(R.id.txndetailsBtn);
        Button redemptionHistoryB = findViewById(R.id.redeemBtn);
        Button addFamily = findViewById(R.id.familyBtn);
        Button exit = findViewById(R.id.exitBtn);
        ImageView profileImg = findViewById(R.id.imageView);
        Intent i = getIntent();
        String cid = i.getStringExtra("cid");

        RequestQueue queue = Volley.newRequestQueue(MainActivity2.this);
        String url = "http://10.0.2.2:8080/loyaltyfirst/Info.jsp?cid="+cid;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String response = s.trim();
                String rows[] = response.split(",");
                nameT.setText(rows[0]);
                pointsT.setText(rows[1]);

                String imageURL = "http://10.0.2.2:8080/loyaltyfirst/images/"+cid+".jpg";
                ImageRequest request2 = new ImageRequest(imageURL, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        profileImg.setImageBitmap(bitmap);
                    }
                }, 0, 0, null, null);
                queue.add(request2);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity2.this, "Some error occured", Toast.LENGTH_LONG).show();
            }
        });

        queue.add(request);

        txnsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(MainActivity2.this, MainActivity3.class);
                i2.putExtra("cid", cid);
                startActivity(i2);
            }
        });

        txnDetailsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3 = new Intent(MainActivity2.this, MainActivity4.class);
                i3.putExtra("cidTxn", cid);
                startActivity(i3);
            }
        });

        redemptionHistoryB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i4 = new Intent(MainActivity2.this, MainActivity5.class);
                i4.putExtra("cidPrize", cid);
                startActivity(i4);
            }
        });

        addFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i5 = new Intent(MainActivity2.this, MainActivity6.class);
                i5.putExtra("cidFam", cid);
                startActivity(i5);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i6 = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(i6);
            }
        });

    }
}