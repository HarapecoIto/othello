package net.yoursweetest.othello;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;
import othello.base.Board;
import othello.base.Disk;
import othello.base.Square;
import othello.player.Player;
import othello.player.RandomPlayer;
import othello.util.Score;
import othello.util.Tools;
import othello.util.ToolsTest;

public class CitrusPlayerTest {

  public static void compareMovedWith(CitrusPlayer player1, CitrusPlayer player2) {
    Board board1 = new Board();
    board1.init();
    Board board2 = board1.clone();
    Player randomPlayer1 = new RandomPlayer("Random", 0);
    Player randomPlayer2 = new RandomPlayer("Random", 0);
    player1.init(Disk.WHITE);
    player2.init(Disk.WHITE);
    randomPlayer1.init(Disk.BLACK);
    randomPlayer2.init(Disk.BLACK);
    Square moved1 = null;
    Square moved2 = null;
    for (int j = 0; j < 5; j++) {
      List<Square> candidates1 = player1.allCandidates(board1, moved1);
      List<Square> candidates2 = player2.allCandidates(board2, moved2);
      System.err.println(candidates1 + " " + candidates2);
      assertEquals(candidates1.size(), candidates2.size());
      assertNotEquals(0, candidates1.size());
      System.err.printf("Candidates: %d %d%n", candidates1.size(), candidates2.size());
      ToolsTest.dumpBoard(board1, new Score());
      ToolsTest.dumpBoard(board2, new Score());
      for (int k = 0; k < candidates1.size(); k++) {
        assertEquals(candidates1.get(k), candidates2.get(k));
      }
      moved1 = player1.moveDisk(board1, moved1).orElse(null);
      moved2 = player2.moveDisk(board2, moved2).orElse(null);
      assertEquals(moved1, moved2);
      Tools.move(board1, moved1, Disk.WHITE);
      Tools.move(board2, moved2, Disk.WHITE);
      assertEquals(board1, board2);

      moved1 = randomPlayer1.moveDisk(board1, moved1).orElse(null);
      moved2 = randomPlayer2.moveDisk(board2, moved2).orElse(null);
      assertEquals(moved1, moved2);
      Tools.move(board1, moved1, Disk.BLACK);
      Tools.move(board2, moved2, Disk.BLACK);
      assertEquals(board1, board2);
    }
    player1.shutdown();
    player2.shutdown();
  }
}
