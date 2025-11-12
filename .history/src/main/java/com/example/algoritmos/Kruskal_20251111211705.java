package com.example.algoritmos;

import com.example.grafos.Aresta;
import com.example.grafos.Grafo;
import java.util.*;

public class Kruskal {
    private static int find(int[] pai, int i) {
        if (pai[i] != i) pai[i] = find(pai, pai[i]);
        return pai[i];
    }

    private static void union(int[] pai, int[] rank, int x, int y) {
        int rx = find(pai, x), ry = find(pai, y);
        if (rx != ry) {
            if (rank[rx] < rank[ry]) pai[rx] = ry;
            else if (rank[rx] > rank[ry]) pai[ry] = rx;
            else { pai[ry] = rx; rank[rx]++; }
        }
    }

    public static double executar(Grafo g) {
        List<Aresta> arestas = new ArrayList<>(g.getTodasArestas());
        arestas.sort(null);  // Usa compareTo de Aresta

        int n = g.getNumeroVertices();
        int[] pai = new int[n + 1], rank = new int[n + 1];
        for (int i = 0; i <= n; i++) pai[i] = i;

        double custo = 0;
        for (Aresta a : arestas) {
            int u = a.getOrigem();   // <-- GETTER
            int v = a.getDestino();  // <-- GETTER
            int ru = find(pai, u), rv = find(pai, v);
            if (ru != rv) {
                custo += a.getPeso();  // <-- GETTER
                union(pai, rank, ru, rv);
            }
        }
        return custo;
    }
}