package IA;

import aima.search.framework.SuccessorFunction;
import aima.search.framework.Successor;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FuncionSucesoraSA implements SuccessorFunction {

    public List getSuccessors(Object state) {
        ArrayList sucesor = new ArrayList();
        Estado estado = (Estado) state;

        /*
         Si getRandomNum == 0, Operador ADD
         si == 1, Operador SwapPeticionNoAtendida
         si == 2, Operador SwapPeticionesCamiones
         */

        int RandLowerBound = 0;
        int RandUpperBound = 2;
        boolean sucesorViable = false;

        while (!sucesorViable) {

            // Solo se puede usar swapViajes
            if (estado.getPeticiones().size() == 0) {
                RandLowerBound = 2;
            }
            // Estado vacio -> Solo se puede usar add
            else if (estado.getNumPeticiones() == estado.getPeticiones().size()) {
                RandUpperBound = 0;
            }

            int randomNum = getRandomNum(RandLowerBound, RandUpperBound);
            int numCamion = getRandomNum(0, estado.getCamiones().size() - 1);
            int numPeticionCamion = getRandomNum(0, estado.getCamiones().get(numCamion).getViajesCamion().size() - 1);
            int numPeticionNoAtendida = getRandomNum(0, estado.getPeticiones().size() - 1);

            Estado estado_sucesor = estado.clona_estado();

            // Falta ver si un sucesor es viable
            switch (randomNum) {
                case 0:
                    // Add
                    if (estado_sucesor.addPeticion_Camion(numCamion, numPeticionCamion)) {
                        sucesorViable = true;
                        sucesor.add(new Successor("Centro " + (numCamion+1) + ", nueva peticion" + (numPeticionCamion+1) + " Beneficio: " + estado.getTotalBeneficio(), estado_sucesor));
                    }
                    break;

                case 1:
                    // SwapPeticionNoAtendida
                    if (estado_sucesor.swapPeticionNoAtendida(numCamion, numPeticionCamion, numPeticionNoAtendida)) {
                        sucesorViable = true;
                        sucesor.add(new Successor("Peticion " + (numPeticionCamion+1) + " del camion " + (numCamion+1) + " cambiada por peticion no atendida " + (numPeticionNoAtendida+1), estado_sucesor));
                    }
                    break;

                case 2:
                    // SwapPeticionesCamiones
                    int numCamion2 = getRandomNum(0, estado.getCamiones().size() - 1);
                    int numPeticion2 = getRandomNum(0, estado.getCamiones().get(numCamion2).getViajesCamion().size() - 1);
                    if (estado_sucesor.swapPeticionesCamiones(numCamion, numPeticionCamion, numCamion2, numPeticion2)) {
                        sucesorViable = true;
                        sucesor.add(new Successor("Peticion " + (numPeticionCamion+1) + " del camion " + (numCamion+1) + " cambiada por peticion " + (numPeticion2) + " del camion " + (numCamion2), estado_sucesor));
                    }
                    break;
            }
        }
        return sucesor;
    }

    private int getRandomNum(int LowerBound, int UpperBound) {
        Random num = new Random();
        return num.nextInt((UpperBound - LowerBound) + 1) + LowerBound;
    }
}
