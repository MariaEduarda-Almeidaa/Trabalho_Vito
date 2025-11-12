package com.example.grafos;

public class Aresta {
    public final int origem;
    public final int destino;
    public final double peso;

    public Aresta(int origem, int destino, double peso) {
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
    }

    @Override
    public String toString() {
        return String.format("(%d -> %d: %.2f)", origem, destino, peso);
    }
}