package com.example.tarea7;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.tarea7.model.Asignatura;
import com.example.tarea7.repository.AsignaturaRepository;

import java.sql.Date;
import java.util.Calendar;

public class CustomDialogFrgamnet extends DialogFragment {

    private OnDialogSubmitListener onDialogSubmitListener;
    private Asignatura asignatura;
    private AsignaturaRepository asignaturaRepository= new AsignaturaRepository();

    public interface OnDialogSubmitListener {
        void onDialogSubmit(Asignatura asignatura);
    }
    public void setOnDialogSubmitListener(OnDialogSubmitListener listener) {
        this.onDialogSubmitListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_layout, container, false);


        //Referenciamos sus elementos
        Spinner spnAsignaturas = view.findViewById(R.id.spnDialog);
        EditText txtFecha = view.findViewById(R.id.txtFechaDialog);
        EditText txtAsignatura = view.findViewById(R.id.txtAsignaturaDialog);
        Button btnGuardar = view.findViewById(R.id.btnAceptar);
        Button btnCancelar = view.findViewById(R.id.btnCancelar);

        //Configuracion del spinner
        String[] asignaturas = {"PMDM", "PSP", "AD","EIE","DI"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, asignaturas);
        spnAsignaturas.setAdapter(adapter);
        spnAsignaturas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedAsignatura = adapter.getItem(position);
                if (adapter.getItem(position) != null){
                    txtAsignatura.setText(selectedAsignatura);
                }
                txtAsignatura.setText(adapter.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                txtAsignatura.setText(asignaturas[0]);
            }
        });

        //Configuramos la fecha
        txtFecha.setOnClickListener(v -> showDatePicker(txtFecha));

        ///Configuramos el boton de guardar
        btnGuardar.setOnClickListener(v -> {
            String asignatura = txtAsignatura.getText().toString();
            String fecha = txtFecha.getText().toString();
            Asignatura asignaturaCreada = new Asignatura(asignatura, fecha,false);
            if (onDialogSubmitListener != null) {
                onDialogSubmitListener.onDialogSubmit(asignaturaCreada);
                Log.d("CustomDialogFragmnet", "onDialogSubmit: " + asignaturaCreada.getNombre());
            }else {
                Log.d("CustomDialogFragmnet", "onDialogSubmit: Listener es null");
            }
            dismiss();
        });

        return view;
    }
    private void showDatePicker(EditText txtFecha){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year1, month1, dayOfMonth) -> {
            month1 = month1 + 1;
            String date = dayOfMonth + "/" + month1 + "/" + year1;
            txtFecha.setText(date);
        }, year, month, day);
        datePickerDialog.show();
    }

    public AsignaturaRepository getAsignaturaRepository() {
        return asignaturaRepository;
    }


}
