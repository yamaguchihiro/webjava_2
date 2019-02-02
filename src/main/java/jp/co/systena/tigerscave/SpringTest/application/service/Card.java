package jp.co.systena.tigerscave.SpringTest.application.service;

import java.util.List;

public class Card {

  static String[] suits = {"♠","❤","♦","♣"};


  static String ConvertCardIndexToString(int cardIndex) {

    String suit = suits[(cardIndex) /13];
    int Number = cardIndex % 13 + 1;

    return suit+Number;
  }

  public static String ConvertCardIndexListToString(List<Integer> hands) {
    String rtn ="";

    for(int i =0; i < hands.size() ; i++) {

      rtn += ConvertCardIndexToString(hands.get(i)) + " , ";
    }

    return rtn;
  }
  public static int ConvertCardIndexToCardNumber(int cardIndex) {
    return  cardIndex % 13 + 1;
  }


}
