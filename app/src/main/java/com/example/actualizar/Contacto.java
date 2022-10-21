package com.example.actualizar;

public class Contacto {
    String Nombre;
    String Telefono;
    int Edad;
    String Id;

    public Contacto(String nombre, String telefono, int edad, String id) {
        Nombre = nombre;
        Telefono = telefono;
        Edad = edad;
        Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public int getEdad() {
        return Edad;
    }

    public void setEdad(int edad) {
        Edad = edad;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
