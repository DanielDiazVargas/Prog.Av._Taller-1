package Model;

public class Orden {
    private String[][] listaProductos;
    private int cantProductosPedidos;
    private Cliente cliente;
    private Trabajador trabajadorAsignado;
    private int numeroMesaAsociada;

    public Orden(Cliente cliente, Trabajador trabajador, int numeroMesa) {
        this.listaProductos = new String[4][999];
        this.cantProductosPedidos = 0;
        this.cliente = cliente;
        this.trabajadorAsignado = trabajador;
        this.numeroMesaAsociada = numeroMesa;
    }

    public boolean pedirProducto(Producto producto, int cantidad) {
        if (producto == null) {
            return false;
        }

        for (int i = 0; i < cantProductosPedidos; i++) {
            if (this.listaProductos[1][i] == producto.getNombre()) {
                this.listaProductos[0][i] = Integer.toString(Integer.parseInt(this.listaProductos[0][i]) + cantidad);
                this.listaProductos[3][i] = Integer.toString(Integer.parseInt(this.listaProductos[3][i]) + (producto.getPrecio() * cantidad));
                return true;
            }
        }

        this.listaProductos[0][this.cantProductosPedidos] = Integer.toString(cantidad);
        this.listaProductos[1][this.cantProductosPedidos] = producto.getNombre();
        this.listaProductos[2][this.cantProductosPedidos] = Integer.toString(producto.getPrecio());
        this.listaProductos[3][this.cantProductosPedidos] = Integer.toString(producto.getPrecio() * cantidad);
        cantProductosPedidos ++;
        return true;
    }

    public String[][] resumen () {
        return listaProductos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Trabajador getTrabajador() {
        return trabajadorAsignado;
    }

    public int getNumeroMesaAsociada() {
        return numeroMesaAsociada;
    }
}
