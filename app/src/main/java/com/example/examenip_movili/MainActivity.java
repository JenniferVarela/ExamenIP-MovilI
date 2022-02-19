package com.example.examenip_movili;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.examenip_movili.Clases.Pais;
import com.example.examenip_movili.Configuraciones.SQLiteConexion;
import com.example.examenip_movili.Configuraciones.Transacciones;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SQLiteConexion conexion = new SQLiteConexion(this,Transacciones.NameDatabase,null,1);
    SQLiteDatabase db;

    EditText txtNombre,txtTelefono,multiNota;
    Button btnGuardar,btnContactos,btnTomarFoto,btnSlcFoto;
    ImageButton btnaddPais;
    Spinner spPais;
    ImageView ObjImagen;
    static final int PETICION_ACCESO_CAM = 100;
    static final int TAKE_PIC_REQUEST = 101;
    Bitmap imagen;
    //String item;
    private static final int SELECT_FILE = 1;
    Uri imageUri;

    ArrayList<String> lista_paises;
    ArrayList<Pais> lista;
    private int requestCode;
    private int resultCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtTelefono = (EditText) findViewById(R.id.txtTelefono);
        spPais = (Spinner) findViewById(R.id.spPaises);
        multiNota = (EditText) findViewById(R.id.multitxtNota);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnContactos = (Button) findViewById(R.id.btnListarContacto);
        btnTomarFoto = (Button) findViewById(R.id.btnTomarFoto);
        btnaddPais = (ImageButton) findViewById(R.id.btnImgAgregar);
        btnSlcFoto = (Button) findViewById(R.id.btnSlcFoto);
        ObjImagen = (ImageView) findViewById(R.id.foto);

        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permisos();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarFoto(imagen);
            }
        });

        btnaddPais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ActivityPaises.class);
                startActivity(intent);
                //ObtenerListaPaises();
            }
        });

        btnSlcFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirGaleria();
            }
        });

        ObtenerListaPaises();

        ArrayAdapter<CharSequence> adp = new ArrayAdapter(this, android.R.layout.simple_spinner_item,lista_paises);
        spPais.setAdapter(adp);

        spPais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                Toast.makeText(getApplicationContext(),adapterView.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
                //spPais.setAdapter(adapterView.getSelectedItem().toString());
                //item = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void ObtenerListaPaises() {
        Pais pais = null;
        lista = new ArrayList<Pais>();
        //conexion = new SQLiteConexion(this, Transacciones.NameDatabase,null,1);
        db = conexion.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Transacciones.tblPaises,null);

        while (cursor.moveToNext())
        {
            pais = new Pais();

            pais.setCodigo(cursor.getString(0));
            pais.setPais(cursor.getString(1));

            lista.add(pais);
        }

        cursor.close();

        fillCombo();

    }

    private void fillCombo() {
        lista_paises = new ArrayList<String>();

        for (int i=0; i<lista.size();i++)
        {
            lista_paises.add(lista.get(i).getPais()+" ( "+lista.get(i).getCodigo()+" )");
        }
    }

    private void permisos() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},PETICION_ACCESO_CAM);
        }else{
            tomarFoto();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PETICION_ACCESO_CAM)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                tomarFoto();
            }
        }else{
            Toast.makeText(getApplicationContext(),"Se necesitan permisos de acceso a la camara",Toast.LENGTH_LONG).show();
        }
    }

    private void tomarFoto() {
        Intent takepic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takepic.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(takepic,TAKE_PIC_REQUEST);
        }
    }

    private void abrirGaleria() {
        /*Intent intentImg = new Intent(Intent.ACTION_GET_CONTENT,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        //  intentImg.setType("image/*");
        //intentImg.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentImg,SELECT_FILE);*/
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PETICION_ACCESO_CAM);
    }

    @Override
    protected void onActivityResult(int requescode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requescode, resultCode, data);

        if(requescode == TAKE_PIC_REQUEST && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            imagen = (Bitmap) extras.get("data");
            ObjImagen.setImageBitmap(imagen);

        }else if (resultCode == RESULT_OK && requestCode == PETICION_ACCESO_CAM)
        {
            imageUri = data.getData();
            ObjImagen.setImageURI(imageUri);

        }
    }

    private void guardarFoto(Bitmap bitmap) {

        //conexion = new SQLiteConexion(this,Transacciones.NameDatabase,null,1);
        db = conexion.getWritableDatabase();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] ArrayFoto  = stream.toByteArray();


        ContentValues valores = new ContentValues();
        valores.put(Transacciones.nombre,txtNombre.getText().toString());
        valores.put(Transacciones.telefono,txtTelefono.getText().toString()+"");
        //valores.put(Transacciones.c_pais,item);
        //valores.put(Transacciones.c_pais,String.valueOf(value));
        valores.put(Transacciones.nota,multiNota.getText().toString());
        valores.put(String.valueOf(Transacciones.foto),ArrayFoto);


        Long resul = db.insert(Transacciones.tblContactos,Transacciones.id,valores);

        Toast.makeText(getApplicationContext(),"Registrado con exito!!! Codigo" + resul,Toast.LENGTH_LONG).show();
        db.close();
    }

}