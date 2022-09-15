package com.example.recarga;

import static com.example.recarga.utils.Utils.getDate;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class recarga extends AppCompatActivity {

    Button recargar;
    private Spinner spinner_operadores;
    String NumTelefono, valorRecarga;
    EditText et1, et2;
    View seleccion;
    TextView tvValor;
    String correoAct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recarga);

        recargar = (Button) findViewById(R.id.buttonRecarga);
        spinner_operadores = (Spinner) findViewById(R.id.spinner_operadores);

        tvValor = findViewById(R.id.tvValor);

        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        seleccion = findViewById(R.id.spinner_operadores);
        correoAct = getIntent().getStringExtra("correo");

        String[] opciones = {"Avantel", "Claro", "Movistar", "Tigo", "Virgin Mobile"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_operadores, opciones);
        spinner_operadores.setAdapter(adapter);
    }

    public void recargar(View view) {

        NumTelefono = et1.getText().toString().trim();
        valorRecarga = et2.getText().toString().trim();

        if (TextUtils.isEmpty(NumTelefono) || TextUtils.isEmpty(valorRecarga)) {
            Toast.makeText(recarga.this, "Llene todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        String seleccion = spinner_operadores.getSelectedItem().toString();

        switch (seleccion) {
            case "Claro":
                if (!(NumTelefono.startsWith("310") || NumTelefono.startsWith("311") || NumTelefono.startsWith("312") ||
                        NumTelefono.startsWith("313") || NumTelefono.startsWith("314") || NumTelefono.startsWith("320") ||
                        NumTelefono.startsWith("321") || NumTelefono.startsWith("322") || NumTelefono.startsWith("323"))) {
                    Toast.makeText(this, "El número no pertenece al operador Claro", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            case "Movistar":
                if (!(NumTelefono.startsWith("315") || NumTelefono.startsWith("316") || NumTelefono.startsWith("317") ||
                        NumTelefono.startsWith("318"))) {
                    Toast.makeText(this, "El número no pertenece al operador Movistar", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            case "Virgin Mobile":
                if (!(NumTelefono.startsWith("319"))) {
                    Toast.makeText(this, "El número no pertenece al operador Virgin Mobile", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            case "Avantel":
                if (!(NumTelefono.startsWith("350") || NumTelefono.startsWith("351"))) {
                    Toast.makeText(this, "El número no pertenece al operador Avantel", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            default:
                if (!(NumTelefono.startsWith("300") || NumTelefono.startsWith("301") || NumTelefono.startsWith("302") ||
                        NumTelefono.startsWith("304") || NumTelefono.startsWith("305"))) {
                    Toast.makeText(this, "El número no pertenece al operador Tigo", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
        }


        AlertDialog.Builder alerta = new AlertDialog.Builder(recarga.this);
        alerta.setMessage("Desea realizar la recarga?")
                .setCancelable(false)
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BdSQLiteOpenHelper registro = new BdSQLiteOpenHelper(recarga.this, "usuarios", null, 1);
                        int valor = Integer.parseInt(valorRecarga);
                        int saldo = Integer.parseInt(registro.checksaldo(correoAct));
                        if (valor <= saldo){
                            int saldototal = (saldo = Integer.parseInt(registro.checksaldo(correoAct)) - valor);
                        SQLiteDatabase db = registro.getWritableDatabase();
                        ContentValues reg = new ContentValues();
                        reg.put("numtelefono", NumTelefono);
                        reg.put("valor", valorRecarga);
                        reg.put("operador", seleccion);
                        reg.put("fecha", getDate());
                        reg.put("correo", correoAct);
                        db.insert("registro", null, reg);
                        db.close();
                        SQLiteDatabase bd = registro.getWritableDatabase();
                        ContentValues consaldo = new ContentValues();
                        consaldo.put("saldo", String.valueOf(saldototal));
                        bd.update("usuario", consaldo, "correo=?", new String[]{correoAct});
                        bd.close();
                        Toast.makeText(recarga.this, "Recarga Exitosa!!", Toast.LENGTH_SHORT).show();
                        finish();
                    }else
                    {
                        Toast.makeText(recarga.this, "Saldo insuficiente", Toast.LENGTH_SHORT).show();
                        }
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


    public void atras(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}