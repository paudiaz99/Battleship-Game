package presentation.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Objects;

/**
 * This class represents the table with the state of the ships.
 */
public class JStateTable extends JPanel {
    private static final String IN_THE_BASE = "IN THE BASE";
    private DefaultTableModel tableModel;
    private final Object[][] data;

    /**
     * Creates a table with the state of the ships.
     */
    public JStateTable() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.tableModel = new DefaultTableModel();
        this.setOpaque(false);
        String state1 = "<html><b><font color='red'>"+JStateTable.IN_THE_BASE+"</font></b></html>";
        String state2 = "<html><b><font color='red'>"+JStateTable.IN_THE_BASE+"</font></b></html>";
        String state3 = "<html><b><font color='red'>I"+JStateTable.IN_THE_BASE+"</font></b></html>";
        String state4 = "<html><b><font color='red'>"+JStateTable.IN_THE_BASE+"</font></b></html>";
        String state5 = "<html><b><font color='red'>"+JStateTable.IN_THE_BASE+"</font></b></html>";
        // Create table content
        this.data = new Object[][] {
                {"<html><b>Submarine 1</b></html>", state1},
                {"<html><b>Submarine 2</b></html>", state2},
                {"<html><b>Boat</b></html>", state3},
                {"<html><b>Destroyer</b></html>", state4},
                {"<html><b>Aircraft Carrier</b></html>", state5}
        };
        // Create column titles
        Object[] columnNames = {"<html><b>Ship</b></html>", "<html><b>State</b></html>"};

        // Create model of the table and add it to the table
        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        table.setFocusable(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.setOpaque(false);

        // Add table to the scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false);


        this.add(scrollPane);
        this.add(Box.createVerticalGlue());

    }

    /**
     * @param i    index of the ship
     * @param state state of the ship
     *              Updates the state of the ship in the table
     */
    public void setState(int i, String state){
        if(Objects.equals(state, "IN THE BASE")){
            state = "<html><b><font color='red'>" + state + "</font></b></html>";
        }
        if(Objects.equals(state, "POSITIONED")){
            state = "<html><b><font color='green'>" + state + "</font></b></html>";
        }
        if(Objects.equals(state, "SUNK")){
            state = "<html><b><font color='red'>" + state + "</font></b></html>";
        }
        if(Objects.equals(state, "FLOATING")){
            state = "<html><b><font color='green'>" + state + "</font></b></html>";
        }

        // Updates the table
        tableModel.setValueAt(state, i, 1);
    }
}