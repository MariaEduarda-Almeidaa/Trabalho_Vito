package com.example.algoritmos;

import com.example.grafos.Grafo;
import com.example.grafos.Aresta;
import java.util.*;

public class EdmondsKarp {

    private static class Edge {
        int to, revIdx;
        double capacity, flow;

        Edge(int to, int revIdx, double capacity) {
            this.to = to;
            this.revIdx = revIdx;
            this.capacity = capacity;
            this.flow = 0;
        }
    }

    public static double executar(Grafo grafo, int s, int t) {
        int V = grafo.getNumeroVertices();
        List<List<Edge>> adj = new ArrayList<>(V + 1);
        for (int i = 0; i <= V; i++) {
            adj.add(new ArrayList<>());
        }

        // Construir grafo residual com lista de adjacência
        for (int u = 1; u <= V; u++) {
            for (Aresta a : grafo.getAdjacencias().get(u)) {
                int v = a.destino;
                double cap = a.peso;

                if (cap <= 0) continue;

                // Aresta direta
                Edge forward = new Edge(v, adj.get(v).size(), cap);
                // Aresta reversa (inicialmente capacidade 0)
                Edge backward = new Edge(u, adj.get(u).size() - 1, 0);

                forward.revIdx = adj.get(v).size();
                backward.revIdx = adj.get(u).size() - 1;

                adj.get(u).add(forward);
                adj.get(v).add(backward);
            }
        }

        double maxFlow = 0;
        int[] parent = new int[V + 1];
        List<Edge> path = new ArrayList<>();

        while (true) {
            Arrays.fill(parent, -1);
            Queue<Integer> q = new LinkedList<>();
            q.add(s);
            parent[s] = -2;
            boolean found = false;

            while (!q.isEmpty() && !found) {
                int u = q.poll();
                for (int i = 0; i < adj.get(u).size(); i++) {
                    Edge e = adj.get(u).get(i);
                    int v = e.to;
                    if (parent[v] == -1 && e.capacity - e.flow > 1e-9) {
                        parent[v] = u;
                        path.add(e);
                        q.add(v);
                        if (v == t) {
                            found = true;
                            break;
                        }
                    }
                }
            }

            if (!found) break;

            double pathFlow = Double.MAX_VALUE;
            for (int v = t; v != s; v = parent[v]) {
                int u = parent[v];
                Edge e = null;
                for (Edge edge : adj.get(u)) {
                    if (edge.to == v) {
                        e = edge;
                        break;
                    }
                }
                pathFlow = Math.min(pathFlow, e.capacity - e.flow);
            }

            for (int v = t; v != s; v = parent[v]) {
                int u = parent[v];
                Edge e = null;
                Edge rev = null;
                for (int i = 0; i < adj.get(u).size(); i++) {
                    if (adj.get(u).get(i).to == v) {
                        e = adj.get(u).get(i);
                        rev = adj.get(v).get(e.revIdx);
                        break;
                    }
                }
                e.flow += pathFlow;
                rev.flow -= pathFlow;
            }

            maxFlow += pathFlow;
        }

        return maxFlow;
    }
}