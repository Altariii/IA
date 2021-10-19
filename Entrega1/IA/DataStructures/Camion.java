package IA.DataStructures;

import IA.Gasolina.Distribucion;
import IA.Gasolina.Gasolinera;
import org.jfree.chart.renderer.xy.DeviationRenderer;

import java.util.ArrayList;


public class Camion {

    // Atributos de la clase camion
    private Distribucion centroCamion;
    private ArrayList<Peticion> viajesCamion;
    private int beneficioViajes;
    private int distanciaRecorrida;

    // Constructor

    public Camion(Distribucion centroCamion) {
        this.centroCamion = centroCamion;
        viajesCamion = new ArrayList<Peticion>();
        beneficioViajes = 0;
        distanciaRecorrida = 0;
    }

    // Getters
    public Distribucion getCentroCamion() {
        return centroCamion;
    }
    public ArrayList<Peticion> getViajesCamion() {
        return viajesCamion;
    }
    public int getViajesTotales() {
        return viajesCamion.size();
    }
    public int getBeneficioViajes() {
        return beneficioViajes;
    }
    public int getDistanciaRecorrida() {
        return distanciaRecorrida;
    }

    // Operadores

    public boolean AddPeticion(Peticion peticion) {
        int peticiones = viajesCamion.size();
        if (peticiones >= 10) return false;
        int distancia = 0;
        if (peticiones % 2 != 0) {
            // Calcular distancia gasolinera1 - gasolinera2 + volver al centro
            Gasolinera g1 = viajesCamion.get(peticiones - 1).getGasolinera();
            Gasolinera g2 = peticion.getGasolinera();
            distancia = calcular_distancia_gasolineras(g1, g2) + calcular_distancia_centro_gasolinera(g2);
        }
        else {
            // Calcular distancia de centro a gasolinera
            distancia = calcular_distancia_centro_gasolinera(peticion.getGasolinera());
        }
        if (distancia + distanciaRecorrida > 640) return false;
        distanciaRecorrida += distancia;
        viajesCamion.add(peticion);
        beneficioViajes += peticion.getBeneficio();
        return true;
    }

    public boolean SwapPeticion(int i, Peticion peticion) {
        Gasolinera g1 = viajesCamion.get(i).getGasolinera();
        Gasolinera nueva_gasolinera = peticion.getGasolinera();
        int distancia = 0;
        int nueva_distancia = 0;

        distancia += calcular_distancia_centro_gasolinera(g1);
        nueva_distancia += calcular_distancia_centro_gasolinera(nueva_gasolinera);

        if (i % 2 != 0) {
            Gasolinera g2 = viajesCamion.get(i - 1).getGasolinera();
            distancia += calcular_distancia_gasolineras(g1, g2);
            nueva_distancia += calcular_distancia_gasolineras(nueva_gasolinera, g2);
        }
        else {
            if (viajesCamion.size() - 1 != i) {
                Gasolinera g2 = viajesCamion.get(i + 1).getGasolinera();
                distancia += calcular_distancia_gasolineras(g1, g2);
                nueva_distancia += calcular_distancia_gasolineras(nueva_gasolinera, g2);
            }
        }

    }

    // Funciones auxiliares
    public int calcular_distancia_gasolineras(Gasolinera g1, Gasolinera g2) {
        return Math.abs(g1.getCoordX() - g2.getCoordX()) + Math.abs(g1.getCoordY() - g2.getCoordY());
    }

    public int calcular_distancia_centro_gasolinera(Gasolinera g) {
        return Math.abs(centroCamion.getCoordX() - g.getCoordX()) + Math.abs(centroCamion.getCoordY() - g.getCoordY());
    }

}
