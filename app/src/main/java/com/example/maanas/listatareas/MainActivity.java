package com.example.maanas.listatareas;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ListView lv_tareas;
FloatingActionButton fab_insertar;
FloatingActionButton fab_borrar;
Context contexto=null;
CheckBox chk_evento, chk_tarea;
String nombre_bd="bd_tareas";
int version_bd=1;
AdaptadorTareas adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cargarvistas();
        mostrarListViewPoblado();
    }

    private void mostrarListViewPoblado() {
        boolean a=chk_evento.isChecked();
        boolean b=chk_tarea.isChecked();
        if ( (a&&b) || (!a && !b))
        {
            //se muestra todo
            AccesoBD db=new AccesoBD(this, nombre_bd, version_bd);
            ArrayList<Tarea> lista_tareas=db.recuperarTareas(99);//meto 99 para que no haga filro el bd
            adaptador=new AdaptadorTareas(lista_tareas, this);
            lv_tareas.setAdapter(adaptador);
        }
        if (a && !b)
        {
            //solo eventos
            mostrarListViewSoloEventos();
        }
        if (!a && b)
        {
            //solo tareas
            mostrarListViewSoloTareas();
        }

    }

    private void cargarvistas() {
        contexto=this;
        lv_tareas=findViewById(R.id.lv_tareas);
        fab_insertar=findViewById(R.id.fab_insertar);
        fab_borrar=findViewById(R.id.fab_borrar);
        chk_tarea=findViewById(R.id.chk_tareas);
        chk_evento=findViewById(R.id.chk_eventos);
        View.OnClickListener oyente_checks=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mostrarListViewPoblado();
            }
        };
        chk_tarea.setOnClickListener(oyente_checks);
        chk_evento.setOnClickListener(oyente_checks);
        fab_insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sacar el DialogAlert
                sacarDialogAlert();
            }
        });
        fab_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pedir la lista al adaptador, y borrar.
                borrarTareas();
            }
        });

    }
    private void mostrarListViewSoloEventos()
    {
        AccesoBD db=new AccesoBD(this, nombre_bd, version_bd);
        ArrayList<Tarea> lista_tareas=db.recuperarTareas(0);
        adaptador=new AdaptadorTareas(lista_tareas, this);
        lv_tareas.setAdapter(adaptador);
    }
    private void mostrarListViewSoloTareas() {
        AccesoBD db=new AccesoBD(this, nombre_bd, version_bd);
        ArrayList<Tarea> lista_tareas=db.recuperarTareas(1);
        adaptador=new AdaptadorTareas(lista_tareas, this);
        lv_tareas.setAdapter(adaptador);
    }

    private void borrarTareas() {

        AlertDialog.Builder constructor=new AlertDialog.Builder(this);
        constructor.setTitle("Borrado");
        constructor.setMessage("¿Realmente quiere borrar los elementos seleccionados?");
        constructor.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ArrayList<Long> lista_chekeados=adaptador.getLista_chekeados();
                AccesoBD bd=new AccesoBD(contexto, nombre_bd, version_bd);
                bd.borrarPorId(lista_chekeados);
                mostrarListViewPoblado();
            }
        });
        constructor.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alerta=constructor.create();
        alerta.show();

    }

    private void sacarDialogAlert() {
        AlertDialog.Builder constructor=new AlertDialog.Builder(this);
        constructor.setTitle("Inserción de tarea");
        final View vista_alert=LayoutInflater.from(this).inflate(R.layout.layout_insertar, null);
        constructor.setView(vista_alert);
        constructor.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText et_fecha=vista_alert.findViewById(R.id.et_fecha);
                Switch tarea=vista_alert.findViewById(R.id.sw_tarea);

                EditText et_asunto=vista_alert.findViewById(R.id.et_asunto_insert);
                String fecha=et_fecha.getText().toString();
                String asunto=et_asunto.getText().toString();
                boolean tarea_evento=tarea.isChecked();

                int tarea_evento_int=0;
                if (tarea_evento==false)//Tarea
                {
                    tarea_evento_int=1;
                }else
                {//Evento
                    tarea_evento_int=0;
                }
                //int tarea2=(tarea_evento==false)?1:0;
                Tarea t=new Tarea(fecha, asunto, tarea_evento_int);
                AccesoBD bd=new AccesoBD(contexto, nombre_bd, version_bd);
                bd.grabarTarea(t);
                mostrarListViewPoblado();
            }
        });
        constructor.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog a=constructor.create();
        a.show();
    }


}
