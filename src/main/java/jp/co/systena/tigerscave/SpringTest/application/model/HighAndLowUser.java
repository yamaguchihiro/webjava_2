package jp.co.systena.tigerscave.SpringTest.application.model;

import java.util.ArrayList;
import java.util.List;

public class HighAndLowUser  extends Actor{

  List<Integer> hands = new ArrayList<Integer>();

  @Override
  public List<Integer> showDown() {
    // TODO 自動生成されたメソッド・スタブ
    return hands;
  }

  @Override
  public void allDiscard() {
    // TODO 自動生成されたメソッド・スタブ
    hands.clear();
  }

  @Override
  public
  Deck drawCard(Deck deck) {
    // TODO 自動生成されたメソッド・スタブ
    hands.add(deck.randomDrawCard());
    return deck;
  }

  @Override
  public Deck executeAction(String action, Deck deck) {
    // TODO 自動生成されたメソッド・スタブ
    return null;
  }

}
