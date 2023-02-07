package duxtennis.controllers;

import duxtennis.Main;
import duxtennis.models.Views;
import duxtennis.views.InputView;
import java.util.regex.Pattern;
import javax.naming.InvalidNameException;

//class that controls the input view
public class InputController extends MainController {

//Public constants
  public static final int SLIDER_INI = 50;
  public static final int SLIDER_MIN = 0;
  public static final int SLIDER_MAX = 100;
  public static final int SLIDER_SPACING_MAJOR = 25;
  public static final int SLIDER_SPACING_MINOR = 5;

//Constructor 
/*
Creates input view controller.
-parameter: inputView (View to control).
*/
public InputController(InputView inputView) {
    super(inputView);
  }

//Public methods
/*
Makes controlled view invisible and resets it to its default values.
*/
@Override
public void resetView() {
  InputView inputView = (InputView) getView();
  hideView();

  inputView.getSliders().get(0).setValue(SLIDER_INI);
  inputView.getTextFields().forEach(tf -> tf.setText(""));
  inputView.getComboBox().setSelectedIndex(0);
  inputView.getContinueButton().setEnabled(false);
}
/*
The "Back" button has a function that, 
when triggered, returns the current view 
to its original settings and displays the main menu view.
*/
public void backButtonEvent() {
  resetView();
  clearNames();

  Main.getController(Views.MENU)
    .showView();
  }

/*
Text fields input event handler.
The input event handler for text fields performs validation on the user's 
input using a regular expression that verifies if the string consists solely
 of Latin characters from A to Z including Ñ, regardless of case or presence 
 of accent marks or spaces. If the input is invalid or already taken, the program 
 will request new input. If the input is valid, it will be assigned as either a player
name or the name of the tournament.
-parameter: tfIndex (The text field index in the text fields list).
-throws IllegalArgumentException When the input is an invalid string.
-throws InvalidNameException     When the input is an invalid name.
*/
public void textFieldEvent(String text, int tfIndex) throws IllegalArgumentException, InvalidNameException {
    text = text.trim().toUpperCase();

    if (!validString(text) || !validName(text)) {
      throw new IllegalArgumentException();
    }

    if (tfIndex == 0) {
      Main.getMatch().setTournamentName(text);
    } else {
      Main.getMatch().getPlayers().get(tfIndex - 1).setName(text);
    }
}

/*
This action hides the current view, obtains the skill points of the players 
from the sliders, updates the number of sets for the match, and displays 
the next view.*/
public void continueButtonEvent() {
    hideView();

    for (int i = 0; i < 2; i++) {
      Main.getMatch()
          .getPlayers()
          .get(i)
          .setSkillPoints(((InputView) getView()).getSliders()
                                                     .get(i)
                                                     .getValue());
    }

    Main.getMatch()
        .setMatchSetsAmount(3 + (2 * ((InputView) getView()).getComboBox()
                                                                .getSelectedIndex()));

    ((LiveMatchController) Main.getController(Views.LIVE_MATCH)).drawPlayersNames();
    ((LiveMatchController) Main.getController(Views.LIVE_MATCH)).updateTable();
    ((LiveMatchController) Main.getController(Views.LIVE_MATCH)).showView();

    Main.simulateMatch();
  }

//Private methods
/*
Clears the players and match names.
*/
private void clearNames() {
    Main.getMatch()
        .getPlayers()
        .forEach(p -> p.setName(""));

    Main.getMatch()
        .setTournamentName("");
  }

/*
Checks if the given string matches the string validation regex.
-parameter: string (The string to validate).
--return: Whether the string matches the string validation regex or not.
*/
private boolean validString(String string) {
    return Pattern.matches(Main.NAMES_VALIDATION_REGEX, string);
  }

/*
This validation verifies if the provided name is within the character
limit of MAX_NAME_LEN, is not empty or blank, and does not match 
the name of any existing player.
-parameter: name (The name to validate.)
--return: If the given name is valid according to the specified conditions.
*/
private boolean validName(String name) {
    return name.length() <= Main.MAX_NAME_LEN
           && !name.isBlank()
           && !name.isEmpty()
           && !alreadyExists(name);
  }

/*
Checks if there is already a player with the specified name.
-parameter: name (Name to validate).
--return: Whether there is already a player with the specified name or not.
 */
private boolean alreadyExists(String name) {
    return Main.getMatch()
               .getPlayers()
               .stream()
               .anyMatch(p -> p.getName()
                               .equals(name));
  }
}
