package duxtennis.views;

import duxtennis.Main;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import net.miginfocom.swing.MigLayout;

/*
Live match view class.
*/
public class LiveMatchView extends MainView {

//Private constants
private static final int TABLE_COLUMNS = 5;
private static final int TABLE_ROWS = 3;

private static final String FRAME_TITLE = "Partido";

private static final String[] TABLE_TITLES = {
    "SAQUE", "SETS", "GAMES", "PUNTOS"
};

//Private fields
private JLabel tournamentNameLabel;
private JPanel panel;
private JTable table;

//Constructor
/*
Creates the current match progression view.
*/
public LiveMatchView() {
    initializeInterface();
  }

//Public methods
/*
Initializes the view and makes it visible.
*/
@Override
public void initializeInterface() {
    panel = new JPanel(new MigLayout("wrap"));

    addTable();
    addLabel();
    add(panel);
    setResizable(false);
    setTitle(FRAME_TITLE);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setIconImage(Main.ICON
                     .getImage());
  }

//Getters
/*
Gets the tournament name label.
--return: The tournament name label.
*/
public JLabel getTournamentNameLabel() {
    return tournamentNameLabel;
  }

/*
Gets the results table.
--return: The results table.
*/
public JTable getTable() {
    return table;
  }

//Protected methods
/*
Adds the buttons to the panel.
*/
@Override
protected void addButtons() {
    }

//Private methods
/*
Adds the current match results table to the panel.
*/
private void addTable() {
  tournamentNameLabel = new JLabel();

  tournamentNameLabel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
  tournamentNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
  tournamentNameLabel.setBackground(Main.LIGHT_GREEN);
  tournamentNameLabel.setOpaque(true);

  panel.add(tournamentNameLabel, "growx, span");

  table = new JTable(TABLE_ROWS, TABLE_COLUMNS);

  setTableFormat();
  fillTableFields();

  panel.add(table, "push, grow, span, center");
}
/*
Sets the table cells format, including text alignment,
style and background color.
*/
private void setTableFormat() {
  table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
  table.setCellSelectionEnabled(false);
  table.setRowSelectionAllowed(false);
  table.setColumnSelectionAllowed(false);
  table.setBorder(BorderFactory.createLineBorder(Color.BLACK));
  table.setEnabled(false);
  
  TableCellRenderer renderer = new DefaultTableCellRenderer() {
      @Override
      public Component getTableCellRendererComponent(JTable table,
                                                    Object value, 
                                                    boolean isSelected,
                                                    boolean hasFocus, 
                                                    int row, 
                                                    int column) {
          final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
          c.setBackground(row == 0 || column == 0 ? Main.GREEN_COLOR : Main.LIGHT_GREEN);
          c.setFont(c.getFont().deriveFont(row == 0 || column == 0 ? Font.BOLD : Font.PLAIN));
          ((DefaultTableCellRenderer) c).setHorizontalAlignment(SwingConstants.CENTER);
          return c;
      }
  };

  for (int column = 0; column < table.getColumnCount(); column++) {
      table.getColumnModel().getColumn(column).setPreferredWidth(Main.TABLE_CELLS_WIDTH);
      table.getColumnModel().getColumn(column).setCellRenderer(renderer);
  }
}


/*
Fills the table cells whose texts do not change.
*/
private void fillTableFields() {
    for (int i = 0; i < TABLE_TITLES.length; i++) {
      table.setValueAt(TABLE_TITLES[i], 0, i + 1);
    }
  }

/*
Adds the informative label to the panel.
*/
private void addLabel() {
  JLabel label = new JLabel("Partido en curso...");
  label.setOpaque(true);
  label.setBackground(Main.LIGHT_GREEN);
  label.setBorder(BorderFactory.createLoweredSoftBevelBorder());
  label.setHorizontalAlignment(SwingConstants.CENTER);
  panel.add(label, "growx, span");
}
}

