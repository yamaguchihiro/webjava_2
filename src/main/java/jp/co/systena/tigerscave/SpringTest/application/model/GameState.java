package jp.co.systena.tigerscave.SpringTest.application.model;

abstract class GameState {
  int score;
  boolean isGameFinished = false;

  public int getScore() {
    return score;
  }


  public void setScore(int score) {
    this.score = score;
  }


  public boolean isGameFinished() {
    return isGameFinished;
  }


  public void setGameFinished(boolean isGameFinished) {
    this.isGameFinished = isGameFinished;
  }

  abstract public boolean isPlayerWinJudgement(int playerHandsCal,int dealerHandsCal);
}
