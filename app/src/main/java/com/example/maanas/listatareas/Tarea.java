package com.example.maanas.listatareas;

public class Tarea {
    private String fecha, asunto;
    private int tarea_evento;//0-evento, 1-tarea
    private long id;

    public Tarea(String fecha, String asunto, int tarea_evento) {
        this.fecha = fecha;
        this.asunto = asunto;
        this.tarea_evento = tarea_evento;
    }

    public Tarea(String fecha, String asunto, int tarea_evento, long id) {
        this.fecha = fecha;
        this.asunto = asunto;
        this.tarea_evento = tarea_evento;
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public String getAsunto() {
        return asunto;
    }

    public int getTarea_evento() {
        return tarea_evento;
    }

    public long getId() {
        return id;
    }
}
