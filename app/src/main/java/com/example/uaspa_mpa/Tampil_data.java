package com.example.uaspa_mpa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tampil_data extends AppCompatActivity {


    private TextView txtData;
    private RequestQueue mQueue;
    private Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_data);

        mQueue = Volley.newRequestQueue(this);
        txtData = findViewById(R.id.txtData);


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout(view);
            }
        });

        uraiJson();
    }

    public void logout(final Activity activity) {


        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void uraiJson() {
        String url = "http://192.168.43.69/widi/hasil/index.php";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("kegemaran");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject db_perusahaan = jsonArray.getJSONObject(i);
                                String id = db_perusahaan.getString("id");
                                String mahasantri = db_perusahaan.getString("mahasantri");
                                String hobby = db_perusahaan.getString("hobby");

                                txtData.append(id+", "+mahasantri+", "+hobby+"\n\n");
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(activity_tampil_data.this, "", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(activity_tampil_data.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(request);
    }


}
}