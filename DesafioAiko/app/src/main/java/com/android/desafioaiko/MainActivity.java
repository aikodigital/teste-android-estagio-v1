package com.android.desafioaiko;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Properties;


public class MainActivity extends AppCompatActivity {

    private Button btnToLines;
    private Button btnToLinesInfo;
    private Button btnToSchedules;
    private Button btnToBusStops;

    // Método para autenticar na API do Olho Vivo
    public void Authenticate(){
        RequestQueue queue = Volley.newRequestQueue(this);
        /*
         O token da API é armazenado no local.properties,
         que não é acessivel pelo gerenciador de versionamento
         */
        String token = getResources().getString(R.string.OLHO_VIVO_API_KEY);
        String url = "https://aiko-olhovivo-proxy.aikodigital.io/Login/Autenticar?token="+token;
        StringRequest PostRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("SUCCESS", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", error.getLocalizedMessage());
            }
        });
        queue.add(PostRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnToLines =  findViewById(R.id.btnLines);
        btnToLinesInfo = findViewById(R.id.btnInfo);
        btnToSchedules = findViewById(R.id.btnSchedule);
        btnToBusStops = findViewById(R.id.btnBusStops);
        Authenticate();


        btnToLines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BusLines.class));
            }
        });
        btnToLinesInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BusLinesInfo.class));
            }
        });
        btnToSchedules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BusSchedule.class));
            }
        });
        btnToBusStops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BusStopInfo.class));
            }
        });
    }
}