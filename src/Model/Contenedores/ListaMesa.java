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

        for (int i = 0; i < this.cantMesasMaxima; i++) {
            if (this.mesas[i] == null) {
                this.mesas[i] = mesa;
                this.cantMesasActual++;
                return true;
            }
        }

        throw new Exception("¡Ah ocurrido un error!");
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

        if (mesas[numero] != null) {
            mesas[numero] = null;
            this.cantMesasActual --;
            return true;
        }

        throw new Exception("¡Ah ocurrido un error!");
    }

    public void actualizarDisponibilidad (int numeroMesa) {
        this.mesas[numeroMesa].setDisponible(!this.mesas[numeroMesa].getDisponible());
    }

    public Mesa obtenerMesa (int numeroMesa) throws Exception {
        if (numeroMesa < 1 || numeroMesa > this.cantMesasMaxima) {
            throw new Exception("¡Numero de mesa invalido");
        }

        for (int i = 0; i < this.cantMesasMaxima; i++) {
            if (mesas[i].getNumero() == numeroMesa) {
                if (mesas[i] == null) {
                    throw new Exception("¡La mesa n°" + i + " no existe!");
                }
                return mesas[i];
            }
        }
        throw new Exception("Ah ocurrido un error!");
    }
}
