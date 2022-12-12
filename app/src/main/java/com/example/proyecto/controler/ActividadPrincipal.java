package com.example.proyecto.controler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.Menu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyecto.model.Anime;
import com.example.proyecto.R;
import com.example.proyecto.adapter.RecyclerAdapter;
import com.example.proyecto.io.ConexionAnime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import eltos.simpledialogfragment.SimpleDialog;

public class ActividadPrincipal extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerAdapter recAdapter;
    ArrayList<Anime> listaAnime = new ArrayList<Anime>();
    String nombreAnime;
    AlertDialog alertDialog;
    String animeSeleccionado =null;
    Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_principal);
        //Tomamos la id del RecyclerView creado
        recyclerView = (RecyclerView) findViewById(R.id.RV_Anime);
        //Añadimos el manager del layout para darle formato
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //Se lo añadimos al recycler
        recyclerView.setLayoutManager(layoutManager);
        //Creamos el adaptador del recyclerView
        recAdapter = new RecyclerAdapter(devolverLista(),this);
        //Se lo pasamos como adaptador
        recyclerView.setAdapter(recAdapter);
        //Metodo listener que nos permitirá obtener el titulo de el anime seleccionado en la tabla
        recAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animeSeleccionado =
                        listaAnime.get(recyclerView.getChildAdapterPosition(view)).getTitulo();
                //Toast para informar al usuario de que ha seleccionado un registro
                toast.makeText(getApplicationContext(), "Ha seleccionado el anime " + animeSeleccionado, Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    //Creación del menú de acción creado en la carpeta menu
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_accion,menu);
        return true;
    }
    @Override
    //Listener del menu
    public boolean onOptionsItemSelected(MenuItem item){
        //Codigo que ejecuta si pulsa el boton de añadir
        if(item.getItemId() == R.id.add){


                //Tomamos el diseño de AlertDialog que contiene un input
                LayoutInflater li = LayoutInflater.from(this);
                View promptsView = li.inflate(R.layout.prompts, null);
                //Creamos el AlertDialogBuild
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        this);

                //Le pasamos el diseño
                alertDialogBuilder.setView(promptsView);
                //Tomamos el texto pasado por el usuario
                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);


                alertDialogBuilder
                        .setCancelable(false)
                        //Si pulsa aceptar se buscan los animes de la API y se introducen en la tabla
                        .setPositiveButton("Aceptar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        nombreAnime = userInput.getText().toString();
                                        if(nombreAnime.length()>=2){
                                            addAnime(nombreAnime);
                                        }else{
                                            alertDialog.setTitle("Debe introducir un nombre");
                                            alertDialog.setMessage("El nombre del anime no puede estar vacio y debe contener al menos 2 letras");
                                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //Se cierra el dialog
                                                    dialog.dismiss();
                                                }
                                            });
                                            //Se muestra el dialog
                                            alertDialog.show();
                                        }
                                    }
                                })
                        //Si pulsa cancelar no se ejecuta ninguna acción
                        .setNegativeButton("Cancelar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        //Se cancela el dialogo
                                        dialog.cancel();
                                    }
                                });

                //Se crea el dialogo
                AlertDialog alertDialog = alertDialogBuilder.create();
                //Se muestra el dialogo
                alertDialog.show();

            }
        //Opcion del menu eliminar
        if(item.getItemId() == R.id.delete) {
            //Si ha seleccionado un anime de la tabla
            if (animeSeleccionado != null) {
                //Se le informa que perdera la información
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("¿Desea eliminar el registro? Se perderán los datos guardados en la tabla")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            //Si acepta se elimina el anime de la lista y se actualiza la tabla
                            public void onClick(DialogInterface dialog, int id) {
                                for (int i = 0; i < listaAnime.size(); i++) {

                                    if (listaAnime.get(i).getTitulo().equals(animeSeleccionado)) {
                                        listaAnime.remove(i);
                                        actualizarLista();
                                    }
                                }
                            }
                        })
                        //En caso de que pulse "No" no se elimina ningun dato
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                toast.makeText(getApplicationContext(),"No se elimino ningun registro",Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                //Implementacion de la biblioteca SimpleDialog para realizar dialogos simples
                //En este caso indicamos que debe pulsar un registro de la tabla para eliminarlo
                SimpleDialog.build()
                        .title("Debe pulsar un registro")
                        .msg("Debe pulsar en la lista el registro que desea eliminar")
                        .show(this);
            }
        }
        //Opcion de modificar una descripción
       if(item.getItemId() == R.id.modify){
          //Igual que antes comprobamos que haya seleccionado un registro
           if (animeSeleccionado != null) {
               AlertDialog.Builder builder = new AlertDialog.Builder(this);
               //Le indicamos que va a modificar los datos y estos se perderán
               builder.setMessage("¿Desea modificar la descripción? ")
                       .setCancelable(false)
                       .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                               LayoutInflater layoutInfla = LayoutInflater.from(ActividadPrincipal.this);
                               View vistaModificar = layoutInfla.inflate(R.layout.modificar, null);
                               AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(
                                       ActividadPrincipal.this);


                               alertDialogBuilder2.setView(vistaModificar);

                               final EditText userInput = (EditText) vistaModificar
                                       .findViewById(R.id.editTextDialogUserInput);
                               alertDialogBuilder2
                                       .setCancelable(false)
                                       //Si acepta el anime seleccionado cambiara su descripción por la que introduzca el usuario(+4 caracteres mínimo)
                                       .setPositiveButton("Aceptar",
                                               new DialogInterface.OnClickListener() {
                                                   public void onClick(DialogInterface dialog,int id) {
                                                       String descripcion=null;
                                                       descripcion = userInput.getText().toString();
                                                       if(descripcion!=null){
                                                          for(Anime anime:listaAnime){
                                                              if(anime.getTitulo().equals(animeSeleccionado)){
                                                                  anime.setDescripcion(descripcion);
                                                                  actualizarLista();
                                                              }
                                                          }
                                                       }else{
                                                           alertDialog.setTitle("Debe introducir una descripcion");
                                                           alertDialog.setMessage("La descripcion del anime no puede estar vacia y debe contener al menos 4 letras");
                                                           alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar", new DialogInterface.OnClickListener() {
                                                               public void onClick(DialogInterface dialog, int which) {
                                                                   dialog.dismiss();
                                                               }
                                                           });
                                                           alertDialog.show();
                                                       }
                                                   }
                                               })
                                       .setNegativeButton("Cancelar",
                                               new DialogInterface.OnClickListener() {
                                                   public void onClick(DialogInterface dialog,int id) {
                                                       dialog.cancel();
                                                   }
                                               });


                               AlertDialog alertDialog2 = alertDialogBuilder2.create();
                               alertDialog2.show();
                           }
                       })
                       .setNegativeButton("No", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                               toast.makeText(getApplicationContext(),"No se modificó ningun registro",Toast.LENGTH_SHORT).show();
                           }
                       });
               AlertDialog alert = builder.create();
               alert.show();
           } else {
               SimpleDialog.build()
                       .title("Debe pulsar un registro")
                       .msg("Debe pulsar en la lista el registro que desea eliminar")
                       .show(this);
           }


        }
        return super.onOptionsItemSelected(item);
    }
    //Metodo para añadir un anime
    public void addAnime(String nombre) {
        new publishTask().execute(nombre);

    }
    //Metodo que devuelve la lista
    public List<Anime> devolverLista(){
        return listaAnime;
    }
    //Metodo que notifica al adaptador los cambios en la lista de animes
    public void actualizarLista(){
        recAdapter.notifyDataSetChanged();
    }
    private class publishTask extends AsyncTask<String, Void, String> {
        //Metodo que se ejecuta en el hilo secundario
        @Override
        protected String doInBackground(String... strings) {
            String resultado = null;
            //Pedimos el resultado de la consulta en la API(Formato JSON)
            resultado = ConexionAnime.getRequest(strings[0]);
            System.out.println(resultado);
            return resultado;
        }
        @Override
        //Tomamos el resultado del hilo secundario y lo transformamos a formato String
        protected void onPostExecute(String resultado) {
            try {
                if(resultado != null){
                    Log.d("D","DATOS: "+ resultado);
                    //Tomamos el objeto completo
                    JSONObject totalidad = new JSONObject(resultado);
                    //De ese objeto tomamos el array de datos
                    JSONArray Datos = totalidad.getJSONArray("data");


                     String titulo = "";
                     String descripcion="";
                     String imagenURL="";
                    for(int i=0; i<Datos.length(); i++) {
                        //Tomamos el objeto imagenes dentro del array
                        JSONObject imagenes = Datos.getJSONObject(i).getJSONObject("images");
                        //Tomamos el objeto jpg dentro de imagenes
                        JSONObject jpg = imagenes.getJSONObject("jpg");
                        //Tomamos el titulo del array Datos
                        titulo = Datos.getJSONObject(i).getString("title");
                        //Tomamos la descripcion del array Datos
                        descripcion = Datos.getJSONObject(i).getString("synopsis");
                        //Tomamos la URL de la imagen desde el objeto jpg
                        imagenURL=jpg.getString("image_url");
                        if(descripcion.length()<=4){
                           //Si la descripcion es menor de 4(nula o vacia) nos muestra que no está disponible
                            descripcion="No hay ninguna descripción disponible";
                        }
                        //Vamos añadiendo los animes a la lista y actualizamos los datos del recycler
                        Anime a = new Anime(titulo, descripcion,imagenURL);
                        listaAnime.add(a);
                        actualizarLista();
                        Log.d("D","Array: "+listaAnime.toString());


                        }






                }else{
                    Toast.makeText(ActividadPrincipal.this, "Problema al cargar los datos", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}