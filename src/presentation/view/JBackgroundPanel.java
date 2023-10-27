package presentation.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A JPanel with a background image.
 */
public class JBackgroundPanel extends JPanel {

    private BufferedImage image;

    /**
     * @param path The path to the image to be used as background.
     *             Read the image from the file system.
     */
    public JBackgroundPanel(String path) {
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return The preferred size of the panel, calculated to maintain the aspect ratio of the image.
     */
    @Override
    public Dimension getPreferredSize() {
        Dimension preferred = super.getPreferredSize();

        float width = image.getWidth();
        float height = image.getHeight();

        // Calculate the height needed to maintain aspect ratio
        preferred.height = Math.round(getWidth() * height / width);

        return preferred;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}
