package othello;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import othello.base.Board;
import othello.base.Col;
import othello.base.Row;
import othello.base.Square;
import othello.base.Stone;

public class ToolsTest {

  @Test
  void testCountStones() {
    Board board = new Board();
    board.clear();
    for (Col col : Col.values()) {
      board.setStone(Row.ROW_1, col, Stone.BLACK);
      board.setStone(Row.ROW_2, col, Stone.WHITE);
    }
    board.setStone(Row.ROW_3, Col.COL_A, Stone.BLACK);
    board.setStone(Row.ROW_4, Col.COL_D, Stone.BLACK);
    board.setStone(Row.ROW_5, Col.COL_F, Stone.BLACK);
    board.setStone(Row.ROW_8, Col.COL_H, Stone.BLACK);
    board.setStone(Row.ROW_6, Col.COL_D, Stone.WHITE);
    board.setStone(Row.ROW_7, Col.COL_F, Stone.WHITE);
    assertEquals(12, Tools.countStones(board, Stone.BLACK));
    assertEquals(10, Tools.countStones(board, Stone.WHITE));
  }

  @Test
  void testCountToTake() {
    Board board = new Board();
    board.init();
    assertEquals(1, Tools.countToTake(board, Square.SQUARE_6_E, Stone.BLACK));
    board.setStone(Square.SQUARE_6_E, Stone.BLACK);
    board.setStone(Square.SQUARE_5_E, Stone.BLACK);
    assertEquals(1, Tools.countToTake(board, Square.SQUARE_6_F, Stone.WHITE));
    board.setStone(Square.SQUARE_6_F, Stone.WHITE);
    board.setStone(Square.SQUARE_5_E, Stone.WHITE);
    assertEquals(1, Tools.countToTake(board, Square.SQUARE_5_F, Stone.BLACK));
    board.setStone(Square.SQUARE_5_E, Stone.BLACK);
    board.setStone(Square.SQUARE_5_E, Stone.BLACK);
  }

  @Test
  void testUpCountToTake() {
    Board board = new Board();
    board.init();
    // on the another stone -> error.
    assertEquals(-1, Tools.upCountToTake(board, Square.SQUARE_4_D, Stone.BLACK));
    assertEquals(-1, Tools.upCountToTake(board, Square.SQUARE_4_D, Stone.WHITE));
    // next square is out of board -> 0.
    assertEquals(0, Tools.upCountToTake(board, Square.SQUARE_1_D, Stone.BLACK));
    assertEquals(0, Tools.upCountToTake(board, Square.SQUARE_1_D, Stone.WHITE));
    // take zero
    board.clear();
    board.setStone(Square.SQUARE_1_B, Stone.BLACK);
    assertEquals(0, Tools.upCountToTake(board, Square.SQUARE_2_B, Stone.BLACK));
    // take one
    board.clear();
    board.setStone(Square.SQUARE_1_B, Stone.BLACK);
    board.setStone(Square.SQUARE_2_B, Stone.WHITE);
    assertEquals(1, Tools.upCountToTake(board, Square.SQUARE_3_B, Stone.BLACK));
    // take two
    board.clear();
    board.setStone(Square.SQUARE_1_B, Stone.BLACK);
    board.setStone(Square.SQUARE_2_B, Stone.WHITE);
    board.setStone(Square.SQUARE_3_B, Stone.WHITE);
    assertEquals(2, Tools.upCountToTake(board, Square.SQUARE_4_B, Stone.BLACK));
    // take three
    board.clear();
    board.setStone(Square.SQUARE_1_B, Stone.BLACK);
    board.setStone(Square.SQUARE_2_B, Stone.WHITE);
    board.setStone(Square.SQUARE_3_B, Stone.WHITE);
    board.setStone(Square.SQUARE_4_B, Stone.WHITE);
    assertEquals(3, Tools.upCountToTake(board, Square.SQUARE_5_B, Stone.BLACK));
    // take four
    board.clear();
    board.setStone(Square.SQUARE_1_B, Stone.BLACK);
    board.setStone(Square.SQUARE_2_B, Stone.WHITE);
    board.setStone(Square.SQUARE_3_B, Stone.WHITE);
    board.setStone(Square.SQUARE_4_B, Stone.WHITE);
    board.setStone(Square.SQUARE_5_B, Stone.WHITE);
    assertEquals(4, Tools.upCountToTake(board, Square.SQUARE_6_B, Stone.BLACK));
    // take five
    board.clear();
    board.setStone(Square.SQUARE_1_B, Stone.BLACK);
    board.setStone(Square.SQUARE_2_B, Stone.WHITE);
    board.setStone(Square.SQUARE_3_B, Stone.WHITE);
    board.setStone(Square.SQUARE_4_B, Stone.WHITE);
    board.setStone(Square.SQUARE_5_B, Stone.WHITE);
    board.setStone(Square.SQUARE_6_B, Stone.WHITE);
    assertEquals(5, Tools.upCountToTake(board, Square.SQUARE_7_B, Stone.BLACK));
    // take six
    board.clear();
    board.setStone(Square.SQUARE_1_B, Stone.BLACK);
    board.setStone(Square.SQUARE_2_B, Stone.WHITE);
    board.setStone(Square.SQUARE_3_B, Stone.WHITE);
    board.setStone(Square.SQUARE_4_B, Stone.WHITE);
    board.setStone(Square.SQUARE_5_B, Stone.WHITE);
    board.setStone(Square.SQUARE_6_B, Stone.WHITE);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    assertEquals(6, Tools.upCountToTake(board, Square.SQUARE_8_B, Stone.BLACK));
    // change black <-> white
    board.clear();
    board.setStone(Square.SQUARE_1_B, Stone.WHITE);
    board.setStone(Square.SQUARE_2_B, Stone.BLACK);
    board.setStone(Square.SQUARE_3_B, Stone.BLACK);
    board.setStone(Square.SQUARE_4_B, Stone.BLACK);
    board.setStone(Square.SQUARE_5_B, Stone.BLACK);
    board.setStone(Square.SQUARE_6_B, Stone.BLACK);
    board.setStone(Square.SQUARE_7_B, Stone.BLACK);
    assertEquals(6, Tools.upCountToTake(board, Square.SQUARE_8_B, Stone.WHITE));
    // terminated by empty square -> 0
    board.clear();
    board.setStone(Square.SQUARE_1_B, null);
    board.setStone(Square.SQUARE_2_B, Stone.WHITE);
    board.setStone(Square.SQUARE_3_B, Stone.WHITE);
    board.setStone(Square.SQUARE_4_B, Stone.WHITE);
    board.setStone(Square.SQUARE_5_B, Stone.WHITE);
    board.setStone(Square.SQUARE_6_B, Stone.WHITE);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    assertEquals(0, Tools.upCountToTake(board, Square.SQUARE_8_B, Stone.BLACK));

  }

