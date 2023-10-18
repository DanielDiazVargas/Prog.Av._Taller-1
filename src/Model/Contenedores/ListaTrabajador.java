package Model.Contenedores;

import Model.Producto;
import Model.Trabajador;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ListaTrabajador {

    private Trabajador[] trabajadores;
    private int cantTrabajadoresMaxima;
    private int cantTrabajadoresActual;


    public ListaTrabajador(int cantTrabajadoresMaxima) {
        this.cantTrabajadoresMaxima = cantTrabajadoresMaxima;
        this.cantTrabajadoresActual = 0;
        this.trabajadores = new Trabajador[this.cantTrabajadoresMaxima];
    }

    public boolean agregarTrabajador(Trabajador trabajador) throws Exception {
        if (this.cantTrabajadoresActual == this.cantTrabajadoresMaxima) {
            throw new Exception("¡La lista de trabajadores esta lleno!");
        }

        if (trabajador == null) {
            throw new Exception("¡El trabajador ingresado no es valido!");
        }

        for (int i = 0; i < this.cantTrabajadoresMaxima; i++) {
            if (this.trabajadores[i] == null) {
                this.trabajadores[i] = trabajador;
                this.cantTrabajadoresActual ++;
                return true;
            }
        }

        throw new Exception("¡Ah ocurrido un error!");
    }

    public boolean finalizarContrato(int posicion) throws Exception {
        if (this.cantTrabajadoresActual == 0) {
            throw new Exception("¡No hay personal contratado en el Restaurante!");
        }

        if (this.trabajadores[posicion] != null){
            this.trabajadores[posicion] = null;
            this.cantTrabajadoresActual --;
            return true;
        }

        throw new Exception("No se ha encontrado el trabajador");
    }

    public boolean renovarContrato(int posicion, LocalDate nuevaFechaContrato, LocalDate nuevaFechaTermino) throws Exception {
        //las fechas permitidas seran de formato dd/MM/yyyy ej: 16/10/2024
        try {
            this.trabajadores[posicion].setFechaContratacion(nuevaFechaContrato);
            this.trabajadores[posicion].setFechaTermino(nuevaFechaTermino);
            return true;

        }catch (Exception e){
            throw new Exception ("¡La fecha de nuevo contrato no es valida!");
        }
    }

    public boolean otorgarContratoIndefinido(int posicion, LocalDate fecha) throws Exception {
        if (this.trabajadores[posicion].getTipoContrato().equalsIgnoreCase("Indefinido")) {
            throw new Exception("¡El trabajador ya tiene un contrato indefinido");
        }

        this.trabajadores[posicion].setTipoContrato("Indefinido");
        this.trabajadores[posicion].setFechaContratacion(fecha);
        this.trabajadores[posicion].setFechaTermino(null);
        return true;
    }

    public int identificarTrabajador(String nombre) throws Exception {
        for (int i = 0; i < this.cantTrabajadoresMaxima; i++) {
            if (this.trabajadores[i] != null) {
                if (this.trabajadores[i].getNombre().equals(nombre)){
                    return i;
                }
            }
        }
        throw new Exception("¡Trabajador no encontrado!");

    }

    public Trabajador obtenerTrabajadorsPorPosicion(int posicion) throws Exception {
        if (this.trabajadores[posicion] != null) {
            return this.trabajadores[posicion];
        }else {
            throw new Exception("¡Trabajador no encontrado!");
        }
    }
}