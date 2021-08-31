package com.example.pathfighter;

public class ListElement {
    public String clase;
    public String nombre;
    public String usuario;
    public String nivel;
    public int id;

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ListElement(String clase, String nombre, String usuario, String nivel, int id) {
        this.clase = clase;
        this.nombre = nombre;
        this.usuario = usuario;
        this.nivel = nivel;
        this.id = id;
    }
}