  @Test
  void testDownCountToTake() {
    Board board = new Board();
    board.init();
    // on the another stone -> error.
    assertEquals(-1, Tools.downCountToTake(board, Square.SQUARE_5_D, Stone.BLACK));
    assertEquals(-1, Tools.downCountToTake(board, Square.SQUARE_5_D, Stone.WHITE));
    // next square is out of board -> 0.
    assertEquals(0, Tools.downCountToTake(board, Square.SQUARE_8_D, Stone.BLACK));
    assertEquals(0, Tools.downCountToTake(board, Square.SQUARE_8_D, Stone.WHITE));
    // next stone is mine -> 0
    assertEquals(0, Tools.downCountToTake(board, Square.SQUARE_3_E, Stone.BLACK));
    assertEquals(0, Tools.downCountToTake(board, Square.SQUARE_3_D, Stone.WHITE));
    // take one
    board.clear();
    board.setStone(Square.SQUARE_8_B, Stone.BLACK);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    assertEquals(1, Tools.downCountToTake(board, Square.SQUARE_6_B, Stone.BLACK));
    // take two
    board.clear();
    board.setStone(Square.SQUARE_8_B, Stone.BLACK);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    board.setStone(Square.SQUARE_6_B, Stone.WHITE);
    assertEquals(2, Tools.downCountToTake(board, Square.SQUARE_5_B, Stone.BLACK));
    // take three
    board.clear();
    board.setStone(Square.SQUARE_8_B, Stone.BLACK);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    board.setStone(Square.SQUARE_6_B, Stone.WHITE);
    board.setStone(Square.SQUARE_5_B, Stone.WHITE);
    assertEquals(3, Tools.downCountToTake(board, Square.SQUARE_4_B, Stone.BLACK));
    // take four
    board.clear();
    board.setStone(Square.SQUARE_8_B, Stone.BLACK);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    board.setStone(Square.SQUARE_6_B, Stone.WHITE);
    board.setStone(Square.SQUARE_5_B, Stone.WHITE);
    board.setStone(Square.SQUARE_4_B, Stone.WHITE);
    assertEquals(4, Tools.downCountToTake(board, Square.SQUARE_3_B, Stone.BLACK));
    // take five
    board.clear();
    board.setStone(Square.SQUARE_8_B, Stone.BLACK);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    board.setStone(Square.SQUARE_6_B, Stone.WHITE);
    board.setStone(Square.SQUARE_5_B, Stone.WHITE);
    board.setStone(Square.SQUARE_4_B, Stone.WHITE);
    board.setStone(Square.SQUARE_3_B, Stone.WHITE);
    assertEquals(5, Tools.downCountToTake(board, Square.SQUARE_2_B, Stone.BLACK));
    // take six
    board.clear();
    board.setStone(Square.SQUARE_8_B, Stone.BLACK);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    board.setStone(Square.SQUARE_6_B, Stone.WHITE);
    board.setStone(Square.SQUARE_5_B, Stone.WHITE);
    board.setStone(Square.SQUARE_4_B, Stone.WHITE);
    board.setStone(Square.SQUARE_3_B, Stone.WHITE);
    board.setStone(Square.SQUARE_2_B, Stone.WHITE);
    assertEquals(6, Tools.downCountToTake(board, Square.SQUARE_1_B, Stone.BLACK));
    // change black <-> white
    board.clear();
    board.setStone(Square.SQUARE_8_B, Stone.WHITE);
    board.setStone(Square.SQUARE_7_B, Stone.BLACK);
    board.setStone(Square.SQUARE_6_B, Stone.BLACK);
    board.setStone(Square.SQUARE_5_B, Stone.BLACK);
    board.setStone(Square.SQUARE_4_B, Stone.BLACK);
    board.setStone(Square.SQUARE_3_B, Stone.BLACK);
    board.setStone(Square.SQUARE_2_B, Stone.BLACK);
    assertEquals(6, Tools.downCountToTake(board, Square.SQUARE_1_B, Stone.WHITE));
    // terminated by empty square -> 0
    board.clear();
    board.setStone(Square.SQUARE_8_B, null);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    board.setStone(Square.SQUARE_6_B, Stone.WHITE);
    board.setStone(Square.SQUARE_5_B, Stone.WHITE);
    board.setStone(Square.SQUARE_4_B, Stone.WHITE);
    board.setStone(Square.SQUARE_3_B, Stone.WHITE);
    board.setStone(Square.SQUARE_2_B, Stone.WHITE);
    assertEquals(0, Tools.downCountToTake(board, Square.SQUARE_1_B, Stone.BLACK));
  }

