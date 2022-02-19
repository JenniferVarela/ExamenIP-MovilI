package com.example.examenip_movili.Clases;

public class Contactos {
    private Integer id;
    private String nombre;
    private String telefono;
    private String nota;
    private byte[] foto;

    public Contactos() {
    }

    public Contactos(Integer id, String nombre, String telefono, String nota, byte[] foto) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.nota = nota;
        this.foto = foto;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
}
