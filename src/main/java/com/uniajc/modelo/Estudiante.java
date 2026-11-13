package com.uniajc.modelo;

public class Estudiante {
    private long id;
    private String name;
    private String lastname;
    private String email;
    private long nit;
    private long numcelular;

    public Estudiante() {}

    public Estudiante(String name, String lastname, String email, long nit, long numcelular) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.nit = nit;
        this.numcelular = numcelular;
    }

    public Estudiante(long id, String name, String lastname, String email, long nit, long numcelular) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.nit = nit;
        this.numcelular = numcelular;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public long getNit() { return nit; }
    public void setNit(long nit) { this.nit = nit; }

    public long getNumcelular() { return numcelular; }
    public void setNumcelular(long numcelular) { this.numcelular = numcelular; }
}
