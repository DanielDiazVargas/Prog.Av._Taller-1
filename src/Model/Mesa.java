package Model;

public class Mesa {
    private int numero;
    private int posicion;
    private boolean disponible;

    public Mesa (int numero, int posicion) {
        this.numero = numero;
        this.posicion = posicion;
        this.disponible = true;
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
}
