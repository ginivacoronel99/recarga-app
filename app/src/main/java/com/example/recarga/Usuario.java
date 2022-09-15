package com.example.recarga;

import java.io.Serializable;

public class Usuario implements Serializable {

    private String telefono;
    private  String operador;
    private String valor;
    private String fecha;
    private String correo;

    public Usuario() {
        this.telefono = telefono;
        this.operador = operador;
        this.valor = valor;
        this.fecha = fecha;
        this.correo = correo;
    }

    public String getTelefono() {

        return telefono;
    }

    public void setTelefono(String telefono) {

        this.telefono = telefono;
    }

    public String getOperador() {

        return operador;
    }

    public void setOperador(String operador) {

        this.operador = operador;
    }

    public String getValor() {

        return valor;
    }

    public void setValor(String valor) {

        this.valor = valor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
