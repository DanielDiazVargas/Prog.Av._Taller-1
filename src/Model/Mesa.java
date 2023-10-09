package Model;

public class Mesa {

    private int numero;
    private int posicion;
    private boolean disponible;
    private Cliente cliente;

    public Mesa (int numero, int posicion) {

        this.numero = numero + 1;
        this.posicion = posicion;
    }

    public int getNumero() {

        return numero;
    }

    public int getPosicion() {

        return posicion;
    }

    public void setPosicion(int posicion) {

        this.posicion = posicion;
    }

    public boolean getDisponible() {

        return disponible;
    }

    public void setDisponible(boolean disponible) {

        this.disponible = disponible;
    }

    public Cliente getCliente() {

        return cliente;
    }

    public void setCliente(Cliente cliente) {

        this.cliente = cliente;
    }
}
