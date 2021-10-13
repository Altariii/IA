package IA;

import IA.Gasolina.*;
import aima.util.Pair;
import java.util.ArrayList;
import java.util.Comparator;

public class GenerateState {

    void print_peticiones(ArrayList<Pair> peticiones) {
        for(int i = 0; i < peticiones.size(); i++) {
            System.out.println("Gasolinera num." + peticiones.get(i).getSecond() + ", dias: " + peticiones.get(i).getFirst());
        }
    }

    int calcular_distancia(Distribucion d, Gasolinera g) {
        return 0;
    }

    public GenerateState(Gasolineras gas, CentrosDistribucion cd) {

        ArrayList<Pair> sorted_peticiones = new ArrayList<>();
        for (int i = 0; i < gas.size(); i++) {
            ArrayList peticiones = gas.get(i).getPeticiones();
            for (int j = 0; j < peticiones.size(); j++) {
                sorted_peticiones.add(new Pair(peticiones.get(j), i));
            }
        }

        sorted_peticiones.sort(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                if ((int)o1.getFirst() > (int)o2.getFirst()) return -1;
                else return 1;
            }
        });



    }
}