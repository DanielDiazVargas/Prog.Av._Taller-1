import Model.Contenedores.Inventario;
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
import java.util.regex.Pattern;


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

    public static void menuSistema() {

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
            StdOut.println("""
                    :::::::::::::::::::::::::::::
                              *SISTEMA*
                    Elija una opcion:
                    [1] Coordinar Mesas
                    [2] Gestionar Inventario
                    [3] Administrar Trabajadores
                    [4] Prosesar Orden
                    [5] Salir
                    :::::::::::::::::::::::::::::""");
            StdOut.print("=> ");
            opcion = StdIn.readString();

            switch (opcion){
                case "1": coordinarMesas();
                    break;
                case "2": gestionarInventario();
                    break;
                case "3": administrarTrabajadores();
                    break;
                case "4":
                    break;
                case "5": menu = false;
                    break;
                default: StdOut.println("¡Opcion no valida!");
            }
        }
    }

    // Menu para gestion de mesas
    private static void coordinarMesas() {
        String opcion;
        boolean menu = true;
        while (menu) {
            StdOut.println("""
                    :::::::::::::::::::::::::::
                         *GESTION DE MESAS*
                    Elija una opcion:
                    [1] Agregar una mesa
                    [2] Eliminar una mesa
                    [3] Ver mesas
                    [4] Volver
                    :::::::::::::::::::::::::::""");
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

    public static void agregarMesa() {
        String auxInt;

        int numero;
        int posicion;

        while (true) {
            StdOut.println("Ingrese un numero para la mesa" +
                    "\n o ingrese [-1] para volver");
            StdOut.print("=> ");
            auxInt = StdIn.readString();

            if (auxInt.equalsIgnoreCase("-1")) {
                return;
            }

            if (auxInt.matches("[0-9]{1,100}") && Integer.parseInt(auxInt) > 0) {
                numero = Integer.parseInt(auxInt);
                while (true) {
                    StdOut.println("Ingrese la fila de la mesa" +
                            "\n o ingrese [-1] para volver");
                    StdOut.print("=> ");
                    auxInt = StdIn.readString();

                    if (auxInt.equalsIgnoreCase("-1")) {
                        return;
                    }

                    if (auxInt.matches("[0-9]{1,100}")) {
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
                        StdOut.println("Numero no valido");
                    }
                }
            }else {
                StdOut.println("Numero no valido");
            }
        }
    }

    public static void eliminarMesa() {
        String auxInt;

        while (true) {
            StdOut.println("Ingrese el numero de la mesa a eliminar" +
                    "\n o ingrese [-1] para volver");
            StdOut.print("=> ");
            auxInt = StdIn.readString();

            if (auxInt.equalsIgnoreCase("-1")) {
                return;
            }

            if (auxInt.matches("[0-9]{1,100}") && Integer.parseInt(auxInt) > 0) {
                try {
                    StdOut.println(sistemaRestaurante.eliminarMesa(Integer.parseInt(auxInt)));
                    escrituraArchivos();
                    return;
                }catch (Exception e) {
                    StdOut.println(e.getMessage());
                    return;
                }
            }else {
                StdOut.println("Numero no valido");
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
                }
            }catch (Exception e){
                if (e.getMessage().equalsIgnoreCase("¡No hay mesas registradas!")) {
                    StdOut.println(e.getMessage());
                    return;
                }
            }

        }
    }

    // Menu para gestion del inventario
    private static void gestionarInventario() {
        String opcion;
        boolean menu = true;

        while (menu) {
            StdOut.println("""
                    :::::::::::::::::::::::::::::
                       *GESTION DE INVENTARIO*
                    Elija una opcion:
                    [1] Agregar un producto al Inventario
                    [2] Eliminar un producto del Inventario
                    [3] Actualizar un producto del Inventario
                    [4] Ver Invetario
                    [5] Volver
                    :::::::::::::::::::::::::::::""");
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
        String aux;

        String nombre;
        int precio;
        int stock;
        String categoria;

        StdOut.println("Ingrese el nombre del producto" +
                "\n o ingrese [-1] para volver");
        StdOut.print("=> ");
        aux = StdIn.readString();

        if (aux.equalsIgnoreCase("-1")) {
            return;
        }

        nombre = aux;

        while (true) {
            StdOut.println("Ingrese el precio del producto" +
                    "\n o ingrese [-1] para volver");
            StdOut.print("=> ");
            aux = StdIn.readString();

            if (aux.equalsIgnoreCase("-1")) {
                return;
            }

            if (aux.matches("[0-9]{1,100}")) {
                precio = Integer.parseInt(aux);

                while (true) {
                    StdOut.println("Ingrese el Stock del producto" +
                            "\n o ingrese [-1] para volver");
                    StdOut.print("=> ");
                    aux = StdIn.readString();

                    if (aux.equalsIgnoreCase("-1")) {
                        return;
                    }

                    if (aux.matches("[0-9]{1,100}")) {
                        stock = Integer.parseInt(aux);

                        StdOut.println("Ingrese la categoria del producto" +
                                "\n o ingrese [-1] para volver");
                        StdOut.print("=> ");
                        aux = StdIn.readString();

                        if (aux.equalsIgnoreCase("-1")) {
                            return;
                        }

                        categoria = aux;

                        Producto nuevoProducto = new Producto(nombre, precio, stock, categoria);
                        StdOut.println(sistemaRestaurante.agregarProductoInventario(nuevoProducto));
                        escrituraArchivos();
                        return;
                    }else {
                        StdOut.println("Numero no valido");
                    }
                }

            }else {
                StdOut.println("Numero no valido");
            }
        }
    }

    public static void eliminarProducto() {
        StdOut.println("Ingrese el nombre del producto a eliminar" +
                "\n o ingrese [-1] para volver");
        StdOut.print("=> ");
        String nombre = StdIn.readString();

        if (nombre.equalsIgnoreCase("-1")) {
            return;
        }

        try {
            StdOut.println(sistemaRestaurante.eliminarProducto(nombre));
            escrituraArchivos();
        }catch (Exception e) {
            StdOut.println(e.getMessage());
        }
    }

    public static void actualizarProducto() {
        String opcion;
        boolean menu = true;

        while (menu) {
            StdOut.println("""
                    :::::::::::::::::::::::::::::
                       *GESTION DE INVENTARIO*
                    Elija una opcion:
                    [1] Actualizar producto
                    [2] Actualizar stock
                    [3] Volver
                    :::::::::::::::::::::::::::::""");
            StdOut.print("=> ");
            opcion = StdIn.readString();

            Inventario inventario = sistemaRestaurante.getInventario();

            switch (opcion) {
                case "1" -> {
                    StdOut.println("Ingrese el nombre del producto a actualizar" +
                            "\n o ingrese [-1] para volver");
                    StdOut.print("=> ");
                    String nombre = StdIn.readString();
                    String aux = nombre;

                    if (nombre.equalsIgnoreCase("-1")) {
                        return;
                    }

                    try {
                        String nuevoNombre = inventario.obtenerProducto(nombre).getNombre();
                        int nuevoPrecio = inventario.obtenerProducto(nombre).getPrecio();
                        int nuevoStock = inventario.obtenerProducto(nombre).getStock();
                        String NuevaCategoria = inventario.obtenerProducto(nombre).getCategoria();

                        StdOut.println("Nombre: " + inventario.obtenerProducto(nombre).getNombre() +
                                " | Precio: " + inventario.obtenerProducto(nombre).getPrecio() +
                                " | Stock: " + inventario.obtenerProducto(nombre).getStock() +
                                " | Caregoria: " + inventario.obtenerProducto(nombre).getCategoria());

                        StdOut.println("Ingrese el nuevo nombre" +
                                "\n o ingrese [-1] para mantenerlo");
                        StdOut.print("=> ");
                        nombre = StdIn.readString();

                        if (!nombre.equalsIgnoreCase("-1")) {
                            nuevoNombre = nombre;
                        }

                        while (true) {
                            StdOut.println("Ingrese el nuevo precio" +
                                    "\n o ingrese [-1] para mantenerlo");
                            StdOut.print("=> ");
                            String precio = StdIn.readString();

                            if (precio.equalsIgnoreCase("-1")) {
                                break;
                            }

                            if (precio.matches("[0-9]{1,100}") && Integer.parseInt(precio) > 0) {
                                nuevoPrecio = Integer.parseInt(precio);
                                break;
                            }else {
                                StdOut.println("¡Numero no valido!");
                            }
                        }

                        while (true) {
                            StdOut.println("Ingrese el nuevo stock" +
                                    "\n o ingrese [-1] para mantenerlo");
                            StdOut.print("=> ");
                            String stock = StdIn.readString();

                            if (stock.equalsIgnoreCase("-1")) {
                                break;
                            }

                            if (stock.matches("[0-9]{1,100}") && Integer.parseInt(stock) > 0) {
                                nuevoStock = Integer.parseInt(stock);
                                break;
                            }else {
                                StdOut.println("¡Numero no valido!");
                            }
                        }

                        StdOut.println("Ingrese la nueva categoria" +
                                "\n o ingrese [-1] para mantenerla");
                        StdOut.print("=> ");
                        nombre = StdIn.readString();

                        if (!nombre.equalsIgnoreCase("-1")) {
                            NuevaCategoria = nombre;
                        }

                        StdOut.println("Nombre: " + nuevoNombre +
                                " | Precio: " + nuevoPrecio +
                                " | Stock: " + nuevoStock +
                                " | Caregoria: " + NuevaCategoria);

                        while (true) {
                            StdOut.println("""
                             Elija una opcion:
                             [1] Actualizar
                             [2] Cancelar""");
                            StdOut.print("=> ");
                            opcion = StdIn.readString();

                            if (opcion.equalsIgnoreCase("1")) {
                                Producto nuevoProducto = new Producto(nuevoNombre, nuevoPrecio, nuevoStock, NuevaCategoria);
                                StdOut.println(sistemaRestaurante.actualizarProducto(aux, nuevoProducto));
                                break;
                            }else if (opcion.equalsIgnoreCase("2")) {
                                StdOut.println("Cancelado");
                                break;
                            }else {
                                StdOut.println("¡Opcion no valida!");
                            }
                        }
                    }catch (Exception e) {
                        StdOut.println(e.getMessage());
                    }
                }
                case "2" -> {
                    StdOut.println("Ingrese el nombre del producto a actualizar" +
                            "\n o ingrese [-1] para volver");
                    StdOut.print("=> ");
                    String nombre = StdIn.readString();

                    if (nombre.equalsIgnoreCase("-1")) {
                        return;
                    }

                    try {
                        int nuevoStock = inventario.obtenerProducto(nombre).getStock();

                        StdOut.println("Nombre: " + inventario.obtenerProducto(nombre).getNombre() +
                                " | Stock: " + inventario.obtenerProducto(nombre).getStock());
                        while (true) {
                            StdOut.println("Ingrese el nuevo stock" +
                                    "\n o ingrese [-1] para cancelar");
                            StdOut.print("=> ");
                            String stock = StdIn.readString();

                            if (stock.equalsIgnoreCase("-1")) {
                                break;
                            }

                            if (stock.matches("[0-9]{1,100}") && Integer.parseInt(stock) > 0) {
                                StdOut.println(sistemaRestaurante.actualizarStockInventario(nombre, Integer.parseInt(stock)));
                                break;
                            }else {
                                StdOut.println("¡Numero no valido!");
                            }
                        }

                    }catch (Exception e){
                        StdOut.println(e.getMessage());
                    }
                }
                case "3" -> menu = false;
                default -> {
                    StdOut.println("¡Opcion no valida!");
                }
            }
        }
    }

    public static void verInventario() {
        Inventario inventario = sistemaRestaurante.getInventario();
        for (int i = 0; i < 999; i++) {
            try {
                if (inventario.obtenerProductosPorPosicion(i) != null) {
                    StdOut.println("Nombre: " + inventario.obtenerProductosPorPosicion(i).getNombre() +
                            " | Precio: " + inventario.obtenerProductosPorPosicion(i).getPrecio() +
                            " | Stock: " + inventario.obtenerProductosPorPosicion(i).getStock() +
                            " | Categoria: " +inventario.obtenerProductosPorPosicion(i).getCategoria());
                }
            }catch (Exception e){
                StdOut.println(e.getMessage());
            }
        }
    }

    // Menu para gestion de trabajadores
    private static void administrarTrabajadores() {
        String opcion;
        boolean menu = true;

        while (menu) {
            StdOut.println("""
                    :::::::::::::::::::::::::::::
                      *ADMINISTRAR TRABAJADORES*
                    Elija una opcion:
                    [1] Contratar Trabajador
                    [2] Despedir Trabajador
                    [3] Renovar Contrato
                    [4] Ver Trabajadores
                    [5] Volver
                    :::::::::::::::::::::::::::::""");
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

    private static void contratarTrabajador() {

        Trabajador trabajador = new Trabajador("Juan", 21, "Definido", LocalDate.now(), LocalDate.now().plusMonths(12));
        String respuesta = sistemaRestaurante.contratarTrabajador(trabajador);
        StdOut.println(respuesta);
    }

    private static void despedirTrabajador() {
    }

    private static void otorgarContratoIndefinido() {
    }

    private static void renovarContrato(){

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