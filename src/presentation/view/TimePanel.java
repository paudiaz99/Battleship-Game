package presentation.view;

import javax.swing.*;
import java.awt.*;

/**
 * Panel that shows the time and the traffic light during the game.
 */
public class TimePanel extends JPanel {
    /**
     * Circle panel that represents the red traffic light.
     */
    private final CirclePanel redCircle;
    /**
     * Circle panel that represents the green traffic light.
     */
    private final CirclePanel greenCircle;
    /**
     * Label that shows the time.
     */
    private final JLabel timeLabel;

    /**
     * Constructor of the class. It sets the layout of the panel and adds the circle panel and the label panel.
     */
    public TimePanel() {
        this.setOpaque(false);
        setLayout(new FlowLayout());

         // Agregar el panel al centro del panel principal

        JPanel circlePanel = new JPanel(new FlowLayout()); // Crear un panel para los círculos
        circlePanel.setBackground(Color.YELLOW);
        this.redCircle = new CirclePanel(Color.RED);
        this.greenCircle = new CirclePanel(Color.GREEN);
        circlePanel.add(greenCircle); // Agregar el panel del círculo rojo al panel de círculos
        circlePanel.add(redCircle); // Agregar el panel del círculo verde al panel de círculos
        circlePanel.setPreferredSize(new Dimension(68,36));
        circlePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        timeLabel = new JLabel("00:00"); // Crear una etiqueta para mostrar la hora
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 25)); // Establecer la fuente de la etiqueta
         // Alinear la etiqueta a la izquierda
        JPanel labelPanel = new JPanel(new BorderLayout()); // Crear un panel para la etiqueta
        labelPanel.setBackground(Color.WHITE); // Establecer el fondo del panel
        labelPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3)); // Establecer el borde del panel
        labelPanel.add(timeLabel); // Agregar la etiqueta al oeste del panel de etiquetas

        add(circlePanel);
        add(labelPanel);
    }

    /**
     * Reset the time label to 00:00.
     */
    public void resetTimer() {
        timeLabel.setText("00:00");
        timeLabel.repaint();
        timeLabel.revalidate();
    }

    /**
     * Change the color of the circle to red.
     */
    public void redLight() {
        this.greenCircle.changeColor(Color.GRAY);
        this.redCircle.changeColor(Color.RED);
    }

    /**
     * @param formattedTime Time to show in the label.
     *                      Update the time label.
     */
    public void updateTime(String formattedTime) {
        timeLabel.setText(formattedTime);
    }

    /**
     * Change the color of the circle to green.
     */
    public void greenLight() {
        this.greenCircle.changeColor(Color.GREEN);
        this.redCircle.changeColor(Color.GRAY);
    }
}
