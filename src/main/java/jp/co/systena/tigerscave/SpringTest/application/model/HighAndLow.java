package jp.co.systena.tigerscave.SpringTest.application.model;

public class HighAndLow extends GameState {

  //左がhigh
  @Override
  public boolean isPlayerWinJudgement(int playerHandsCal, int dealerHandsCal) {
    // TODO 自動生成されたメソッド・スタブ

    if(playerHandsCal <= dealerHandsCal) {
      return true;
    }

    return false;
  }

}
