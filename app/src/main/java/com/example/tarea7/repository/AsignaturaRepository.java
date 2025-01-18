package com.example.tarea7.repository;

import android.util.Log;

import com.example.tarea7.model.Asignatura;

import java.util.ArrayList;

public class AsignaturaRepository {
    ArrayList<Asignatura> tareas = new ArrayList<>();

    public AsignaturaRepository() {
    }

    public void setTareas(ArrayList<Asignatura> tareas) {
        this.tareas = tareas;
    }

    public ArrayList<Asignatura> getTareas() {
        //Vemos las asignaturas en la lista con un for y sout
        for (Asignatura asignatura : tareas) {
            Log.d("AsignaturaRepository", "Asignatura: " + asignatura.toString());
        }
        return tareas;
    }
    public void addTarea(Asignatura asignatura) {
        tareas.add(asignatura);
    }

    public void deleteTarea(Asignatura asignatura) {
        tareas.remove(asignatura);
    }
}
