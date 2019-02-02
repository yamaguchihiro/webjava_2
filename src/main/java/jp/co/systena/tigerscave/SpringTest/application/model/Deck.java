package jp.co.systena.tigerscave.SpringTest.application.model;

import java.util.Random;

public class Deck {
  boolean[] deck;

  //コンストラクタ
  public Deck(){
    deck = new boolean[52];

    for(int i =0;i<deck.length;i++) {
     deck[i] = true;
    }
  }

  //deck配列からまだ引かれていない（true）の配列を一つランダムにfalseにしてそのインデックス(カード番号）を返す
  int randomDrawCard() {

    boolean canDraw = false;
    int drawCardIndex = 0;

    //draw可能なカードがないとか一定枚数切ったらシャッフルする処理入れる

    while(!canDraw) {

      drawCardIndex = new Random().nextInt(52);
      System.out.println(drawCardIndex);
      canDraw = deck[drawCardIndex];
    }

    deck[drawCardIndex] = false;

    return drawCardIndex;

  }

  //deck配列をすべて初期値にする
  void deckReset() {
    for(int i =0;i<=deck.length;i++) {
      deck[i] = true;
     }
  }

}
