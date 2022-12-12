package com.example.proyecto.adapter;

import android.app.Activity;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.example.proyecto.R;
import com.example.proyecto.model.Anime;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> implements View.OnClickListener{
    private List<Anime> animesLista;
    private Activity activity;
    private CircularProgressDrawable progressDrawable;
    private View.OnClickListener listener;
//Creamos el contructor del recycler donde le pasamos la lista y la actividad
    public RecyclerAdapter(List<Anime> animesLista,Activity activity) {
        this.animesLista = animesLista;
        this.activity=activity;
    }
    @NonNull
    @Override
    //Creamos metodo onCreateViewHolder  y le añadimos el listener para controlar que el usuario pulsa una fila
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diseniolista_anime,parent, false);
        RecyclerHolder recyclerHolder = new RecyclerHolder(view);
        view.setOnClickListener(this);
        return recyclerHolder;
    }


    @Override
    //Añadimos los datos al holder
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        Anime anime = animesLista.get(position);
        holder.txt_descripcion.setText(anime.getDescripcion());
        holder.txt_nombreAnime.setText(anime.getTitulo());
        progressDrawable = new CircularProgressDrawable(holder.img_portadaAnime.getContext());
        progressDrawable.setStrokeWidth(10f);
        progressDrawable.setStyle(CircularProgressDrawable.LARGE);
        progressDrawable.setCenterRadius(30f);
        progressDrawable.start();
        Glide.with(activity)
                .load(anime.getImagenURL())
                .placeholder(progressDrawable)
                .error(R.mipmap.ic_launcher)
                .into(holder.img_portadaAnime);
    }

    @Override
    //metodo que devuelve la lista
    public int getItemCount() {
        return animesLista.size();
    }
    //Metodo que indica el listener
    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    //Si el listener no es nulo le pasamos la vista
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }

    }
//Clase RecyclerHolder
    public class RecyclerHolder extends ViewHolder{
        ImageView img_portadaAnime;
        TextView txt_nombreAnime;
        TextView  txt_descripcion;


    //Constructor del Recycler
        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);


            img_portadaAnime  = (ImageView) itemView.findViewById(R.id.iv_imagenanime);
            txt_nombreAnime = (TextView)  itemView.findViewById(R.id.txt_titulo);
            txt_descripcion  = (TextView)  itemView.findViewById(R.id.txt_descripcion);
            //Le ponemos un metodo la descripción que hace que se pueda scrollear(Se scrollea en horizontal/diagonal)
            txt_descripcion.setScrollBarSize (32);
            txt_descripcion.setMovementMethod(new ScrollingMovementMethod());

        }
    }
}
