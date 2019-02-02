package jp.co.systena.tigerscave.SpringTest.application.model;

public class BlackJack extends GameState {

  @Override
  public boolean isPlayerWinJudgement(int playerHandsCal, int dealerHandsCal) {
    // TODO 自動生成されたメソッド・スタブ

    if(dealerHandsCal >21) {
      return true;
   }

    if(playerHandsCal >21) {
      return false;
   }

    if(playerHandsCal <= dealerHandsCal) {
      return false;
    }

    return true;

  }
}
