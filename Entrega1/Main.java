import IA.Gasolina.*;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Random seed = new Random();
        int GAS_MAX = 100;
        // NUM_GAS Luego se cambia por args[0]
        int NUM_GAS = 20;

        Gasolineras new_gasolineras = new Gasolineras(NUM_GAS, seed.nextInt(GAS_MAX));
        for(int i = 0; i < new_gasolineras.size(); i++) {
            Gasolinera g = new_gasolineras.get(i);
            System.out.println(g.getCoordY());
        }
    }
}