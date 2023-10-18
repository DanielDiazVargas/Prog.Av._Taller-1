import Model.Contenedores.ListaMesa;
import Model.Mesa;
import Model.Producto;
import Model.Trabajador;
import Service.Sistema;
import Util.Instalador;
import ucn.ArchivoEntrada;
import ucn.ArchivoSalida;
import ucn.StdIn;
import ucn.StdOut;


import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Main {
    public static Sistema sistemaRestaurante;


    public static void main(String[] args) throws Exception {
        configuracion();

    }

    public static void configuracion() throws Exception {
        sistemaRestaurante = instalarSistema();
        lecturaArchivos();
        menuSistema();
        escrituraArchivos();
    }

    public static void menuSistema() throws Exception {

        //4 opciones
        /*
        1. Coordinar disponibilidad mesas
        2. Gestionar Inventario
        3. Administracion de trabajadores
        4. Procesar ordenes
         */
        String opcion;
        boolean menu = true;
        while (menu) {
            StdOut.println(":::::::::::::::::::::::::::::" +
                    "\n         *SISTEMA*" +
                    "\nElija una opcion:" +
                    "\n[1] Coordinar Mesas" +
                    "\n[2] Gestionar Inventario" +
                    "\n[3] Administrar Trabajadores" +
                    "\n[4] Prosesar Orden" +
                    "\n[5] Salir" +
                    "\n:::::::::::::::::::::::::::::");
            StdOut.print("=> ");
            opcion = StdIn.readString();

            switch (opcion){
                case "1": coordinarMesas();
                    break;
                case "2": gestionarInventario();
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "5": menu = false;
                    break;
                default: StdOut.println("¡Opcion no valida!");
            }
        }
    }

    private static void coordinarMesas() throws Exception {
        String opcion;
        boolean menu = true;
        while (menu) {
            StdOut.println(":::::::::::::::::::::::::::" +
                    "\n   GESTION DE MESAS" +
                    "\nElija una opcion:" +
                    "\n[1] Agregar una mesa" +
                    "\n[2] Eliminar una mesa" +
                    "\n[3] Ver mesas" +
                    "\n[4] Volver" +
                    "\n:::::::::::::::::::::::::::");
            StdOut.print("=> ");
            opcion = StdIn.readString();

            switch (opcion) {
                case "1" -> agregarMesa();
                case "2" -> eliminarMesa();
                case "3" -> verMesas();
                case "4" -> menu = false;
                default -> {
                    StdOut.println("¡Opcion no valida!");
                }
            }
        }

    }

    public static void agregarMesa() throws Exception {
        String auxInt;
        int numero;
        int posicion;

        while (true) {
            StdOut.println("Ingrese un numero para la mesa" +
                    "\n o ingrese <<-1>> para volver");
            StdOut.print("=> ");
            auxInt = StdIn.readString();

            if (auxInt.equalsIgnoreCase("-1")) {
                return;
            }

            if (auxInt.matches("[0-9]") && Integer.parseInt(auxInt) > 0) {
                numero = Integer.parseInt(auxInt);
                while (true) {
                    StdOut.println("Ingrese la fila de la mesa" +
                            "\n o ingrese <<-1>> para volver");
                    StdOut.print("=> ");
                    auxInt = StdIn.readString();

                    if (auxInt.equalsIgnoreCase("-1")) {
                        return;
                    }

                    if (auxInt.matches("[0-9]")) {
                        posicion = Integer.parseInt(auxInt);
                        Mesa nuevaMesa = new Mesa(numero, posicion);
                        try {
                            StdOut.println(sistemaRestaurante.agregarMesa(nuevaMesa));
                        }catch (Exception e) {
                            StdOut.println(e.getMessage());
                            return;
                        }
                        escrituraArchivos();
                        return;
                    }else {
                        System.out.println("Numero no valido");
                    }
                }
            }else {
                System.out.println("Numero no valido");
            }
        }
    }

    public static void eliminarMesa() {
        String auxInt;
        while (true) {
            StdOut.println("Ingrese el numero de la mesa a eliminar" +
                    "\n o ingrese <<-1>> para volver");
            StdOut.print("=> ");
            auxInt = StdIn.readString();

            if (auxInt.equalsIgnoreCase("-1")) {
                return;
            }

            if (auxInt.matches("[0-9]") && Integer.parseInt(auxInt) > 0) {
                try {
                    StdOut.println(sistemaRestaurante.eliminarMesa(Integer.parseInt(auxInt)));
                    escrituraArchivos();
                    return;
                }catch (Exception e) {
                    StdOut.println(e.getMessage());
                    return;
                }
            }
        }
    }

    public static void verMesas() {
        ListaMesa listaMesa = sistemaRestaurante.getListaMesa();
        for (int i = 1; i < 999; i++) {
            try {
                if (listaMesa.obtenerMesa(i) != null) {
                    StdOut.print("Mesa n°" + listaMesa.obtenerMesa(i).getNumero() +
                            " | Fila: " + listaMesa.obtenerMesa(i).getPosicion() +
                            " | Estado: ");
                    if (listaMesa.obtenerMesa(i).getDisponible()) {
                        StdOut.print("Disponible\n");
                    }else {
                        StdOut.print("Ocupada\n");
                    }
                    return;
                }
            }catch (Exception e){
                if (e.getMessage().equalsIgnoreCase("¡No hay mesas registradas!")) {
                    StdOut.println(e.getMessage());
                    return;
                }
            }

        }
    }

    private static void gestionarInventario() {
        String opcion;
        boolean menu = true;
        while (menu) {
            StdOut.println(":::::::::::::::::::::::::::::" +
                    "\n  *GESTION DE INVENTARIO*" +
                    "\nElija una opcion:" +
                    "\n[1] Agregar un producto al Inventario" +
                    "\n[2] Eliminar un producto del Inventario" +
                    "\n[3] Actualizar un producto del Inventario" +
                    "\n[4] Ver Invetario" +
                    "\n[5] Volver" +
                    "\n:::::::::::::::::::::::::::::");
            StdOut.print("=> ");
            opcion = StdIn.readString();

            switch (opcion) {
                case "1" -> agregarProducto();
                case "2" -> eliminarProducto();
                case "3" -> actualizarProducto();
                case "4" -> verInventario();
                case "5" -> menu = false;
                default -> {
                    StdOut.println("¡Opcion no valida!");
                }
            }
        }
    }

    public static void agregarProducto() {

    }

    public static void eliminarProducto() {

    }

    public static void actualizarProducto() {

    }

    public static void verInventario() {

    }

    private static void administrarTrabajadores(int opcion) {

        switch (opcion){
            case 1: contratarTrabajador();
                break;
            case 2: despedirTrabajador();
                break;
            case 3: renovarContrato();
                break;
            case 4: otorgarContratoIndefinido();
                break;

        }


    }

    private static void otorgarContratoIndefinido() {
    }

    private static void despedirTrabajador() {
    }

    private static void renovarContrato(){

    }

    private static void contratarTrabajador() {

        Trabajador trabajador = new Trabajador("Juan", 21, "Definido", LocalDate.now(), LocalDate.now().plusMonths(12));
        String respuesta = sistemaRestaurante.contratarTrabajador(trabajador);
        System.out.println(respuesta);
    }

    private static void procesarOrdenes() {

    }

    public static Sistema instalarSistema() {
        return new Instalador().instalarSistema();
    }

    public static void lecturaArchivos() throws IOException {
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

    public static void escrituraArchivos() {

    }
}