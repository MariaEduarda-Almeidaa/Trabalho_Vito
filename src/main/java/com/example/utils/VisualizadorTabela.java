package com.example.utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class VisualizadorTabela extends JFrame {

    // --- Início das Configurações de Estilo ---
    
    // Fonte geral (aumentada)
    private final Font fonteGeral = new Font("Arial", Font.PLAIN, 15);
    // Fonte do cabeçalho (negrito e aumentada)
    private final Font fonteHeader = new Font("Arial", Font.BOLD, 16);
    
    // Cor do cabeçalho (azul mais forte)
    private final Color corHeaderFundo = new Color(65, 105, 225); // RoyalBlue
    // Cor do texto do cabeçalho (branco para contrastar)
    private final Color corHeaderText = Color.WHITE;
    
    // Cores alternadas das linhas
    private final Color corLinhaPar = Color.WHITE;
    private final Color corLinhaImpar = new Color(240, 240, 255); // Um cinza-azulado bem claro
    
    // Altura da linha (para acomodar a fonte maior)
    private final int alturaLinha = 22;

    // --- Fim das Configurações de Estilo ---

    public VisualizadorTabela(String caminhoArquivo) {
        // Configurações da Janela
        super("Resultados dos Algoritmos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 400); // Aumentei um pouco a largura
        setLocationRelativeTo(null); 

        // Modelo da tabela (Dados)
        DefaultTableModel modelo = new DefaultTableModel();

        // --- AQUI A MÁGICA ACONTECE ---
        
        // 1. Criamos uma JTable customizada para "zebrar" as linhas
        JTable tabela = new JTable(modelo) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                
                // Aplica a cor alternada se a linha não estiver selecionada
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? corLinhaPar : corLinhaImpar);
                }
                
                // Formata números com pontuação de separação (fração)
                if (c instanceof JLabel) {
                    JLabel label = (JLabel) c;
                    String texto = label.getText();
                    try {
                        double valor = Double.parseDouble(texto);
                        // Formata com separador de milhares (ponto) e casas decimais (ponto)
                        label.setText(String.format("%,.2f", valor)); // Padrão americano: 1,234,567.89
                        label.setHorizontalAlignment(JLabel.CENTER);
                    } catch (NumberFormatException e) {
                        // Se não for número, deixa como está
                        if (c instanceof JLabel) {
                            ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
                        }
                    }
                }
                
                return c;
            }
        };

        // 2. Aplicamos a fonte e altura da linha
        tabela.setFont(fonteGeral);
        tabela.setRowHeight(alturaLinha);

        // 3. Estilizamos o Cabeçalho (Header)
        JTableHeader header = tabela.getTableHeader();
        header.setFont(fonteHeader);
        header.setBackground(corHeaderFundo);
        header.setForeground(corHeaderText);
        header.setReorderingAllowed(false); // Impede de arrastar colunas

        // --- Fim da Mágica ---

        // Ler o CSV e preencher a tabela (mesmo código de antes)
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

        // Adiciona a tabela em uma barra de rolagem
        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);

        // Mostra a janela
        setVisible(true);
    }
}