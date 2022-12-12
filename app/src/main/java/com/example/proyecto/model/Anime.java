package com.example.proyecto.model;

public class Anime {
    //Atributos del anime
    private String titulo;
    private String descripcion;
    private String imagenURL;
    public Anime(String titulo, String descripcion, String imagenURL) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagenURL = imagenURL;
    }
    //Getters y Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if(descripcion.length()>4){
        this.descripcion = descripcion;
        }else{
            this.descripcion="Descripción no disponible";
        }
    }

    public String getImagenURL() {
        return imagenURL;
    }

    public void setImagenURL(String imagenURL) {
        this.imagenURL = imagenURL;
    }
}
