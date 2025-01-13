package com.example.tarea7;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarea7.adapters.TareaAdapters;
import com.example.tarea7.repository.AsignaturaRepository;

public class MainActivity extends AppCompatActivity {
    private AsignaturaRepository asignaturaRepository = new AsignaturaRepository();
    private TareaAdapters tareaAdapters;
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
            asignaturaRepository.addTarea(asignatura);
            tareaAdapters.updateAsignaturas(asignaturaRepository.getTareas());
            Log.d("MainActivity", "Asignaturas en repositorio despues de agregar: " + asignaturaRepository.getTareas().toString());
            dialog.dismiss();
        });
        dialog.show(getSupportFragmentManager(), "dialog");
    }


}