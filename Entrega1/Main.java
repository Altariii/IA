import java.util.*;
import IA.Gasolina.*;
import IA.*;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import  aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

public class Main {

    public static void main(String[] args) throws Exception {

        //PARAMETROS
        Random num = new Random();
        int seed = 1234; //num.nextInt(100);
        int num_cd = 10;
        int num_gas = 95;
        boolean hill_climbing = true;
        CentrosDistribucion centros = new CentrosDistribucion(num_cd, 1, seed);
        Gasolineras gasolineras = new Gasolineras(num_gas, seed);

        //ESTADO INICIAL
        Estado inicial = new Estado(centros, gasolineras, true);
        Problem problema;
        Search algoritmo;

        if (hill_climbing) {
            problema = new Problem(inicial, new FuncionSucesoraHC(), new IAGoalTest(), new FuncionHeurisitica());
            algoritmo = new HillClimbingSearch();
        }

        else {
            problema = new Problem(inicial, new FuncionSucesoraSA(), new IAGoalTest(), new FuncionHeurisitica());
            algoritmo = new SimulatedAnnealingSearch(1000,10,100,0.01);
        }

        double tiempo = System.currentTimeMillis();

        SearchAgent agent = new SearchAgent(problema,algoritmo);


        //RESULTADOS
        if (hill_climbing) pinta_acciones(agent.getActions());
        pinta_Instrumentacion(agent.getInstrumentation());
        System.out.println("Tiempo de ejecucion: " + (System.currentTimeMillis() - tiempo) + "ms");
    }

    //FUNCIONES PRIVADAS
    private static void pinta_acciones(List acciones) {
        for (int i = 0; i < acciones.size(); ++i) {
            String accion = (String) acciones.get(i);
            System.out.println(accion);
        }
    }

    private static void pinta_Instrumentacion (Properties propiedades) {
        Iterator keys = propiedades.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String propiedad = propiedades.getProperty(key);
            System.out.println(key + " : " + propiedad);
        }
    }
}