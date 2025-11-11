package com.example.algoritmos;


import java.util.*;

import com.example.grafos.Aresta;
import com.example.grafos.Grafo;

public class Dijkstra {
    public static double[] executar(Grafo grafo, int origem) {
        int n = grafo.getNumeroVertices();
        double[] dist = new double[n + 1];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        dist[origem] = 0.0;
        
        class Node {
            int v;
            double d;
            Node(int v, double d) { this.v = v; this.d = d; }
        }

        PriorityQueue<Node> fila = new PriorityQueue<>(Comparator.comparingDouble(n1 -> n1.d));
        fila.add(new Node(origem, 0.0));

        while (!fila.isEmpty()) {
            Node no = fila.poll();
            int u = no.v;
            
            if (no.d > dist[u]) continue;

            for (Aresta a : grafo.getAdjacencias().get(u)) {
                int v = a.getDestino();
                double novo = dist[u] + a.getPeso();
                if (novo < dist[v]) {
                    dist[v] = novo;
                    fila.add(new Node(v, novo));
                }
            }
        }
        return dist;
    }

    public static double custoTotal(double[] dist) {
        double soma = 0;
        for (double d : dist)
            if (d < Double.POSITIVE_INFINITY) soma += d;
        return soma;
    }
}
