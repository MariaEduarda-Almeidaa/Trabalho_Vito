package com.example.algoritmos;

import com.example.grafos.Aresta;
import com.example.grafos.Grafo;

import java.util.LinkedList;
import java.util.Queue;

public class EdmondsKarp {

    /**
    
     * @param residual 
     * @param s 
     * @param t 
     * @param pai 
     * @return 
     */
    private static boolean bfs(double[][] residual, int s, int t, int[] pai) {
        int n = residual.length - 1;
        boolean[] visitado = new boolean[n + 1];
        Queue<Integer> fila = new LinkedList<>();

        fila.add(s);
        visitado[s] = true;
        pai[s] = -1; 
        while (!fila.isEmpty()) {
            int u = fila.poll();
            for (int v = 1; v <= n; v++) {
                if (!visitado[v] && residual[u][v] > 0) {
                    fila.add(v);
                    visitado[v] = true;
                    pai[v] = u; 
                }
            }
        }
        return visitado[t];
    }

    /**
     *
     * @param g 
     * @param s 
     * @param t 
     * @return 
     */
    public static double executar(Grafo g, int s, int t) {
        int n = g.getNumeroVertices();

        double[][] residual = new double[n + 1][n + 1];
        
        
        for (int u = 1; u <= n; u++) {
            for (Aresta a : g.getAdjacencias().get(u)) {
                residual[u][a.getDestino()] += a.getPeso();
            }
        }

        int[] pai = new int[n + 1]; 
        double fluxoMaximo = 0;

        
        while (bfs(residual, s, t, pai)) {
            
            double fluxoCaminho = Double.POSITIVE_INFINITY;
            for (int v = t; v != s; v = pai[v]) {
                int u = pai[v];
                fluxoCaminho = Math.min(fluxoCaminho, residual[u][v]);
            }

            
            for (int v = t; v != s; v = pai[v]) {
                int u = pai[v];
                residual[u][v] -= fluxoCaminho; 
                residual[v][u] += fluxoCaminho; 
            }

            
            fluxoMaximo += fluxoCaminho;
        }

        return fluxoMaximo;
    }
}