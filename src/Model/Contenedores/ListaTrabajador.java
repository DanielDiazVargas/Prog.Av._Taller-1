package Model.Contenedores;

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

    public boolean renovarContrato(int posicion, String nuevaFechaContrato, String nuevaFechaTermino) throws Exception {
        //las fechas permitidas seran de formato dd/MM/yyyy ej: 16/10/2024
        try {
            LocalDate fechaConvertida = LocalDate.parse(nuevaFechaContrato, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            this.trabajadores[posicion].setFechaContratacion(fechaConvertida);
            fechaConvertida = LocalDate.parse(nuevaFechaTermino, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            this.trabajadores[posicion].setFechaTermino(fechaConvertida);
            return true;

        }catch (DateTimeParseException e){
            throw new Exception ("La fecha de nuevo contrato no es valida");
        }
    }

    public boolean otorgarContratoIndefinido(int posicion, String fecha) throws Exception {
        if (this.trabajadores[posicion].getTipoContrato().equalsIgnoreCase("Indefinido")) {
            throw new Exception("¡El trabajador ya tiene un contrato indefinido");
        }

        this.trabajadores[posicion].setTipoContrato("Indefinido");
        LocalDate fechaConvertida = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.trabajadores[posicion].setFechaContratacion(fechaConvertida);
        this.trabajadores[posicion].setFechaTermino(null);
        return true;
    }

    public int identificarTrabajador(String nombre){
        for (int i = 0; i < this.cantTrabajadoresMaxima; i++) {
            if (this.trabajadores[i].getNombre().equals(nombre)){
                return i;
            }
        }
        return -1;

    }
}