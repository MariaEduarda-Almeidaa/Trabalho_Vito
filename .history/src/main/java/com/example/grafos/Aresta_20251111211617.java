package com.example.grafos;

public class Aresta implements Comparable<Aresta> {
    public final int origem;
    public final int destino;
    public final double peso;

    public Aresta(int origem, int destino, double peso) {
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
    }

    // GETTERS OBRIGATÓRIOS
    public int getOrigem() { return origem; }
    public int getDestino() { return destino; }
    public double getPeso() { return peso; }

    @Override
    public int compareTo(Aresta outra) {
        return Double.compare(this.peso, outra.peso);
    }

    @Override
    public String toString() {
        return String.format("(%d -> %d: %.2f)", origem, destino, peso);
    }
}