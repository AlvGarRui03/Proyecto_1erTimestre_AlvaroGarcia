package com.example.proyecto.fragments;

import android.os.Bundle;

import com.example.proyecto.R;
import androidx.preference.PreferenceFragmentCompat;

public class AjustesFragmento extends PreferenceFragmentCompat {
    @Override
   //Creamos las preferencias
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferencias, rootKey);
    }
}
