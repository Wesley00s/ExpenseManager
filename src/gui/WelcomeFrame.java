package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Uma Janela de Frame personalizada que exibe uma tela de boas-vindas com um plano de fundo.
 * Esta janela apresenta uma mensagem de boas-vindas, informações sobre o aplicativo,
 * e um botão "Continuar" para iniciar a interface principal.
 */
public class WelcomeFrame extends JFrame {

    /**
     * Construtor que inicializa as propriedades da janela,
     * como título, tamanho, localização, e operação de fechamento padrão.
     */
    public WelcomeFrame() {
        setTitle("Bem-vindo ao Gerenciador de Despesas");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        BackgroundPanel backgroundPanel = createBackgroundPanel();

        JPanel contentPanel = createContentPanel();

        backgroundPanel.add(contentPanel, BorderLayout.CENTER);

        setContentPane(backgroundPanel);
        setVisible(true);
    }

    /**
     * Cria um novo painel de plano de fundo com a imagem de fundo fornecida.
     *
     * @return O painel de plano de fundo criado.
     */
    private BackgroundPanel createBackgroundPanel() {
        ImageIcon bg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/bg.png")));
        Image imgBg = bg.getImage();
        BackgroundPanel backgroundPanel = new BackgroundPanel(imgBg);
        backgroundPanel.setLayout(new BorderLayout());
        return backgroundPanel;
    }

    /**
     * Cria um novo painel de conteúdo que contém a label de boas-vindas,
     * a área de texto de informações, e o painel de botões.
     *
     * @return O painel de conteúdo criado.
     */
    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel welcomeLabel = createWelcomeLabel();
        JTextArea infoArea = createInfoArea();
        JPanel buttonPanel = createButtonPanel();

        contentPanel.add(welcomeLabel, BorderLayout.NORTH);
        contentPanel.add(infoArea, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        return contentPanel;
    }

    /**
     * Cria uma nova label com a mensagem de boas-vindas.
     *
     * @return A label de boas-vindas criada.
     */
    private JLabel createWelcomeLabel() {
        JLabel welcomeLabel = new JLabel("Bem-vindo ao Gerenciador de Despesas!");
        welcomeLabel.setFont(new Font("DejaVu Sans", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return welcomeLabel;
    }

    /**
     * Cria uma nova área de texto com informações sobre o aplicativo.
     *
     * @return A área de texto de informações criada.
     */
    private JTextArea createInfoArea() {
        JTextArea infoArea = new JTextArea();
        infoArea.setText(
                """
                
                
                Este programa permite a você gerenciar suas despesas de maneira fácil e eficiente.

                Funcionalidades principais:
                - Adicionar novos gastos, especificando a descrição, valor, data e categoria.
                - Remover gastos selecionados da lista.
                - Agrupar suas despesas por categoria para uma melhor visualização.
                - Visualizar o total gasto diretamente na interface principal.

                Para começar, utilize os campos e botões na interface principal.
                Espero que esta ferramenta ajude você a manter suas finanças em ordem!
                """
        );
        infoArea.setFont(new Font("DejaVu Sans", Font.ITALIC, 16));
        infoArea.setForeground(Color.WHITE);
        infoArea.setOpaque(false);
        infoArea.setEditable(false);
        infoArea.setWrapStyleWord(true);
        infoArea.setLineWrap(true);
        return infoArea;
    }

    /**
     * Cria um novo painel de botões com um botão "Continuar".
     *
     * @return O painel de botões criado.
     */
    private JPanel createButtonPanel() {
        JButton continueButton = new JButton("Continuar");
        continueButton.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
        continueButton.setBackground(new Color(0, 0, 0, 0));
        continueButton.setForeground(Color.WHITE);
        continueButton.setFocusPainted(false);

        continueButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                ExpenseManager mainFrame = new ExpenseManager();
                mainFrame.setVisible(true);
            });
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(continueButton);
        return buttonPanel;
    }
}