  @Test
  void testLeftCountToTake() {
    Board board = new Board();
    board.init();
    // on the another stone -> error.
    assertEquals(-1, Tools.leftCountToTake(board, Square.SQUARE_4_D, Stone.BLACK));
    assertEquals(-1, Tools.leftCountToTake(board, Square.SQUARE_4_D, Stone.WHITE));
    // next square is out of board -> 0.
    assertEquals(0, Tools.leftCountToTake(board, Square.SQUARE_1_A, Stone.BLACK));
    assertEquals(0, Tools.leftCountToTake(board, Square.SQUARE_1_A, Stone.WHITE));
    // take zero
    board.clear();
    board.setStone(Square.SQUARE_7_A, Stone.BLACK);
    assertEquals(0, Tools.leftCountToTake(board, Square.SQUARE_7_B, Stone.BLACK));
    // take one
    board.clear();
    board.setStone(Square.SQUARE_7_A, Stone.BLACK);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    assertEquals(1, Tools.leftCountToTake(board, Square.SQUARE_7_C, Stone.BLACK));
    // take two
    board.clear();
    board.setStone(Square.SQUARE_7_A, Stone.BLACK);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    board.setStone(Square.SQUARE_7_C, Stone.WHITE);
    assertEquals(2, Tools.leftCountToTake(board, Square.SQUARE_7_D, Stone.BLACK));
    // take three
    board.clear();
    board.setStone(Square.SQUARE_7_A, Stone.BLACK);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    board.setStone(Square.SQUARE_7_C, Stone.WHITE);
    board.setStone(Square.SQUARE_7_D, Stone.WHITE);
    assertEquals(3, Tools.leftCountToTake(board, Square.SQUARE_7_E, Stone.BLACK));
    // take four
    board.clear();
    board.setStone(Square.SQUARE_7_A, Stone.BLACK);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    board.setStone(Square.SQUARE_7_C, Stone.WHITE);
    board.setStone(Square.SQUARE_7_D, Stone.WHITE);
    board.setStone(Square.SQUARE_7_E, Stone.WHITE);
    assertEquals(4, Tools.leftCountToTake(board, Square.SQUARE_7_F, Stone.BLACK));
    // take five
    board.clear();
    board.setStone(Square.SQUARE_7_A, Stone.BLACK);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    board.setStone(Square.SQUARE_7_C, Stone.WHITE);
    board.setStone(Square.SQUARE_7_D, Stone.WHITE);
    board.setStone(Square.SQUARE_7_E, Stone.WHITE);
    board.setStone(Square.SQUARE_7_F, Stone.WHITE);
    assertEquals(5, Tools.leftCountToTake(board, Square.SQUARE_7_G, Stone.BLACK));
    // take six
    board.clear();
    board.setStone(Square.SQUARE_7_A, Stone.BLACK);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    board.setStone(Square.SQUARE_7_C, Stone.WHITE);
    board.setStone(Square.SQUARE_7_D, Stone.WHITE);
    board.setStone(Square.SQUARE_7_E, Stone.WHITE);
    board.setStone(Square.SQUARE_7_F, Stone.WHITE);
    board.setStone(Square.SQUARE_7_G, Stone.WHITE);
    assertEquals(6, Tools.leftCountToTake(board, Square.SQUARE_7_H, Stone.BLACK));
    // change black <-> white
    board.clear();
    board.setStone(Square.SQUARE_7_A, Stone.WHITE);
    board.setStone(Square.SQUARE_7_B, Stone.BLACK);
    board.setStone(Square.SQUARE_7_C, Stone.BLACK);
    board.setStone(Square.SQUARE_7_D, Stone.BLACK);
    board.setStone(Square.SQUARE_7_E, Stone.BLACK);
    board.setStone(Square.SQUARE_7_F, Stone.BLACK);
    board.setStone(Square.SQUARE_7_G, Stone.BLACK);
    assertEquals(6, Tools.leftCountToTake(board, Square.SQUARE_7_H, Stone.WHITE));
    // terminated by empty square -> 0
    board.clear();
    board.setStone(Square.SQUARE_7_A, null);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    board.setStone(Square.SQUARE_7_C, Stone.WHITE);
    board.setStone(Square.SQUARE_7_D, Stone.WHITE);
    board.setStone(Square.SQUARE_7_E, Stone.WHITE);
    board.setStone(Square.SQUARE_7_F, Stone.WHITE);
    board.setStone(Square.SQUARE_7_G, Stone.WHITE);
    assertEquals(0, Tools.leftCountToTake(board, Square.SQUARE_7_H, Stone.BLACK));
  }

