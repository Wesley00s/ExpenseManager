package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * A janela principal do aplicativo Gerenciador de Gastos.
 * Inicializa a janela com uma tabela para exibir gastos e um painel de entrada para adicionar novos gastos.
 */
public class ExpenseManager extends JFrame {

    /**
     * Constrói uma nova janela ExpenseManager.
     */
    public ExpenseManager() {
        setTitle("Gerenciador de Gastos");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Descrição", "Valor ($)", "Data", "Categoria"}, 0);
        JTable table = new JTable(tableModel);

        InputPanel inputPanel = new InputPanel(tableModel);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);

        tableModel.addRow(new Object[]{"Total", "0.00", "", ""});

        setVisible(true);
    }

    /**
     * Retorna o componente de tabela da janela ExpenseManager.
     *
     * @return O componente JTable que representa os gastos.
     */
    public JTable getTable() {
        return (JTable) ((JScrollPane) getContentPane().getComponent(0)).getViewport().getView();
    }
}
