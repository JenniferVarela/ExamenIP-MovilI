package com.example.examenip_movili;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.examenip_movili.Clases.Contactos;
import com.example.examenip_movili.Configuraciones.SQLiteConexion;
import com.example.examenip_movili.Configuraciones.Transacciones;

import java.util.ArrayList;

public class ActivityListarContacto extends AppCompatActivity {

    SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDatabase,null,1);
    SQLiteDatabase db;
    ListView listaContactos;
    EditText alctxtnombre;
    ArrayList<String> ArregloContactos;
    ArrayList<Contactos> lista_Contactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_contacto);

        listaContactos = (ListView) findViewById(R.id.listaContactos);
        alctxtnombre = (EditText) findViewById(R.id.alctxtnombre);

        ObtenerListaContactos();

        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1,ArregloContactos);
        listaContactos.setAdapter(adp);

        alctxtnombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                adp.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void ObtenerListaContactos() {
        db = conexion.getReadableDatabase();

        Contactos lista_contac = null;
        lista_Contactos = new ArrayList<Contactos>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Transacciones.tblContactos,null);

        while (cursor.moveToNext())
        {
            lista_contac = new Contactos();
            lista_contac.setNombre(cursor.getString(0));
            lista_contac.setTelefono(cursor.getString(1));

            lista_Contactos.add(lista_contac);
        }
        cursor.close();

        llenarLista();

    }

    private void llenarLista() {

        ArregloContactos = new ArrayList<String>();

        for (int i=0; i<lista_Contactos.size(); i++)
        {
            ArregloContactos.add(lista_Contactos.get(i).getNombre()+" | " + lista_Contactos.get(i).getTelefono());

        }
    }
}