  @Test
  void testRightCountToTake() {
    Board board = new Board();
    board.init();
    // on the another stone -> error.
    assertEquals(-1, Tools.rightCountToTake(board, Square.SQUARE_4_E, Stone.BLACK));
    assertEquals(-1, Tools.rightCountToTake(board, Square.SQUARE_4_E, Stone.WHITE));
    // next square is out of board -> 0.
    assertEquals(0, Tools.rightCountToTake(board, Square.SQUARE_1_H, Stone.BLACK));
    assertEquals(0, Tools.rightCountToTake(board, Square.SQUARE_1_H, Stone.WHITE));
    // take zero
    board.clear();
    board.setStone(Square.SQUARE_7_H, Stone.BLACK);
    assertEquals(0, Tools.rightCountToTake(board, Square.SQUARE_7_G, Stone.BLACK));
    // take one
    board.clear();
    board.setStone(Square.SQUARE_7_H, Stone.BLACK);
    board.setStone(Square.SQUARE_7_G, Stone.WHITE);
    assertEquals(1, Tools.rightCountToTake(board, Square.SQUARE_7_F, Stone.BLACK));
    // take two
    board.clear();
    board.setStone(Square.SQUARE_7_H, Stone.BLACK);
    board.setStone(Square.SQUARE_7_G, Stone.WHITE);
    board.setStone(Square.SQUARE_7_F, Stone.WHITE);
    assertEquals(2, Tools.rightCountToTake(board, Square.SQUARE_7_E, Stone.BLACK));
    // take three
    board.clear();
    board.setStone(Square.SQUARE_7_H, Stone.BLACK);
    board.setStone(Square.SQUARE_7_G, Stone.WHITE);
    board.setStone(Square.SQUARE_7_F, Stone.WHITE);
    board.setStone(Square.SQUARE_7_E, Stone.WHITE);
    assertEquals(3, Tools.rightCountToTake(board, Square.SQUARE_7_D, Stone.BLACK));
    // take four
    board.clear();
    board.setStone(Square.SQUARE_7_H, Stone.BLACK);
    board.setStone(Square.SQUARE_7_G, Stone.WHITE);
    board.setStone(Square.SQUARE_7_F, Stone.WHITE);
    board.setStone(Square.SQUARE_7_E, Stone.WHITE);
    board.setStone(Square.SQUARE_7_D, Stone.WHITE);
    assertEquals(4, Tools.rightCountToTake(board, Square.SQUARE_7_C, Stone.BLACK));
    // take five
    board.clear();
    board.setStone(Square.SQUARE_7_H, Stone.BLACK);
    board.setStone(Square.SQUARE_7_G, Stone.WHITE);
    board.setStone(Square.SQUARE_7_F, Stone.WHITE);
    board.setStone(Square.SQUARE_7_E, Stone.WHITE);
    board.setStone(Square.SQUARE_7_D, Stone.WHITE);
    board.setStone(Square.SQUARE_7_C, Stone.WHITE);
    assertEquals(5, Tools.rightCountToTake(board, Square.SQUARE_7_B, Stone.BLACK));
    // take six
    board.clear();
    board.setStone(Square.SQUARE_7_H, Stone.BLACK);
    board.setStone(Square.SQUARE_7_G, Stone.WHITE);
    board.setStone(Square.SQUARE_7_F, Stone.WHITE);
    board.setStone(Square.SQUARE_7_E, Stone.WHITE);
    board.setStone(Square.SQUARE_7_D, Stone.WHITE);
    board.setStone(Square.SQUARE_7_C, Stone.WHITE);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    assertEquals(6, Tools.rightCountToTake(board, Square.SQUARE_7_A, Stone.BLACK));
    // change black <-> white
    board.clear();
    board.setStone(Square.SQUARE_7_H, Stone.WHITE);
    board.setStone(Square.SQUARE_7_G, Stone.BLACK);
    board.setStone(Square.SQUARE_7_F, Stone.BLACK);
    board.setStone(Square.SQUARE_7_E, Stone.BLACK);
    board.setStone(Square.SQUARE_7_D, Stone.BLACK);
    board.setStone(Square.SQUARE_7_C, Stone.BLACK);
    board.setStone(Square.SQUARE_7_B, Stone.BLACK);
    assertEquals(6, Tools.rightCountToTake(board, Square.SQUARE_7_A, Stone.WHITE));
    // terminated by empty square -> 0
    board.clear();
    board.setStone(Square.SQUARE_7_H, null);
    board.setStone(Square.SQUARE_7_G, Stone.WHITE);
    board.setStone(Square.SQUARE_7_F, Stone.WHITE);
    board.setStone(Square.SQUARE_7_E, Stone.WHITE);
    board.setStone(Square.SQUARE_7_D, Stone.WHITE);
    board.setStone(Square.SQUARE_7_C, Stone.WHITE);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    assertEquals(0, Tools.rightCountToTake(board, Square.SQUARE_7_A, Stone.BLACK));
  }

