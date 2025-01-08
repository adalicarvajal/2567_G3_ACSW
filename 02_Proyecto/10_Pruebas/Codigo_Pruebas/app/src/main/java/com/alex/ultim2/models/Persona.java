package com.alex.ultim2.models;

public class Persona {
    private String id;
    private String name;
    private String surname;
    private String fec_nac;
    private String age;
    private String anio;
    private String correo;
    private String telefono;
    private String ciudad;
    private String pais;

    public Persona(String id, String name, String surname,String fec_nac, String age,String anio, String correo, String telefono, String ciudad, String pais) {
        this.setId(id);
        this.setName(name);
        this.setSurname(surname);
        this.setFec_nac(fec_nac);
        this.setAge(age);
        this.setAnio(anio);
        this.setCorreo(correo);
        this.setTelefono(telefono);
        this.setCiudad(ciudad);
        this.setPais(pais);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFec_nac() {
        return fec_nac;
    }

    public void setFec_nac(String fec_nac) {
        this.fec_nac = fec_nac;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
