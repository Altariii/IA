import java.util.Random;
import IA.Gasolina.*;
import IA.Estado;

public class Main {

    public static void main(String[] args) {
        Random num = new Random();
        int seed = num.nextInt(100);
        CentrosDistribucion centros = new CentrosDistribucion(5, 1, seed);
        Gasolineras gasolineras = new Gasolineras(10, seed);
        Estado inicial = new Estado(centros, gasolineras, true);
        inicial.print_camiones();
    }
}