  @Test
  void testUpLeftCountToTake() {
    Board board = new Board();
    board.init();
    board.setStone(Square.SQUARE_3_C, null);
    board.setStone(Square.SQUARE_3_D, null);
    board.setStone(Square.SQUARE_4_C, null);
    board.setStone(Square.SQUARE_5_D, null);
    // on the another stone -> error.
    assertEquals(-1, Tools.upLeftCountToTake(board, Square.SQUARE_4_D, Stone.BLACK));
    assertEquals(-1, Tools.upLeftCountToTake(board, Square.SQUARE_4_D, Stone.WHITE));
    // next square is out of board -> 0.
    assertEquals(0, Tools.upLeftCountToTake(board, Square.SQUARE_1_A, Stone.BLACK));
    assertEquals(0, Tools.upLeftCountToTake(board, Square.SQUARE_1_A, Stone.WHITE));
    // take zero
    board.clear();
    board.setStone(Square.SQUARE_1_A, Stone.BLACK);
    assertEquals(0, Tools.upLeftCountToTake(board, Square.SQUARE_2_B, Stone.BLACK));
    // take one
    board.clear();
    board.setStone(Square.SQUARE_1_A, Stone.BLACK);
    board.setStone(Square.SQUARE_2_B, Stone.WHITE);
    assertEquals(1, Tools.upLeftCountToTake(board, Square.SQUARE_3_C, Stone.BLACK));
    // take two
    board.clear();
    board.setStone(Square.SQUARE_1_A, Stone.BLACK);
    board.setStone(Square.SQUARE_2_B, Stone.WHITE);
    board.setStone(Square.SQUARE_3_C, Stone.WHITE);
    assertEquals(2, Tools.upLeftCountToTake(board, Square.SQUARE_4_D, Stone.BLACK));
    // take three
    board.clear();
    board.setStone(Square.SQUARE_1_A, Stone.BLACK);
    board.setStone(Square.SQUARE_2_B, Stone.WHITE);
    board.setStone(Square.SQUARE_3_C, Stone.WHITE);
    board.setStone(Square.SQUARE_4_D, Stone.WHITE);
    assertEquals(3, Tools.upLeftCountToTake(board, Square.SQUARE_5_E, Stone.BLACK));
    // take four
    board.clear();
    board.setStone(Square.SQUARE_1_A, Stone.BLACK);
    board.setStone(Square.SQUARE_2_B, Stone.WHITE);
    board.setStone(Square.SQUARE_3_C, Stone.WHITE);
    board.setStone(Square.SQUARE_4_D, Stone.WHITE);
    board.setStone(Square.SQUARE_5_E, Stone.WHITE);
    assertEquals(4, Tools.upLeftCountToTake(board, Square.SQUARE_6_F, Stone.BLACK));
    // take five
    board.clear();
    board.setStone(Square.SQUARE_1_A, Stone.BLACK);
    board.setStone(Square.SQUARE_2_B, Stone.WHITE);
    board.setStone(Square.SQUARE_3_C, Stone.WHITE);
    board.setStone(Square.SQUARE_4_D, Stone.WHITE);
    board.setStone(Square.SQUARE_5_E, Stone.WHITE);
    board.setStone(Square.SQUARE_6_F, Stone.WHITE);
    assertEquals(5, Tools.upLeftCountToTake(board, Square.SQUARE_7_G, Stone.BLACK));
    // take six
    board.clear();
    board.setStone(Square.SQUARE_1_A, Stone.BLACK);
    board.setStone(Square.SQUARE_2_B, Stone.WHITE);
    board.setStone(Square.SQUARE_3_C, Stone.WHITE);
    board.setStone(Square.SQUARE_4_D, Stone.WHITE);
    board.setStone(Square.SQUARE_5_E, Stone.WHITE);
    board.setStone(Square.SQUARE_6_F, Stone.WHITE);
    board.setStone(Square.SQUARE_7_G, Stone.WHITE);
    assertEquals(6, Tools.upLeftCountToTake(board, Square.SQUARE_8_H, Stone.BLACK));
    // change black <-> white
    board.clear();
    board.setStone(Square.SQUARE_1_A, Stone.WHITE);
    board.setStone(Square.SQUARE_2_B, Stone.BLACK);
    board.setStone(Square.SQUARE_3_C, Stone.BLACK);
    board.setStone(Square.SQUARE_4_D, Stone.BLACK);
    board.setStone(Square.SQUARE_5_E, Stone.BLACK);
    board.setStone(Square.SQUARE_6_F, Stone.BLACK);
    board.setStone(Square.SQUARE_7_G, Stone.BLACK);
    assertEquals(6, Tools.upLeftCountToTake(board, Square.SQUARE_8_H, Stone.WHITE));
    // terminated by empty square -> 0
    board.clear();
    board.setStone(Square.SQUARE_1_A, null);
    board.setStone(Square.SQUARE_2_B, Stone.WHITE);
    board.setStone(Square.SQUARE_3_C, Stone.WHITE);
    board.setStone(Square.SQUARE_4_D, Stone.WHITE);
    board.setStone(Square.SQUARE_5_E, Stone.WHITE);
    board.setStone(Square.SQUARE_6_F, Stone.WHITE);
    board.setStone(Square.SQUARE_7_G, Stone.WHITE);
    assertEquals(0, Tools.upLeftCountToTake(board, Square.SQUARE_8_H, Stone.BLACK));
  }

