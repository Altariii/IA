package IA;

import IA.Gasolina.*;
import aima.util.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import IA.DataStructures.Camion;

public class GenerateState {

    // Constantes
    int NUM_VIAJES = 5;
    int NUM_KILOM = 640;
    int NUM_DEPOSITOS = 2;

    // Calcula la distancia entre un centro de un camion y una gasolinera
    int calcular_distancia(Pair p, Gasolinera g) {
        return 0;
    }

    // Imprime las peticiones indicando el número de gasolinera y el número de días desde que se ha solicitado
    void print_peticiones(ArrayList<Pair> peticiones) {
        for(int i = 0; i < peticiones.size(); i++) {
            System.out.println("Gasolinera num." + peticiones.get(i).getSecond() + ", dias: " + peticiones.get(i).getFirst());
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

        ArrayList<Camion> Camiones = new ArrayList<Camion>();
        for (int i = 0; i < cd.size(); i++) {
            Camiones.add(new Camion(NUM_VIAJES, NUM_KILOM, NUM_DEPOSITOS, new ArrayList<Integer>()));
        }

    }
}