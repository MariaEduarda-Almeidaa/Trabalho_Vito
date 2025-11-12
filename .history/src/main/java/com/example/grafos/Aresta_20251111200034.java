package com.example.grafos;

public class Aresta implements Comparable<Aresta> {
    private final int origem;
    private final int destino;
    private final double peso;

    public Aresta(int origem, int destino, double peso) {
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
    }

    public int getOrigem() { return origem; }
    public int getDestino() { return destino; }
    public double getPeso() { return peso; }

    @Override
    public int compareTo(Aresta outra) {
        return Double.compare(this.peso, outra.peso);
    }
}
