package presentation.view;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Class that extends JPanel and shows the graphic stats of the player.
 */
public class GraphicStats extends JPanel {
    private final Color lossesColor = new Color(220, 20, 60);
    private final Color winsColor = new Color(50, 205, 50);
    private final JBarraPanel jBarraPanel;
    private int totalGames = 3;
    private int wins = 1;
    private ArrayList<Point> data;

    /**
     * Constructor of the class. It creates a new JPanel with a JBarraPanel and a legend.
     */
    public GraphicStats() {
        this.setOpaque(false);
        this.jBarraPanel = new JBarraPanel();
        setLayout(new BorderLayout());
        this.add(jBarraPanel, BorderLayout.NORTH);
        legend();
        this.data = new ArrayList<>();
    }

    /**
     * Method that sets the total games played by the player. It also updates the graphic.
     */
    private void legend() {
        JLabel title = new JLabel("Player Win Rate ("+wins+"/"+totalGames+")");
        title.setForeground(Color.WHITE);
        JLabel title2 = new JLabel("Games by Movements");
        title2.setForeground(Color.WHITE);
        Font font2 = new Font("Arial", Font.BOLD, 40);
        JLabel noGames = new JLabel("NO GAMES PLAYED YET :(");
        noGames.setForeground(Color.WHITE);
        noGames.setFont(font2);
        title.setBorder(BorderFactory.createEmptyBorder(0,0,600,0));
        title2.setBorder(BorderFactory.createEmptyBorder(0,0,600,230));
        title.setFont(font2);
        title2.setFont(font2);
        JPanel legend = new JPanel();
        legend.setOpaque(false);
        legend.setLayout(new BoxLayout(legend, BoxLayout.Y_AXIS));

        Font font = new Font("Arial", Font.BOLD, 20);
        int borderThickness = 5;
        LineBorder border = new LineBorder(Color.BLACK, borderThickness);
        legend.setBorder(border);
        JLabel winNumber = new JLabel("Wins: "+wins);
        winNumber.setForeground(Color.WHITE);
        winNumber.setForeground(winsColor);
        winNumber.setFont(font);
        JLabel loseNumber = new JLabel("Defeats: "+(totalGames-wins));
        loseNumber.setForeground(Color.WHITE);
        loseNumber.setForeground(lossesColor);
        loseNumber.setFont(font);
        JLabel gamesNumber = new JLabel("Total Games: "+totalGames);
        gamesNumber.setForeground(Color.WHITE);
        gamesNumber.setFont(font);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        JLabel winRate = new JLabel("Win Rate: "+ decimalFormat.format((((double)wins/totalGames)*100))+"%");
        winRate.setForeground(Color.WHITE);
        winRate.setFont(font);
        JLabel loseRate = new JLabel("Lose Rate: "+ decimalFormat.format((((double)totalGames-wins)/totalGames)*100)+"%");
        loseRate.setFont(font);
        loseRate.setForeground(Color.WHITE);

        legend.add(winNumber);
        legend.add(loseNumber);
        legend.add(gamesNumber);
        legend.add(winRate);
        legend.add(loseRate);
        legend.setBorder(BorderFactory.createEmptyBorder(550,20,0,0));
        noGames.setBorder(BorderFactory.createEmptyBorder(0,440,130,0));

        if (totalGames == 0){
            winRate = new JLabel("Win Rate: 0.00%");
            winRate.setFont(font);
            winRate.setForeground(Color.WHITE);
            loseRate = new JLabel("Lose Rate: 0.00%");
            loseRate.setForeground(Color.WHITE);
            loseRate.setFont(font);
            noGames.setVisible(true);
            this.add(noGames, BorderLayout.CENTER);
        }
        else{
            noGames.setVisible(false);
            this.add(title);
            this.add(title2,BorderLayout.EAST);
            this.add(legend,BorderLayout.WEST);
        }

    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        double winRate = (wins * 100.0) / totalGames;
        double loseRate = 100.0 - winRate;

        int centerX = getWidth() / 4;
        int centerY = getHeight() / 2;
        int radius = Math.min(getWidth(), getHeight()) / 4;

        g.setColor(winsColor);
        int startAngle = 0;
        int arcAngle = (int) (winRate * 3.6);
        g.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2, startAngle, arcAngle);

        g.setColor(lossesColor);
        startAngle += arcAngle;
        arcAngle = (int) (loseRate * 3.6);
        g.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2, startAngle, arcAngle);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = 600;
        int height = 500;
        int yOffset = 175;
        int xOffset = 800;

        Font boldFont = new Font(g.getFont().getName(), Font.BOLD, g.getFont().getSize());
        int numBars = data.size();

        int barWidth = 50;

        int totalBarWidth = (barWidth + 20) * numBars;
        int startX = (width - totalBarWidth) / 2;

        if (startX < 0) {
            startX = 0;
            barWidth = (width - 20 * numBars) / numBars;
        }

        for (int i = 0; i < numBars; i++) {
            int x = startX + i * (barWidth+20);
            int barHeight = (data.get(i).y * (height - 20) / getMaxYValue());

            g.setColor(Color.WHITE);
            g.fillRect(x +xOffset, height - barHeight - 10 + yOffset, barWidth, barHeight);
            g.setFont(boldFont);
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(data.get(i).x), x +xOffset + barWidth / 2, height + yOffset);
            g.drawString(String.valueOf(data.get(i).y), x +xOffset+ barWidth / 2, height - barHeight - 15 + yOffset);

        }

    }

    private int getMaxYValue() {
        int maxY = Integer.MIN_VALUE;
        for (Point point : data) {
            if (point.y > maxY) {
                maxY = point.y;
            }
        }
        return maxY;
    }


    /**
     * @param stats the stats to be set
     *              stats[0] = wins
     *              stats[1] = totalGames
     */
    public void setStats(int[] stats, ArrayList<Point> data) {
        this.totalGames = stats[1];
        this.wins = stats[0];
        this.removeAll();
        this.add(jBarraPanel, BorderLayout.NORTH);
        legend();
        this.repaint();
        this.data = new ArrayList<>();
        this.data.addAll(data);
        this.data.sort(Comparator.comparingInt(o -> o.x));
    }

    /**
     * @param actionListener the action listener to be registered
     */
    public void addActionListener(ActionListener actionListener){
        jBarraPanel.registerListener(actionListener);
    }

    /**
     * @return true if the user wants to log out, false otherwise
     */
    public boolean logOutConfirmation() {
        return jBarraPanel.logOut() == JOptionPane.YES_OPTION;
    }
}
