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
        pq.addAll(grafo.getAdjacencias().get(1));
        double custo = 0;

        while (!pq.isEmpty()) {
            Aresta a = pq.poll();
            if (visitado[a.getDestino()]) continue;
            visitado[a.getDestino()] = true;
            custo += a.getPeso();
            for (Aresta prox : grafo.getAdjacencias().get(a.getDestino()))
                if (!visitado[prox.getDestino()]) pq.add(prox);
        }
        return custo;
    }
}
