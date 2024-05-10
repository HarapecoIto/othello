package net.yoursweetest.othello;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import othello.base.Board;
import othello.base.Col;
import othello.base.Disk;
import othello.base.Row;
import othello.base.Square;
import othello.player.Player;
import othello.player.RandomPlayer;
import othello.util.Tools;

public class SetokaPlayerTest {

  static final int MAX_STEP = 5;

  @Test
  void testMoveDisk1() {
    Board board = new Board();
    board.init();
    List<Square> expects = Arrays.asList(
        Square.SQUARE_3_E, Square.SQUARE_4_F, Square.SQUARE_5_C, Square.SQUARE_6_D
    );
    for (long seed = 0; seed < 10; seed++) {
      Player player = new SetokaPlayer("Setoka Player", seed, MAX_STEP);
      player.init(Disk.WHITE);
      Optional<Square> square = player.moveDisk(board.clone(), null);
      assertTrue(square.isPresent());
      assertTrue(expects.contains(square.orElse(null)));
      player.shutdown();
    }
  }

  @Test
  void testMoveDisk2() {
    Board board = new Board();
    board.init();
    List<Square> expects = Arrays.asList(
        Square.SQUARE_3_D, Square.SQUARE_4_C, Square.SQUARE_5_F, Square.SQUARE_6_E
    );
    for (long seed = 0; seed < 10; seed++) {
      Player player = new SetokaPlayer("Setoka Player", seed, MAX_STEP);
      player.init(Disk.BLACK);
      Optional<Square> square = player.moveDisk(board.clone(), null);
      assertTrue(square.isPresent());
      assertTrue(expects.contains(square.orElse(null)));
      player.shutdown();
    }
  }

  @Test
  void testMoveDisk3() {
    // ㊚㊚㊚㊚㊚㊚＿＿
    // ㊚㊚㊚㊚㊚㊚＿＿
    // ㊚㊚㊚㊚㊚㊚＿＿
    // ㊚㊚㊚㊚㊚㊚＿＿
    // ㊚㊚㊚㊚㊚㊚＿＿
    // ㊚㊚㊚㊚㊚㊚＿＿
    // ＿＿＿＿＿＿＿＿
    // ＿＿＿＿＿＿＿＿
    Board board = new Board();
    board.clear();
    for (Row row : new Row[]{Row.ROW_1, Row.ROW_2, Row.ROW_3, Row.ROW_4, Row.ROW_5, Row.ROW_6}) {
      for (Col col : new Col[]{Col.COL_A, Col.COL_B, Col.COL_C, Col.COL_D, Col.COL_E,
          Col.COL_F}) {
        board.setDisk(row, col, Disk.BLACK);
      }
    }
    Player player = new SetokaPlayer("Setoka Player", 13L, MAX_STEP);
    player.init(Disk.WHITE);
    Optional<Square> square = player.moveDisk(board.clone(), Square.SQUARE_6_F);
    assertTrue(square.isEmpty());
    player.shutdown();
  }

  @Test
  void testMoveDisk4() {
    // ㊚㊚㊚㊚㊚㊚＿＿
    // ㊚㊚㊚㊚㊚㊚＿＿
    // ㊚㊚㊚㊚㊚㊚＿＿
    // ㊚㊚㊚㊚㊚㊚＿＿
    // ㊚㊚㊚㊛㊚㊚＿＿
    // ㊚㊚㊚㊚㊚㊚＿＿
    // ＿＿＿＿＿＿＿＿
    // ＿＿＿＿＿＿＿＿
    Board board = new Board();
    board.clear();
    for (Row row : new Row[]{Row.ROW_1, Row.ROW_2, Row.ROW_3, Row.ROW_4, Row.ROW_5, Row.ROW_6}) {
      for (Col col : new Col[]{Col.COL_A, Col.COL_B, Col.COL_C, Col.COL_D, Col.COL_E,
          Col.COL_F}) {
        board.setDisk(row, col, Disk.BLACK);
      }
    }
    board.setDisk(Square.SQUARE_5_D, Disk.WHITE);

    List<Square> expects = Arrays.asList(
        Square.SQUARE_7_B, Square.SQUARE_7_D, Square.SQUARE_7_F,
        Square.SQUARE_2_G, Square.SQUARE_5_G
    );
    for (long seed = 0; seed < 10; seed++) {
      Player player = new SetokaPlayer("Setoka Player", seed, MAX_STEP);
      player.init(Disk.WHITE);
      Optional<Square> square = player.moveDisk(board.clone(), Square.SQUARE_6_F);
      assertTrue(square.isPresent());
      assertTrue(expects.contains(square.orElse(null)));
      player.shutdown();
    }
  }

  @Test
  void compareWithLemonPlayer() {
    for (long i = 0; i < 3; i++) {
      for (int step = 3; step < 5; step++) {
        Board board1 = new Board();
        board1.init();
        Board board2 = board1.clone();
        CitrusPlayer player1 = new SetokaPlayer("Setoka", i, step);
        CitrusPlayer player2 = new LemonPlayer("Lemon", i, step);
        Player randomPlayer1 = new RandomPlayer("Random", i);
        Player randomPlayer2 = new RandomPlayer("Random", i);
        player1.init(Disk.WHITE);
        player2.init(Disk.WHITE);
        randomPlayer1.init(Disk.BLACK);
        randomPlayer2.init(Disk.BLACK);
        Square moved1 = null;
        Square moved2 = null;
        for (int j = 0; j < 5; j++) {
          List<Square> candidates1 = player1.allCandidates(board1, moved1);
          List<Square> candidates2 = player2.allCandidates(board2, moved2);
          assertEquals(candidates1.size(), candidates2.size());
          assertNotEquals(0, candidates1.size());
          System.err.printf("Candidates: %d%n", candidates1.size());
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
  }
}
