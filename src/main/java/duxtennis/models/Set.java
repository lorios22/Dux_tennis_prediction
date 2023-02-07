package duxtennis.models;

import duxtennis.Main;

//Set Class
public class Set {

  //Private fields
  private int winnerIndex;
  private int loserIndex;
  private int winnerWonGames;
  private int loserWonGames;

  //Constructor
  public Set(Player winner) {
    winnerIndex = Main.getMatch().getPlayers().indexOf(winner);
    loserIndex = 1 - winnerIndex;
    Player loser = Main.getMatch().getPlayers().get(loserIndex);
    winnerWonGames = winner.getGamesWon();
    loserWonGames = loser.getGamesWon();
  }

  //Getters
  public int getWinnerIndex() { return winnerIndex; }
  public int getLoserIndex() { return loserIndex; }
  public int getWinnerWonGames() { return winnerWonGames; }
  public int getLoserWonGames() { return loserWonGames; }
}