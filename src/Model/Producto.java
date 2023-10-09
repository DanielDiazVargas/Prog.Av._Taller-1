package Model;

public class Producto {

    private String nombre;
    private int precio;
    private int stock;
    private String categoria;

    public Producto(String nombre, int precio, int stock, String categoria) {

        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }

    public String getNombre() {

        return nombre;
    }

    public void setNombre(String nombre) {

        this.nombre = nombre;
    }

    public int getPrecio() {

        return precio;
    }

    public boolean setPrecio(int precio) {

        if (precio < 0) {

            return false;
        }

        this.precio = precio;
        return true;
    }

    public int getStock() {

        return stock;
    }

    public boolean setStock(int stock) {

        if (stock < 0) {

            return false;
        }

        this.stock = stock;
        return true;
    }

    public String getCategoria() {

        return categoria;
    }

    public void setCategoria(String categoria) {

        this.categoria = categoria;
    }
}
