package com.example.grafos;

import java.util.*;

public class Grafo {
    private final int n;
    private final List<List<Aresta>> adj;
    private int m = 0;

    public Grafo(int n) {
        this.n = n;
        this.adj = new ArrayList<>(n + 1);
        for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());
    }


    
    public void adicionarArestaDirigida(int u, int v, double peso) {
        adj.get(u).add(new Aresta(u, v, peso));
        this.m++;
    }

    
    public void adicionarArestaNaoDirigida(int u, int v, double peso) {
        adj.get(u).add(new Aresta(u, v, peso));
        adj.get(v).add(new Aresta(v, u, peso));
    }
    
    public double getPeso(int u, int v) {
    // Se for matriz
    if (this.matrizAdj != null) {
        return this.matrizAdj[u][v];
    }
    // Se for lista
    if (this.listaAdj != null) {
        for (Aresta a : this.listaAdj[u]) {
            if (a.destino == v) {
                return a.peso;
            }
        }
    }
    return 0;
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
}