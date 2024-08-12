package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Um JPanel personalizado que exibe uma imagem como plano de fundo.
 *
 * Esta classe estende JPanel e substitui o método paintComponent para desenhar a imagem de fundo.
 * A imagem é dimensionada para ajustar o tamanho do painel.
 */
public class BackgroundPanel extends JPanel {
    /**
     * A imagem de fundo a ser exibida.
     */
    private final Image backgroundImage;

    /**
     * Constrói um novo BackgroundPanel com a imagem de fundo fornecida.
     *
     * @param image A imagem de fundo a ser exibida.
     */
    public BackgroundPanel(Image image) {
        this.backgroundImage = image;
    }

    /**
     * Substitui o método paintComponent para desenhar a imagem de fundo.
     *
     * @param g O objeto Graphics usado para desenhar.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
