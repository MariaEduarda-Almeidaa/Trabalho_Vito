package com.example;


import com.example.algoritmos.EdmondsKarp;
import com.example.algoritmos.Dijkstra;
import com.example.algoritmos.Kruskal;
import com.example.algoritmos.Prim;
import com.example.grafos.*;
import com.example.utils.LeitorGrafo;
import com.example.utils.GeradorCSV;

public class Main {
    public static void main(String[] args) throws Exception {
        String[] instancias = {
            "arquivos/USA-road-d.NY.gr",
            "arquivos/USA-road-d.BAY.gr",
            "arquivos/USA-road-d.COL.gr"
        };

        GeradorCSV csv = new GeradorCSV("resultados.csv");

        
        final int LIMITE_VERTICES_FM = 50000;

        for (String caminho : instancias) {
            System.out.println("\n==> Processando: " + caminho);
            long inicio, fim;

            Grafo g_CM = LeitorGrafo.lerArquivo(caminho, true);
            Grafo g_AGM = LeitorGrafo.lerArquivo(caminho, false);

            int n = g_CM.getNumeroVertices();
            int m = g_CM.getNumeroArestas();

            inicio = System.nanoTime();
            double[] dist = Dijkstra.executar(g_CM, 1);
            fim = System.nanoTime();
            double cmTempo = (fim - inicio) / 1e9;
            double cmCusto = Dijkstra.custoTotal(dist);

            inicio = System.nanoTime();
            double kCusto = Kruskal.executar(g_AGM);
            fim = System.nanoTime();
            double kTempo = (fim - inicio) / 1e9;

            inicio = System.nanoTime();
            double pCusto = Prim.executar(g_AGM);
            fim = System.nanoTime();
            double pTempo = (fim - inicio) / 1e9;

            double fmCusto = 0;
            double fmTempo = 0;

            if (n > LIMITE_VERTICES_FM) {
                
                System.out.printf("FM: N/A (Grafo com n=%d excede limite de %d para matriz O(V^2))\n", n, LIMITE_VERTICES_FM);
            } else {
                
                int s = 1;
                int t = n;
                
                try {
                    inicio = System.nanoTime();
                    fmCusto = EdmondsKarp.executar(g_CM, s, t);
                    fim = System.nanoTime();
                    fmTempo = (fim - inicio) / 1e9;
                    System.out.printf("FM: custo=%.2f tempo=%.3fs\n", fmCusto, fmTempo);
                } catch (OutOfMemoryError oom) {
                    Systemout.println("FM: OutOfMemoryError durante cálculo - marcando como N/A");
                    fmCusto = 0;
                    fmTempo = 0;
                } catch (Exception e) {
                    System.out.println("FM: Erro durante cálculo - " + e.getMessage());
                    fmCusto = 0;
                    fmTempo = 0;
                }
            }

            double agmCusto = kCusto;
            double agmTempo = Math.min(kTempo, pTempo);
            csv.adicionarLinha(n, m, cmCusto, cmTempo, agmCusto, agmTempo, fmCusto, fmTempo);

            System.out.printf("CM: custo=%.2f tempo=%.3fs\n", cmCusto, cmTempo);
            System.out.printf("Kruskal: custo=%.2f tempo=%.3fs\n", kCusto, kTempo);
            System.out.printf("Prim: custo=%.2f tempo=%.3fs\n", pCusto, pTempo);
        }

        csv.salvar();
        System.out.println("\nTabela salva em resultados.csv ✅");

        System.out.println("Abrindo visualizador...");
        javax.swing.SwingUtilities.invokeLater(() -> {
            new com.example.utils.VisualizadorTabela("resultados.csv");
        });
    }
}