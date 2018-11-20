package com.example.maanas.listatareas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class AccesoBD extends SQLiteOpenHelper {
    public AccesoBD( Context context,  String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_tabla="CREATE TABLE tareas(id INTEGER PRIMARY KEY, fecha TEXT, tipo NUMBER, asunto TEXT)";
        db.execSQL(sql_tabla);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void grabarTarea(Tarea t) {
        SQLiteDatabase db=getWritableDatabase();
        String insert_sql="INSERT INTO tareas(fecha, tipo, asunto) VALUES(?, ?, ?)";
        Object[] lista_parametros={t.getFecha(), t.getTarea_evento(), t.getAsunto()};
        db.execSQL(insert_sql,lista_parametros );
    }

    public ArrayList<Tarea> recuperarTareas(int tipo_pedido) {//tipo vale 0 o 1
        ArrayList<Tarea> lista_tareas=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        String consulta="";
        Cursor c;
        if (tipo_pedido==0 || tipo_pedido==1)
        {
            consulta="SELECT *  FROM tareas WHERE tipo=?";
            String[] argumentos={String.valueOf(tipo_pedido)};
             c=db.rawQuery(consulta, argumentos);
        }
        else
        {
            consulta="SELECT *  FROM tareas";
             c=db.rawQuery(consulta, null);
        }


        while(c.moveToNext())
        {
            long id=c.getLong(0);
            String fecha=c.getString(1);
            int tipo=c.getInt(2);
            String asunto=c.getString(3);
            //(String fecha, String asunto, int tarea_evento, int id)
            Tarea t=new Tarea(fecha, asunto, tipo, id);
            lista_tareas.add(t);

        }
        return lista_tareas;
    }

    public void borrarPorId(ArrayList<Long> lista_chekeados) {
        SQLiteDatabase db=getWritableDatabase();
        for (long id:lista_chekeados             ) {
            String delete_query="DELETE FROM tareas WHERE id=?";
            String[] argumentos={String.valueOf(id)};
            db.execSQL(delete_query, argumentos);

        }

    }
}
