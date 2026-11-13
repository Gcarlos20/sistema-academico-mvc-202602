package com.uniajc.modelo;

public class Curso {
    private int id;
    private String facultad;

    public Curso() {}

    public Curso(String facultad) {
        this.facultad = facultad;
    }

    public Curso(int id, String facultad) {
        this.id = id;
        this.facultad = facultad;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFacultad() { return facultad; }
    public void setFacultad(String facultad) { this.facultad = facultad; }
}
