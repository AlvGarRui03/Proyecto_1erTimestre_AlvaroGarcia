package com.example.proyecto.DataBase;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
public class AccesoBD extends SQLiteOpenHelper {
    private static final String DB_NAME = "db_proyecto";

    private static final String DB_TABLE_NAME = "Usuarios";

    private static final int DB_VERSION = 1;
    private static final String NOMBRE_USUARIO = "usuario";

    private static final String CONTRASENIA_USUARIO = "contrasenia";
    private Context mContext;

    public AccesoBD(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        mContext = context;

    }
//Metodo que crea la BD si esta no esta creada
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Parametro para crear una tabla de usuarios con su nombre y contraseña
        String CREATE_USER_TABLE = "CREATE TABLE " + DB_TABLE_NAME + "("
                + NOMBRE_USUARIO + " TEXT," + CONTRASENIA_USUARIO + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        switch (oldVersion) {
        //Aquí se introduciría el código para pasar de versiones cuando se actualice a la siguiente version

        }

    }
    //Metodo que comprueba que el usuario no exista ya
    public boolean comprobarExiste(String usuario,String pass){
        boolean res=false;
        String[] cols = new String[]{ NOMBRE_USUARIO,CONTRASENIA_USUARIO };
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(DB_TABLE_NAME,cols,null,null,null,null,null);
        if(c.moveToFirst()){
            do{
              if(usuario.equals(c.getString(0)) && pass.equals(c.getString(1))){
                  res=true;
              }

            }while(c.moveToNext() && res!=true);

        }
        c.close();
        db.close();
        return res;
    }
    //Metodo para introducir un registro en la BD
    public long insertarUsuario(String usuario,String pass){
        long resultado=0;
        if(comprobarExiste(usuario,pass)){
            resultado=-1;
        }else {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues valores = new ContentValues();
            valores.put(NOMBRE_USUARIO, usuario);
            valores.put(CONTRASENIA_USUARIO, pass);
            resultado = db.insert(DB_TABLE_NAME, null, valores);
            db.close();
        }
        return resultado;
    }










}


