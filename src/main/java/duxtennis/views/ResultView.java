package duxtennis.views;

import duxtennis.Main;
import duxtennis.controllers.ResultController;
import duxtennis.models.Views;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import net.miginfocom.swing.MigLayout;

/*
Result view class.
*/
public class ResultView extends MainView {

  private static final String FRAME_TITLE = "Resultado del partido";

//Private fields
private JLabel titleLabel;
private JPanel panel;
private JTable table;

//Constructor 
/*
Builds the match result view.
*/
public ResultView() {
  }

//Public methods
/*
Initializes the view and makes it visible.
*/
@Override
public void initializeInterface() {
    panel = new JPanel(new MigLayout("wrap"));

    addLabel();
    addTable();
    addButtons();
    add(panel);
    setResizable(false);
    setTitle(FRAME_TITLE);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setIconImage(Main.ICON
                     .getImage());
  }

/*
Gets the title label.
--return: The title label.
*/
public JLabel getTitleLabel() {
    return titleLabel;
  }

/*Gets the results table.
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
    JButton rematchButton = new JButton("Revancha");
    JButton mainMenuButton = new JButton("Volver al menú principal");

    rematchButton.addActionListener(e ->
        ((ResultController) Main.getController(Views.RESULT)).rematchButtonEvent()
    );

    mainMenuButton.addActionListener(e ->
        ((ResultController) Main.getController(Views.RESULT)).mainMenuButtonEvent()
    );

    panel.add(rematchButton, "growx");
    panel.add(mainMenuButton, "growx");
  }

//Private methods
/*
Adds the title label to the panel.
*/
private void addLabel() {
    titleLabel = new JLabel();

    titleLabel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    titleLabel.setBackground(Main.LIGHT_GREEN);
    titleLabel.setOpaque(true);

    panel.add(titleLabel, "growx, span");
  }

/*
Adds the results table to the panel.
*/
private void addTable() {
    table = new JTable(2, Main.getMatch()
                              .getFinishedSets()
                              .size() + 1);

    setTableFormat();

    panel.add(table, "growx, span");
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

  DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
    @Override
    public Component getTableCellRendererComponent(JTable myTable, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
      final Component c = super.getTableCellRendererComponent(myTable, value, isSelected,
                                                              hasFocus, row, column);
      c.setBackground(column == 0 ? Main.GREEN_COLOR : Main.LIGHT_GREEN);
      c.setFont(c.getFont().deriveFont(column == 0 ? Font.BOLD : Font.PLAIN));
      ((DefaultTableCellRenderer) c).setHorizontalAlignment(SwingConstants.CENTER);
      return c;
    }
  };
  table.setDefaultRenderer(Object.class, renderer);

  for (int column = 0; column < table.getColumnCount(); column++) {
    table.getColumnModel().getColumn(column).setPreferredWidth(Main.TABLE_CELLS_WIDTH);
  }
}

}