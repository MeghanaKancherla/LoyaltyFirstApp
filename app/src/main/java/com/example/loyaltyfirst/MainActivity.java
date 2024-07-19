package com.example.loyaltyfirst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText username = findViewById(R.id.editTextText);
        EditText password = findViewById(R.id.editTextTextPassword);
        Button loginBtn = findViewById(R.id.loginBtn);
        TextView errorMessage = findViewById(R.id.errorMessage);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = username.getText().toString();
                String pass = password.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url = "http://10.0.2.2:8080/loyaltyfirst/login?user="+uname+"&pass="+pass;
                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String response = s.trim();
                        String rows[] = response.split(":");
                        String message = rows[0];
                        String cid = rows[1];
                        if (message.equalsIgnoreCase("yes")){
                            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                            intent.putExtra("cid", cid);
                            startActivity(intent);
                        }
                        else {
                            errorMessage.setText("Your credentials are incorrect!");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(MainActivity.this, "Error connecting to database!", Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(request);
            }
        });


    }
}