package jp.co.systena.tigerscave.SpringTest.application.model;

import java.util.List;

abstract class Actor {
  abstract List<Integer> showDown();
  abstract void allDiscard();
  abstract Deck drawCard(Deck deck);
  abstract Deck executeAction(String action , Deck deck);
}
