package com.example.proyecto.io;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConexionAnime {
    //URL BASE PARA BUSCAR EN LA API
    private static final String URL_BASE =" https://api.jikan.moe/v4/anime?q=";
    private static final String FINAL_URL="&sfw";

    //Metodo para hacer una llamada a la API con el filtro del nombre del anime
    public static String getRequest(String nombreAnime ) {

        HttpURLConnection http = null;
        String contenido = null;
        try {
            //Creamos la URL
            URL url = new URL( URL_BASE + nombreAnime + FINAL_URL);
            //Empezamos la conexion
            http = (HttpURLConnection) url.openConnection();
            //Le pasamos el tipo de devolución
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Accept", "application/json");
            //Si el codigo de respuesta es correcto tomamos el contenido en una variable
            if( http.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader( http.getInputStream() ));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                contenido = sb.toString();
                reader.close();
            }


        }
        //Controlamos la excepción
        catch(Exception e) {
            Log.i("I",e.getMessage());
        }
        finally {
            if( http != null ) http.disconnect();
        }
        //Devolvemos la variable con el contenido
        return contenido;
    }

    }


