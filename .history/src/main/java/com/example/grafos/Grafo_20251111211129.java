package com.example.grafos;

import java.util.*;

public class Grafo {
    private final int n;
    private final List<List<Aresta>> adj;
    private int m = 0;

    public Grafo(int n) {
        this.n = n;
        this.adj = new ArrayList<>(n + 1);
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void adicionarArestaDirigida(int u, int v, double peso) {
        adj.get(u).add(new Aresta(u, v, peso));
        this.m++;
    }

    public void adicionarArestaNaoDirigida(int u, int v, double peso) {
        adj.get(u).add(new Aresta(u, v, peso));
        adj.get(v).add(new Aresta(v, u, peso));
        this.m += 2; // duas arestas
    }

    public List<Aresta> getTodasArestas() {
        List<Aresta> todas = new ArrayList<>();
        for (List<Aresta> lista : adj) {
            todas.addAll(lista);
        }
        return todas;
    }

    public int getNumeroVertices() { return n; }
    public int getNumeroArestas() { return m; }
    public List<List<Aresta>> getAdjacencias() { return adj; }

    // CORRIGIDO: usa a lista de adjacência que já existe
    public double getPeso(int u, int v) {
        if (u < 1 || u > n || v < 1 || v > n) return 0;
        for (Aresta a : adj.get(u)) {
            if (a.destino == v) {
                return a.peso;
            }
        }
        return 0;
    }
}