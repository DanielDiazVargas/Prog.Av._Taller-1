import Model.Mesa;
import Model.Producto;
import Model.Trabajador;
import Service.Sistema;
import Util.Instalador;
import ucn.ArchivoEntrada;
import ucn.ArchivoSalida;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Formatter;

public class Main {
    public static void main(String[] args) throws IOException {
        configuracion();
    }

    public static void configuracion() throws IOException {
        Sistema sistemaRestaurante = instalarSistema();
        lecturaArchivos(sistemaRestaurante);
    }

    public static Sistema instalarSistema() {
        return new Instalador().instalarSistema();
    }

    public static void lecturaArchivos(Sistema sistemaRestaurante) throws IOException {
        try {
            ArchivoEntrada archivoEntrada = new ArchivoEntrada("mesas.txt");

            while (!archivoEntrada.isEndFile()) {
                int numero = archivoEntrada.getRegistro().getInt();
                int fila = archivoEntrada.getRegistro().getInt();

                Mesa nuevaMesa = new Mesa(numero, fila);
                sistemaRestaurante.agregarMesa(nuevaMesa);
            }

            archivoEntrada.close();

        }catch (IOException e) {
            ArchivoSalida archivoSalida = new ArchivoSalida("mesas.txt");
        }

        try {
            ArchivoEntrada archivoEntrada = new ArchivoEntrada("productos.txt");

            while (!archivoEntrada.isEndFile()) {
                String nombre = archivoEntrada.getRegistro().getString();
                int precio = archivoEntrada.getRegistro().getInt();
                int stock = archivoEntrada.getRegistro().getInt();
                String categoria = archivoEntrada.getRegistro().getString();

                Producto nuevoProducto = new Producto(nombre, precio, stock, categoria);
                sistemaRestaurante.agregarProductoInventario(nuevoProducto);
            }

            archivoEntrada.close();
        }catch (IOException e) {
            ArchivoSalida archivoSalida = new ArchivoSalida("productos.txt");
        }

        try {
            ArchivoEntrada archivoEntrada = new ArchivoEntrada("trabajadores.txt");
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            while (!archivoEntrada.isEndFile()) {
                String nombre = archivoEntrada.getRegistro().getString();
                int edad = archivoEntrada.getRegistro().getInt();
                String tipoContrato = archivoEntrada.getRegistro().getString();
                LocalDate fechaContratacion = LocalDate.parse(archivoEntrada.getRegistro().getString(), formato);
                LocalDate fechaTermino = LocalDate.parse(archivoEntrada.getRegistro().getString(), formato);

                Trabajador nuevoTrabajador = new Trabajador(nombre, edad, tipoContrato, fechaContratacion, fechaTermino);
                sistemaRestaurante.contratarTrabajador(nuevoTrabajador);
            }

            archivoEntrada.close();
        }catch (IOException e) {
            ArchivoSalida archivoSalida = new ArchivoSalida("trabajadores.txt");
        }
    }
}