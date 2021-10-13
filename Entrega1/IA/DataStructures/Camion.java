package IA.DataStructures;

import java.util.ArrayList;

public class Camion {
    int num_viajes;
    int kilometros;
    int depositocamion;
    ArrayList<Integer> Peticiones;

    public int getNum_viajes() {
        return num_viajes;
    }

    public int getKilometros() {
        return kilometros;
    }

    public int getDepositocamion() {
        return depositocamion;
    }

    public ArrayList<Integer> getPeticiones() {
        return Peticiones;
    }

    public Camion(int num_viajes, int kilometros, int depositocamion, ArrayList<Integer> Peticiones) {
        this.num_viajes = num_viajes;
        this.kilometros = kilometros;
        this.depositocamion = depositocamion;
        this.Peticiones = Peticiones;
    }

}
