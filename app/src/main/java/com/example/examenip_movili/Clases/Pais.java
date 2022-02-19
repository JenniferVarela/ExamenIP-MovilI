package com.example.examenip_movili.Clases;

public class Pais {
    private String codigo;
    private String pais;

    public Pais() {
    }

    public Pais(String codigo, String pais) {
        this.codigo = codigo;
        this.pais = pais;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

}
