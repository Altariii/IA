import IA.Gasolina.*;
import IA.GenerateState;

import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Random seed = new Random();
        int GAS_MAX = 100;
        // NUM_GAS Luego se cambia por args[0]
        int NUM_GAS = 20;
        // NUM_CENTR Luego se cambia por args[1]
        int NUM_CENTR = 3;
        // NUM_CAMIONS Luego se cambia por args[2]
        int NUM_CAMIONS = 1;

        Gasolineras new_gasolineras = new Gasolineras(NUM_GAS, seed.nextInt(GAS_MAX));
        CentrosDistribucion new_centros = new CentrosDistribucion(NUM_CENTR, NUM_CAMIONS, seed.nextInt(GAS_MAX));

        GenerateState estado_inicial = new GenerateState(new_gasolineras, new_centros);

    }
}