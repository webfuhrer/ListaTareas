package com.example.maanas.listatareas;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorTareas extends BaseAdapter {
    private ArrayList<Tarea> lista_tareas;
    private Context contexto;
    private ArrayList<Long> lista_chekeados=new ArrayList<>();
    public AdaptadorTareas(ArrayList<Tarea> lista_tareas, Context contexto) {
        this.lista_tareas = lista_tareas;
        this.contexto = contexto;
    }

    public ArrayList<Long> getLista_chekeados() {
        return lista_chekeados;
    }

    @Override
    public int getCount() {
        return lista_tareas.size();
    }

    @Override
    public Object getItem(int position) {
        return lista_tareas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lista_tareas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista=LayoutInflater.from(contexto).inflate(R.layout.elemento_list_view, null);
        TextView tv_fecha=vista.findViewById(R.id.tv_fecha);
        TextView tv_tarea=vista.findViewById(R.id.tv_tarea);
        EditText et_asunto=vista.findViewById(R.id.et_asunto);
        final CheckBox chk_borrar=vista.findViewById(R.id.chk_borrar);
        final Tarea t=lista_tareas.get(position);
        chk_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id_clicado=t.getId();
                if(chk_borrar.isChecked())
                {
                    annadirId(id_clicado);
                }
                else
                {
                    borrarId(id_clicado);
                }
            }
        });

        String fecha=t.getFecha();
        String asunto=t.getAsunto();
        int tarea=t.getTarea_evento();//Vamos a cambiar background en fución de esto
        tv_fecha.setText(fecha);
        et_asunto.setText(asunto);
        String tarea_texto=(tarea==0)?"Evento":"Tarea";
        tv_tarea.setText(tarea_texto);        int color_fondo=(tarea==0)? Color.CYAN:Color.GREEN;
        vista.setBackgroundColor(color_fondo);
        return vista;


    }
    private void annadirId(long id_clicado) {
        lista_chekeados.add(id_clicado);
    }
    private void borrarId(long id_clicado)
    {
        lista_chekeados.remove(id_clicado);
    }
}
