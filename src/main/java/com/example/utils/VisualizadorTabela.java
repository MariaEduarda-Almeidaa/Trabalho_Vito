package com.example.utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.Locale;
import java.io.BufferedReader;
import java.io.FileReader;

public class VisualizadorTabela extends JFrame {

    
    private final Font fonteGeral = new Font("Arial", Font.PLAIN, 15);
    
    private final Font fonteHeader = new Font("Arial", Font.BOLD, 16);
    
    
    private final Color corHeaderFundo = new Color(65, 105, 225); 
    
    private final Color corHeaderText = Color.WHITE;
    
    private final Color corLinhaPar = Color.WHITE;
    private final Color corLinhaImpar = new Color(240, 240, 255); 
    
    
    private final int alturaLinha = 22;

  

    public VisualizadorTabela(String caminhoArquivo) {
       
        super("Resultados dos Algoritmos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 400); 
        setLocationRelativeTo(null); 

        
        DefaultTableModel modelo = new DefaultTableModel();

       
        JTable tabela = new JTable(modelo) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                
                
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? corLinhaPar : corLinhaImpar);
                }
                
                
                if (c instanceof JLabel) {
                    JLabel label = (JLabel) c;
                    String texto = label.getText();
                    try {
                        double valor = Double.parseDouble(texto);
                        
                        if (Double.isNaN(valor)) {
                            label.setText("N/A");
                        } else {

                            label.setText(String.format(Locale.US, "%,.2f", valor));
                        }
                        label.setHorizontalAlignment(JLabel.CENTER);
                    } catch (NumberFormatException e) {
                        label.setHorizontalAlignment(JLabel.CENTER);
                    }
                }
                
                return c;
            }
        };


        tabela.setFont(fonteGeral);
        tabela.setRowHeight(alturaLinha);

        
        JTableHeader header = tabela.getTableHeader();
        header.setFont(fonteHeader);
        header.setBackground(corHeaderFundo);
        header.setForeground(corHeaderText);
        header.setReorderingAllowed(false); 


        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                
                if (primeiraLinha) {
                    for (String coluna : dados) {
                        modelo.addColumn(coluna);
                    }
                    primeiraLinha = false;
                } else {
                    modelo.addRow(dados);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao ler CSV: " + e.getMessage());
        }

       
        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);

        
        setVisible(true);
    }
}