package IA;

import aima.search.framework.SuccessorFunction;
import aima.search.framework.Successor;
import java.util.ArrayList;
import java.util.List;

public class FuncionSucesoraHC implements SuccessorFunction{

    public List getSuccessors(Object state) {
        ArrayList sucesores = new ArrayList();
        Estado estado = (Estado) state;

        // Operador ADD
        int n_camiones = estado.getCamiones().size();
        int n_peticiones = estado.getPeticiones().size();
        for (int i = 0; i < n_camiones; i++) {
            for (int j = 0; j < n_peticiones; j++) {
                Estado estado_sucesor = estado.clona_estado();
                if (estado_sucesor.addPeticion_Camion(i, j)) {
                    sucesores.add(new Successor("Centro " + (i+1) + ", nueva peticion" + (j+1) + " Beneficio: " + estado.getTotalBeneficio(), estado_sucesor));
                }
            }
        }

        // Operador SwapPeticionesCamiones
        for (int i = 0; i < n_camiones; i++) {
            for (int j = 0; j < estado.getCamiones().get(i).getViajesCamion().size(); j++) {
                for (int k = i; k < n_camiones; k++) {
                    for (int l = 0; l < estado.getCamiones().get(k).getViajesCamion().size(); l++) {
                        // if (
                        Estado estado_sucesor = estado.clona_estado();
                        if (estado_sucesor.swapPeticionesCamiones(i, j, k, l)) {
                            sucesores.add(new Successor("Peticion " + (j+1) + " del camion" + (i+1) + " cambiada por peticion " + (l+1) + " del camion " + (k+1), estado_sucesor));
                        }
                    }
                }
            }
        }

        // Operador SwapPeticionNoAtendida
        for (int i = 0; i < n_camiones; i++) {
            for (int j = 0; j < estado.getCamiones().get(i).getViajesCamion().size(); j++) {
                for (int k = 0; k < estado.getPeticiones().size(); k++) {
                    Estado estado_sucesor = estado.clona_estado();
                    if (estado_sucesor.swapPeticionNoAtendida(i, j, k)) {
                        sucesores.add(new Successor("Peticion " + (j+1) + " del camion" + (i+1) + " cambiada por peticion no atendida" + (k+1), estado_sucesor));
                    }
                }
            }
        }

        return sucesores;
    }

}
