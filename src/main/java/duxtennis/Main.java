package duxtennis;

import duxtennis.controllers.MainController;
import duxtennis.controllers.LiveMatchController;
import duxtennis.controllers.InputController;
import duxtennis.controllers.MenuController;
import duxtennis.controllers.ResultController;
import duxtennis.models.Match;
import duxtennis.models.Player;
import duxtennis.models.Views;
import duxtennis.simulator.MatchSimulator;
import duxtennis.views.LiveMatchView;
import duxtennis.views.InputView;
import duxtennis.views.MenuView;
import duxtennis.views.ResultView;
import java.awt.Color;
import java.util.EnumMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
/*
Main class for program initialization and declaration of useful fields.
*/
public final class Main {

  // Private constants for error message title and icon file name
  private static final String ICON_FILENAME = "icono.png";
  private static final String ERROR_MESSAGE_TITLE = "¡Error!";
  
  // Public constants for table cells width, max name length, colors, and program information
  public static final int TABLE_CELLS_WIDTH = 130;
  public static final int MAX_NAME_LEN = 10;
  public static final Color LIGHT_GREEN = new Color( 181,255,139);
  public static final Color GREEN_COLOR = new Color(145,201,113);
  public static final Color YELLOW_COLOR = new Color(154, 255, 70);
  public static final String IMG_PATH = "img/";
  public static final String NAMES_VALIDATION_REGEX = "[a-z A-ZÁÉÍÓÚáéíóúñÑ]+";
  public static final String PROGRAM_TITLE = "Dux_Tennis_Match_Prediction";
  public static final String PROGRAM_VERSION = "V 0.1";
  
  // Public constant for program icon
  public static final ImageIcon ICON = new ImageIcon(
  Main.class
  .getClassLoader()
  .getResource(Main.IMG_PATH + Main.ICON_FILENAME)
  );
  
  // Private map for controllers and match
  private static Map<Views, MainController> controllersMap;
  private static Match match;
  
  // Empty private constructor
  private Main() {
  // No body needed
  }
  
  // Main entry point for the program
  public static void main(String[] args) {
  setGraphicalProperties();
  setupMatch();
  setControllers();
  ((MenuController) getController(Views.MENU)).showView();
}

//Public methods
/*
Creates an error window with a custom message.
-parameter: errorMessage (Custom error message to show).
*/
public static void showErrorMessage(String errorMessage) {
    JOptionPane.showMessageDialog(null, errorMessage, ERROR_MESSAGE_TITLE,
                                  JOptionPane.ERROR_MESSAGE, null);
  }

/*
Triggers the match simulation.
*/
  public static void simulateMatch() {
    MatchSimulator matchSimulator = new MatchSimulator(Main.getMatch());

    matchSimulator.simulate();
  }

//Getters
/*
Gets the corresponding controller to the requested view.
-parameter: view (The view whose controller is needed).
--return: The requested view's controller.
*/
public static MainController getController(Views view) {
    return controllersMap.get(view);
  }

/*
Gets the current match.
--return: The current match.
   */
  public static Match getMatch() {
    return match;
  }

//Private methods
//Setters
/*
Creates an empty match.
*/
private static final void setupMatch() {
    match = new Match(new Player(""), new Player(""));
  }

/*
Sets up the views' controllers.
*/
private static final void setControllers() {
//Initialize a map to store the controllers
  controllersMap = new EnumMap<>(Views.class);
//Create an instance of MenuController and store it in the controllersMap
  MainController mainMenuController = new MenuController(new MenuView());
  controllersMap.put(Views.MENU, mainMenuController);
  
//Create an instance of InputController and store it in the controllersMap
  MainController dataInputController = new InputController(new InputView());
  controllersMap.put(Views.INPUT, dataInputController);
  
//Create an instance of LiveMatchController and store it in the controllersMap
  MainController currentMatchController = new LiveMatchController(new LiveMatchView());
  controllersMap.put(Views.LIVE_MATCH, currentMatchController);
  
//Create an instance of ResultController and store it in the controllersMap
  MainController matchResultController = new ResultController(new ResultView());
  controllersMap.put(Views.RESULT, matchResultController);
}  

/*
Sets up the program's GUI properties.
*/
private static final void setGraphicalProperties() {
  UIManager.put("Table.background", LIGHT_GREEN);
  UIManager.put("ComboBox.background", Color.WHITE);
  }
}
