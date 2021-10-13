import IA.Gasolina.*;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Random seed = new Random();
        int GAS_MAX = 100;
        // NUM_GAS Luego se cambia por args[0]
        int NUM_GAS = 20;
        // NUM_CENTR Luebo se cambia por args[1]
        int NUM_CENTR = 3;

        Gasolineras new_gasolineras = new Gasolineras(NUM_GAS, seed.nextInt(GAS_MAX));
        CentrosDistribucion new_centros = new CentrosDistribucion(NUM_CENTR, 1, seed.nextInt(GAS_MAX));

        }
    }
}