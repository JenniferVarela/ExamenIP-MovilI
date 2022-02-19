package com.example.examenip_movili.Configuraciones;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteConexion  extends SQLiteOpenHelper {

    public SQLiteConexion (@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory,int version)
    {
        super(context,name,factory,version);
    }
    /*TABLA PAISES*/
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(Transacciones.CreateTablePaises);
        sqLiteDatabase.execSQL(Transacciones.CreateTableContactos);
    }

    @Override
    public void onUpgrade (SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        sqLiteDatabase.execSQL(Transacciones.DropTablePaises);
        sqLiteDatabase.execSQL(Transacciones.DropTableContactos);
        onCreate(sqLiteDatabase);
    }

}
