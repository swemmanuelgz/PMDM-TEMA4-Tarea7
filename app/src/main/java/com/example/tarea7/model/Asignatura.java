package com.example.tarea7.model;

import java.sql.Date;

public class Asignatura {
    private String nombre;
    private String fecha;
    private boolean estado;

    public Asignatura(String nombre, String fecha, boolean estado) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.estado = estado;
    }
    public Asignatura() {

    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Asignatura{" +
                "nombre='" + nombre + '\'' +
                ", fecha='" + fecha + '\'' +
                ", estado=" + estado +
                '}';
    }
}
