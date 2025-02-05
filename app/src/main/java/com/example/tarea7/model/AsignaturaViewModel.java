package com.example.tarea7.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tarea7.repository.AsignaturaRepository;

import java.util.ArrayList;

public class AsignaturaViewModel extends ViewModel {
    private AsignaturaRepository asignaturaRepository = new AsignaturaRepository();
    private MutableLiveData<ArrayList<Asignatura>> asignaturasLiveData= new MutableLiveData<>();

    public AsignaturaViewModel() {
       asignaturaRepository = new AsignaturaRepository();
       asignaturasLiveData = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Asignatura>> getAsignaturas() {
        return asignaturasLiveData;
    }

    public void addAsignatura(Asignatura asignatura) {
        asignaturaRepository.addTarea(asignatura);
        asignaturasLiveData.setValue(asignaturaRepository.getTareas());
    }
    //Metodo para eliminar una tarea
    public void deleteAsignatura(Asignatura asignatura) {
        asignaturaRepository.deleteTarea(asignatura);
        asignaturasLiveData.setValue(asignaturaRepository.getTareas());
    }
    //Metodo para actualizar una tarea
    public void updateAsignatura(Asignatura asignatura) {
        asignaturaRepository.updateTarea(asignatura);
        asignaturasLiveData.setValue(asignaturaRepository.getTareas());
    }

}
