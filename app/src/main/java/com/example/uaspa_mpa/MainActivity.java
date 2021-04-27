package com.example.uaspa_mpa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText edt_username, edt_password;
    private String username,password ;
    Button login;
    private RequestQueue mQueue;
    private String url = "http://192.168.43.69/widi/hasil/hasil1.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = password = "";
        edt_username = findViewById(R.id.username);
        edt_password = findViewById(R.id.password);
        login = findViewById(R.id.btn_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    public void login() {
        username = edt_username.getText().toString().trim();
        password = edt_password.getText().toString().trim();

        if (!username.equals("") && !password.equals("")) {
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.contains("success")) {
                        startActivity(new Intent(getApplicationContext(),activity_tampil_data.class));
                    }else {
                        pesan("Username atau password salah");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pesan(error.toString().trim());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("username", username);
                    data.put("password", password);

                    return data;
                }
            };
            Volley.newRequestQueue(this).add(request);

    }
    public void pesan(String isi) {
        Toast.makeText(this, isi, Toast.LENGTH_SHORT).show();
    }
}
}