package jp.co.systena.tigerscave.SpringTest.application.model;

import java.util.ArrayList;
import java.util.List;
import jp.co.systena.tigerscave.SpringTest.application.service.Card;

public class BlackjackUser extends Actor {

  List<Integer> hands = new ArrayList<Integer>();

  @Override
  public
  List<Integer> showDown() {
    // TODO 自動生成されたメソッド・スタブ

    return hands;
  }

  @Override
  public
  void allDiscard() {
    // TODO 自動生成されたメソッド・スタブ
    hands.clear();
  }

  @Override
  public
  Deck drawCard(Deck deck) {

      hands.add(deck.randomDrawCard());
      return deck;
  }

  public int calHands() {
    int aceCount =0;
    int handNum =0;
    for(int i =0; i < hands.size() ; i++) {
      int hand = Card.ConvertCardIndexToCardNumber(hands.get(i));

      if(hand == 1) {
        aceCount +=1;
      }

      if(hand >1) {
        if(hand <10) {
          handNum += hand;
        }else if(hand >= 10){
          handNum +=10;
        }
      }
    }

    for(int i=0; i < aceCount;i++) {
      if(handNum >= 12 - aceCount) {
        handNum +=1;
      }else{
        handNum +=10;
      }
    }

    return handNum;
  }


  boolean isBurst() {
    if(calHands() >21) {
      return true;
    }

    return false;
  }

  @Override
  public Deck executeAction(String action , Deck deck) {
    // TODO 自動生成されたメソッド・スタブ
    if(action =="hit") {
      deck = drawCard(deck);
    }
    return deck;
  }

}
