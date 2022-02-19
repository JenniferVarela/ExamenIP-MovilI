package com.example.examenip_movili;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.examenip_movili.Configuraciones.SQLiteConexion;
import com.example.examenip_movili.Configuraciones.Transacciones;

public class ActivityPaises extends AppCompatActivity {

    EditText aptxtCodigo,aptxtPais;
    Button apbtnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paises);

        aptxtCodigo = (EditText) findViewById(R.id.aptxtCodigo);
        aptxtPais = (EditText) findViewById(R.id.aptxtPais);
        apbtnGuardar = (Button) findViewById(R.id.apbtnGuardar);

        apbtnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertarPais();
            }
        });
    }

    private void InsertarPais() {
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDatabase,null,1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Transacciones.codigo,aptxtCodigo.getText().toString());
        valores.put(Transacciones.pais,aptxtPais.getText().toString());

        Long resultado = db.insert(Transacciones.tblPaises,Transacciones.codigo,valores);

        Toast.makeText(getApplicationContext(),"Registro Exitoso!!!"+resultado.toString(),Toast.LENGTH_LONG).show();
        db.close();

        limpiarPantalla();

    }

    private void limpiarPantalla() {
        aptxtPais.setText("");
        aptxtCodigo.setText("");
    }
}