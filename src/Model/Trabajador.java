package Model;

import java.time.LocalDateTime;

public class Trabajador {

    private String nombre;
    private int edad;
    private String tipoContrato;
    private LocalDateTime fechaContratacion;

    public Trabajador (String nombre, int edad, String tipoContrato, LocalDateTime fechaContratacion) {

        this.nombre = nombre;
        this.edad = edad;
        this.tipoContrato = tipoContrato;
        this.fechaContratacion = fechaContratacion;
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

        this.tipoContrato = tipoContrato;
        return true;
    }

    public LocalDateTime getFechaContratacion() {

        return fechaContratacion;
    }

    public boolean setFechaContratacion(LocalDateTime fechaContratacion) {

        this.fechaContratacion = fechaContratacion;
        return true;
    }
}
