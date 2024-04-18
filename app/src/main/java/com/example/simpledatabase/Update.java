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

public class Update extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        EditText txt_id = (EditText) findViewById(R.id.txt_id);
        EditText txt_username = (EditText) findViewById(R.id.txt_username);
        EditText txt_password = (EditText) findViewById(R.id.txt_password);
        EditText txt_email = (EditText) findViewById(R.id.txt_email);
        Button btn_update = (Button) findViewById(R.id.btn_update);

        Button insert_dataPage = (Button) findViewById(R.id.btn_db);

        insert_dataPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Create.class);
                startActivity(intent);
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String id = txt_id.getText().toString();
                String uname = txt_username.getText().toString();
                String pword = txt_password.getText().toString();
                String email = txt_email.getText().toString();

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://192.168.0.108/android_crud/update.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Success")){
                            Toast.makeText(Update.this, "Data Updated", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Update.this, "Data Failed to Update to Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Update.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> params = new HashMap<>();
                        params.put("id", id);
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