package othello;

import othello.view.ConsoleView;
import othello.view.OthelloView;

public class App {

  public static void main(String[] args) {
    OthelloView view = new ConsoleView("㊚", "㊛");
    Match match = new Match(view, 1L);
    Thread thread = new Thread(match);
    thread.start();
  }

}
