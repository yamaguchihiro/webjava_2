package jp.co.systena.tigerscave.SpringTest.application.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import jp.co.systena.tigerscave.SpringTest.application.model.BlackJack;
import jp.co.systena.tigerscave.SpringTest.application.model.BlackjackDealer;
import jp.co.systena.tigerscave.SpringTest.application.model.BlackjackUser;
import jp.co.systena.tigerscave.SpringTest.application.model.Deck;
import jp.co.systena.tigerscave.SpringTest.application.model.GameForm;
import jp.co.systena.tigerscave.SpringTest.application.model.HighAndLow;
import jp.co.systena.tigerscave.SpringTest.application.model.HighAndLowUser;
import jp.co.systena.tigerscave.SpringTest.application.service.Card;

@Controller // Viewあり。Viewを返却するアノテーション

public class GameController {
  @Autowired
  HttpSession session;

  @RequestMapping(value = {"/game"}, method = {RequestMethod.GET}) // URLとのマッピング
  public ModelAndView show(ModelAndView mav) {

    // なんもなかったら

    // テスト出力

    String message1 = (String) session.getAttribute("message1");
    String message2 = (String) session.getAttribute("message2");
    String message3 = (String) session.getAttribute("message3");

    if (message1 != null) {
      mav.addObject("message1", message1);
    }
    if (message2 != null) {
      mav.addObject("message2", message2);
    }
    if (message3 != null) {
      mav.addObject("message3", message3);
    }

    String button1 = (String) session.getAttribute("button1");
    String button2 = (String) session.getAttribute("button2");

    if (button1 != null) {
      mav.addObject("button1", button1);
    } else {
      // 初期のボタン
      mav.addObject("button1", "Blackjuck");
    }

    if (button2 != null) {
      mav.addObject("button2", button2);
    } else {
      // 初期のボタン
      mav.addObject("button2", "HighAndLow");
    }
    // 1.もしゲームが始まっていなかったら、ゲームを開始する選択に関する選択肢をボタン二つに出す
    // session.getAttribute("CurrentCart");で分岐する
    // 2.ゲームが始まっていて、かつ終わってなければ、ブラックジャックとハイアンドローのどっちが行われているか判別する

    // BJ-1.hit standのボタンを表示する

    // HL-1.hi low のボタンを表示する

    // 3.ゲームが始まっていて、終わっていたら、resetボタンを表示する

    // 4.get側でクッキーにぶち込んだメッセージ（ディーラーとプレイヤーの手札とか表示する）


    // 基本的にはここは表示部分なのでボタン表示以外はやらない

    mav.setViewName("GamePage");
    return mav;

  }

  @RequestMapping(value = "/game", method = RequestMethod.POST) // URLとのマッピング

