package IA;

import IA.Gasolina.*;
import IA.DataStructures.*;

import java.util.ArrayList;
import java.util.Comparator;

public class Estado {

    // Atributos de la clase State
    private CentrosDistribucion centros;
    private Gasolineras gasolineras;
    private ArrayList<Camion> Camiones;
    private ArrayList<Peticion> Peticiones;
    private int numPeticiones;
    private int TotalPerdidas;

    // Constructor
    public Estado(CentrosDistribucion centros, Gasolineras gasolineras, boolean solucionCompleja) {
        this.centros = centros;
        this.gasolineras = gasolineras;
        Camiones = new ArrayList<Camion>();
        Peticiones = new ArrayList<Peticion>();
        numPeticiones = Peticiones.size();

        for (Gasolinera gasolinera : gasolineras) {
            ArrayList<Integer> Peticiones_gasolinera = gasolinera.getPeticiones();
            for (Integer peticion : Peticiones_gasolinera) {
                Peticion nueva_peticion = new Peticion(gasolinera, peticion);
                Peticiones.add(nueva_peticion);
                TotalPerdidas += nueva_peticion.getPerdidas();
            }
        }

        // Estado inicial vacio
        for (Distribucion centro : centros) Camiones.add(new Camion(centro));

        // Estado inicial complejo
        if (solucionCompleja) {
            ArrayList<Peticion> peticiones_ordenadas = ordenar_peticiones(Peticiones);
            for (int i = 0; i < peticiones_ordenadas.size(); i++) {
                Peticion peticion = peticiones_ordenadas.get(i);
                int camion_asignado = asignar_camion_cercano(peticion);
                if (camion_asignado != -1) {
                    Camiones.get(camion_asignado).AddPeticion(peticion);
                    TotalPerdidas -= peticion.getPerdidas();
                    Peticiones.remove(i);
                    i--;
                }
            }
        }
        //System.out.println("Estado Inicial:");
        //print_camiones();

    }

    public Estado(CentrosDistribucion centros, Gasolineras gasolineras, ArrayList<Camion> Camiones, ArrayList<Peticion> Peticiones, int numPeticiones, int TotalPerdidas) {
        this.centros = centros;
        this.gasolineras = gasolineras;
        this.Camiones = new ArrayList<Camion>();
        for (int i = 0; i < Camiones.size(); i ++) this.Camiones.add(Camiones.get(i).clonaCamion());
        this.Peticiones = new ArrayList<Peticion>();
        for (int i = 0; i < Peticiones.size(); i ++) this.Peticiones.add(Peticiones.get(i));
        this.numPeticiones = numPeticiones;
        this.TotalPerdidas = TotalPerdidas;
    }

    public Estado clona_estado() {
        return new Estado(centros, gasolineras, Camiones, Peticiones, numPeticiones, TotalPerdidas);
    }


    // Getters

    public CentrosDistribucion getCentros() {
        return centros;
    }

    public Gasolineras getGasolineras() {
        return gasolineras;
    }

    public ArrayList<Camion> getCamiones() {
        return Camiones;
    }

    public ArrayList<Peticion> getPeticiones() {
        return Peticiones;
    }

    public int getNumPeticiones() { return numPeticiones; }

    public int getTotalPerdidas() {
        return TotalPerdidas;
    }

    public int getTotalBeneficio() {
        int beneficio = 0;
        for (Camion camion : Camiones) beneficio += camion.getBeneficioViajes() - (camion.getDistanciaRecorrida() * 2);
        return beneficio - TotalPerdidas;
    }

    // Operadores
    public boolean addPeticion_Camion(int index_camion, int index_peticion) {
        if (index_camion == -1 ||index_peticion == -1) return false;
        Peticion peticion = Peticiones.get(index_peticion);
        if (Camiones.get(index_camion).AddPeticion(peticion)) {
            TotalPerdidas -= peticion.getPerdidas();
            Peticiones.remove(index_peticion);
            return true;
        }
        return false;
    }

    public boolean swapPeticionesCamiones(int index_camion1, int index_peticion1, int index_camion2, int index_peticion2) {
        if (index_camion1 == -1 || index_camion2 == -1 || index_peticion1 == -1 || index_peticion2 == -1) return false;
        Peticion peticion1 = Camiones.get(index_camion1).getViajesCamion().get(index_peticion1);
        Peticion peticion2 = Camiones.get(index_camion2).getViajesCamion().get(index_peticion2);
        return Camiones.get(index_camion1).SwapPeticion(index_peticion1, peticion2) && Camiones.get(index_camion2).SwapPeticion(index_peticion2, peticion1);
    }

    public boolean swapPeticionNoAtendida(int index_camion, int index_peticion1, int index_peticion2) {
        if (index_camion == -1 || index_peticion1 == -1 || index_peticion2 == -1) return false;
        Camion camion = Camiones.get(index_camion);
        Peticion nueva_peticion = Peticiones.get(index_peticion2);
        Peticion peticion_activa = camion.getViajesCamion().get(index_peticion1);
        if (camion.SwapPeticion(index_peticion1, nueva_peticion)) {
            Peticiones.set(index_peticion2, peticion_activa);
            TotalPerdidas += peticion_activa.getPerdidas() - nueva_peticion.getPerdidas();
            return true;
        }
        return false;
    }

    // Funcion heuristica
    public double heuristico() {
        int heuristico = 0;
        for (Camion camion : Camiones) heuristico += (camion.getDistanciaRecorrida() * 2) - camion.getBeneficioViajes();
        return heuristico + TotalPerdidas;
    }


    // Prints
    public void print_camiones() {
        for (int i = 0; i < Camiones.size(); i++) {
            System.out.println("Camion numero " + (i + 1) + ":");
            System.out.println("    Posicion: (" + Camiones.get(i).getCentroCamion().getCoordX() + "," + Camiones.get(i).getCentroCamion().getCoordY() + ").");
            System.out.println("    Asignaciones:");
            for (int j = 0; j < Camiones.get(i).getViajesCamion().size(); j++) {
                Peticion peticion = Camiones.get(i).getViajesCamion().get(j);
                System.out.print("        Peticion numero " + (j + 1) + ", Gasolinera (");
                System.out.println(peticion.getGasolinera().getCoordX() + "," + peticion.getGasolinera().getCoordY() + "): Dias sin atender: " + peticion.getDias() + ".");
            }
        }
    }

    // Funciones auxiliares
    private ArrayList<Peticion> ordenar_peticiones(ArrayList<Peticion> peticiones) {
        // Peticiones ordenadas por dias
        peticiones.sort(new Comparator<Peticion>() {
            @Override
            public int compare(Peticion p1, Peticion p2) {
                if (p1.getDias() > p2.getDias()) return -1;
                else if (p1.getDias() == p2.getDias()) return 0;
                else return 1;
            }
        });
        return peticiones;
    }

    private int asignar_camion_cercano(Peticion p) {
        int distancia_minima = -1;
        int index = -1;
        for (int i = 0; i < Camiones.size(); i++) {
            int distancia = Camiones.get(i).comprueba_peticion(p);
            if (distancia != -1 && (distancia < distancia_minima || distancia_minima == -1)) {
                distancia_minima = distancia;
                index = i;
            }
        }
        return index;
    }


}
