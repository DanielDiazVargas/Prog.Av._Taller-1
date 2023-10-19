import Model.*;
import Model.Contenedores.Inventario;
import Model.Contenedores.ListaMesa;
import Model.Contenedores.ListaOrden;
import Model.Contenedores.ListaTrabajador;
import Service.Sistema;
import Util.Instalador;
import ucn.*;


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
        escrituraArchivos("All");
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

            switch (opcion) {
                case "1" -> coordinarMesas();
                case "2" -> gestionarInventario();
                case "3" -> administrarTrabajadores();
                case "4" -> procesarOrdenes();
                case "5" -> menu = false;
                default -> StdOut.println("¡Opcion no valida!");
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
                            escrituraArchivos("Mesas");
                        }catch (Exception e) {
                            StdOut.println(e.getMessage());
                            return;
                        }
                        try {
                            escrituraArchivos("Mesas");
                        }catch (Exception ignored) {

                        }
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
                    escrituraArchivos("Mesas");
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
                        try {
                            escrituraArchivos("Productos");
                        }catch (Exception ignored) {

                        }
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
            escrituraArchivos("Productos");
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
                                escrituraArchivos("Productos");
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
            }catch (Exception ignored){
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
                case "1" -> contratarTrabajador();
                case "2" -> despedirTrabajador();
                case "3" -> renovarContrato();
                case "4" -> verTrabajadores();
                case "5" -> menu = false;
                default -> {
                    StdOut.println("¡Opcion no valida!");
                }
            }
        }
    }

    private static void contratarTrabajador() {
        String nombre;
        int edad;
        LocalDate fechaContratacion = LocalDate.now();
        LocalDate fechaTermino;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        StdOut.println("Ingrese el nombre del Trabajador a contratar" +
                "\n o ingrese [-1] para volver");
        StdOut.print("=> ");
        String aux = StdIn.readString();

        if (aux.equalsIgnoreCase("-1")) {
            return;
        }

        nombre = aux;

        while (true) {
            StdOut.println("Ingrese la edad del Trabajador a contratar" +
                    "\n o ingrese [-1] para volver");
            StdOut.print("=> ");
            String auxInt = StdIn.readString();


            if (auxInt.equalsIgnoreCase("-1")) {
                return;
            }

            if (auxInt.matches("[0-9]{1,100}") && Integer.parseInt(auxInt) > 0) {
                edad = Integer.parseInt(auxInt);
                break;
            }else {
                StdOut.println("Numero no valido");
            }
        }

        while (true) {
            StdOut.println("Ingrese la fecha de termino del Trabajador a contratar. En formato \"dd/MM/yyyy\", ejemplo: 18/10/2023" +
                    "\n o ingrese [-1] para volver");
            StdOut.print("=> ");
            String fecha = StdIn.readString();

            try {
                fechaTermino = LocalDate.parse(fecha, formato);
                break;
            }catch (Exception e){
                StdOut.println("¡Formato incorrecto!");
            }
        }

        Trabajador nuevoTrabajador = new Trabajador(nombre, edad,"Fijo" , fechaContratacion, fechaTermino);
        StdOut.println(sistemaRestaurante.contratarTrabajador(nuevoTrabajador));
        try {
            escrituraArchivos("trabajadores");
        }catch (Exception ignored) {

        }
    }

    private static void despedirTrabajador() {
        StdOut.println("Ingrese el nombre del Trabajador a despedir" +
                "\n o ingrese [-1] para volver");
        StdOut.print("=> ");
        String nombre = StdIn.readString();

        if (nombre.equalsIgnoreCase("-1")) {
            return;
        }

        try {
            StdOut.println(sistemaRestaurante.despedirTrabajador(nombre));
            escrituraArchivos("trabajadores");
        }catch (Exception e) {
            StdOut.println(e.getMessage());
        }
    }

    private static void renovarContrato(){
        String opcion;
        boolean menu = true;

        while (menu) {
            StdOut.println("""
                    :::::::::::::::::::::::::::::
                       *GESTION DE INVENTARIO*
                    Elija una opcion:
                    [1] Renovar contrato
                    [2] Otorgar Contrato Indefinido
                    [3] Volver
                    :::::::::::::::::::::::::::::""");
            StdOut.print("=> ");
            opcion = StdIn.readString();

            ListaTrabajador listaTrabajador = sistemaRestaurante.getListaTrabajador();

            switch (opcion) {
                case "1" -> {
                    LocalDate fechaContratacion = LocalDate.now();
                    LocalDate fechaTermino;
                    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                    StdOut.println("Ingrese el nombre del Trabajador a renovar" +
                            "\n o ingrese [-1] para volver");
                    StdOut.print("=> ");
                    String nombre = StdIn.readString();

                    if (nombre.equalsIgnoreCase("-1")) {
                        return;
                    }

                    while (true) {
                        StdOut.println("Ingrese la fecha de termino del Trabajador a contratar. En formato \"dd/MM/yyyy\", ejemplo: 18/10/2023" +
                                "\n o ingrese [-1] para volver");
                        StdOut.print("=> ");
                        String fecha = StdIn.readString();

                        try {
                            fechaTermino = LocalDate.parse(fecha, formato);
                            break;
                        }catch (Exception e){
                            StdOut.println("¡Formato incorrecto!");
                        }
                    }

                    try {
                        StdOut.println(sistemaRestaurante.renovarContrato(nombre, fechaContratacion, fechaTermino));
                        escrituraArchivos("trabajadores");
                    }catch (Exception e) {
                        StdOut.println(e.getMessage());
                    }
                }
                case "2" -> otorgarContratoIndefinido();
                case "3" -> menu = false;

            }
        }
    }

    private static void otorgarContratoIndefinido() {
        LocalDate fechaContratacion = LocalDate.now();

        StdOut.println("Ingrese el nombre del Trabajador a renovar" +
                "\n o ingrese [-1] para volver");
        StdOut.print("=> ");
        String nombre = StdIn.readString();

        if (nombre.equalsIgnoreCase("-1")) {
            return;
        }

        try {
            StdOut.println(sistemaRestaurante.otorgarContratoIndefinido(nombre, fechaContratacion));
            escrituraArchivos("trabajadores");
        }catch (Exception ignored){
        }
    }

    public static void verTrabajadores() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        ListaTrabajador listaTrabajador = sistemaRestaurante.getListaTrabajador();
        for (int i = 0; i < 999; i++) {
            try {
                if (listaTrabajador.obtenerTrabajadorsPorPosicion(i) != null) {
                    StdOut.print("Nombre: " + listaTrabajador.obtenerTrabajadorsPorPosicion(i).getNombre() +
                            " | Edad: " + listaTrabajador.obtenerTrabajadorsPorPosicion(i).getEdad() +
                            " | Tipo de Contrato: " + listaTrabajador.obtenerTrabajadorsPorPosicion(i).getTipoContrato() +
                            " | Fecha de Contratacion: " + listaTrabajador.obtenerTrabajadorsPorPosicion(i).getFechaContratacion().format(formato));
                    if (listaTrabajador.obtenerTrabajadorsPorPosicion(i).getFechaTermino() != null) {
                        StdOut.println(" | Fecha de Termino: " + listaTrabajador.obtenerTrabajadorsPorPosicion(i).getFechaTermino().format(formato));
                    }
                }
            }catch (Exception ignored){
            }
        }
    }

    // Menu para gestion de las ordenes
    private static void procesarOrdenes() {
        String opcion;
        boolean menu = true;
        while (menu) {
            StdOut.println("""
                    :::::::::::::::::::::::::::
                              *ORDEN*
                    Elija una opcion:
                    [1] Atender Cliente
                    [2] Desplegar Menu Disponible
                    [3] Mostrar resumen
                    [4] Pedir Cuenta
                    [5] Ver Ordenes
                    [6] Volver
                    :::::::::::::::::::::::::::""");
            StdOut.print("=> ");
            opcion = StdIn.readString();

            switch (opcion) {
                case "1" -> atenderCliente();
                case "2" -> desplegarMenu();
                case "3" -> mostrarResumen();
                case "4" -> pedirCuenta();
                case "5" -> verOrdenes();
                case "6" -> menu = false;
                default -> StdOut.println("¡Opcion no valida!");
            }
        }
    }

    public static void atenderCliente() {
        String opcion;
        boolean menu = true;
        while (menu) {
            StdOut.println("""
                    :::::::::::::::::::::::::::
                              *ORDEN*
                    Elija una opcion:
                    [1] Atender nuevo Cliente
                    [2] Pedir Productos
                    [3] Volver
                    :::::::::::::::::::::::::::""");
            StdOut.print("=> ");
            opcion = StdIn.readString();

            switch (opcion) {
                case "1" -> {
                    String aux;

                    String nombre;
                    int edad;
                    Trabajador trabajador;

                    StdOut.println("Ingrese el nombre del Cliente" +
                            "\n o ingrese [-1] para volver");
                    StdOut.print("=> ");
                    aux = StdIn.readString();

                    if (aux.equalsIgnoreCase("-1")) {
                        return;
                    }

                    nombre = aux;

                    StdOut.println("Ingrese la edad del Cliente" +
                            "\n o ingrese [-1] para volver");
                    StdOut.print("=> ");
                    aux = StdIn.readString();

                    if (aux.equalsIgnoreCase("-1")) {
                        return;
                    }

                    while (true) {
                        if (aux.matches("[0-9]{1,100}")) {
                            edad = Integer.parseInt(aux);
                            break;
                        }else{
                            StdOut.println("Numero no valido");
                        }
                    }

                    while (true) {
                        StdOut.println("Ingrese el nombre del Trabajador que atendera la Mesa" +
                                "\n o ingrese [-1] para volver");
                        StdOut.print("=> ");
                        aux = StdIn.readString();

                        if (aux.equalsIgnoreCase("-1")) {
                            return;
                        }

                        try {
                            trabajador = sistemaRestaurante.getListaTrabajador().obtenerTrabajadorsPorPosicion(sistemaRestaurante.getListaTrabajador().identificarTrabajador(aux));
                            break;
                        }catch (Exception e) {
                            StdOut.println(e.getMessage());
                        }
                    }

                    try {
                        StdOut.println(sistemaRestaurante.atenderCliente(nombre, edad, trabajador));
                    }catch (Exception e) {
                        StdOut.println(e.getMessage());
                    }
                }
                case "2" -> {
                    Producto producto;
                    String nombre;
                    int posicionCliente;
                    String nombreProducto;
                    int cantidad;
                    Inventario inventario = sistemaRestaurante.getInventario();
                    ListaOrden listaOrden = sistemaRestaurante.getListaOrden();

                    while (true) {
                        StdOut.println("Ingrese el nombre del Cliente" +
                                "\n o ingrese [-1] para volver");
                        StdOut.print("=> ");
                        nombre = StdIn.readString();

                        if (nombre.equalsIgnoreCase("-1")) {
                            return;
                        }

                        try {
                            posicionCliente = listaOrden.obtenerPosicion(nombre);
                            break;
                        }catch (Exception e) {
                            StdOut.println(e.getMessage());
                        }
                    }

                    while (true) {
                        StdOut.println("Ingrese el nombre del Producto" +
                                "\n o ingrese [-1] para volver");
                        StdOut.print("=> ");
                        nombre = StdIn.readString();

                        if (nombre.equalsIgnoreCase("-1")) {
                            return;
                        }

                        try {
                            producto = inventario.obtenerProducto(nombre);
                            break;
                        }catch (Exception e) {
                            StdOut.println(e.getMessage());
                        }
                    }

                    while (true) {
                        StdOut.println("Ingrese la cantidad de Producto" +
                                "\n o ingrese [-1] para volver");
                        StdOut.print("=> ");
                        String auxCantidad = StdIn.readString();

                        if (auxCantidad.equalsIgnoreCase("-1")) {
                            return;
                        }

                        if (auxCantidad.matches("[0-9]{1,100}")) {
                            cantidad = Integer.parseInt(auxCantidad);
                            break;
                        }else{
                            StdOut.println("Numero no valido");
                        }
                    }

                    try {
                        inventario.actualizarStock(inventario.obtenerPosicion(nombre), (cantidad * -1));
                        listaOrden.obtenerOrdenPorPosicion(posicionCliente).pedirProducto(producto, cantidad);
                    }catch (Exception e) {
                        StdOut.println(e.getMessage());
                    }
                }
                case "3" -> menu = false;
                default -> StdOut.println("¡Opcion no valida!");
            }
        }

    }

    public static void desplegarMenu() {
        try {
            String[] menu = sistemaRestaurante.desplegarMenu();
            for (String s : menu) {
                StdOut.println(s);
            }
        }catch (Exception e) {
            StdOut.println(e.getMessage());
        }
    }

    public static void mostrarResumen() {
        StdOut.println("Ingrese el nombre del Cliente" +
                "\n o ingrese [-1] para volver");
        StdOut.print("=> ");
        String nombre = StdIn.readString();

        if (nombre.equalsIgnoreCase("-1")) {
            return;
        }

        try {
            String[][] resumen = sistemaRestaurante.mostrarResumen(nombre);
            for (int i = 0; i < 999; i++) {
                if (!(resumen[0][1] == null || resumen[1][i] == null)) {
                    StdOut.println("Cantidad: " + resumen[0][i] +
                            ", Producto: " + resumen[1][i]);
                }

            }
        }catch (Exception e) {
            StdOut.println(e.getMessage());
        }
    }

    public static void pedirCuenta() {
        String nombre;
        ListaOrden listaOrden = sistemaRestaurante.getListaOrden();
        Orden cuenta;

        while (true) {
            StdOut.println("Ingrese el nombre del Cliente" +
                    "\n o ingrese [-1] para volver");
            StdOut.print("=> ");
            nombre = StdIn.readString();

            if (nombre.equalsIgnoreCase("-1")) {
                return;
            }

            try {
                nombre = listaOrden.obtenerOrden(nombre).getCliente().getNombre();
                break;
            }catch (Exception e) {
                StdOut.println(e.getMessage());
            }
        }

        try {
            cuenta = sistemaRestaurante.pedirCuenta(nombre);
        }catch (Exception e) {
            StdOut.println(e.getMessage());
            return;
        }

        int precioFinal = 0;
        try {
            String[][] resumen = sistemaRestaurante.mostrarResumen(nombre);
            for (int i = 0; i < 999; i++) {
                StdOut.println("Cantidad: " + resumen[0][i] +
                        ", Producto: " + resumen[1][i] +
                        ", Precio: $" + resumen[2][i] +
                        ", Precio total: $" + resumen[3][i]);
                precioFinal += Integer.parseInt(resumen[3][i]);
            }

            StdOut.println("Total del Pedido: $" + precioFinal +
                    "\n Trabajador Asignado: " + cuenta.getTrabajador().getNombre() +
                    "\n n° de Mesa: n°" + cuenta.getNumeroMesaAsociada());
        }catch (Exception e) {
            StdOut.println(e.getMessage());
        }
    }

    public static void verOrdenes() {
        ListaOrden listaOrden = sistemaRestaurante.getListaOrden();
        for (int i = 0; i < 999; i++) {
            try {
                if (listaOrden.obtenerOrdenPorPosicion(i) != null) {
                    StdOut.println("Cliente: " + listaOrden.obtenerOrdenPorPosicion(i).getCliente().getNombre() +
                            " | Trabajador Asignado: " + listaOrden.obtenerOrdenPorPosicion(i).getTrabajador().getNombre() +
                            " | n° de Mesa Asociada: " + listaOrden.obtenerOrdenPorPosicion(i).getNumeroMesaAsociada());
                }
            }catch (Exception ignored){
            }
        }
    }

    // ...
    public static Sistema instalarSistema() {
        return new Instalador().instalarSistema();
    }

    public static void lecturaArchivos() throws IOException {
        try {
            ArchivoEntrada archivoEntrada = new ArchivoEntrada("mesas.txt");

            while (!archivoEntrada.isEndFile()) {
                Registro registro = archivoEntrada.getRegistro();
                int numero = registro.getInt();
                int fila = registro.getInt();

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
                Registro registro = archivoEntrada.getRegistro();
                String nombre = registro.getString();
                int precio = registro.getInt();
                int stock = registro.getInt();
                String categoria = registro.getString();

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
            LocalDate fechaTermino = null;

            while (!archivoEntrada.isEndFile()) {
                Registro registro = archivoEntrada.getRegistro();
                String nombre = registro.getString();
                int edad = registro.getInt();
                String tipoContrato = registro.getString();
                LocalDate fechaContratacion = LocalDate.parse(registro.getString(), formato);
                try {
                    fechaTermino = LocalDate.parse(registro.getString(), formato);
                }catch (Exception ignored) {
                }

                Trabajador nuevoTrabajador = new Trabajador(nombre, edad, tipoContrato, fechaContratacion, fechaTermino);
                sistemaRestaurante.contratarTrabajador(nuevoTrabajador);
            }

            archivoEntrada.close();
        }catch (IOException e) {
            ArchivoSalida archivoSalida = new ArchivoSalida("trabajadores.txt");
        }
    }

    public static void escrituraArchivos(String opcion) throws Exception {
        if (opcion.equalsIgnoreCase("mesas") || opcion.equalsIgnoreCase("all")) {
            ArchivoSalida archivoSalida = new ArchivoSalida("mesas.txt");
            ListaMesa listaMesa = sistemaRestaurante.getListaMesa();
            for (int i = 0; i < 999; i++) {
                Registro linea = new Registro(2);
                try {
                    linea.agregarCampo(listaMesa.obtenerMesa(i).getNumero());
                    linea.agregarCampo(listaMesa.obtenerMesa(i).getPosicion());
                    archivoSalida.writeRegistro(linea);
                }catch (Exception ignored){

                }
            }
            archivoSalida.close();
        }

        if (opcion.equalsIgnoreCase("productos") || opcion.equalsIgnoreCase("all")) {
            ArchivoSalida archivoSalida = new ArchivoSalida("productos.txt");
            Inventario inventario = sistemaRestaurante.getInventario();
            for (int i = 0; i < 999; i++) {
                Registro linea = new Registro(4);
                try {
                    linea.agregarCampo(inventario.obtenerProductosPorPosicion(i).getNombre());
                    linea.agregarCampo(inventario.obtenerProductosPorPosicion(i).getPrecio());
                    linea.agregarCampo(inventario.obtenerProductosPorPosicion(i).getStock());
                    linea.agregarCampo(inventario.obtenerProductosPorPosicion(i).getCategoria());
                    archivoSalida.writeRegistro(linea);
                }catch (Exception ignored){

                }
            }
            archivoSalida.close();
        }

        if (opcion.equalsIgnoreCase("trabajadores") || opcion.equalsIgnoreCase("all")) {
            ArchivoSalida archivoSalida = new ArchivoSalida("trabajadores.txt");
            ListaTrabajador listaTrabajador = sistemaRestaurante.getListaTrabajador();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (int i = 0; i < 5; i++) {
                Registro linea = new Registro(5);
                try {
                    linea.agregarCampo(listaTrabajador.obtenerTrabajadorsPorPosicion(i).getNombre());
                    linea.agregarCampo(listaTrabajador.obtenerTrabajadorsPorPosicion(i).getEdad());
                    linea.agregarCampo(listaTrabajador.obtenerTrabajadorsPorPosicion(i).getTipoContrato());
                    linea.agregarCampo(listaTrabajador.obtenerTrabajadorsPorPosicion(i).getFechaContratacion().format(formato));
                    try {
                        linea.agregarCampo(listaTrabajador.obtenerTrabajadorsPorPosicion(i).getFechaTermino().format(formato));
                    }catch (Exception e) {
                        linea.agregarCampo(null);
                    }
                    archivoSalida.writeRegistro(linea);
                }catch (Exception ignored){

                }
            }
            archivoSalida.close();
        }
    }
}