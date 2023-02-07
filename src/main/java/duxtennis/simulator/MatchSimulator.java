package duxtennis.simulator;

import duxtennis.Main;
import duxtennis.controllers.LiveMatchController;
import duxtennis.models.Match;
import duxtennis.models.Player;
import duxtennis.models.Set;
import duxtennis.models.Views;

import java.util.Arrays;
import java.util.Random;
import javax.swing.Timer;

/*
Match simulator class.
*/
public class MatchSimulator {

//Private constants
private static final int TIMER_DELAY_MS = 500;

// Private fields 
private boolean matchEnded;
private int gamesToWinSet;
private Match match;
private Random randomGenerator;
private Timer timer;

//Constructor
/*
Builds the match simulator.
-parameter: match (The match object to use in the simulation)
*/
public MatchSimulator(Match match) {
    this.match = match;

    matchEnded = false;
    gamesToWinSet = Match.GAMES_TO_WIN_SET;

    randomGenerator = new Random();
  }

//Public methods
/*
Initiates match simulation
*/
public void simulate() {
    whoServes(true);

    timer = new Timer(TIMER_DELAY_MS, null);

    timer.addActionListener(e -> {
      generatePoints();

      if (matchEnded) {
        ((LiveMatchController) Main.getController(Views.LIVE_MATCH)).matchFinished();
      }
    });
    timer.setRepeats(true);
    timer.start();
  }
//Private methods
//This method determines which player serves in a match
private void whoServes(boolean firstTime) {
//Gets the current server (if there is one)
  Player lastServer = match.getPlayers().stream()
                          .filter(Player::serves)
                          .findFirst()
                          .orElse(null);

  Player newServer;
//If this is the first time the method is called, choose a server randomly
  if (firstTime) {
      int serverIndex = randomGenerator.nextInt(2);
      newServer = match.getPlayers().get(serverIndex);
  } else {
      // Otherwise, choose the player who is not currently serving
      newServer = match.getPlayers().get(1 - match.getPlayers().indexOf(lastServer));
  }

//Unsets the current server
  if (lastServer != null) {
      lastServer.setServes(false);
  }
//Sets the new server
  newServer.setServes(true);

//Draws the new server on the live match view
  ((LiveMatchController) Main.getController(Views.LIVE_MATCH)).drawServer(newServer);
}

/* 
Method that generates points in a match and updates the scores of the players
*/
private void generatePoints() {
  Player[] players = match.getPlayers().toArray(new Player[2]);

//Determine the winner of the point randomly based on their skill points
  Player pointWinner = players[randomGenerator.nextDouble() <= (double)players[0].getSkillPoints() / 100 ? 0 : 1];

  int playerGamePoints = match.deuce() ? pointWinner.getGamePoints() + 1 : pointWinner.getGamePoints() + Match.GAMES_STEP;

//Handle deuce logic
  if (playerGamePoints == 45) {
    playerGamePoints = 40;
  }
  pointWinner.setGamePoints(playerGamePoints);

  Player pointLoser = players[1 - Arrays.asList(players).indexOf(pointWinner)];

//Checks if the game is a draw or if someone has won
  if (checkDraw(pointWinner.getGamePoints(), pointLoser.getGamePoints(), Match.DEUCE_POINTS)) {
    match.setDeuce(true);
  } else if (match.deuce() && pointWinner.getGamePoints() == pointLoser.getGamePoints() + 2) {
    match.setDeuce(false);
    playerWonGame(pointWinner, pointLoser);
    return;
  } else if (!match.deuce() && pointWinner.getGamePoints() > Match.POINTS_TO_WIN_GAME) {
    playerWonGame(pointWinner, pointLoser);
    return;
  }
  ((LiveMatchController) Main.getController(Views.LIVE_MATCH)).drawPoints();
}

/*
playerWonGame() updates game statistics after a player has won a game.
-parameter: gameWinner (The player who won the game).
-parameter: gameLoser (The player who lost the game).
   */
private void playerWonGame(Player gameWinner, Player gameLoser) {
//Reset game points for both players
  gameWinner.setGamePoints(0);
  gameLoser.setGamePoints(0);

//Increments the number of games won by the game winner
int gamesWon = gameWinner.getGamesWon() + 1;
  gameWinner.setGamesWon(gamesWon);

//Calculates the difference in the number of games won between the players
int difference = Math.abs(gameWinner.getGamesWon() - gameLoser.getGamesWon());

//Checks if the difference in games won reaches the required number to trigger a tiebreak or super tiebreak
    if (difference >= Match.GAMES_TO_WIN_SET - 1) {
      match.setTie5(true);
      gamesToWinSet = Match.GAMES_TO_WIN_SET + 1;
    } else if (difference >= Match.GAMES_TO_WIN_SET) {
      match.setTie5(false);
      match.setTie6(true);
      gamesToWinSet = Match.GAMES_TO_WIN_SET + 7;
    }

//Checks if it is a tiebreak and the game winner's game points difference is two or more compared to the game loser's
    if (match.isTieBreak() && gameWinner.getGamePoints() == gameLoser.getGamePoints() + 2) {
      playerWonSet(gameWinner, gameLoser);
      return;
    }

//Checks if the game winner has won the required number of games to win a set
    if (gameWinner.getGamesWon() == gamesToWinSet) {
      match.setTieBreak(true);
      playerWonSet(gameWinner, gameLoser);
      return;
    }

//Updates the score table
    ((LiveMatchController) Main.getController(Views.LIVE_MATCH)).updateTable();
  }

/*
Method to handle player winning a set
 * 
-parameter: setWinner (Player who won the set).
-parameter: setLoser  (Player who lost the set).
 */
private void playerWonSet(Player setWinner, Player setLoser) {
//Adds the set to the finished set of the match
  match.addFinishedSet(new Set(setWinner));

//Updates the number of sets won by the winner and sets game won to 0 for both players
  setWinner.setSetsWon(setWinner.getSetsWon() + 1);
  setWinner.setGamesWon(0);
  setLoser.setGamesWon(0);

//If match has not ended, updates serve and sets tie flags to false
  if (!matchEnd(setWinner, setLoser)) {
      if (match.isTie5() || match.isTie6()) {
          match.setTie5(false);
          match.setTie6(false);
          match.setTieBreak(false);
          gamesToWinSet = Match.GAMES_TO_WIN_SET;
      }
      whoServes(false);
  } 
//If match has ended, stops the timer, sets match sets amount, sets winner, and match ended flag
  else {
      timer.stop();
      match.setMatchSetsAmount(setWinner.getSetsWon());
      setWinner.setWinner(true);
      matchEnded = true;
  }
  
//Updates the match table
  ((LiveMatchController) Main.getController(Views.LIVE_MATCH)).updateTable();
}

private boolean matchEnd(Player setWinner, Player setLoser) {
    int setsWon = setWinner.getSetsWon();
    int setsLost = setLoser.getSetsWon();
    int matchSets = match.getSetsAmount();
  
/*Checks if the match has ended by comparing the number of sets won 
by the winner and the loser to the number of sets in the match
or if the winner has more sets than half of the match sets and
more sets than the loser.*/
    return (setsWon + setsLost == matchSets) || 
           (setsWon > matchSets / 2 && setsWon > setsLost);
  }

// Function to check if game points of both players are equal to the reference 
//(deuce points)
private boolean checkDraw(int player1Field, int player2Field, int reference) {
//Checks if player1 and player2 have equal game points as reference
  return player1Field == reference && player1Field == player2Field;
}
}