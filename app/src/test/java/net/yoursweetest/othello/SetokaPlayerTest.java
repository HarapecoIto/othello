package net.yoursweetest.othello;

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

public class SetokaPlayerTest {

  static final int MAX_STEP = 5;

  @Test
  void testMoveDisk() {
    {
      Board board = new Board();
      board.init();
      List<Square> expects = Arrays.asList(
          Square.SQUARE_3_E, Square.SQUARE_4_F, Square.SQUARE_5_C, Square.SQUARE_6_D
      );
      for (long seed = 0; seed < 10; seed++) {
        Player player = new SetokaPlayer("Setoka Player", seed, MAX_STEP);
        player.init(Disk.WHITE);
        Optional<Square> square = player.moveDisk(board.clone());
        assertTrue(square.isPresent());
        assertTrue(expects.contains(square.orElse(null)));
      }
    }
    {
      Board board = new Board();
      board.init();
      List<Square> expects = Arrays.asList(
          Square.SQUARE_3_D, Square.SQUARE_4_C, Square.SQUARE_5_F, Square.SQUARE_6_E
      );
      for (long seed = 0; seed < 10; seed++) {
        Player player = new SetokaPlayer("Setoka Player", seed, MAX_STEP);
        player.init(Disk.BLACK);
        Optional<Square> square = player.moveDisk(board.clone());
        assertTrue(square.isPresent());
        assertTrue(expects.contains(square.orElse(null)));
      }
    }
    {
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
      Optional<Square> square = player.moveDisk(board.clone());
      assertTrue(square.isEmpty());
    }
    {
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
        Optional<Square> square = player.moveDisk(board.clone());
        assertTrue(square.isPresent());
        assertTrue(expects.contains(square.orElse(null)));
      }
    }
  }

}
