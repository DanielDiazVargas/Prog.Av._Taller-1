package Model.Contenedores;

import Model.Producto;

public class Inventario {

    private Producto[] productos;
    private int cantProdductoMaxima;
    private int cantProdductoActual;

    public Inventario (int cantProdductoMaxima) {
        this.cantProdductoMaxima = cantProdductoMaxima;
        this.cantProdductoActual = 0;
        this.productos = new Producto[this.cantProdductoMaxima];
    }

    public boolean agregarProducto (Producto producto) throws Exception {
        if (this.cantProdductoActual == this.cantProdductoMaxima) {
            throw new Exception("¡El inventario esta lleno!");
        }

        if (producto == null) {
            throw new Exception("¡El producto no es valido!");
        }

        for (int i = 0; i < this.cantProdductoMaxima; i++) {
            if (this.productos[i] == null) {
                this.productos[i] = producto;
                this.cantProdductoActual ++;
                return true;
            }
        }

        throw new Exception("¡Ah ocurrido un error!");
    }

    public boolean actualizarProducto (int posicion, Producto producto) throws Exception {
        if (producto == null) {
            throw new Exception("¡El producto no es valido!");
        }
        this.productos[posicion] = producto;
        return true;
    }

    public boolean quitarProducto (int posicion) throws Exception {
        if (this.cantProdductoActual == 0) {
            throw new Exception("¡No hay productos en el inventario!");
        }

        if (this.productos[posicion] != null) {
            this.productos[posicion] = null;
            this.cantProdductoActual --;
            return true;
        }

        throw new Exception("¡Producto no encontrado!");
    }

    public int obtenerPosicion (String nombre) {
        for (int i = 0; i < this.cantProdductoMaxima; i++) {
            if (nombre.equalsIgnoreCase(this.productos[i].getNombre())) {
                return i;
            }
        }

        return -1;
    }

    public boolean actualizarStock (int posicion, int stock) throws Exception {
        stock += this.productos[posicion].getStock();
        if (stock < 0) {
            throw new Exception("¡Stock insuficiente!");
        }
        this.productos[posicion].setStock(stock);
        return true;
    }

    public String[] menuDisponible() throws Exception {
        String menu[] = new String[this.cantProdductoActual];

        for (int i = 0; i < this.cantProdductoMaxima; i++) {
            if (this.cantProdductoActual == 0) {
                throw new Exception("¡No hay productos en el menu!");
            }

            if (this.productos[i] != null) {
                menu[i] = this.productos[i].getNombre() + " | $" + this.productos[i].getPrecio();
            }
        }
        return menu;
    }
}