  @Test
  void testUpRightCountToTake() {
    Board board = new Board();
    board.init();
    board.setStone(Square.SQUARE_3_C, null);
    board.setStone(Square.SQUARE_3_D, null);
    board.setStone(Square.SQUARE_4_C, null);
    board.setStone(Square.SQUARE_5_D, null);
    // on the another stone -> error.
    assertEquals(-1, Tools.upRightCountToTake(board, Square.SQUARE_4_D, Stone.BLACK));
    assertEquals(-1, Tools.upRightCountToTake(board, Square.SQUARE_4_D, Stone.WHITE));
    // next square is out of board -> 0.
    assertEquals(0, Tools.upRightCountToTake(board, Square.SQUARE_1_A, Stone.BLACK));
    assertEquals(0, Tools.upRightCountToTake(board, Square.SQUARE_1_A, Stone.WHITE));
    // take zero
    board.clear();
    board.setStone(Square.SQUARE_1_H, Stone.BLACK);
    assertEquals(0, Tools.upRightCountToTake(board, Square.SQUARE_2_G, Stone.BLACK));
    // take one
    board.clear();
    board.setStone(Square.SQUARE_1_H, Stone.BLACK);
    board.setStone(Square.SQUARE_2_G, Stone.WHITE);
    assertEquals(1, Tools.upRightCountToTake(board, Square.SQUARE_3_F, Stone.BLACK));
    // take two
    board.clear();
    board.setStone(Square.SQUARE_1_H, Stone.BLACK);
    board.setStone(Square.SQUARE_2_G, Stone.WHITE);
    board.setStone(Square.SQUARE_3_F, Stone.WHITE);
    assertEquals(2, Tools.upRightCountToTake(board, Square.SQUARE_4_E, Stone.BLACK));
    // take three
    board.clear();
    board.setStone(Square.SQUARE_1_H, Stone.BLACK);
    board.setStone(Square.SQUARE_2_G, Stone.WHITE);
    board.setStone(Square.SQUARE_3_F, Stone.WHITE);
    board.setStone(Square.SQUARE_4_E, Stone.WHITE);
    assertEquals(3, Tools.upRightCountToTake(board, Square.SQUARE_5_D, Stone.BLACK));
    // take four
    board.clear();
    board.setStone(Square.SQUARE_1_H, Stone.BLACK);
    board.setStone(Square.SQUARE_2_G, Stone.WHITE);
    board.setStone(Square.SQUARE_3_F, Stone.WHITE);
    board.setStone(Square.SQUARE_4_E, Stone.WHITE);
    board.setStone(Square.SQUARE_5_D, Stone.WHITE);
    assertEquals(4, Tools.upRightCountToTake(board, Square.SQUARE_6_C, Stone.BLACK));
    // take five
    board.clear();
    board.setStone(Square.SQUARE_1_H, Stone.BLACK);
    board.setStone(Square.SQUARE_2_G, Stone.WHITE);
    board.setStone(Square.SQUARE_3_F, Stone.WHITE);
    board.setStone(Square.SQUARE_4_E, Stone.WHITE);
    board.setStone(Square.SQUARE_5_D, Stone.WHITE);
    board.setStone(Square.SQUARE_6_C, Stone.WHITE);
    assertEquals(5, Tools.upRightCountToTake(board, Square.SQUARE_7_B, Stone.BLACK));
    // take six
    board.clear();
    board.setStone(Square.SQUARE_1_H, Stone.BLACK);
    board.setStone(Square.SQUARE_2_G, Stone.WHITE);
    board.setStone(Square.SQUARE_3_F, Stone.WHITE);
    board.setStone(Square.SQUARE_4_E, Stone.WHITE);
    board.setStone(Square.SQUARE_5_D, Stone.WHITE);
    board.setStone(Square.SQUARE_6_C, Stone.WHITE);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    assertEquals(6, Tools.upRightCountToTake(board, Square.SQUARE_8_A, Stone.BLACK));
    // change black <-> white
    board.clear();
    board.setStone(Square.SQUARE_1_H, Stone.WHITE);
    board.setStone(Square.SQUARE_2_G, Stone.BLACK);
    board.setStone(Square.SQUARE_3_F, Stone.BLACK);
    board.setStone(Square.SQUARE_4_E, Stone.BLACK);
    board.setStone(Square.SQUARE_5_D, Stone.BLACK);
    board.setStone(Square.SQUARE_6_C, Stone.BLACK);
    board.setStone(Square.SQUARE_7_B, Stone.BLACK);
    assertEquals(6, Tools.upRightCountToTake(board, Square.SQUARE_8_A, Stone.WHITE));
    // terminated by empty square -> 0
    board.clear();
    board.setStone(Square.SQUARE_1_H, null);
    board.setStone(Square.SQUARE_2_G, Stone.WHITE);
    board.setStone(Square.SQUARE_3_F, Stone.WHITE);
    board.setStone(Square.SQUARE_4_E, Stone.WHITE);
    board.setStone(Square.SQUARE_5_D, Stone.WHITE);
    board.setStone(Square.SQUARE_6_C, Stone.WHITE);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    assertEquals(0, Tools.upRightCountToTake(board, Square.SQUARE_8_A, Stone.BLACK));
  }

  @Test
  void testDownLeftCountToTake() {
    Board board = new Board();
    board.init();
    board.setStone(Square.SQUARE_3_C, null);
    board.setStone(Square.SQUARE_3_D, null);
    board.setStone(Square.SQUARE_4_C, null);
    board.setStone(Square.SQUARE_5_D, null);
    // on the another stone -> error.
    assertEquals(-1, Tools.downLeftCountToTake(board, Square.SQUARE_4_D, Stone.BLACK));
    assertEquals(-1, Tools.downLeftCountToTake(board, Square.SQUARE_4_D, Stone.WHITE));
    // next square is out of board -> 0.
    assertEquals(0, Tools.downLeftCountToTake(board, Square.SQUARE_1_A, Stone.BLACK));
    assertEquals(0, Tools.downLeftCountToTake(board, Square.SQUARE_1_A, Stone.WHITE));
    // take zero
    board.clear();
    board.setStone(Square.SQUARE_8_A, Stone.BLACK);
    assertEquals(0, Tools.downLeftCountToTake(board, Square.SQUARE_7_B, Stone.BLACK));
    // take one
    board.clear();
    board.setStone(Square.SQUARE_8_A, Stone.BLACK);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    assertEquals(1, Tools.downLeftCountToTake(board, Square.SQUARE_6_C, Stone.BLACK));
    // take two
    board.clear();
    board.setStone(Square.SQUARE_8_A, Stone.BLACK);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    board.setStone(Square.SQUARE_6_C, Stone.WHITE);
    assertEquals(2, Tools.downLeftCountToTake(board, Square.SQUARE_5_D, Stone.BLACK));
    // take three
    board.clear();
    board.setStone(Square.SQUARE_8_A, Stone.BLACK);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    board.setStone(Square.SQUARE_6_C, Stone.WHITE);
    board.setStone(Square.SQUARE_5_D, Stone.WHITE);
    assertEquals(3, Tools.downLeftCountToTake(board, Square.SQUARE_4_E, Stone.BLACK));
    // take four
    board.clear();
    board.setStone(Square.SQUARE_8_A, Stone.BLACK);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    board.setStone(Square.SQUARE_6_C, Stone.WHITE);
    board.setStone(Square.SQUARE_5_D, Stone.WHITE);
    board.setStone(Square.SQUARE_4_E, Stone.WHITE);
    assertEquals(4, Tools.downLeftCountToTake(board, Square.SQUARE_3_F, Stone.BLACK));
    // take five
    board.clear();
    board.setStone(Square.SQUARE_8_A, Stone.BLACK);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    board.setStone(Square.SQUARE_6_C, Stone.WHITE);
    board.setStone(Square.SQUARE_5_D, Stone.WHITE);
    board.setStone(Square.SQUARE_4_E, Stone.WHITE);
    board.setStone(Square.SQUARE_3_F, Stone.WHITE);
    assertEquals(5, Tools.downLeftCountToTake(board, Square.SQUARE_2_G, Stone.BLACK));
    // take six
    board.clear();
    board.setStone(Square.SQUARE_8_A, Stone.BLACK);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    board.setStone(Square.SQUARE_6_C, Stone.WHITE);
    board.setStone(Square.SQUARE_5_D, Stone.WHITE);
    board.setStone(Square.SQUARE_4_E, Stone.WHITE);
    board.setStone(Square.SQUARE_3_F, Stone.WHITE);
    board.setStone(Square.SQUARE_2_G, Stone.WHITE);
    assertEquals(6, Tools.downLeftCountToTake(board, Square.SQUARE_1_H, Stone.BLACK));
    // change black <-> white
    board.clear();
    board.setStone(Square.SQUARE_8_A, Stone.WHITE);
    board.setStone(Square.SQUARE_7_B, Stone.BLACK);
    board.setStone(Square.SQUARE_6_C, Stone.BLACK);
    board.setStone(Square.SQUARE_5_D, Stone.BLACK);
    board.setStone(Square.SQUARE_4_E, Stone.BLACK);
    board.setStone(Square.SQUARE_3_F, Stone.BLACK);
    board.setStone(Square.SQUARE_2_G, Stone.BLACK);
    assertEquals(6, Tools.downLeftCountToTake(board, Square.SQUARE_1_H, Stone.WHITE));
    // terminated by empty square -> 0
    board.clear();
    board.setStone(Square.SQUARE_8_A, null);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    board.setStone(Square.SQUARE_6_C, Stone.WHITE);
    board.setStone(Square.SQUARE_5_D, Stone.WHITE);
    board.setStone(Square.SQUARE_4_E, Stone.WHITE);
    board.setStone(Square.SQUARE_3_F, Stone.WHITE);
    board.setStone(Square.SQUARE_2_G, Stone.WHITE);
    assertEquals(0, Tools.downLeftCountToTake(board, Square.SQUARE_1_H, Stone.BLACK));
  }

