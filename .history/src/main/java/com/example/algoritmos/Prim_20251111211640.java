package com.example.algoritmos;

import com.example.grafos.Aresta;
import com.example.grafos.Grafo;
import java.util.*;

public class Prim {
    public static double executar(Grafo grafo) {
        int n = grafo.getNumeroVertices();
        boolean[] visitado = new boolean[n + 1];
        PriorityQueue<Aresta> pq = new PriorityQueue<>();

        visitado[1] = true;
        for (Aresta a : grafo.getAdjacencias().get(1)) {
            pq.add(a);
        }

        double custo = 0;

        while (!pq.isEmpty()) {
            Aresta a = pq.poll();
            int v = a.getDestino();  // <-- GETTER
            if (visitado[v]) continue;

            visitado[v] = true;
            custo += a.getPeso();  // <-- GETTER

            for (Aresta prox : grafo.getAdjacencias().get(v)) {
                if (!visitado[prox.getDestino()]) {
                    pq.add(prox);
                }
            }
        }
        return custo;
    }
}