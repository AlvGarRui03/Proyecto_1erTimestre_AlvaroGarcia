package com.example.proyecto.controler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.proyecto.R;
import com.example.proyecto.fragments.AjustesFragmento;

public class ActividadFragmento extends AppCompatActivity {
    @Override
    //Le pasamos el fragmento en el onCreate
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_fragmento);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedor_ajustes, new AjustesFragmento())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }
    @Override
    //Comprobamos que si le da a la opción de salir del fragmento vuelva a la actividad anterior
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /*
    public void cargarPreferencias(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        ConstraintLayout ActividadPrincipal = findViewById(R.id.layoutprincipal);
        ConstraintLayout ActividadLogin = findViewById(R.id.layout_login);
        int i = Integer.parseInt(sharedPreferences.getString("key_color",""));
        switch (i){
            case 0:
                ActividadPrincipal.setBackgroundResource(R.color.rojo);
                ActividadLogin.setBackgroundResource(R.color.rojo);
                break;
            case 1:
                ActividadPrincipal.setBackgroundResource(R.color.verde);
                ActividadLogin.setBackgroundResource(R.color.verde);
                break;
            case 2:
                ActividadPrincipal.setBackgroundResource(R.color.azul);
                ActividadLogin.setBackgroundResource(R.color.azul);
                break;
            case 3:
                ActividadPrincipal.setBackgroundResource(R.color.rosa);
                ActividadLogin.setBackgroundResource(R.color.rosa);
                break;
            case 4:
                ActividadPrincipal.setBackgroundResource(R.color.amarillo);
                ActividadLogin.setBackgroundResource(R.color.amarillo);
                break;


        }


    }*/
}