package Model;

import java.time.LocalDate;

public class Trabajador {

    private String nombre;
    private int edad;
    private String tipoContrato;
    private LocalDate fechaContratacion;
    private LocalDate fechaTermino;

    public Trabajador (String nombre, int edad, String tipoContrato, LocalDate fechaContratacion, LocalDate fechaTermino) {
        this.nombre = nombre;
        this.edad = edad;
        this.tipoContrato = tipoContrato;
        this.fechaContratacion = fechaContratacion;
        this.fechaTermino = fechaTermino;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public boolean setEdad(int edad) {
        if (edad < 0) {
            return false;
        }

        this.edad = edad;
        return true;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public boolean setTipoContrato(String tipoContrato) {
        if (this.tipoContrato == tipoContrato) {

            return false;
        }

        this.tipoContrato = tipoContrato;
        return true;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public LocalDate getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(LocalDate fechaTermino) {
        this.fechaTermino = fechaTermino;
    }
}