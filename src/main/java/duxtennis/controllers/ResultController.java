package duxtennis.controllers;

import duxtennis.Main;
import duxtennis.models.Match;
import duxtennis.models.Player;
import duxtennis.models.Set;
import duxtennis.models.Views;
import duxtennis.views.InputView;
import duxtennis.views.ResultView;
import java.util.stream.Collectors;

//class to control the result of the match.
public class ResultController extends MainController {
//Constructor
/*
Create the match result view controller.
-parameter: resultView (View to control).
*/
public ResultController(ResultView resultView) {
  super(resultView);
  }

//Public methods
/*
This method sets up the match result view interface 
and should only be called once the match has ended to 
obtain the names of the players and the results of each set.
*/
public void setupInterface() {
  ((ResultView) getView()).initializeInterface();
  }

/*
The user can request a rematch, in which case the match 
result view will be hidden and reset to its default state, 
the number of match sets will be reset to the chosen option in the combobox, 
the match table will be refreshed, and the match will be replayed.
*/
public void rematchButtonEvent() {
    hideView();
    resetView();
  
    LiveMatchController matchController = (LiveMatchController) Main.getController(Views.LIVE_MATCH);
    updateMatchSetsAmount();
    matchController.drawPlayersNames();
    matchController.updateTable();
    matchController.showView();
    simulateMatch();
  }
  
private void updateMatchSetsAmount() {
    Main.getMatch().setMatchSetsAmount(3 + (2 * ((InputView) Main.getController(Views.INPUT)
                                                                  .getView())
                                                                  .getComboBox()
                                                                  .getSelectedIndex()));
  }
  
private void simulateMatch() {
  Main.simulateMatch();
  }
  
/*
The program goes back to its initial state by 
resetting all its parameters and navigating to the main menu.
*/
public void mainMenuButtonEvent() {
    hideView();
    resetView();
    
    InputController inputController = (InputController) Main.getController(Views.INPUT);
    inputController.resetView();
    
    Match match = Main.getMatch();
    match.getPlayers().forEach(p -> {
      p.setName("");
      p.setSkillPoints(0);
    });
    
    match.setTournamentName("");
    match.setMatchSetsAmount(0);
    
    Main.getController(Views.MENU).showView();
  }
  
//Protected methods
/*
Sets the current view back to its default 
state and resets the match parameters and values, 
including the player information and tournament name, 
only when the main menu button is pressed.
The player skill points and match set amount will also be reset.
*/
@Override
protected void resetView() {
    Main.getMatch()
        .getPlayers()
        .forEach(p -> {
          p.setGamePoints(0);
          p.setGamesWon(0);
          p.setServes(false);
          p.setSetsWon(0);
          p.setWinner(false);
        });

    Main.getMatch()
        .getFinishedSets()
        .clear();

    getView().dispose();
    setView(new ResultView());
  }
  
/*
Updates the title name label and makes the controlled view visible.
*/
@Override
protected void showView() {
    String winnerName = Main.getMatch()
                            .getPlayers()
                            .stream()
                            .filter(Player::isWinner)
                            .collect(Collectors.toList())
                            .get(0).
                            getName();
    ResultView view = ((ResultView) getView());
    view.getTitleLabel().setText("GANADOR DEL TORNEO \"" + Main.getMatch()
                                                              .getTournamentName() + "\": " + winnerName);
    fillTable();
    getView().pack();
    getView().setLocationRelativeTo(null);
    getView().setVisible(true);
  }

//Private methods
/*
Fills the match result table with the sets results.
*/
private void fillTable() {
    for (int i = 0; i < 2; i++) {
      ((ResultView) getView()).getTable()
                                   .setValueAt(Main.getMatch()
                                                   .getPlayers()
                                                   .get(i)
                                                   .getName(), i, 0);
    }

    for (int i = 0; i < Main.getMatch()
                            .getFinishedSets()
                            .size(); i++) {
      Set set = Main.getMatch()
                    .getFinishedSets()
                    .get(i);

      ((ResultView) getView()).getTable()
                                   .setValueAt(getWonGamesString(set.getLoserWonGames(), false),
                                               set.getLoserIndex(), i + 1);

      ((ResultView) getView()).getTable()
                                   .setValueAt(getWonGamesString(set.getWinnerWonGames(), true),
                                               set.getWinnerIndex(), i + 1);
    }
  }

/*
Transforms the number of games won into a string format. 
If the number of games won exceeds the required amount to win a set 
(resulting in a tie), the string will be displayed in the conventional
tennis score format.
-parameter: wonGames (Number of games won by the player).
-parameter: isWinner (Whether the player is the set winner).
--return: The games won formatted string.
*/
private String getWonGamesString(int wonGames, boolean isWinner) {
    int threshold = Match.GAMES_TO_WIN_SET + 1;
    return wonGames > threshold ? 
           (isWinner ? "7 (" + (wonGames - threshold) + ")" : "6 (" + (wonGames - threshold) + ")")
           : Integer.toString(wonGames);
}

}