package com.example.tarea7.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tarea7.model.Asignatura;

import java.util.ArrayList;

public class BaseDeDatos extends SQLiteOpenHelper {
    private static final String NOMBRE_BD = "BaseDeDatos";
    private static final int VERSION_BD = 1;

    public BaseDeDatos(Context context){
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_USUARIOS = "CREATE TABLE usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, " +
                "email TEXT, " +
                "password TEXT)";
        db.execSQL(CREATE_TABLE_USUARIOS);

        String CREATE_TABLE_TAREAS = "CREATE TABLE tareas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "asignatura TEXT, " +
                "fecha TEXT, " +
                "estado INTEGER)";
        db.execSQL(CREATE_TABLE_TAREAS);
    }
    public void  insertarTarea(Asignatura asignatura) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("asignatura", asignatura.getNombre());
        valores.put("fecha", asignatura.getFecha());
        valores.put("estado", asignatura.isEstado() ? 1 : 0);
        db.insert("tareas", null, valores);
        db.close();

    }
    //metodo para obtener todas las tareas
    public ArrayList<Asignatura> obtenerTareas() {
        ArrayList<Asignatura> tareas = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String consulta = "SELECT * FROM tareas";
        //Cursor es un objeto que nos permite recorrer los registros de una tabla
        Cursor cursor = db.rawQuery(consulta, null);

        //Si hay registros en la tabla
        if (cursor.moveToFirst()) {
            do {
                //Obtenemos los datos de la fila actual
                int id = cursor.getInt(0);
                String asignatura = cursor.getString(1);
                String fecha = cursor.getString(2);
                boolean estado = cursor.getInt(3) == 1;
                //Creamos un objeto de tipo Asignatura
                Asignatura tarea = new Asignatura(asignatura, fecha, estado);
                tarea.setId(id);
                //Añadimos la tarea a la lista
                tareas.add(tarea);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tareas;
    }
    //Metodo para actualizar una tarea
    public void actualizarTarea(Asignatura asignatura) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("asignatura", asignatura.getNombre());
        valores.put("fecha", asignatura.getFecha());
        valores.put("estado", asignatura.isEstado() ? 1 : 0);
        db.update("tareas", valores, "id = ?", new String[]{String.valueOf(asignatura.getId())});
        db.close();

    }
    //Metodo para eliminar una tarea
    public void eliminarTarea(Asignatura asignatura) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("tareas", "id = ?", new String[]{String.valueOf(asignatura.getId())});
        db.close();
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar la tabla
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        // Eliminar la tabla
        db.execSQL("DROP TABLE IF EXISTS tareas");
        onCreate(db);
    }
}
