package com.example.tarea7;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarea7.adapters.TareaAdapters;
import com.example.tarea7.fragments.CustomDialogFrgamnet;
import com.example.tarea7.model.Asignatura;
import com.example.tarea7.repository.AsignaturaRepository;
import com.example.tarea7.repository.BaseDeDatos;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MainActivity extends AppCompatActivity {
    private AsignaturaRepository asignaturaRepository = new AsignaturaRepository();
    private TareaAdapters tareaAdapters;
    private BaseDeDatos baseDeDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Inizializamos la base de datos
         baseDeDatos = new BaseDeDatos(this);
        //cargar las tareas de la base de datos
        asignaturaRepository.setTareas(baseDeDatos.obtenerTareas());

        Button btnShowDialog = findViewById(R.id.btnAgregar);
        btnShowDialog.setOnClickListener(v -> showDialog());

        //Configuramos recyclerView y adapter
        RecyclerView recyclerView = findViewById(R.id.recyclerAsignaturas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tareaAdapters = new TareaAdapters(asignaturaRepository.getTareas(), this);
        recyclerView.setAdapter(tareaAdapters);



    }

    private void showDialog() {
        CustomDialogFrgamnet dialog = new CustomDialogFrgamnet();
        RecyclerView recyclerView = findViewById(R.id.recyclerAsignaturas);
        dialog.setOnDialogSubmitListener(asignatura -> {
            Log.d("MainActivity", "Asignatura recibida: " + asignatura.getNombre());
            baseDeDatos.insertarTarea(asignatura);
            asignaturaRepository.addTarea(asignatura);
            tareaAdapters.updateAsignaturas(asignaturaRepository.getTareas());
            Log.d("MainActivity", "Asignaturas en repositorio despues de agregar: " + asignaturaRepository.getTareas().toString());
            dialog.dismiss();
        });
        dialog.show(getSupportFragmentManager(), "dialog");
    }
    //Metodo donde se muestra un BottomSheetDialog cuando se pulsa en un item del recyclerView
    public void showBottomSheetDialog(Asignatura asignatura) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet, null);
        //Encontramos las vistas
        TextView txtEdit = view.findViewById(R.id.txtEdit);
        TextView txtDelete = view.findViewById(R.id.txtDelete);
        TextView txtComplete = view.findViewById(R.id.txtComplete);

        //Configuramos los eventos de los botones
        txtEdit.setOnClickListener(v -> {
            editAsignatura(asignatura);
            Log.d("MainActivity", "Editando tarea");
            bottomSheetDialog.dismiss();
        });
        txtDelete.setOnClickListener(v -> {
            deleteAsignatura(asignatura);
            Log.d("MainActivity", "Eliminando tarea");
            bottomSheetDialog.dismiss();
        });
        txtComplete.setOnClickListener(v -> {
            Log.d("MainActivity", "Completando tarea");
            asignatura.setEstado(true);
            baseDeDatos.actualizarTarea(asignatura);
            tareaAdapters.updateAsignaturas(asignaturaRepository.getTareas());
            Log.d("MainActivity", "Asignaturas en repositorio despues de completar: " + asignaturaRepository.getTareas().toString());
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();

    }
    //metodo que coge el objeto asignatura y lo borra de la lista
    public void deleteAsignatura(Asignatura asignatura) {
        baseDeDatos.eliminarTarea(asignatura);
        asignaturaRepository.deleteTarea(asignatura);
        tareaAdapters.updateAsignaturas(asignaturaRepository.getTareas());
    }
    //metodo que coge el objeto asignatura y lo edita
    public void editAsignatura(Asignatura asignatura) {
        CustomDialogFrgamnet dialog = new CustomDialogFrgamnet();
        dialog.setOnDialogSubmitListener(asignatura1 -> {
            Log.d("MainActivity", "Asignatura recibida: " + asignatura1.getNombre());
            baseDeDatos.actualizarTarea(asignatura1);
            asignaturaRepository.updateTarea(asignatura1);
            tareaAdapters.updateAsignaturas(asignaturaRepository.getTareas());
            Log.d("MainActivity", "Asignaturas en repositorio despues de editar: " + asignaturaRepository.getTareas().toString());
            dialog.dismiss();
        });

        Bundle args = new Bundle();
        args.putInt("id", asignatura.getId());
        args.putString("nombre", asignatura.getNombre());
        args.putString("fecha", asignatura.getFecha());
        args.putBoolean("estado", asignatura.isEstado());
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "dialog");
    }


    public AsignaturaRepository getAsignaturaRepository() {
        return asignaturaRepository;
    }
}