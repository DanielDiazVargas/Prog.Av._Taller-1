package Service;

import Model.*;
import Model.Contenedores.Inventario;
import Model.Contenedores.ListaMesa;
import Model.Contenedores.ListaTrabajador;

import java.time.LocalDate;

public interface Sistema {

    String agregarProductoInventario(Producto producto);
    String actualizarProducto(String nombre, Producto producto) throws Exception;
    String eliminarProducto(String nombre);
    String actualizarStockInventario(String nombre, int stock);
    public Inventario getInventario();
    String contratarTrabajador(Trabajador trabajador);
    String despedirTrabajador(String nombre);
    String renovarContrato(String nombre, LocalDate fechaContratacion, LocalDate fechaTermino);
    String otorgarContratoIndefinido(String nombre, LocalDate fecha);
    public ListaTrabajador getListaTrabajador();
    String agregarMesa(Mesa mesa);
    String eliminarMesa(int numero);
    ListaMesa getListaMesa();
    String atenderCliente(String nombre, int edad, Trabajador trabajador) throws Exception;
    String[][] mostrarResumen(String nombreCliente);
    public Orden pedirCuenta(String nombreCliente) throws Exception;
    String[] desplegarMenu() throws Exception;
    //Orden pedirCuenta();
}
