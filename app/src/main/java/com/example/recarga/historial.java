package com.example.recarga;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class historial extends AppCompatActivity {

    RecyclerView recyclerPersonas;
    ArrayList<Usuario> listaUsuario;
    SQLiteOpenHelper registro;
    private ListaPersonaAdaptador listaPersonaAdaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        recyclerPersonas=findViewById(R.id.recyclerPersonas);

        registro = new BdSQLiteOpenHelper (this,"usuarios",null,1);
        listaPersonaAdaptador = new ListaPersonaAdaptador(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerPersonas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listaPersonaAdaptador);
        mostrarDatos(getIntent().getStringExtra("correo"));
    }

    public void mostrarDatos(String correo){
        SQLiteDatabase sqLiteDatabase = registro.getReadableDatabase();
        Usuario usuario = null;

        Cursor cursor = sqLiteDatabase.rawQuery("select * from registro where correo=?", new String[]{correo});
        while (cursor.moveToNext()){
            usuario = new Usuario();
            usuario.setTelefono(cursor.getString(0));
            usuario.setOperador(cursor.getString(1));
            usuario.setValor(cursor.getString(2));
            usuario.setFecha(cursor.getString(3));
            listaPersonaAdaptador.agregarregistro(usuario);

        }
    }

    public void atras(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}