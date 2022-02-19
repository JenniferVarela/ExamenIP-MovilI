package com.example.examenip_movili.Configuraciones;

import java.sql.Blob;

public class Transacciones {

    public static final String NameDatabase = "PM01DB";
    /*TABLA PAISES*/
    public static String tblPaises = "paises";
    public static final String codigo ="codigo";
    public static final String pais="pais";

    public static final String CreateTablePaises = "CREATE TABLE " + tblPaises + "(codigo INTEGER PRIMARY KEY,"+"pais TEXT )";
    public static final String DropTablePaises = "DROP TABLE " + tblPaises;

    /*TABLA CONTACTOS*/
    public static String tblContactos = "contactos";
    public static final String id = "id";
    public static final String nombre = "nombre";
    public static final String telefono = "telefono";
    public static final String c_pais = "pais";
    public static String nota = "nota";
    public static final String foto = "foto";

    public static final String CreateTableContactos = "CREATE TABLE " + tblContactos + "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nombre TEXT,telefono INTEGER, c_pais TEXT, nota TEXT,foto BLOB)";
    public static final String DropTableContactos = "DROP TABLE "+ tblContactos;


}
