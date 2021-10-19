package Old;

import IA.Gasolina.*;
import aima.util.Pair;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Random;

import Old.DataStructures.Camion;

public class Estat {

    // Constantes
    int NUM_VIAJES;
    int NUM_KILOM;
    int NUM_DEPOSITOS;
    Gasolineras gas;
    CentrosDistribucion cd;

    // Calcula la distancia entre dos posiciones
    int calcular_distancia(Pair a, Pair b) {
        return Math.abs((int) a.getFirst() - (int) b.getFirst()) + Math.abs((int) a.getSecond() - (int) b.getSecond());
    }

    // Calcula si un camion es valido
    boolean camion_valido(Camion camion, Pair gas_pos) {
        if (camion.getNum_viajes() > 0) {
            if (calcular_distancia(camion.getPosicion(), gas_pos) + calcular_distancia(gas_pos, camion.getPosicion_centro()) <= camion.getKilometros()) {
                return true;
            }
        }
        return false;
    }

    // Calcula la distancia minima en un vector de camiones
    int calcular_camion_cercano(ArrayList<Camion> camiones, Pair gas_pos) {
        int distancia_min = 150;
        int index = -1;
        for (int i = 0; i < camiones.size(); i++) {
            if (camion_valido(camiones.get(i), gas_pos)) {
                int distancia = calcular_distancia(camiones.get(i).getPosicion(), gas_pos);
                if (distancia_min > distancia) {
                    distancia_min = distancia;
                    index = i;
                } else if (distancia_min == distancia) {
                    if (calcular_distancia(camiones.get(i).getPosicion(), gas_pos) < calcular_distancia(camiones.get(index).getPosicion_centro(), gas_pos)) {
                        index = i;
                    }
                }
            }
        }
        return index;
    }

    // Ejecuta una peticion para un camion
    Camion ejecutar_peticion(Camion camion, Pair posicion_gasolinera) {
        camion.setKilometros(camion.getKilometros() - calcular_distancia(camion.getPosicion(), posicion_gasolinera));
        camion.setPosicion(posicion_gasolinera);
        camion.setDepositocamion(camion.getDepositocamion() - 1);
        if (camion.getDepositocamion() == 0) {
            camion.setKilometros(camion.getKilometros() - calcular_distancia(camion.getPosicion(), camion.getPosicion_centro()));
            camion.setPosicion(camion.getPosicion_centro());
            camion.setDepositocamion(NUM_DEPOSITOS);
            camion.setNum_viajes(camion.getNum_viajes() - 1);
        }

        return camion;
    }

    // Devuelve al centro a los camiones que aun estan en una gasolinera
    ArrayList<Camion> volver_centro(ArrayList<Camion> camiones) {
        for (int i = 0; i < camiones.size(); i++) {
            if (camiones.get(i).getNum_viajes() > 0)
                camiones.set(i, ejecutar_peticion(camiones.get(i), camiones.get(i).getPosicion_centro()));
        }
        return camiones;
    }

    // Asigna peticiones a los camiones
    Pair asignar_peticiones(ArrayList<Camion> camiones, ArrayList<Pair> peticiones, Gasolineras gas, CentrosDistribucion cd) {
        for (int i = 0; i < peticiones.size(); i++) {
            Pair posicion_gasolinera = new Pair(gas.get((int) peticiones.get(i).getSecond()).getCoordX(), gas.get((int) peticiones.get(i).getSecond()).getCoordY());
            int index_camion = calcular_camion_cercano(camiones, posicion_gasolinera);
            if (index_camion != -1) {
                ArrayList<Integer> peticiones_camion = camiones.get(index_camion).getPeticiones();
                peticiones_camion.add((int) peticiones.get(i).getSecond());
                camiones.get(index_camion).setPeticiones(peticiones_camion);
                camiones.set(index_camion, ejecutar_peticion(camiones.get(index_camion), posicion_gasolinera));
                peticiones.remove(i);
                i--;
            }
        }
        camiones = volver_centro(camiones);
        return new Pair(camiones, peticiones);
    }

    // Imprime las peticiones indicando el número de gasolinera y el número de días desde que se ha solicitado
    void print_peticiones(ArrayList<Pair> peticiones) {
        for (int i = 0; i < peticiones.size(); i++) {
            System.out.println("Gasolinera num." + peticiones.get(i).getSecond() + ", dias: " + peticiones.get(i).getFirst());
        }
    }

    // Imprime la información de un camión
    void print_camiones(ArrayList<Camion> camiones) {
        for (int i = 0; i < camiones.size(); i++) {
            System.out.println("Camion numero " + i + ":");
            System.out.println("    viajes restantes: " + camiones.get(i).getNum_viajes());
            System.out.println("    kilometros restantes: " + camiones.get(i).getKilometros());
            System.out.println("    depositos restantes: " + camiones.get(i).getDepositocamion());
            System.out.println("    peticion activa del camion: " + camiones.get(i).getPeticiones());
            System.out.println("    Posiciones del camion: " + camiones.get(i).getPosicion());
            System.out.println("    Posicion del centro: " + camiones.get(i).getPosicion_centro() + "\n");
        }
    }

    public Estat(int GAS_MAX, int NUM_GAS, int NUM_CENTR, int NUM_CAMIONS, int NUM_VIAJES, int NUM_KILOM, int NUM_DEPOSITOS, Random seed) {

        this.NUM_VIAJES = NUM_VIAJES;
        this.NUM_KILOM = NUM_KILOM;
        this.NUM_DEPOSITOS = NUM_DEPOSITOS;
        this.gas = new Gasolineras(NUM_GAS, seed.nextInt(GAS_MAX));
        this.cd = new CentrosDistribucion(NUM_CENTR, NUM_CAMIONS, seed.nextInt(GAS_MAX));

    }

    public Estat(Estat estat) {

        this.NUM_VIAJES = estat.NUM_VIAJES;
        this.NUM_KILOM = estat.NUM_KILOM;
        this.NUM_DEPOSITOS = estat.NUM_DEPOSITOS;
        this.gas = estat.gas;
        this.cd = estat.cd;

    }
}

    /*
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
            Camiones.add(new Camion(NUM_VIAJES, NUM_KILOM, NUM_DEPOSITOS, new ArrayList<Integer>(), posicion_camion, new Pair(cd.get(i).getCoordX(), cd.get(i).getCoordY())));
        }

        System.out.println("CAMIONES INICIALES:");
        print_camiones(Camiones);
        System.out.println("PETICIONES INICIALES:");
        print_peticiones(sorted_peticiones);

        Pair Resultado = asignar_peticiones(Camiones, sorted_peticiones, gas, cd);
        Camiones = (ArrayList<Camion>) Resultado.getFirst();
        sorted_peticiones = (ArrayList<Pair>) Resultado.getSecond();

        System.out.println("CAMIONES FINALES:");
        print_camiones(Camiones);
        System.out.println("PETICIONES FINALES:");
        print_peticiones(sorted_peticiones);

        // Añadir al camion una variable trayecto con las posiciones de cada viaje (y cambiar por peticiones)
    }

     */