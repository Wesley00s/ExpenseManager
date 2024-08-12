package gui;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Um painel de interface gráfica para gerenciar gastos pessoais.
 * Este painel inclui campos de entrada para descrição, valor, data e categoria,
 * além de botões para adicionar, remover e agrupar gastos.
 * Também exibe uma tabela para mostrar os gastos e seus detalhes.
 */
public class InputPanel extends JPanel {
    private final JTextField descriptionField;
    private final JTextField amountField;
    private final JTextField dateField;
    private final JComboBox<String> categoryComboBox;
    private final DefaultTableModel tableModel;

    /**
     * Constrói uma nova instância de InputPanel com o modelo de tabela fornecido.
     *
     * @param tableModel o modelo de tabela para exibir os gastos
     */
    public InputPanel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;

        ImageIcon bg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/bg.png")));
        Image imgBg = bg.getImage();

        BackgroundPanel backgroundPanel = new BackgroundPanel(imgBg);
        backgroundPanel.setLayout(new GridBagLayout());

        UIManager.put("Label.font", new Font("DejaVu Sans", Font.BOLD, 15));
        UIManager.put("Label.foreground", Color.WHITE);
        UIManager.put("TextField.font", new Font("DejaVu Sans", Font.PLAIN, 15));
        UIManager.put("Button.font", new Font("DejaVu Sans", Font.BOLD, 13));
        UIManager.put("ComboBox.font", new Font("DejaVu Sans", Font.PLAIN, 15));
        UIManager.put("Button.background", new Color(70, 150, 95));
        UIManager.put("Button.foreground", Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        descriptionField = new JTextField(25);
        amountField = new JTextField(10);
        dateField = new JTextField(10);
        dateField.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        categoryComboBox = new JComboBox<>(new String[]{"Comida", "Transporte", "Entretenimento", "Outros"});
        JButton addButton = createAddButton();
        JButton removeButton = createRemoveButton();
        JButton groupButton = createGroupExpenseButton();

        gbc.gridx = 0; gbc.gridy = 1;
        backgroundPanel.add(new JLabel("Descrição: "), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        backgroundPanel.add(descriptionField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        backgroundPanel.add(new JLabel("Valor ($): "), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        backgroundPanel.add(amountField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
        backgroundPanel.add(new JLabel("Data: "), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        backgroundPanel.add(dateField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1;
        backgroundPanel.add(new JLabel("Categoria: "), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        backgroundPanel.add(categoryComboBox, gbc);

        gbc.gridx = 1; gbc.gridy = 5; gbc.gridwidth = 1;
        backgroundPanel.add(addButton, gbc);
        gbc.gridx = 2;
        backgroundPanel.add(removeButton, gbc);
        gbc.gridx = 1; gbc.gridy = 6; gbc.gridwidth = 2;
        backgroundPanel.add(groupButton, gbc);

        setLayout(new BorderLayout());
        add(backgroundPanel, BorderLayout.CENTER);
    }

    /**
     * Cria e retorna um novo botão para adicionar gastos.
     * O botão adiciona um novo gasto à tabela quando clicado.
     *
     * @return o botão de adição
     */
    private JButton createAddButton() {
        JButton button = new JButton("Adicionar gasto");
        button.addActionListener(_ -> {
            String description = descriptionField.getText();
            String amountText = amountField.getText();
            String date = dateField.getText();
            String category = (String) categoryComboBox.getSelectedItem();

            try {
                amountText = amountText.replace(',', '.');
                double amount = Double.parseDouble(amountText);

                if (!description.isEmpty() && !date.isEmpty() && category != null) {
                    tableModel.insertRow(tableModel.getRowCount() - 1, new Object[]{description, String.format("%.2f", amount), date, category});
                    clearFields();
                    updateTotalAmount();
                } else {
                    JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "O valor inserido deve ser um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        return button;
    }

    /**
     * Cria e retorna um novo botão para remover gastos.
     * O botão remove o gasto selecionado da tabela quando clicado.
     *
     * @return o botão de remoção
     */
    private JButton createRemoveButton() {
        JButton button = new JButton("Remover gasto");
        button.addActionListener(_ -> {
            int opcao = JOptionPane.showConfirmDialog(
                    this,
                    "Tem certeza que deseja remover este registro?",
                    "Confirmação de Remoção",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (opcao == JOptionPane.YES_OPTION) {
                SwingUtilities.invokeLater(() -> {
                    int selectedRow = getTable().getSelectedRow();
                    if (selectedRow >= 0) {
                        if (selectedRow == tableModel.getRowCount() - 1) {
                            JOptionPane.showMessageDialog(this, "Não é possível remover a linha de total.", "Erro", JOptionPane.ERROR_MESSAGE);
                        } else {
                            tableModel.removeRow(selectedRow);
                            updateTotalAmount();
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Nenhum gasto selecionado para remoção.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                });
            }
        });
        return button;
    }

    /**
     * Cria e retorna um novo botão para agrupar gastos por categoria.
     * O botão exibe uma nova janela com uma tabela mostrando os gastos totais
     * agrupados por categoria quando clicado.
     *
     * @return o botão de agrupamento
     */
    private JButton createGroupExpenseButton() {
        JButton button = new JButton("Agrupar por categoria");
        button.addActionListener(_ -> {
            Map<String, Double> categoryTotals = new HashMap<>();

            for (int i = 0; i < tableModel.getRowCount() - 1; i++) {
                String category = (String) tableModel.getValueAt(i, 3);
                String amountText = tableModel.getValueAt(i, 1).toString().replace(',', '.');
                double amount = Double.parseDouble(amountText);

                categoryTotals.put(category, categoryTotals.getOrDefault(category, 0.0) + amount);
            }

            DefaultTableModel groupedTableModel = new DefaultTableModel(new Object[]{"Categoria", "Total Gasto ($)"}, 0);
            categoryTotals.forEach((category, total) -> groupedTableModel.addRow(new Object[]{category, String.format("%.2f", total)}));

            JTable groupedTable = new JTable(groupedTableModel);
            JScrollPane scrollPane = new JScrollPane(groupedTable);

            JFrame groupedFrame = new JFrame("Gastos Agrupados por Categoria");
            groupedFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            groupedFrame.setSize(500, 300);
            groupedFrame.add(scrollPane);
            groupedFrame.setLocationRelativeTo(this);
            groupedFrame.setVisible(true);        });
        return button;
    }

    /**
     * Limpa os campos de entrada de descrição, valor, data e categoria.
     */
    private void clearFields() {
        descriptionField.setText("");
        amountField.setText("");
        dateField.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        categoryComboBox.setSelectedIndex(0);
    }

    /**
     * Atualiza o valor total exibido na tabela.
     * O valor total é calculado somando os valores de todos os gastos.
     */
    private void updateTotalAmount() {
        double total = 0;
        for (int i = 0; i < tableModel.getRowCount() - 1; i++) {
            try {
                String amountText = tableModel.getValueAt(i, 1).toString().replace(',', '.');
                double amount = Double.parseDouble(amountText);
                total += amount;
            } catch (NumberFormatException e) {
                System.err.println("Erro ao converter o valor para double: " + tableModel.getValueAt(i, 1));
            }
        }
        tableModel.setValueAt(String.format("%.2f", total), tableModel.getRowCount() - 1, 1);
    }

    /**
     * Recupera a tabela associada ao InputPanel.
     *
     * @return a tabela associada ao InputPanel
     */
    private JTable getTable() {
        Container parent = this.getParent();
        while (!(parent instanceof JFrame)) {
            parent = parent.getParent();
        }
        return ((ExpenseManager) parent).getTable();
    }
}
