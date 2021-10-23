package IA;

import aima.search.framework.HeuristicFunction;

public class FuncionHeurisitica {

    public double getHeuristicValue(Object n) {
        return ((Estado) n).heuristico();
    }
}
