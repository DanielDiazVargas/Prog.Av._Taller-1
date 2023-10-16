package Util;

import Service.Sistema;
import Service.SistemaImpl;

public class Instalador {
    private Sistema sistemaRestaurante;

    public Instalador() {
        this.sistemaRestaurante = new SistemaImpl();
    }

    public Sistema instalarSistema() {
        return this.sistemaRestaurante;
    }
}
