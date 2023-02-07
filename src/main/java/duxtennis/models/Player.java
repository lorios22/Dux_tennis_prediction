package duxtennis.models;

/* 
Player class.
*/

public class Player {

  private boolean serves;
  private boolean winner;

  private int setsWon;
  private int skillPoints;
  private int gamePoints;
  private int gamesWon;

  private String name;

// Constructor for initializing a Player object
  public Player(String name) {
    this.name = name;
    this.skillPoints = 0;
    this.gamePoints = 0;
    this.gamesWon = 0;
    this.setsWon = 0;
    this.serves = false;
    this.winner = false;
  }
  
// Getters
  public boolean serves() {
    return serves;
  }

  public int getGamePoints() {
    return gamePoints;
  }

  public int getGamesWon() {
    return gamesWon;
  }

  public int getSetsWon() {
    return setsWon;
  }

  public int getSkillPoints() {
    return skillPoints;
  }

  public String getName() {
    return name;
  }

  public boolean isWinner() {
    return winner;
  }

// Setters
  public void setGamePoints(int gamePoints) {
    this.gamePoints = gamePoints;
  }

  public void setGamesWon(int gamesWon) {
    this.gamesWon = gamesWon;
  }

  public void setSetsWon(int setsWon) {
    this.setsWon = setsWon;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setSkillPoints(int skillPoints) {
    this.skillPoints = skillPoints;
  }

  public void setServes(boolean serves) {
    this.serves = serves;
  }

  public void setWinner(boolean winner) {
    this.winner = winner;
  }
} 