  private ModelAndView order(ModelAndView mav, @Valid GameForm gameForm,
      BindingResult bindingResult, HttpServletRequest request) {

    Deck currentDeck;
    // deckcheck
    if (session.getAttribute("deck") == null) {
      currentDeck = new Deck();
    } else {
      currentDeck = (Deck) session.getAttribute("deck");
    }

    // まず表示用のメッセージはすべてリセットする
    session.removeAttribute("message1");
    session.removeAttribute("message2");
    session.removeAttribute("message3");
    session.removeAttribute("button1");
    session.removeAttribute("button2");

    // resetが降ってきたらreset処理する
    if (gameForm.getAction().equals("reset")) {
      session.removeAttribute("GameState");
      session.removeAttribute("deck");
      session.removeAttribute("player");
      session.removeAttribute("dealer");
    }

    // Blackjuck,HI and LOWが降ってきたらGameStateにブチ込む
    if (gameForm.getAction().equals("Blackjuck")) {
      session.setAttribute("GameState", new BlackJack());
      session.setAttribute("player", new BlackjackUser());
      session.setAttribute("dealer", new BlackjackDealer());
    }

    if (gameForm.getAction().equals("HighAndLow")) {
      session.setAttribute("GameState", new HighAndLow());
      session.setAttribute("player", new HighAndLowUser());
      session.setAttribute("dealer", new HighAndLowUser());
    }

    // hilowも本当は書く

    if (session.getAttribute("GameState") instanceof HighAndLow) {
      HighAndLow HL = (HighAndLow) session.getAttribute("GameState");
      session.setAttribute("button1", "High");
      session.setAttribute("button2", "Low");

      HighAndLowUser player = (HighAndLowUser) session.getAttribute("player");
      HighAndLowUser dealer = (HighAndLowUser) session.getAttribute("dealer");

      boolean isPlayerWin = false;

      if (gameForm.getAction().equals("HighAndLow") || gameForm.getAction().equals("OK")) {
        player.allDiscard();
        dealer.allDiscard();
        currentDeck = player.drawCard(currentDeck);



      }
      else {
        if (gameForm.getAction().equals("High")) {
          currentDeck = dealer.drawCard(currentDeck);

          isPlayerWin = HL.isPlayerWinJudgement(Card.ConvertCardIndexToCardNumber(player.showDown().get(0)), Card.ConvertCardIndexToCardNumber(dealer.showDown().get(0)));

        } else if (gameForm.getAction().equals("Low")) {
          currentDeck = dealer.drawCard(currentDeck);
          isPlayerWin = HL.isPlayerWinJudgement(Card.ConvertCardIndexToCardNumber(dealer.showDown().get(0)), Card.ConvertCardIndexToCardNumber(player.showDown().get(0)));
        }

        session.setAttribute("message2", "Card :" + Card.ConvertCardIndexListToString(dealer.showDown()));

        if(isPlayerWin) {
          HL.setScore(HL.getScore() + 1);
          session.setAttribute("message3",
              "Score:" + HL.getScore() + " Player Win , Next Game is Started.");
          session.setAttribute("button1", "OK");
          session.setAttribute("button2", "OK");
        }else {
          session.setAttribute("message3", "Score:" + HL.getScore() + " Player Lose");
          session.setAttribute("button1", "reset");
          session.setAttribute("button2", "reset");

        }

      }
      session.setAttribute("message1", "Targets :" + Card.ConvertCardIndexListToString(player.showDown()));
    }

    // bj処理
    if (session.getAttribute("GameState") instanceof BlackJack) {
      BlackJack bj = (BlackJack) session.getAttribute("GameState");
      session.setAttribute("button1", "hit");
      session.setAttribute("button2", "stand");

      BlackjackUser player = (BlackjackUser) session.getAttribute("player");
      BlackjackDealer dealer = (BlackjackDealer) session.getAttribute("dealer");

      if (gameForm.getAction().equals("Blackjuck") || gameForm.getAction().equals("OK")) {
        player.allDiscard();
        dealer.allDiscard();

        currentDeck = player.drawCard(currentDeck);
        currentDeck = player.drawCard(currentDeck);
        currentDeck = dealer.drawCard(currentDeck);
        currentDeck = dealer.drawCard(currentDeck);
      } else if (gameForm.getAction().equals("hit")) {
        currentDeck = player.drawCard(currentDeck);
        if (player.calHands() > 21) {
          bj.setGameFinished(true);
        }
      } else if (gameForm.getAction().equals("stand")) {
        currentDeck = dealer.executeAction("execute", currentDeck);
        bj.setGameFinished(true);

      }

      session.setAttribute("deck", currentDeck);
      session.setAttribute("message1", "YourHands " + player.calHands() + " :"
          + Card.ConvertCardIndexListToString(player.showDown()));
      session.setAttribute("message2",
          "DealerHands :" + Card.ConvertCardIndexListToString(dealer.showDown()));

      if (bj.isGameFinished()) {
        session.removeAttribute("button1");
        session.removeAttribute("button2");
        session.removeAttribute("message2");
        session.setAttribute("message2", "DealerHands " + dealer.calHands() + " :"
            + Card.ConvertCardIndexListToString(dealer.showDownAll()));
        if (bj.isPlayerWinJudgement(player.calHands(), dealer.calHands())) {
          bj.setScore(bj.getScore() + 1);
          bj.setGameFinished(false);
          session.setAttribute("message3",
              "Score:" + bj.getScore() + " Player Win , Next Game is Started.");
          session.setAttribute("button1", "OK");
          session.setAttribute("button2", "OK");

        } else {
          session.setAttribute("message3", "Score:" + bj.getScore() + " Player Lose");
          session.setAttribute("button1", "reset");
          session.setAttribute("button2", "reset");
        }

      }
    }

    // それ以外は進行中のゲームに併せて分岐する
    return new ModelAndView("redirect:/game"); // リダイレクト

  }
}