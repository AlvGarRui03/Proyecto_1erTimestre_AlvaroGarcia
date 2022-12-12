package com.example.proyecto.controler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;
import com.example.proyecto.controler.ActividadLogIn;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.proyecto.R;
import com.example.proyecto.fragments.AjustesFragmento;

import eltos.simpledialogfragment.SimpleDialog;

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
    public void onResume(){
        super.onResume();
        //Actualizamos el color de las preferencias
        actualizaciondeColor();
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
    //Metodo para devolver la clave de color seleccionado
    public static int cargarPreferencias(Context contexto){
        int i= 1 ;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(contexto);
        i = Integer.parseInt(sharedPreferences.getString("key_color",""));

        return i;
    }
    //Metodo que actualiza el color del fragment de android
    public void actualizaciondeColor(){
        int color = ActividadFragmento.cargarPreferencias(this);
        ConstraintLayout actividadLogin = (ConstraintLayout) findViewById(R.id.contenedor_ajustes);
        switch (color){
            case 0:
                actividadLogin.setBackgroundColor(Color.RED);
                break;
            case 1:
                actividadLogin.setBackgroundColor(Color.GREEN);
                break;
            case 2:
                actividadLogin.setBackgroundColor(Color.rgb(154,242,253));
                break;
            case 3:
                actividadLogin.setBackgroundColor(Color.MAGENTA);
                break;
            case 4:
                actividadLogin.setBackgroundColor(Color.YELLOW);
                break;
        }
    }

    /*
    public void cargarPreferencias(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        ConstraintLayout ActividadPrincipal = (ConstraintLayout) findViewById(R.id.layoutprincipal);
        ConstraintLayout ActividadLogin =(ConstraintLayout) findViewById(R.id.layout_login);
        int i = Integer.parseInt(sharedPreferences.getString("key_color",""));
        switch (i){
            case 0:
                ActividadPrincipal.setBackgroundColor(Color.RED);
                ActividadLogin.setBackgroundColor(Color.RED);
                break;
            case 1:
                ActividadPrincipal.setBackgroundColor(Color.GREEN);
                ActividadLogin.setBackgroundColor(Color.GREEN);
                break;
            case 2:
                ActividadPrincipal.setBackgroundColor(Color.BLUE);
                ActividadLogin.setBackgroundColor(Color.BLUE);
                break;
            case 3:
                ActividadPrincipal.setBackgroundColor(Color.MAGENTA);
                ActividadLogin.setBackgroundColor(Color.MAGENTA);
                break;
            case 4:
                ActividadPrincipal.setBackgroundColor(Color.YELLOW);
                ActividadLogin.setBackgroundColor(Color.YELLOW);
                break;


        }


    }*/
}