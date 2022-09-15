package com.example.recarga;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class principal extends AppCompatActivity {

    TextView tvNombre;
    TextView tvValor;
    String correoAct;
    String nombreAct;
    String millon;
    BdSQLiteOpenHelper usuario;
    BroadcastReceiver receiver;
    IntentFilter filter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        usuario = new BdSQLiteOpenHelper(this, "usuarios", null, 1);
        tvNombre = findViewById(R.id.tvNombre);
        tvValor = findViewById(R.id.tvValor);
        correoAct = getIntent().getStringExtra("correo");
        /*filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);*/
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        nombreAct = usuario.getNombreSQLite(correoAct);
        millon = usuario.getSaldoSQLite(correoAct);
        tvNombre.setText("Hola, " + nombreAct);
        tvValor.setText("Saldo disponible: $" + millon);
        /*receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("API123",""+intent.getAction());
                BdSQLiteOpenHelper registros = new BdSQLiteOpenHelper(principal.this, "usuarios", null, 1);

                SQLiteDatabase bd = registros.getWritableDatabase();
                ContentValues consaldo = new ContentValues();
                consaldo.put("saldo", "1000000");
                bd.update("usuario", consaldo, "correo=?", new String[]{correoAct});
                bd.close();
                Toast.makeText(principal.this, "SALDO ACTUALIZADO!!", Toast.LENGTH_SHORT).show();
                unregisterReceiver(receiver);
                onResume();
            }
        };
        registerReceiver(receiver, filter);*/
    }

    /*@Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }*/

    public void recargas(View view) {
        Intent siguiente = new Intent(this, recarga.class);
        siguiente.putExtra("correo", correoAct);
        startActivity(siguiente);
    }

    public void historial(View view) {
        Intent siguiente = new Intent(this, historial.class);
        siguiente.putExtra("correo", correoAct);
        startActivity(siguiente);
    }

    public void cerrarsesion(View view) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(principal.this);
        alerta.setMessage("Desea cerrar sesion?")
                .setCancelable(false)
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        Toast.makeText(principal.this, "Vuelve pronto", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog titulo = alerta.create();
        titulo.setTitle("Salida");
        titulo.show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}