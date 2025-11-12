// src/main/java/com/example/algoritmos/Dinic.java
package com.example.algoritmos;

import com.example.grafos.Grafo;
import com.example.grafos.Aresta;
import java.util.*;

public class Dinic {

    private static class Edge {
        int to, rev;
        double cap, flow;

        Edge(int to, int rev, double cap) {
            this.to = to;
            this.rev = rev;
            this.cap = cap;
            this.flow = 0;
        }
    }

    private List<List<Edge>> adj;
    private int[] level, iter;
    private int V;

    private void bfs(int s) {
        level = new int[V + 1];
        Arrays.fill(level, -1);
        Queue<Integer> q = new LinkedList<>();
        level[s] = 0;
        q.add(s);
        while (!q.isEmpty()) {
            int u = q.poll();
            for (Edge e : adj.get(u)) {
                if (e.cap - e.flow > 1e-9 && level[e.to] < 0) {
                    level[e.to] = level[u] + 1;
                    q.add(e.to);
                }
            }
        }
    }

    private double dfs(int u, int t, double f) {
        if (u == t) return f;
        for (int i = iter[u]; i < adj.get(u).size(); i++) {
            iter[u] = i;
            Edge e = adj.get(u).get(i);
            if (e.cap - e.flow > 1e-9 && level[u] < level[e.to]) {
                double d = dfs(e.to, t, Math.min(f, e.cap - e.flow));
                if (d > 1e-9) {
                    e.flow += d;
                    adj.get(e.to).get(e.rev).flow -= d;
                    return d;
                }
            }
        }
        return 0;
    }

    public double maxFlow(Grafo grafo, int s, int t) {
        V = grafo.getNumeroVertices();
        adj = new ArrayList<>(V + 1);
        for (int i = 0; i <= V; i++) adj.add(new ArrayList<>());

        // Construir grafo residual
        for (int u = 1; u <= V; u++) {
            for (Aresta a : grafo.getAdjacencias().get(u)) {
                int v = a.getDestino();
                double cap = a.getPeso();
                if (cap <= 0) continue;

                Edge e1 = new Edge(v, adj.get(v).size(), cap);
                Edge e2 = new Edge(u, adj.get(u).size(), 0);
                adj.get(u).add(e1);
                adj.get(v).add(e2);
            }
        }

        double flow = 0;
        level = new int[V + 1];
        iter = new int[V + 1];

        while (true) {
            bfs(s);
            if (level[t] < 0) break;
            Arrays.fill(iter, 0);
            double f;
            while ((f = dfs(s, t, Double.MAX_VALUE)) > 1e-9) {
                flow += f;
            }
        }
        return flow;
    }
}