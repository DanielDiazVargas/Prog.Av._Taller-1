package Service;

import Model.*;
import Model.Contenedores.Inventario;
import Model.Contenedores.ListaMesa;
import Model.Contenedores.ListaOrden;
import Model.Contenedores.ListaTrabajador;
import Model.Orden;

public class SistemaImpl implements Sistema{

    private Inventario producto;
    private ListaTrabajador trabajador;
    private ListaMesa mesa;
    private ListaOrden orden;

    public SistemaImpl() {
        this.producto = new Inventario(999);
        this.trabajador = new ListaTrabajador(999);
        this.mesa = new ListaMesa(999);
        this.orden = new ListaOrden(9999);
    }

    @Override
    public String agregarProductoInventario(Producto producto) {
        try {
            this.producto.agregarProducto(producto);
            return "Producto agregado exitosamente";
        }catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String actualizarProducto(String nombre, Producto producto) {
        if (this.producto.obtenerPosicion(nombre) == -1) {
            return "¡Producto no encontrado";
        }

        try {
            this.producto.actualizarProducto(this.producto.obtenerPosicion(nombre), producto);
            return "Producto actualizado correctamente";
        }catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String eliminarProducto(String nombre) {
        try {
            this.producto.quitarProducto(this.producto.obtenerPosicion(nombre));
            return "Producto eliminado";
        }catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String actualizarStockInventario(String nombre, int stock) {
        try {
            this.producto.actualizarStock(this.producto.obtenerPosicion(nombre), stock);
            return "Stock actualizado correctamente";
        }catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String contratarTrabajador(Trabajador trabajador) {
        try {
            this.trabajador.agregarTrabajador(trabajador);
            return "Trabajador contratado";
        }catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String despedirTrabajador(String nombre) {
        try {
            this.trabajador.finalizarContrato(this.trabajador.identificarTrabajador(nombre));
            return "El trabajador ha sido despedido";
        }catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String renovarContrato(String nombre, String fechaContratacion, String fechaTermino) {
        if (this.trabajador.identificarTrabajador(nombre) == -1) {
            return "¡Trabajador no encontrado";
        }

        try {
            this.trabajador.renovarContrato(this.trabajador.identificarTrabajador(nombre), fechaContratacion, fechaTermino);
            return "El contrato del trabajador ha sido renovado";
        }catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String otorgarContratoIndefinido(String nombre, String fecha) {
        if (this.trabajador.identificarTrabajador(nombre) == -1) {
            return "¡Trabajador no encontrado";
        }
        try {
            this.trabajador.otorgarContratoIndefinido(this.trabajador.identificarTrabajador(nombre), fecha);
            return "El contrato del trabajador ha sido actualizado a Indefinido";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String agregarMesa(Mesa mesa) {
        try {
            this.mesa.agregarMesa(mesa);
            return "Mesa agregada exitosamente";

        }catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String eliminarMesa(int numero) {
        try {
            this.mesa.quitarMesa(numero);
            return "Mesa eliminada";
        }catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String atenderCliente(String nombre, int edad, Trabajador trabajador) throws Exception {
        try {
            Cliente nuevoCliente = new Cliente(nombre, edad);
            Orden nuevaOrden = new Orden(nuevoCliente, trabajador, mesa.buscarMesaDisponible().getNumero());
            this.orden.agregarOrden(nuevaOrden);
            return "Cliente registrado";
        }catch (Exception e) {
            return e.getMessage();
        }
    }

    public String[][] mostrarResumen(String nombreCliente) {
        return orden.obtenerResumen(nombreCliente);
    }

    public Orden pedirCuenta(String nombreCliente) throws Exception {
        try {
            Orden cuenta = orden.obtenerOrden(nombreCliente);
            orden.eliminarOrden(orden.obtenerPosicion(nombreCliente));
            return cuenta;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public String[] desplegarMenu() throws Exception {
        try {
            return producto.menuDisponible();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
