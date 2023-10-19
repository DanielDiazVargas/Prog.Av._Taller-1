package Model.Contenedores;

import Model.Orden;

public class ListaOrden {

    private Orden[] ordenes;
    private int cantOrdenesMaximas;
    private int cantOrdenesActual;

    public ListaOrden(int cantOrdenesMaximas) {
        this.cantOrdenesMaximas = cantOrdenesMaximas;
        this.cantOrdenesActual = 0;
        this.ordenes = new Orden[9999];
    }

    public boolean agregarOrden(Orden orden) throws Exception {
        if (this.cantOrdenesActual == this.cantOrdenesMaximas) {
            throw new Exception("¡No se puede generar mas ordenes!");
        }

        if (orden == null) {
            throw new Exception("¡La orden no es valida!");
        }

        for (int i = 0; i < this.cantOrdenesMaximas; i++) {
            if (this.ordenes[i] == null) {
                this.ordenes[i] = orden;
                this.cantOrdenesActual ++;
                return true;
            }
        }

        throw new Exception("¡Ah ocurrido un error!");
    }

    public boolean eliminarOrden(int posicion) throws Exception {
        if (this.cantOrdenesActual == 0) {
            throw new Exception("¡No hay Ordenes!");
        }

        if (this.ordenes[posicion] != null) {
            this.ordenes[posicion] = null;
            this.cantOrdenesActual --;
            return true;
        }

        throw new Exception("¡Orden no encontrada!");
    }

    public int obtenerPosicion(String nombre) throws Exception {
        for (int i = 0; i < this.cantOrdenesMaximas; i++) {
            if (this.ordenes[i] != null) {
                if (nombre.equalsIgnoreCase(this.ordenes[i].getCliente().getNombre())) {
                    return i;
                }
            }
        }

        throw new Exception("¡Cliente no encontrado!");
    }

    public String [][] obtenerResumen(String nombre) throws Exception {
        try {
            return ordenes[obtenerPosicion(nombre)].resumen();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public Orden obtenerOrden(String nombre) throws Exception {
        try {
            return ordenes[obtenerPosicion(nombre)];
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public Orden obtenerOrdenPorPosicion(int posicion) throws Exception {
        if (this.ordenes[posicion] != null) {
            return this.ordenes[posicion];
        }else {
            throw new Exception("¡Orden no encontrada!");
        }
    }
}