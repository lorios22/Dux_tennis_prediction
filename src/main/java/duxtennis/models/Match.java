package duxtennis.models;

import java.util.ArrayList;
import java.util.List;

/*
Match class.
*/
public class Match {

//Public constants
public static final int DEUCE_POINTS = 40;
public static final int GAMES_STEP = 15;
public static final int GAMES_TO_WIN_SET = 6;
public static final int POINTS_TO_WIN_GAME = 40;

//Private fields
private boolean deuce;
private boolean tie5;
private boolean tie6;
private boolean tieBreak;

private int setsAmount;

private String tournamentName;

private List<Player> players;
private List<Set> finishedSets;

//Constructor
/*
Creates a basic match with empty data.
-parameter: player1.
-parameter: player2.
*/
public Match(Player player1, Player player2) {
    finishedSets = new ArrayList<>();
    players = new ArrayList<>();

    players.add(player1);
    players.add(player2);

    setTournamentName("");
    setMatchSetsAmount(0);
    setDeuce(false);
    setTie5(false);
    setTie6(false);
    setTieBreak(false);
  }

// Public methods
/*
Adds a finished set to the list.
-parameter: set (Finished set to add).
*/
public void addFinishedSet(Set set) {
    finishedSets.add(set);
  }

//Getters
/*
Returns whether the match is in deuce or not.
*/
  public boolean deuce() {
    return deuce;
  }

/*
Returns whether the match is in a tie with 5 games.
*/
  public boolean isTie5() {
    return tie5;
  }

/*
Returns whether the match is in a tie with 6 games.
*/
public boolean isTie6() {
    return tie6;
  }

/*
Returns whether the match is in a tie break.
*/
  public boolean isTieBreak() {
    return tieBreak;
  }

/*
Returns the match sets amount.
*/
  public int getSetsAmount() {
    return setsAmount;
  }

/*
Returns the tournament name.
*/
  public String getTournamentName() {
    return tournamentName;
  }

/*
Returns the match players.
*/
  public List<Player> getPlayers() {
    return players;
  }

/*
Returns the finished sets.
*/
  public List<Set> getFinishedSets() {
    return finishedSets;
  }

//Setters 
/* 
Updates the deuce flag.
-parameter: deuce (The deuce flag new value).
*/
  public void setDeuce(boolean deuce) {
    this.deuce = deuce;
  }

/*
Updates the 5-games tie flag.
-parameter: tie5 (The 5-games tie flag new value).
*/
public void setTie5(boolean tie5) {
    this.tie5 = tie5;
  }

/*
Updates the 6-games tie flag.
-parameter: tie6 (The 6-games tie flag new value).
*/
public void setTie6(boolean tie6) {
    this.tie6 = tie6;
  }

/*
Updates the tie break flag.
-parameter: tieBreak (The tie break flag new value).
*/
public void setTieBreak(boolean tieBreak) {
    this.tieBreak = tieBreak;
  }

/*
Updates tournament name.
-parameter: tournamentName.
*/
public void setTournamentName(String tournamentName) {
    this.tournamentName = tournamentName;
  }

/*
Updates the match sets amount.
-parameter: setsAmount (The match sets amount).
*/
public void setMatchSetsAmount(int setsAmount) {
    this.setsAmount = setsAmount;
  }
}