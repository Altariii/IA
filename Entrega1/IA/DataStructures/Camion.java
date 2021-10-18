package IA.DataStructures;

import aima.util.Pair;
import java.util.ArrayList;

public class Camion {
    int num_viajes;
    int kilometros;
    int depositocamion;
    ArrayList<Integer> Peticiones;
    Pair posicion;

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

    public Pair getPosicion() {
        return posicion;
    }

    public void setNum_viajes(int num_viajes) {
        this.num_viajes = num_viajes;
    }

    public void setKilometros(int kilometros) {
        this.kilometros = kilometros;
    }

    public void setDepositocamion(int depositocamion) {
        this.depositocamion = depositocamion;
    }

    public void setPeticiones(ArrayList<Integer> Peticiones) {
        this.Peticiones = Peticiones;
    }

    public void setPosicion(Pair posicion) {
        this.posicion = posicion;
    }

    public Camion(int num_viajes, int kilometros, int depositocamion, ArrayList<Integer> Peticiones, Pair posicion) {
        this.num_viajes = num_viajes;
        this.kilometros = kilometros;
        this.depositocamion = depositocamion;
        this.Peticiones = Peticiones;
        this.posicion = posicion;
    }

}
