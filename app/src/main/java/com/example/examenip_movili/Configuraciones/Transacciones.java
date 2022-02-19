package com.example.examenip_movili.Configuraciones;

public class Transacciones {

    public static final String NameDatabase = "PM01DB";

    public static String tblPaises = "paises";

    public static final String codigo ="codigo";
    public static final String pais="pais";


    public static final String CreateTablePaises = "CREATE TABLE " + tblPaises + "(codigo INTEGER PRIMARY KEY,"+"pais TEXT )";

    public static final String DropTablePaises = "DROP TABLE " + tblPaises;

}
