/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package othello;

import othello.base.Board;
import othello.view.ConsoleView;
import othello.view.OthelloView;

public class App {

  public String getGreeting() {
    return "Hello World!";
  }

  public static void main(String[] args) {
    OthelloView view = new ConsoleView();
    Board board = new Board();
    view.update(board);
  }

}
