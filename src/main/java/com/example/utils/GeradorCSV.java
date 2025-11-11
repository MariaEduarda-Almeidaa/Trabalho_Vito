package com.example.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class GeradorCSV {
    private final String caminho;
    private final StringBuilder sb = new StringBuilder();

    public GeradorCSV(String caminho) {
        this.caminho = caminho;
        sb.append("n,m,CM_custo,CM_tempo,AGM_custo,AGM_tempo,FM_custo,FM_tempo\n");
    }

    /**
     * * @param n 
     * @param m 
     * @param cmCusto 
     * @param cmTempo 
     * @param agmCusto 
     * @param agmTempo 
     * @param fmCusto 
     * @param fmTempo 
     */
    public void adicionarLinha(int n, int m, double cmCusto, double cmTempo,
        double agmCusto, double agmTempo, double fmCusto, double fmTempo) {
        
    
    sb.append(String.format(Locale.US, "%d,%d,%.2f,%.6f,%.2f,%.6f,%.2f,%.6f\n",
        n, m, cmCusto, cmTempo, agmCusto, agmTempo, fmCusto, fmTempo));
    }

    public void salvar() throws IOException {
        try (FileWriter fw = new FileWriter(caminho)) {
            fw.write(sb.toString());
        }
    }
}