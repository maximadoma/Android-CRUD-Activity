package com.example.simpledatabase;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Retrieve extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);

        Button update_dataPage = (Button) findViewById(R.id.btn_update);
        Button retrieve_dataPage = (Button) findViewById(R.id.btn_retrieve);

        update_dataPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Update.class);
                startActivity(intent);
            }
        });




        EditText txt_id = (EditText) findViewById(R.id.txt_id);
        TextView lbl_username =findViewById(R.id.txt_username);
        TextView lbl_password =findViewById(R.id.txt_password);
        TextView lbl_email = findViewById(R.id.txt_email);
        Button btn_retrieve = (Button) findViewById(R.id.btn_retrieve);

        btn_retrieve.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                String id = txt_id.getText().toString().trim();


                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://192.168.0.108/android_crud/retrieve.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.startsWith("<")) {
                            // Response is not JSON, handle the error
                            Toast.makeText(Retrieve.this, "Error: Invalid Response", Toast.LENGTH_SHORT).show();
                        }

                        try{
                            JSONArray jsonArray = new JSONArray(response);

                            for(int i= 0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String user_name = jsonObject.getString("user_username");
                                String user_pass= jsonObject.getString("user_password");
                                String user_email= jsonObject.getString("user_email");

                                lbl_username.setText("Username: "+user_name);
                                lbl_password.setText("Password: "+user_pass);
                                lbl_email.setText("Email: "+user_email);
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Retrieve.this, "Error in Response", Toast.LENGTH_SHORT).show();
                            System.out.print(response);
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Retrieve.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> params = new HashMap<>();
                        params.put("id", id);
                        return params;
                    }
                };
                queue.add(stringRequest);

            }
        });

    }
}