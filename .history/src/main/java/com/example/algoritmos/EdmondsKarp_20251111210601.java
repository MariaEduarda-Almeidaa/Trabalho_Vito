// src/main/java/com/example/algoritmos/EdmondsKarp.java
package com.example.algoritmos;

import com.example.grafos.Grafo;
import java.util.*;

public class EdmondsKarp {

    private static class Aresta {
        int destino;
        int capacidade;
        int fluxo;
        Aresta reversa;

        Aresta(int destino, int capacidade) {
            this.destino = destino;
            this.capacidade = capacidade;
            this.fluxo = 0;
        }
    }

    public static double executar(Grafo grafo, int s, int t) {
        int V = grafo.getNumeroVertices();
        List<Aresta>[] adj = new List[V + 1];
        for (int i = 0; i <= V; i++) {
            adj[i] = new ArrayList<>();
        }

        // Preenche a lista de adjacência com capacidades
        for (int u = 1; u <= V; u++) {
            for (int v = 1; v <= V; v++) {
                double cap = grafo.getPeso(u, v);
                if (cap > 0 && cap != Double.POSITIVE_INFINITY) {
                    Aresta forward = new Aresta(v, (int) cap);
                    Aresta backward = new Aresta(u, 0);
                    forward.reversa = backward;
                    backward.reversa = forward;
                    adj[u].add(forward);
                    adj[v].add(backward);
                }
            }
        }

        int[] parent = new int[V + 1];
        double maxFluxo = 0;

        while (true) {
            Arrays.fill(parent, -1);
            Queue<Integer> queue = new LinkedList<>();
            queue.add(s);
            parent[s] = -2;

            int curr;
            boolean found = false;
            while (!queue.isEmpty() && !found) {
                curr = queue.poll();
                for (Aresta a : adj[curr]) {
                    int v = a.destino;
                    if (parent[v] == -1 && a.capacidade - a.fluxo > 0) {
                        parent[v] = curr;
                        queue.add(v);
                        if (v == t) {
                            found = true;
                            break;
                        }
                    }
                }
            }

            if (!found) break;

            int pathFluxo = Integer.MAX_VALUE;
            for (int v = t; v != s; v = parent[v]) {
                int u = parent[v];
                Aresta a = getAresta(adj[u], v);
                pathFluxo = Math.min(pathFluxo, a.capacidade - a.fluxo);
            }

            for (int v = t; v != s; v = parent[v]) {
                int u = parent[v];
                Aresta a = getAresta(adj[u], v);
                a.fluxo += pathFluxo;
                a.reversa.fluxo -= pathFluxo;
            }

            maxFluxo += pathFluxo;
        }

        return maxFluxo;
    }

    private static Aresta getAresta(List<Aresta> lista, int destino) {
        for (Aresta a : lista) {
            if (a.destino == destino) {
                return a;
            }
        }
        return null;
    }
}