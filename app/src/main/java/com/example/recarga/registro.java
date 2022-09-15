package com.example.recarga;

import static com.example.recarga.utils.Utils.getDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class registro extends AppCompatActivity {
    EditText et1, et2, et3, et4;
    String nombre, email, password, comfirpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);
    }

    public void guardar(View view) {
        nombre = et1.getText().toString().trim();
        email = et2.getText().toString().trim();
        password = et3.getText().toString().trim();
        comfirpassword = et4.getText().toString().trim();

        BdSQLiteOpenHelper usuario = new BdSQLiteOpenHelper(this, "usuarios", null, 1);
        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(comfirpassword))
            Toast.makeText(registro.this, "llene todos los campos", Toast.LENGTH_SHORT).show();
        else {
            SQLiteDatabase bd = usuario.getWritableDatabase();
            Cursor cursor = bd.rawQuery("select nombre from usuario where correo=?", new String[]{email});
            if (cursor.moveToFirst()) {
                Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Objects.equals(password, comfirpassword)) {
                Toast.makeText(this, "las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                return;
            }
            ContentValues registro = new ContentValues();
            registro.put("nombre", nombre);
            registro.put("correo", email);
            registro.put("contraseña", password);
            registro.put("saldo", 1000000);
            registro.put("fecha", getDate());
            bd.insert("usuario", null, registro);
            bd.close();

            if (validar()) {
                Toast.makeText(this, "El registro se realizo con éxito", Toast.LENGTH_SHORT).show();
                et1.setText("");
                et2.setText("");
                et3.setText("");
                et4.setText("");
                finish();
                // } else {
                /*Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            }*/
            }
        }

    }



    public boolean validar (){
        if (nombre.isEmpty()){
            et1.setError("campo obligatorio");
            return false;
        }
        if (email.isEmpty()){
            et2.setError("campo obligatorio");
            return false;
        }else if(!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()){
            et2.setError("Ingresa un correo valido");
            return false;
        }

        if (password.isEmpty()){
            et3.setError("campo obligatorio");
            return false;
        }

        if (comfirpassword.isEmpty()){
            et4.setError("campo obligatorio");
            return false;
        }
        return true;
    }



    public void cancelar (View view) {
        finish();
    }
}