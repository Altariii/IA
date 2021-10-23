package IA;

import aima.search.framework.HeuristicFunction;

public class FuncionHeurisitica implements HeuristicFunction {

    public double getHeuristicValue(Object n) {
        return ((Estado) n).heuristico();
    }
}
