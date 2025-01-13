package com.example.tarea7.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarea7.R;
import com.example.tarea7.model.Asignatura;

import java.util.ArrayList;

public class TareaAdapters extends RecyclerView.Adapter<TareaAdapters.TareaViewHolder> {
    private ArrayList<Asignatura> asignaturas;
    private Context context;

    public TareaAdapters(ArrayList<Asignatura> asignaturas, Context context) {
        this.asignaturas = asignaturas;
        this.context = context;
    }
    public void updateAsignaturas(ArrayList<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
        Log.d("TareaAdapters", "Asignatura actualizadas: " + asignaturas.toString());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.asignatura, parent, false);
        return new TareaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
        Asignatura asignatura = asignaturas.get(position);
        Log.d("TareaAdapters","Asignatura en Adapter: "+ asignatura.toString());
        holder.txtNombre.setText(asignatura.getNombre());
        holder.txtFecha.setText(asignatura.getFecha());
        holder.txtEstado.setText(asignatura.isEstado() ? "Completada" : "Pendiente");
    }

    @Override
    public int getItemCount() {
        return asignaturas.size();
    }

    public class TareaViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtFecha, txtEstado;


        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtAsignatura);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            txtEstado = itemView.findViewById(R.id.txtEstado);
        }
    }
}
