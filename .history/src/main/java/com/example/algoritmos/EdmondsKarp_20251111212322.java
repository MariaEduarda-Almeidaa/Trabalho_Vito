// === SUBSTITUA A PARTIR DAQUI ===
for (int u = 1; u <= V; u++) {
    for (Aresta a : grafo.getAdjacencias().get(u)) {
        int v = a.getDestino();
        double cap = a.getPeso();

        if (cap <= 0) continue;

        // Primeiro, reserve os índices
        int forwardIdxInU = adj.get(u).size();
        int backwardIdxInV = adj.get(v).size();

        // Crie as arestas
        Edge forward = new Edge(v, backwardIdxInV, cap);
        Edge backward = new Edge(u, forwardIdxInU, 0);

        // Agora adicione
        adj.get(u).add(forward);
        adj.get(v).add(backward);
    }
}
// === ATÉ AQUI ===