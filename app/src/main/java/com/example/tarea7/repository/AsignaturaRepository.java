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

    //Metodo para actualizar una tarea
    public void updateTarea(Asignatura asignatura) {
        for (Asignatura tarea : tareas) {
            if (tarea.getId() == asignatura.getId()) {
                tarea.setNombre(asignatura.getNombre());
                tarea.setFecha(asignatura.getFecha());
                tarea.setEstado(asignatura.isEstado());
            }
        }
    }
    //Metodo para marcar la tarea como completada
    public void updateTareaEstado(Asignatura asignatura) {
        for (Asignatura tarea : tareas) {
            if (tarea.getNombre().equals(asignatura.getNombre()) && tarea.getFecha().equals(asignatura.getFecha())) {
                if (tarea.isEstado()){
                    tarea.setEstado(false);
                }
                tarea.setEstado(true);

            }
        }
    }
    //Metodo para actualizar la tarea donde recibe la tarea a modificar y la tarea modificada con un hashmap
    public void updateTarea(Asignatura asignatura, Asignatura asignaturaModificada) {
        for (Asignatura tarea : tareas) {
            if (tarea.getNombre().equals(asignatura.getNombre())) {
                tarea.setNombre(asignaturaModificada.getNombre());
                tarea.setFecha(asignaturaModificada.getFecha());
                tarea.setEstado(asignaturaModificada.isEstado());
            }
        }
    }
}
