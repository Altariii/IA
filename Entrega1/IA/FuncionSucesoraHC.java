package IA;

import aima.search.framework.SuccessorFunction;
import aima.search.framework.Successor;
import java.util.ArrayList;
import java.util.List;

public class FuncionSucesoraHC implements SuccessorFunction{

    public List getSuccessors(Object state) {
        ArrayList<Successor> sucesores = new ArrayList();
        Estado estado = (Estado) state;
        Estado estado_sucesor = null;

        // Operador ADD
        for (int i = 0; i < estado.getCamiones().size(); i++) {
            for (int j = 0; j < estado.getPeticiones().size(); j++) {
                estado_sucesor = estado.clona_estado();
                if (estado_sucesor.addPeticion_Camion(i, j)) {
                    sucesores.add(new Successor("Centro " + (i+1) + ", nueva peticion " + (j+1) + " Beneficio: " + estado_sucesor.getTotalBeneficio(), estado_sucesor));
                    /*System.out.println("Estado Inicial suc:");
                    estado.print_camiones();
                    System.out.println("Estado sucessor:");
                    estado_sucesor.print_camiones();*/
                }
            }
        }

        // Operador SwapPeticionesCamiones
        /*
        for (int i = 0; i < estado.getCamiones().size(); i++) {
            for (int j = 0; j < estado.getCamiones().get(i).getViajesCamion().size(); j++) {
                for (int k = i; k < estado.getCamiones().size(); k++) {
                    for (int l = 0; l < estado.getCamiones().get(k).getViajesCamion().size(); l++) {
                        // Estamos intentando cambiar dos peticiones de un mismo viaje de orden
                        if (!(i == k && (j % 2 == 0 && l == j + 1 || j % 2 != 0 && l == j - 1))) {
                            estado_sucesor = estado.clona_estado();
                            if (estado_sucesor.swapPeticionesCamiones(i, j, k, l)) {
                                sucesores.add(new Successor("Peticion " + (j + 1) + " del camion " + (i + 1) + " cambiada por peticion " + (l + 1) + " del camion " + (k + 1)+ " Beneficio: " + estado_sucesor.getTotalBeneficio(), estado_sucesor));
                            }
                        }
                    }
                }
            }
        }*/
        // Operador SwapPeticionNoAtendida
        for (int i = 0; i < estado.getCamiones().size(); i++) {
            for (int j = 0; j < estado.getCamiones().get(i).getViajesCamion().size(); j++) {
                for (int k = 0; k < estado.getPeticiones().size(); k++) {
                    estado_sucesor = estado.clona_estado();
                    if (estado_sucesor.swapPeticionNoAtendida(i, j, k)) {
                        sucesores.add(new Successor("Peticion " + (j+1) + " del camion " + (i+1) + " cambiada por peticion no atendida " + (k+1)+ " Beneficio: " + estado_sucesor.getTotalBeneficio(), estado_sucesor));
                    }
                }
            }
        }
        return sucesores;
    }

}
