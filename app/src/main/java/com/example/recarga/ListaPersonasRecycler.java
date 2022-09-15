package com.example.recarga;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListaPersonasRecycler extends AppCompatActivity {

    ArrayList<Usuario> listaUsuario;
    RecyclerView recyclerViewUsuarios;

    BdSQLiteOpenHelper conn;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        conn = new BdSQLiteOpenHelper(getApplicationContext(),"usuarios",null,1);

        listaUsuario=new ArrayList<>();

        recyclerViewUsuarios= (RecyclerView) findViewById(R.id.recyclerPersonas);
        recyclerViewUsuarios.setLayoutManager(new LinearLayoutManager(this));

        consultarListaPersonas();

//        ListaPersonaAdaptador adaptador=new ListaPersonaAdaptador(listaUsuario);
//        recyclerViewUsuarios.setAdapter(adaptador);

    }

    private void consultarListaPersonas() {
        SQLiteDatabase db=conn.getReadableDatabase();

        Usuario usuario=null;
        // listaUsuarios=new ArrayList<Usuario>();
        //select * from usuarios

        Cursor cursor = db.rawQuery("select * from registro where nombre=? and telefono=? and fecha=? ",null);

        while (cursor.moveToNext()){
            usuario=new Usuario();
            usuario.setTelefono(cursor.getString(0));
            usuario.setOperador(cursor.getString(1));
            usuario.setValor(cursor.getString(2));
            usuario.setFecha(cursor.getString(3));

            listaUsuario.add(usuario);
        }
    }


}
