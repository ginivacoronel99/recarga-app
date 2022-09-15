package com.example.recarga;

import static com.example.recarga.utils.Utils.getDate;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    EditText et1, et2;
    String  correo, contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
    }

    public void ingresar(View v) {
        correo = et1.getText().toString();
            contraseña = et2.getText().toString();

        BdSQLiteOpenHelper usuario = new BdSQLiteOpenHelper(this, "usuarios", null, 1);

            if (TextUtils.isEmpty(correo) || TextUtils.isEmpty(contraseña))
                Toast.makeText(MainActivity.this, "Llene todos los campos", Toast.LENGTH_SHORT).show();
            else {
                    Boolean checkuserpassword = usuario.checkuserpassword(correo, contraseña);
                if (checkuserpassword == true) {

                    Toast.makeText(MainActivity.this, "Bienvenid@ " + usuario.getNombreSQLite(correo), Toast.LENGTH_SHORT).show();
                    String fechaActual = getDate();

                    String fechaguardada = usuario.checkfecha(correo);
                    System.out.println("Ingreso ---> fechaActual "+fechaActual);
                    System.out.println("Ingreso ---> fechaguardada "+fechaguardada);

                    if (!fechaguardada.equals(fechaActual)){
                        SQLiteDatabase bd = usuario.getWritableDatabase();
                        ContentValues newSaldo = new ContentValues();
                        newSaldo.put("saldo","1000000");
                        bd.update("usuario", newSaldo, "correo=?", new String[]{correo});
                    }
                    if (!fechaguardada.equals(fechaActual)){
                        SQLiteDatabase bd = usuario.getWritableDatabase();
                        ContentValues newFecha = new ContentValues();
                        newFecha.put("fecha", fechaActual);
                        bd.update("usuario", newFecha, "correo=?", new String[]{correo});

                    }
                    startActivity(new Intent(getApplicationContext(), principal.class).putExtra("correo",correo));
                } else {
                    Toast.makeText(MainActivity.this, "Usuario no registrado", Toast.LENGTH_SHORT).show();
                }
                et1.setText("");
                et2.setText("");
            }
        }

         public  void registro (View view){
                Intent siguiente = new Intent(this, registro.class);
                startActivity(siguiente);
         }
}
