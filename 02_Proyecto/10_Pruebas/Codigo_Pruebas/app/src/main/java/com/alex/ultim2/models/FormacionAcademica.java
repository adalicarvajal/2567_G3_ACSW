package com.alex.ultim2.models;

import android.widget.TextView;

public class FormacionAcademica {
    public String denominacion;
    public String titulo;


    public FormacionAcademica(String denominacion, String titulo) {
        this.denominacion = denominacion;
        this.titulo = titulo;

    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }



}
