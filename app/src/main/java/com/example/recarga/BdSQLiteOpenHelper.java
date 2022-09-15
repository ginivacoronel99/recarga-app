package com.example.recarga;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BdSQLiteOpenHelper extends SQLiteOpenHelper {

    public BdSQLiteOpenHelper(
            @Nullable Context context,
            @Nullable String name,
            @Nullable SQLiteDatabase.CursorFactory factory,
            int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table usuario (correo text primary key, nombre text, contraseña text, saldo text, fecha text)");
        db.execSQL("create table registro (numtelefono number primary key, operador text, valor text, fecha text, correo text)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean checkuserpassword(String correo, String contraseña) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from usuario where correo=? and contraseña=?", new String[]{correo, contraseña});
        return cursor.getCount() > 0;
    }

    public String getNombreSQLite(String primaryKey) {
        String nom = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select nombre from usuario where correo=?", new String[]{primaryKey});
        if (cursor.moveToFirst()) {
            nom = cursor.getString(0);
        }
        return nom;
    }

    public String getSaldoSQLite(String primaryKey) {
        String saldo = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select saldo from usuario where correo=?", new String[]{primaryKey});
        if (cursor.moveToFirst()) {
            saldo = cursor.getString(0);
        }
        return saldo;
    }

    public String checksaldo(String primaryKey) {
        String saldo = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select saldo from usuario where correo=?", new String[]{primaryKey});
        if (cursor.moveToFirst()) {
            saldo = cursor.getString(0);
        }
        return saldo;
    }

    public String checkfecha(String primaryKey){
        String fecha = "";
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select fecha from usuario where correo=?",new String[] {primaryKey});
        if (cursor.moveToFirst()) {
            fecha = cursor.getString(0);
        }
        return fecha;
    }




}
