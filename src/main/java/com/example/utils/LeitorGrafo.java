package com.example.utils;

import com.example.grafos.Grafo;
import java.io.*;

public class LeitorGrafo {

    /**
     * @param caminho 
     * @param isDirigido 
     * @return 
     * @throws IOException
     */
    public static Grafo lerArquivo(String caminho, boolean isDirigido) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(caminho));
        String linha;
        int n = 0;
        Grafo g = null;

        while ((linha = br.readLine()) != null) {
            if (linha.startsWith("c") || linha.isBlank()) continue;
            String[] partes = linha.split(" ");
            
            if (linha.startsWith("p")) {
                n = Integer.parseInt(partes[2]);
                g = new Grafo(n);
            } else if (linha.startsWith("a")) {
                int u = Integer.parseInt(partes[1]);
                int v = Integer.parseInt(partes[2]);
                double peso = Double.parseDouble(partes[3]);
                
                
                if (isDirigido) {
                    g.adicionarArestaDirigida(u, v, peso);
                } else {
                    g.adicionarArestaNaoDirigida(u, v, peso);
                }
            }
        }
        br.close();
        return g;
    }
}