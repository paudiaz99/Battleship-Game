package presentation.view;

import javax.swing.*;
import java.awt.*;

/**
 * JPanel that draws a circle with a given color. Used to build the traffic light.
 */
public class CirclePanel extends JPanel {
    /**
     * Color of the circle.
     */
    private Color color;

    /**
     * @param color Color of the circle.
     *              Constructor of the class. It sets the preferred size of the panel and the background as transparent.
     */
    public CirclePanel(Color color) {
        this.color = color;
        setPreferredSize(new Dimension(23, 23)); // Establecer el tamaño preferido del panel
        setBackground(new Color(0,0,0,0)); // Establecer el fondo del panel como transparente
    }

    /**
     * @param g Graphics object. Used to draw the circle.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int diameter = Math.min(getWidth(), getHeight()); // Calcular el diámetro del círculo como el tamaño mínimo entre el ancho y la altura del panel
        int x = (getWidth() - diameter) / 2; // Calcular la coordenada x del círculo para centrarlo horizontalmente
        int y = (getHeight() - diameter) / 2; // Calcular la coordenada y del círculo para centrarlo verticalmente
        g2d.setColor(color); // Establecer el color del círculo
        g2d.fillOval(x, y, diameter, diameter); // Dibujar el círculo
        g2d.setColor(Color.BLACK); // Establecer el color del borde del círculo
        g2d.setStroke(new BasicStroke(3));
        g2d.drawOval(x, y, diameter, diameter); // Dibujar el borde del círculo
    }

    /**
     * @param color Color to change to.
     *              Change the color of the circle.
     */
    public void changeColor(Color color) {
        this.color = color;
        repaint();
    }
}
