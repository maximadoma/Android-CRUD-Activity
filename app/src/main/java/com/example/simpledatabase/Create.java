package com.example.simpledatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Create extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Button update_dataPage = (Button) findViewById(R.id.btn_update);
        Button retrieve_dataPage = (Button) findViewById(R.id.btn_retrieve);

        update_dataPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Update.class);
                startActivity(intent);
            }
        });

        retrieve_dataPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Retrieve.class);
                startActivity(intent);
            }
        });




        EditText txt_username = (EditText) findViewById(R.id.txt_username);
        EditText txt_password = (EditText) findViewById(R.id.txt_password);
        EditText txt_email = (EditText) findViewById(R.id.txt_email);
        Button btn_save = (Button) findViewById(R.id.btn_db);

        btn_save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String uname = txt_username.getText().toString();
                String pword = txt_password.getText().toString();
                String email = txt_email.getText().toString();

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://192.168.0.108/android_crud/create.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      if (response.equals("Success")){
                          Toast.makeText(Create.this, "Data Added", Toast.LENGTH_SHORT).show();
                      }else{
                          Toast.makeText(Create.this, "Data Failed to Add to Database", Toast.LENGTH_SHORT).show();
                      }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Create.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> params = new HashMap<>();
                        params.put("uname", uname);
                        params.put("pword", pword);
                        params.put("email", email);
                        return params;
                    }
                };
                queue.add(stringRequest);

            }
        });


    }
}