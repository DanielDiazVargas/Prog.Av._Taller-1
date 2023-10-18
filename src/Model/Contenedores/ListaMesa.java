package Model.Contenedores;

import Model.Mesa;

public class ListaMesa {

    private Mesa[] mesas;
    private int cantMesasMaxima;
    private int cantMesasActual;

    public ListaMesa (int cantMesasMaxima) {
        this.cantMesasMaxima = cantMesasMaxima;
        this.cantMesasActual = 0;
        this.mesas = new Mesa[this.cantMesasMaxima];
    }

    public boolean agregarMesa (Mesa mesa) throws Exception {
        if (this.cantMesasActual == this.cantMesasMaxima) {
            throw new Exception("¡No hay capacidad para mas mesas!");
        }

        if (this.mesas[mesa.getNumero() - 1] == null) {
            this.mesas[mesa.getNumero() - 1] = mesa;
            this.cantMesasActual++;
            return true;
        }else {
            throw new Exception("¡La mesa n°" + mesa.getNumero() + " ya existe!");
        }
    }

    public Mesa buscarMesaDisponible() throws Exception {
        for (int i = 0; i < cantMesasActual; i++) {
            if (this.mesas[i].getDisponible()) {
                return mesas[i];
            }
        }

        throw new Exception("¡Ah ocurrido un error!");
    }

    public boolean quitarMesa (int numero) throws Exception {
        if (this.cantMesasActual == 0) {
            throw new Exception("¡No hay mesas registradas!");
        }

        if (mesas[numero - 1] != null) {
            mesas[numero - 1] = null;
            this.cantMesasActual --;
            return true;
        }

        throw new Exception("¡Mesa no encontrada!");
    }

    public void actualizarDisponibilidad (int numeroMesa) {
        this.mesas[numeroMesa].setDisponible(!this.mesas[numeroMesa].getDisponible());
    }

    public Mesa obtenerMesa (int numeroMesa) throws Exception {
        if (numeroMesa < 1 || numeroMesa > this.cantMesasMaxima) {
            throw new Exception("¡Numero de mesa invalido");
        }

        if (this.cantMesasActual == 0) {
            throw new Exception("¡No hay mesas registradas!");
        }

        for (int i = 0; i < this.cantMesasMaxima; i++) {
            if (this.mesas[i] != null) {
                if (this.mesas[i].getNumero() == numeroMesa) {
                    return this.mesas[i];
                }
            }
        }
        throw new Exception("Ah ocurrido un error!");
    }
}