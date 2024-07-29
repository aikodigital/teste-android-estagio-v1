package com.example.bustrackingsp;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bustrackingsp.api.utils.ApiRequest;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bustrackingsp.databinding.ActivityMainBinding;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void loginButton(View view) throws IOException {
        ApiRequest.login();
    }

    public void previsaoChegada(View view){
        EditText inputParada;
        EditText inputLinha;
        inputParada = (EditText) findViewById(R.id.codigoParadaInput);
        inputLinha = (EditText) findViewById(R.id.codigoLinhaInput);

        ApiRequest.request("/Previsao/Parada?codigoParada="+ inputParada.getText());
        // Previsao/Parada?codigoParada=150015738
    }

    //TODO: consertar erro ao clicar no botão sem fazer login e, potencialmente ao receber um Json vazio.
    public void linhasButton(View view) throws IOException {
        EditText inputText;
        inputText = (EditText) findViewById(R.id.textInput);
        ApiRequest.request("/Linha/Buscar?termosBusca=" + inputText.getText());
    }

    //TODO: consertar erro ao clicar no botão sem fazer login e, potencialmente ao receber um Json vazio.
    public void paradasButton(View view) throws IOException {
        EditText inputText;
        inputText = (EditText) findViewById(R.id.textInput);

        ApiRequest.request("/Parada/Buscar?termosBusca=" + inputText.getText());
    }
    
    //TODO: consertar erro ao clicar no botão sem fazer login e, potencialmente ao receber um Json vazio.
    public void posicoesButton(View view) throws IOException {
        ApiRequest.request("/Posicao");
    }
}