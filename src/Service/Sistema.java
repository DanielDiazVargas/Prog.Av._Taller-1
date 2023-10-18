package Service;

import Model.*;
import Model.Contenedores.ListaMesa;

public interface Sistema {

    String agregarProductoInventario(Producto producto);
    String actualizarProducto(String nombre, Producto producto);
    String eliminarProducto(String nombre);
    String actualizarStockInventario(String nombre, int stock);
    String contratarTrabajador(Trabajador trabajador);
    String despedirTrabajador(String nombre);
    String renovarContrato(String nombre, String fechaContratacion, String fechaTermino);
    String otorgarContratoIndefinido(String nombre, String fecha);
    String agregarMesa(Mesa mesa);
    String eliminarMesa(int numero);
    ListaMesa getListaMesa();
    String atenderCliente(String nombre, int edad, Trabajador trabajador) throws Exception;
    String[][] mostrarResumen(String nombreCliente);
    public Orden pedirCuenta(String nombreCliente) throws Exception;
    String[] desplegarMenu() throws Exception;
    //Orden pedirCuenta();
}
