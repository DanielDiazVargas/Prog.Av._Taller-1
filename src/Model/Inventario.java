package Model;

public class Inventario {

    private Producto[] productos;
    private int cantProdductoMaxima;
    private int cantProdductoActual;

    public Inventario (int cantProdductoMaxima) {

        this.cantProdductoMaxima = cantProdductoMaxima;
        this.cantProdductoActual = 0;
        this.productos = new Producto[this.cantProdductoMaxima];
    }

    public boolean agregarProducto (Producto producto) {

        //TODO
        this.cantProdductoActual ++;
        return true;
    }

    public boolean actualizarProducto (int posicion) {

        //TODO
        return true;
    }

    public boolean quitarProducto (int posicion) {

        //TODO
        this.cantProdductoActual --;
        return true;
    }

    public int obtenerPosicion (String nombre) {

        //TODO
        return -1;
    }
}
