package IA.DataStructures;

import IA.Gasolina.Gasolinera;

/*
    Clase peticion: contiene la gasolinera que la solicita y los dias desde que se produjo la solicitud.
    Ademas, implementa un metodo para obtener el beneficio de atender la peticion y otro metodo para
    obtener las perdidas de no atender esa peticion durante el dia
 */


public class Peticion {

    // Atributos de la clase peticion
    private Gasolinera g;
    private int dias;

    // Constructor
    public Peticion(Gasolinera g, int dias) {
        this.g = g;
        this.dias = dias;
    }


    // Getters
    public Gasolinera getGasolinera() {
        return g;
    }
    public int getDias() {
        return dias;
    }

    public int getBeneficio() {

        // Retorna el beneficio de atender una peticion

        // El valor de un deposito es de 1020 el primer dia
        if (dias == 0) return 1020;
        // El valor de un deposito es de 1000 - (10 * 2^dias) a partir del segundo dia
        return 1000 - (10 * (int)Math.pow(2, dias));
    }
    public int getPerdidas() {

        // Retorna la resta entre el beneficio de haber atendido la peticion en ese dia y haberla atendido al siguiente (perdidas)

        int beneficio_dia0 = getBeneficio();
        dias++;
        int beneficio_dia1 = getBeneficio();
        dias--;
        return beneficio_dia0 - beneficio_dia1;
    }

}
