package Model;

public class ListaMesa {

    private Mesa[] mesas;
    private int cantMesasMaxima = 200;
    private int cantMesasActual;

    public ListaMesa (int cantMesasMaxima) {

        this.cantMesasMaxima = cantMesasMaxima;
        this.cantMesasActual = 0;
        this.mesas = new Mesa[this.cantMesasMaxima];
    }

    public boolean agregarMesa (Mesa mesa) {

        for (int i = 0; i < this.cantMesasMaxima; i++) {

            if (this.mesas[i] == null) {

                this.mesas[i] = mesa;
                this.cantMesasActual++;
                return true;
            }
        }
        return false;
    }

    public Mesa buscarMesaDisponible() {

        for (int i = 0; i < cantMesasActual; i++) {

            if (this.mesas[i].getDisponible() == true) {

                return mesas[i];
            }
        }

        return null;
    }

    public boolean quitarMesa () {

        //TODO
        return true;
    }

    public boolean actualizarDisponibilidad (int numeroMesa) {

        numeroMesa--;

        if (this.mesas[numeroMesa] == null) {
            return false;
        }

        if (this.mesas[numeroMesa].getDisponible() == false) {

           this.mesas[numeroMesa].setDisponible(true);
        }else {

            this.mesas[numeroMesa].setDisponible(false);
        }
        return true;
    }
}
