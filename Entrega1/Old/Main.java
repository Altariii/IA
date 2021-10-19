package Old;

import Old.Estat;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Random seed = new Random();
        int GAS_MAX = 100;
        int NUM_GAS = 20;
        int NUM_CENTR = 3;
        int NUM_CAMIONS = 1;
        int NUM_VIAJES = 5;
        int NUM_KILOM = 640;
        int NUM_DEPOS = 2;

        Estat estado_inicial = new Estat(GAS_MAX, NUM_GAS, NUM_CENTR, NUM_CAMIONS, NUM_VIAJES, NUM_KILOM, NUM_DEPOS, seed);

    }
}