  @Test
  void testDownRightCountToTake() {
    Board board = new Board();
    board.init();
    board.setStone(Square.SQUARE_3_C, null);
    board.setStone(Square.SQUARE_3_D, null);
    board.setStone(Square.SQUARE_4_C, null);
    board.setStone(Square.SQUARE_5_D, null);
    // on the another stone -> error.
    assertEquals(-1, Tools.downRightCountToTake(board, Square.SQUARE_4_D, Stone.BLACK));
    assertEquals(-1, Tools.downRightCountToTake(board, Square.SQUARE_4_D, Stone.WHITE));
    // next square is out of board -> 0.
    assertEquals(0, Tools.downRightCountToTake(board, Square.SQUARE_1_A, Stone.BLACK));
    assertEquals(0, Tools.downRightCountToTake(board, Square.SQUARE_1_A, Stone.WHITE));
    // take zero
    board.clear();
    board.setStone(Square.SQUARE_8_H, Stone.BLACK);
    assertEquals(0, Tools.downRightCountToTake(board, Square.SQUARE_7_G, Stone.BLACK));
    // take one
    board.clear();
    board.setStone(Square.SQUARE_8_H, Stone.BLACK);
    board.setStone(Square.SQUARE_7_G, Stone.WHITE);
    assertEquals(1, Tools.downRightCountToTake(board, Square.SQUARE_6_F, Stone.BLACK));
    // take two
    board.clear();
    board.setStone(Square.SQUARE_8_H, Stone.BLACK);
    board.setStone(Square.SQUARE_7_G, Stone.WHITE);
    board.setStone(Square.SQUARE_6_F, Stone.WHITE);
    assertEquals(2, Tools.downRightCountToTake(board, Square.SQUARE_5_E, Stone.BLACK));
    // take three
    board.clear();
    board.setStone(Square.SQUARE_8_H, Stone.BLACK);
    board.setStone(Square.SQUARE_7_G, Stone.WHITE);
    board.setStone(Square.SQUARE_6_F, Stone.WHITE);
    board.setStone(Square.SQUARE_5_E, Stone.WHITE);
    assertEquals(3, Tools.downRightCountToTake(board, Square.SQUARE_4_D, Stone.BLACK));
    // take four
    board.clear();
    board.setStone(Square.SQUARE_8_H, Stone.BLACK);
    board.setStone(Square.SQUARE_7_G, Stone.WHITE);
    board.setStone(Square.SQUARE_6_F, Stone.WHITE);
    board.setStone(Square.SQUARE_5_E, Stone.WHITE);
    board.setStone(Square.SQUARE_4_D, Stone.WHITE);
    assertEquals(4, Tools.downRightCountToTake(board, Square.SQUARE_3_C, Stone.BLACK));
    // take five
    board.clear();
    board.setStone(Square.SQUARE_8_H, Stone.BLACK);
    board.setStone(Square.SQUARE_7_G, Stone.WHITE);
    board.setStone(Square.SQUARE_6_F, Stone.WHITE);
    board.setStone(Square.SQUARE_5_E, Stone.WHITE);
    board.setStone(Square.SQUARE_4_D, Stone.WHITE);
    board.setStone(Square.SQUARE_3_C, Stone.WHITE);
    assertEquals(5, Tools.downRightCountToTake(board, Square.SQUARE_2_B, Stone.BLACK));
    // take six
    board.clear();
    board.setStone(Square.SQUARE_8_H, Stone.BLACK);
    board.setStone(Square.SQUARE_7_G, Stone.WHITE);
    board.setStone(Square.SQUARE_6_F, Stone.WHITE);
    board.setStone(Square.SQUARE_5_E, Stone.WHITE);
    board.setStone(Square.SQUARE_4_D, Stone.WHITE);
    board.setStone(Square.SQUARE_3_C, Stone.WHITE);
    board.setStone(Square.SQUARE_2_B, Stone.WHITE);
    assertEquals(6, Tools.downRightCountToTake(board, Square.SQUARE_1_A, Stone.BLACK));
    // change black <-> white
    board.clear();
    board.setStone(Square.SQUARE_8_H, Stone.WHITE);
    board.setStone(Square.SQUARE_7_G, Stone.BLACK);
    board.setStone(Square.SQUARE_6_F, Stone.BLACK);
    board.setStone(Square.SQUARE_5_E, Stone.BLACK);
    board.setStone(Square.SQUARE_4_D, Stone.BLACK);
    board.setStone(Square.SQUARE_3_C, Stone.BLACK);
    board.setStone(Square.SQUARE_2_B, Stone.BLACK);
    assertEquals(6, Tools.downRightCountToTake(board, Square.SQUARE_1_A, Stone.WHITE));
    // terminated by empty square -> 0
    board.clear();
    board.setStone(Square.SQUARE_8_H, null);
    board.setStone(Square.SQUARE_7_G, Stone.WHITE);
    board.setStone(Square.SQUARE_6_F, Stone.WHITE);
    board.setStone(Square.SQUARE_5_E, Stone.WHITE);
    board.setStone(Square.SQUARE_4_D, Stone.WHITE);
    board.setStone(Square.SQUARE_3_C, Stone.WHITE);
    board.setStone(Square.SQUARE_2_B, Stone.WHITE);
    assertEquals(0, Tools.downRightCountToTake(board, Square.SQUARE_1_A, Stone.BLACK));
  }

