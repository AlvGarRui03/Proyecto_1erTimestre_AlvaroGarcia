package com.example.proyecto.controler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.proyecto.controler.ActividadFragmento;
import com.example.proyecto.DataBase.AccesoBD;
import com.example.proyecto.R;

import eltos.simpledialogfragment.SimpleDialog;

public class ActividadLogIn extends AppCompatActivity {
    AccesoBD aBD;
    Button mButtonLogin;
    Button mButtonSignIn;
    Button mButtonVer;
    EditText mEtUsuario;
    EditText mEtContrasenia;
    AlertDialog alertDialog;
    Toast toast;
    ImageButton mAjustes;
    ActividadFragmento actividadFragmento;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Actualizamos el color de preferencias
        actualizarColor();
        alertDialog= new AlertDialog.Builder(ActividadLogIn.this).create();
        mButtonLogin = (Button) findViewById(R.id.BT_Login);
        mButtonSignIn = (Button) findViewById(R.id.BT_Registro);
        mButtonVer = (Button) findViewById(R.id.BT_Ver);
        mEtContrasenia = (EditText) findViewById(R.id.TxT_Contraseña);
        mEtUsuario = (EditText) findViewById(R.id.TxT_Usuario);
        mAjustes= (ImageButton) findViewById(R.id.ajustes);
        aBD = new AccesoBD(this);
        //Añadimos un listener al boton de ajustes para que nos abra el fragmento de preferencias
        mAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActividadLogIn.this, ActividadFragmento.class);
                startActivity(i);


            }
        });
        //Boton de login que comprueba que el usuario este en la BD y si esta le deja acceder a la actividad principal
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEtContrasenia.getText().toString().length()>3 || mEtUsuario.getText().toString().length()>3){
                    //Metodo que comprueba que el usuario este en la BD
                    if(aBD.comprobarExiste(mEtUsuario.getText().toString(),mEtContrasenia.getText().toString())){
                        Intent i = new Intent(ActividadLogIn.this, ActividadPrincipal.class);
                        //Informamos al usuario que el usuario es correcto
                        toast.makeText(getApplicationContext(), "Usuario correcto", Toast.LENGTH_SHORT).show();
                        //Empezamos la actividad Principal
                        startActivity(i);
                    }else{
                       //Informamos al usuario de que su cuenta no es correcta
                        alertDialog.setTitle("Usuario no encontrado");
                        alertDialog.setMessage("El usuario o la contraseña no son correctas, compruébelas o cree una nueva cuenta con el botón de registro");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }

                }else{
                    //Informamos al usuario de que no puede existir ninguna cuenta con esos parámetros
                    alertDialog.setTitle("Usuario imposible");
                    alertDialog.setMessage("No puede haber ningun nombre de usuario o contraseña con menos de 4 caracteres");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
            }
        });
        //Boton para introducir una cuenta en la BD
       mButtonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Comprobamos que sea mayor a 4(Minimo de caracteres para el nombre y contraseña)
                if(mEtContrasenia.getText().toString().length()>3 && mEtUsuario.getText().toString().length()>3) {
                    //Introducimos el usuario
                    long continuar = aBD.insertarUsuario(mEtContrasenia.getText().toString(), mEtUsuario.getText().toString());
                    //Si el codigo devuelto es -1 le decimos que ese usuario ya esta registrado
                    if (continuar == -1) {
                        alertDialog.setTitle("Usuario repetido");
                        alertDialog.setMessage("El usuario que ha introducido ya esta registrado, por favor cambie el nombre de usuario para crear una nueva cuenta");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }else{
                        //Si no da error le indicamos que creo correctamente al usuario nuevo
                        toast.makeText(getApplicationContext(), "Usuario creado correctamente", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    alertDialog.setTitle("Formato erróneo");
                    alertDialog.setMessage("El usuario y la contraseña deben de tener por lo menos 4 carácteres");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
                }
        });
       //Boton que nos permite visualizar el texto de la contraseña mientras que este se encuentre pulsado
        mButtonVer.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                switch ( event.getAction() ) {
                    //Si lo tiene pulsado el tipo del editText sera texto normal por lo que será visible
                    case MotionEvent.ACTION_DOWN:
                        mEtContrasenia.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                        //Si no lo tiene pulsado se pasará otra vez al tipo de editText contraseña
                    case MotionEvent.ACTION_UP:
                        mEtContrasenia.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });

    }
    @Override
    //Metodo onResume que actualiza el color
    public void onResume(){
        super.onResume();
        //Actualizamos el color de las preferencias
        actualizarColor();
    }
    //Metodo para actualizar el color seleccionado en las preferencias
    public void actualizarColor(){
       int color = ActividadFragmento.cargarPreferencias(this);
        ConstraintLayout actividadLogin = (ConstraintLayout) findViewById(R.id.layout_login);
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
}
