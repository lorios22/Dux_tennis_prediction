package duxtennis.controllers;

import duxtennis.Main;
import duxtennis.models.Match;
import duxtennis.models.Player;
import duxtennis.models.Views;
import duxtennis.views.LiveMatchView;

//class that controls the live match.
public class LiveMatchController extends MainController {

//Constructor
/*
Creates the live match view controller.
-parameter: liveMatchView View to control.
*/
public LiveMatchController(LiveMatchView liveMatchView) {
    super(liveMatchView);
  }
@Override
  public void resetView() {
      // implementation logic goes here
  }

//Public methods
/* 
Updates the data shown in the live match table.
This includes the players points, the players games won
and the players sets won.
*/
public void updateTable() {
    drawPoints();
    drawGamesWon();
    drawSetsWon();
  }

/*
Updates the players points in the live match table.
 */
public void drawPoints() {
  Main.getMatch()
      .getPlayers()
      .forEach(p -> {
        if (p.getGamePoints() > 40) {
          ((LiveMatchView) getView()).getTable()
                                      .setValueAt("40 (" + (p.getGamePoints() - 40) + ")",
                                      Main.getMatch()
                                      .getPlayers()
                                      .indexOf(p) + 1, 4);
          } else {
            ((LiveMatchView) getView()).getTable()
                                       .setValueAt(Integer.toString(p.getGamePoints()),
                                        Main.getMatch()
                                        .getPlayers()
                                        .indexOf(p) + 1, 4);
          }
        });
  }

/*
Updates the players games won in the live match table.
*/
public void drawGamesWon() {
  LiveMatchView view = (LiveMatchView) getView();
  int gamesToWinSet = Match.GAMES_TO_WIN_SET;
  
  Main.getMatch().getPlayers().forEach(p -> {
    int gamesWon = p.getGamesWon();
    int index = Main.getMatch().getPlayers().indexOf(p) + 1;

    view.getTable().setValueAt(
      gamesWon > gamesToWinSet ? gamesToWinSet + " (" + (gamesWon - gamesToWinSet) + ")"
                               : Integer.toString(gamesWon),
      index, 3
    );
  });
}

/*
Updates the players sets won in the current match table.
*/
public void drawSetsWon() {
    Player player1 = Main.getMatch()
                         .getPlayers()
                         .get(0);

    Player player2 = Main.getMatch()
                         .getPlayers()
                         .get(1);

    ((LiveMatchView) getView()).getTable()
                                  .setValueAt(Integer.toString(player1.getSetsWon()), 1, 2);

    ((LiveMatchView) getView()).getTable()
                                  .setValueAt(Integer.toString(player2.getSetsWon()), 2, 2);
  }

/*
Draws an '🎾' in the table cell corresponding to the server player.
-parameter: server (The server player).
 */
public void drawServer(Player server) {
    int serverIndex = Main.getMatch()
                          .getPlayers()
                          .indexOf(server);

    ((LiveMatchView) getView()).getTable()
                                  .setValueAt("🎾", 1 + serverIndex, 1);

    ((LiveMatchView) getView()).getTable()
                                 .setValueAt("", 2 - serverIndex, 1);
  }

/*
Updates the players names in the live match table.
*/
public void drawPlayersNames() {
    for (int i = 0; i < 2; i++) {
      ((LiveMatchView) getView()).getTable()
                                    .setValueAt(Main.getMatch()
                                                    .getPlayers()
                                                    .get(i)
                                                    .getName(), i + 1, 0);
    }
  }

/*
When the match ends, the current match view is hidden,
and the match result view is set and displayed.
*/
public void matchFinished() {
  hideView();

  ((ResultController) Main.getController(Views.RESULT)).setupInterface();
  ((ResultController) Main.getController(Views.RESULT)).showView();
  }

//Protected methods
/*
Makes the controlled view invisible and resets it to its default values.
*/
/*@Override
protected void resetView() {
  }

/*
Updates the tournament name label and makes the controlled view visible.
*/
@Override
protected void showView() {
    LiveMatchView view = (LiveMatchView) getView();
    view.getTournamentNameLabel().setText("Torneo \"" + Main.getMatch().getTournamentName() + "\"");
    view.pack();
    view.setLocationRelativeTo(null);
    view.setVisible(true);
}
}