  @Test
  void testMove() {
    Board board = new Board();
    board.init();
    Board clone = board.clone();
    Optional<List<Square>> opt = Tools.move(clone, Square.SQUARE_4_D, Stone.BLACK);
    assertTrue(opt.isEmpty());
    assertEquals(board, clone);

    board.init();
    clone = board.clone();
    opt = Tools.move(clone, Square.SQUARE_6_D, Stone.BLACK);
    assertTrue(opt.isPresent());
    assertEquals(0, opt.get().size());
    assertEquals(board, clone);

    board.init();
    clone = board.clone();
    opt = Tools.move(clone, Square.SQUARE_6_E, Stone.BLACK);
    assertTrue(opt.isPresent());
    assertEquals(1, opt.get().size());
    assertEquals(Square.SQUARE_5_E, opt.get().get(0));
    board.setStone(Square.SQUARE_5_E, Stone.BLACK);
    board.setStone(Square.SQUARE_6_E, Stone.BLACK);
    assertEquals(board, clone);

    // ㊚㊚㊚㊚㊚㊚＿＿
    // ㊚㊛㊛㊛㊛㊚＿＿
    // ㊚㊛＿㊛㊛㊚＿＿
    // ㊚㊛㊛㊛㊛㊚＿＿
    // ㊚㊛㊛㊛㊛㊚＿＿
    // ㊚㊚㊚㊚㊚㊚＿＿
    // ＿＿＿＿＿＿＿＿
    // ＿＿＿＿＿＿＿＿
    board.clear();
    for (Row row : new Row[]{Row.ROW_1, Row.ROW_2, Row.ROW_3, Row.ROW_4, Row.ROW_5, Row.ROW_6}) {
      for (Col col : new Col[]{Col.COL_A, Col.COL_B, Col.COL_C, Col.COL_D, Col.COL_E, Col.COL_F}) {
        board.setStone(row, col, Stone.BLACK);
      }
    }
    for (Row row : new Row[]{Row.ROW_2, Row.ROW_3, Row.ROW_4, Row.ROW_5}) {
      for (Col col : new Col[]{Col.COL_B, Col.COL_C, Col.COL_D, Col.COL_E}) {
        board.setStone(row, col, Stone.WHITE);
      }
    }
    board.setStone(Square.SQUARE_3_C, null);

    // move
    Optional<List<Square>> result = Tools.move(board, Square.SQUARE_3_C, Stone.BLACK);
    assertTrue(result.isPresent());
    assertEquals(11, result.get().size());
    Square[] taken = new Square[]{
        Square.SQUARE_2_B, Square.SQUARE_2_C, Square.SQUARE_2_D, Square.SQUARE_3_B,
        Square.SQUARE_3_D, Square.SQUARE_3_E, Square.SQUARE_4_B, Square.SQUARE_4_C,
        Square.SQUARE_4_D, Square.SQUARE_5_C, Square.SQUARE_5_E
    };
    assertEquals(0, Arrays.stream(taken).filter(sq -> !result.get().contains(sq)).count());

    // ㊚㊚㊚㊚㊚㊚＿＿
    // ㊚㊚㊚㊚㊛㊚＿＿
    // ㊚㊚㊚㊚㊚㊚＿＿
    // ㊚㊚㊚㊚㊛㊚＿＿
    // ㊚㊛㊚㊛㊚㊚＿＿
    // ㊚㊚㊚㊚㊚㊚＿＿
    // ＿＿＿＿＿＿＿＿
    // ＿＿＿＿＿＿＿＿
    Board expects = new Board();
    for (Row row : new Row[]{Row.ROW_1, Row.ROW_2, Row.ROW_3, Row.ROW_4, Row.ROW_5, Row.ROW_6}) {
      for (Col col : new Col[]{Col.COL_A, Col.COL_B, Col.COL_C, Col.COL_D, Col.COL_E, Col.COL_F}) {
        expects.setStone(row, col, Stone.BLACK);
      }
    }
    expects.setStone(Square.SQUARE_5_B, Stone.WHITE);
    expects.setStone(Square.SQUARE_5_D, Stone.WHITE);
    expects.setStone(Square.SQUARE_2_E, Stone.WHITE);
    expects.setStone(Square.SQUARE_4_E, Stone.WHITE);
    assertEquals(expects, board);
  }

}
