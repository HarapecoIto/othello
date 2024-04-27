package othello.rule;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import othello.base.Board;
import othello.base.Square;
import othello.base.Stone;

public class ToolsTest {

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
    board.init();
    board.setStone(Square.SQUARE_1_B, Stone.BLACK);
    assertEquals(0, Tools.upCountToTake(board, Square.SQUARE_2_B, Stone.BLACK));
    // take one
    board.init();
    board.setStone(Square.SQUARE_1_B, Stone.BLACK);
    board.setStone(Square.SQUARE_2_B, Stone.WHITE);
    assertEquals(1, Tools.upCountToTake(board, Square.SQUARE_3_B, Stone.BLACK));
    // take two
    board.init();
    board.setStone(Square.SQUARE_1_B, Stone.BLACK);
    board.setStone(Square.SQUARE_2_B, Stone.WHITE);
    board.setStone(Square.SQUARE_3_B, Stone.WHITE);
    assertEquals(2, Tools.upCountToTake(board, Square.SQUARE_4_B, Stone.BLACK));
    // take three
    board.init();
    board.setStone(Square.SQUARE_1_B, Stone.BLACK);
    board.setStone(Square.SQUARE_2_B, Stone.WHITE);
    board.setStone(Square.SQUARE_3_B, Stone.WHITE);
    board.setStone(Square.SQUARE_4_B, Stone.WHITE);
    assertEquals(3, Tools.upCountToTake(board, Square.SQUARE_5_B, Stone.BLACK));
    // take four
    board.init();
    board.setStone(Square.SQUARE_1_B, Stone.BLACK);
    board.setStone(Square.SQUARE_2_B, Stone.WHITE);
    board.setStone(Square.SQUARE_3_B, Stone.WHITE);
    board.setStone(Square.SQUARE_4_B, Stone.WHITE);
    board.setStone(Square.SQUARE_5_B, Stone.WHITE);
    assertEquals(4, Tools.upCountToTake(board, Square.SQUARE_6_B, Stone.BLACK));
    // take five
    board.init();
    board.setStone(Square.SQUARE_1_B, Stone.BLACK);
    board.setStone(Square.SQUARE_2_B, Stone.WHITE);
    board.setStone(Square.SQUARE_3_B, Stone.WHITE);
    board.setStone(Square.SQUARE_4_B, Stone.WHITE);
    board.setStone(Square.SQUARE_5_B, Stone.WHITE);
    board.setStone(Square.SQUARE_6_B, Stone.WHITE);
    assertEquals(5, Tools.upCountToTake(board, Square.SQUARE_7_B, Stone.BLACK));
    // take six
    board.init();
    board.setStone(Square.SQUARE_1_B, Stone.BLACK);
    board.setStone(Square.SQUARE_2_B, Stone.WHITE);
    board.setStone(Square.SQUARE_3_B, Stone.WHITE);
    board.setStone(Square.SQUARE_4_B, Stone.WHITE);
    board.setStone(Square.SQUARE_5_B, Stone.WHITE);
    board.setStone(Square.SQUARE_6_B, Stone.WHITE);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    assertEquals(6, Tools.upCountToTake(board, Square.SQUARE_8_B, Stone.BLACK));
    // change black <-> white
    board.init();
    board.setStone(Square.SQUARE_1_B, Stone.WHITE);
    board.setStone(Square.SQUARE_2_B, Stone.BLACK);
    board.setStone(Square.SQUARE_3_B, Stone.BLACK);
    board.setStone(Square.SQUARE_4_B, Stone.BLACK);
    board.setStone(Square.SQUARE_5_B, Stone.BLACK);
    board.setStone(Square.SQUARE_6_B, Stone.BLACK);
    board.setStone(Square.SQUARE_7_B, Stone.BLACK);
    assertEquals(6, Tools.upCountToTake(board, Square.SQUARE_8_B, Stone.WHITE));

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
    board.init();
    board.setStone(Square.SQUARE_8_B, Stone.BLACK);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    assertEquals(1, Tools.downCountToTake(board, Square.SQUARE_6_B, Stone.BLACK));
    // take two
    board.init();
    board.setStone(Square.SQUARE_8_B, Stone.BLACK);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    board.setStone(Square.SQUARE_6_B, Stone.WHITE);
    assertEquals(2, Tools.downCountToTake(board, Square.SQUARE_5_B, Stone.BLACK));
    // take three
    board.init();
    board.setStone(Square.SQUARE_8_B, Stone.BLACK);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    board.setStone(Square.SQUARE_6_B, Stone.WHITE);
    board.setStone(Square.SQUARE_5_B, Stone.WHITE);
    assertEquals(3, Tools.downCountToTake(board, Square.SQUARE_4_B, Stone.BLACK));
    // take four
    board.init();
    board.setStone(Square.SQUARE_8_B, Stone.BLACK);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    board.setStone(Square.SQUARE_6_B, Stone.WHITE);
    board.setStone(Square.SQUARE_5_B, Stone.WHITE);
    board.setStone(Square.SQUARE_4_B, Stone.WHITE);
    assertEquals(4, Tools.downCountToTake(board, Square.SQUARE_3_B, Stone.BLACK));
    // take five
    board.init();
    board.setStone(Square.SQUARE_8_B, Stone.BLACK);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    board.setStone(Square.SQUARE_6_B, Stone.WHITE);
    board.setStone(Square.SQUARE_5_B, Stone.WHITE);
    board.setStone(Square.SQUARE_4_B, Stone.WHITE);
    board.setStone(Square.SQUARE_3_B, Stone.WHITE);
    assertEquals(5, Tools.downCountToTake(board, Square.SQUARE_2_B, Stone.BLACK));
    // take six
    board.init();
    board.setStone(Square.SQUARE_8_B, Stone.BLACK);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    board.setStone(Square.SQUARE_6_B, Stone.WHITE);
    board.setStone(Square.SQUARE_5_B, Stone.WHITE);
    board.setStone(Square.SQUARE_4_B, Stone.WHITE);
    board.setStone(Square.SQUARE_3_B, Stone.WHITE);
    board.setStone(Square.SQUARE_2_B, Stone.WHITE);
    assertEquals(6, Tools.downCountToTake(board, Square.SQUARE_1_B, Stone.BLACK));
    // change black <-> white
    board.init();
    board.setStone(Square.SQUARE_8_B, Stone.WHITE);
    board.setStone(Square.SQUARE_7_B, Stone.BLACK);
    board.setStone(Square.SQUARE_6_B, Stone.BLACK);
    board.setStone(Square.SQUARE_5_B, Stone.BLACK);
    board.setStone(Square.SQUARE_4_B, Stone.BLACK);
    board.setStone(Square.SQUARE_3_B, Stone.BLACK);
    board.setStone(Square.SQUARE_2_B, Stone.BLACK);
    assertEquals(6, Tools.downCountToTake(board, Square.SQUARE_1_B, Stone.WHITE));
  }

}
