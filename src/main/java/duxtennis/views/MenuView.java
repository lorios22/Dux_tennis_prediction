package duxtennis.views;

import duxtennis.Main;
import duxtennis.controllers.MenuController;
import duxtennis.models.Views;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import net.miginfocom.swing.MigLayout;

/* 
Menu view class.
*/
public class MenuView extends MainView {

//Private constants
private static final String BG_IMG_FILENAME = "tennis_match_prediction.png";
private static final String GROWX = "growx";
private static final String FRAME_TITLE = Main.PROGRAM_TITLE + Main.PROGRAM_VERSION;

//Private fields
private JPanel panel;

//Constructor
/*
Creates the main view.
*/
public MenuView() {
    initializeInterface();
  }

//Protected methods
/*
Starts the view and displays it to the user.
*/
@Override
protected void initializeInterface() {
    panel = new JPanel(new MigLayout("wrap"));

    addBackground();
    addButtons();
    add(panel);
    setResizable(false);
    setTitle(FRAME_TITLE);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setIconImage(Main.ICON.getImage());
    pack();
    setLocationRelativeTo(null);
  }

/*
Adds the buttons to the panel.
*/
  @Override
  protected void addButtons() {
    JButton startButton = new JButton("Comenzar");
    JButton exitButton = new JButton("Salir");

    startButton.addActionListener(e ->
        ((MenuController) Main.getController(Views.MENU)).startButtonEvent()
    );

    exitButton.addActionListener(e -> System.exit(0));

    panel.add(startButton, GROWX);
    panel.add(exitButton, GROWX);
  }

//Private methods

/*
Adds the background image to the panel.
*/
  private void addBackground() {
    ImageIcon Image = new ImageIcon(getClass().getClassLoader()
                                                .getResource(Main.IMG_PATH
                                                             + BG_IMG_FILENAME));

    JLabel bgLabel = new JLabel("", Image, SwingConstants.CENTER);

    panel.add(bgLabel, GROWX);
  }
}