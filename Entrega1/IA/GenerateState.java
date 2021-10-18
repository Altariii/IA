package IA;

import IA.Gasolina.*;
import aima.util.Pair;
import java.util.ArrayList;
import java.util.Comparator;
import java.lang.Math;
import IA.DataStructures.Camion;

public class GenerateState {

    // Constantes
    int NUM_VIAJES = 5;
    int NUM_KILOM = 640;
    int NUM_DEPOSITOS = 2;

    // Calcula la distancia entre dos posiciones
    int calcular_distancia(Pair a, Pair b) {
        return Math.abs((int)a.getFirst() - (int)b.getFirst()) + Math.abs((int)a.getSecond() - (int)b.getSecond());
    }

    // Asigna peticiones a los camiones que esten mas cerca de esa peticion
    ArrayList<Camion> asignar_peticiones(ArrayList<Camion> camiones, ArrayList<Pair> peticiones, Gasolineras gas) {

        return camiones;
    }

    // Cuando se ha asignado una peticion a cada camion, se descuentan los quilometros, el numero de viajes, etc.
    ArrayList<Camion> descontar_peticiones(ArrayList<Camion> camiones, ArrayList<Pair> peticiones, Gasolineras gas, CentrosDistribucion cd) {
        for (int i = 0; i < camiones.size(); i++) {

        }
        return camiones;
    }

    // Imprime las peticiones indicando el número de gasolinera y el número de días desde que se ha solicitado
    void print_peticiones(ArrayList<Pair> peticiones) {
        for(int i = 0; i < peticiones.size(); i++) {
            System.out.println("Gasolinera num." + peticiones.get(i).getSecond() + ", dias: " + peticiones.get(i).getFirst());
        }
    }

    // Imprime la información de un camión
    void print_camiones(ArrayList<Camion> camiones) {
        for(int i = 0; i < camiones.size(); i++) {
            System.out.println("Camion numero " + i + ":");
            System.out.println("    viajes restantes: " + camiones.get(i).getNum_viajes());
            System.out.println("    kilometros restantes: " + camiones.get(i).getKilometros());
            System.out.println("    depositos restantes: " + camiones.get(i).getDepositocamion());
            System.out.println("    peticion activa del camion: " + camiones.get(i).getPeticiones());
            System.out.println("    Posicion del camion: " + camiones.get(i).getPosicion() + "\n");
        }
    }

    public GenerateState(Gasolineras gas, CentrosDistribucion cd) {

        ArrayList<Pair> sorted_peticiones = new ArrayList<>();
        for (int i = 0; i < gas.size(); i++) {
            ArrayList peticiones = gas.get(i).getPeticiones();
            for (int j = 0; j < peticiones.size(); j++) {
                sorted_peticiones.add(new Pair(peticiones.get(j), i));
            }
        }

        // Peticiones ordenadas por dias
        sorted_peticiones.sort(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                if ((int)o1.getFirst() > (int)o2.getFirst()) return -1;
                else return 1;
            }
        });

        // Inicializar camiones
        ArrayList<Camion> Camiones = new ArrayList<Camion>();
        for (int i = 0; i < cd.size(); i++) {
            Pair posicion_camion = new Pair(cd.get(i).getCoordX(), cd.get(i).getCoordY());
            Camiones.add(new Camion(NUM_VIAJES, NUM_KILOM, NUM_DEPOSITOS, new ArrayList<Integer>(), posicion_camion));
        }

        // Calcular solucion
        while(Camiones.size() > 0) {
            Camiones = asignar_peticiones(Camiones, sorted_peticiones, gas);
            Camiones = descontar_peticiones(Camiones, sorted_peticiones, gas, cd);
            print_camiones(Camiones);
        }


    }
}