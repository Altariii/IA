package IA.DataStructures;

import IA.Gasolina.Distribucion;
import IA.Gasolina.Gasolinera;
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

    public Camion(Distribucion centroCamion, ArrayList<Peticion> viajesCamion, int beneficioViajes, int distanciaRecorrida){
        this.centroCamion = centroCamion;
        this.viajesCamion =  (ArrayList<Peticion>)viajesCamion.clone();
        this.beneficioViajes = beneficioViajes;
        this.distanciaRecorrida = distanciaRecorrida;
    }

    public Camion clonaCamion() {
        return new Camion(this.centroCamion, this.viajesCamion, this.beneficioViajes, this.distanciaRecorrida);
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

    //

    public int comprueba_peticion(Peticion peticion) {
        int peticiones = viajesCamion.size();
        if (peticiones >= 10) return -1;
        int distancia = 0;
        if (peticiones % 2 != 0) {
            // Calcular distancia gasolinera1 - gasolinera2 + volver al centro
            // restamos la anterior que habiamos sumado dos veces por si era la ultima peticion por atender
            Gasolinera g1 = viajesCamion.get(peticiones - 1).getGasolinera();
            Gasolinera g2 = peticion.getGasolinera();
            distancia = calcular_distancia_gasolineras(g1, g2) + calcular_distancia_centro_gasolinera(g2) - calcular_distancia_centro_gasolinera(g1);
        }
        else {
            // Calcular distancia de centro a gasolinera
            distancia = 2 * calcular_distancia_centro_gasolinera(peticion.getGasolinera());
        }
        if (distancia + distanciaRecorrida > 640) return -1;
        return distancia;
    }

    // Operadores

    public boolean AddPeticion(Peticion peticion) {
        int distancia = comprueba_peticion(peticion);
        if (distancia == -1) return false;
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
            else {
                distancia += calcular_distancia_centro_gasolinera(g1);
                nueva_distancia += calcular_distancia_centro_gasolinera(nueva_gasolinera);
            }
        }
        if (distanciaRecorrida - distancia + nueva_distancia > 640) return false;
        distanciaRecorrida -= distancia; distanciaRecorrida += nueva_distancia;
        beneficioViajes -= viajesCamion.get(i).getBeneficio(); beneficioViajes += peticion.getBeneficio();
        viajesCamion.set(i, peticion);
        return true;
    }

    // Funciones auxiliares
    public int calcular_distancia_gasolineras(Gasolinera g1, Gasolinera g2) {
        return Math.abs(g1.getCoordX() - g2.getCoordX()) + Math.abs(g1.getCoordY() - g2.getCoordY());
    }

    public int calcular_distancia_centro_gasolinera(Gasolinera g) {
        return Math.abs(centroCamion.getCoordX() - g.getCoordX()) + Math.abs(centroCamion.getCoordY() - g.getCoordY());
    }

    // Funcion print
    public void print_viajes_camion() {
        System.out.println("Viajes programados del centro de distribucion: (" + centroCamion.getCoordX() + "," + centroCamion.getCoordY() + "):");
        for (int i = 0; i < viajesCamion.size(); i++) {
            if (i % 2 == 0) System.out.println("Viaje " + (i / 2) + 1 + ":");
            System.out.print("    Se llena el deposito de la gasolinera (" + viajesCamion.get(i).getGasolinera().getCoordX() + "," + viajesCamion.get(i).getGasolinera().getCoordY() + "). ");
            System.out.println("Llevaba " + viajesCamion.get(i).getDias() + " dias sin atender.");
        }
        System.out.println("Distancia recorrida durante el dia: " + distanciaRecorrida + "km. Beneficio diario obtenido: " + beneficioViajes + "eur.");
    }

}
