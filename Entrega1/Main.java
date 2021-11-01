import java.util.*;
import IA.Gasolina.*;
import IA.*;
import aima.basic.Agent;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import  aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

public class Main {

    public static void main(String[] args) throws Exception {

        //PARAMETROS
        ArrayList<Integer> seeds = new ArrayList<Integer>(List.of(17, 94, 64, 2132, 2422, 2154, 2685, 2379, 6195, 9522, 2564, 1175, 7421, 4458, 5417, 8563, 7618, 1329, 8782, 4253));
        int num_cd = 10;
        int num_gas = 100;
        for (int j = 0; j < seeds.size(); j++) {
            int seed = seeds.get(j);

            boolean hill_climbing = true;
            boolean solucion_compleja = false;
            CentrosDistribucion centros = new CentrosDistribucion(num_cd, 1, seed);
            Gasolineras gasolineras = new Gasolineras(num_gas, seed);

            System.out.println("SEED: " + seed);

            //if (solucion_compleja) System.out.println("Solucion compleja: ");
            //else System.out.println("Solucion simple: ");


            //ESTADO INICIAL
            Estado inicial = new Estado(centros, gasolineras, solucion_compleja);
            Problem problema;
            Search algoritmo;

            if (hill_climbing) {
                problema = new Problem(inicial, new FuncionSucesoraHC(), new IAGoalTest(), new FuncionHeurisitica());
                algoritmo = new HillClimbingSearch();
            } else {
                problema = new Problem(inicial, new FuncionSucesoraSA(), new IAGoalTest(), new FuncionHeurisitica());
                algoritmo = new SimulatedAnnealingSearch(10000, 100, 60, 0.0003);
            }

            double tiempo = System.currentTimeMillis();

            SearchAgent agent = new SearchAgent(problema, algoritmo);


            //RESULTADOS
            if (hill_climbing) {
                pinta_acciones(agent.getActions());
            }

            pinta_Instrumentacion(agent.getInstrumentation());
            System.out.println("Tiempo de ejecucion: " + (System.currentTimeMillis() - tiempo) + "ms");
        }


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