package othello.rule;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import othello.base.Board;
import othello.base.Square;
import othello.base.Stone;

public class ToolsTest {

  @Test
  void testUpReversibleCount() {

    Board board = new Board();
    board.init();
    // on the another stone -> error.
    assertEquals(-1, Tools.upReversibleCount(board, Square.SQUARE_4_D, Stone.BLACK));
    assertEquals(-1, Tools.upReversibleCount(board, Square.SQUARE_4_D, Stone.WHITE));
    // next square is out of board -> 0.
    assertEquals(0, Tools.upReversibleCount(board, Square.SQUARE_1_D, Stone.BLACK));
    assertEquals(0, Tools.upReversibleCount(board, Square.SQUARE_1_D, Stone.WHITE));
    // next stone is mine -> 0
    assertEquals(0, Tools.upReversibleCount(board, Square.SQUARE_6_D, Stone.BLACK));
    assertEquals(0, Tools.upReversibleCount(board, Square.SQUARE_6_E, Stone.WHITE));
    // take one
    board.init();
    board.setStone(Square.SQUARE_1_B, Stone.BLACK);
    board.setStone(Square.SQUARE_2_B, Stone.WHITE);
    assertEquals(1, Tools.upReversibleCount(board, Square.SQUARE_3_B, Stone.BLACK));
    // take two
    board.init();
    board.setStone(Square.SQUARE_1_B, Stone.BLACK);
    board.setStone(Square.SQUARE_2_B, Stone.WHITE);
    board.setStone(Square.SQUARE_3_B, Stone.WHITE);
    assertEquals(2, Tools.upReversibleCount(board, Square.SQUARE_4_B, Stone.BLACK));
    // take three
    board.init();
    board.setStone(Square.SQUARE_1_B, Stone.BLACK);
    board.setStone(Square.SQUARE_2_B, Stone.WHITE);
    board.setStone(Square.SQUARE_3_B, Stone.WHITE);
    board.setStone(Square.SQUARE_4_B, Stone.WHITE);
    assertEquals(3, Tools.upReversibleCount(board, Square.SQUARE_5_B, Stone.BLACK));
    // take four
    board.init();
    board.setStone(Square.SQUARE_1_B, Stone.BLACK);
    board.setStone(Square.SQUARE_2_B, Stone.WHITE);
    board.setStone(Square.SQUARE_3_B, Stone.WHITE);
    board.setStone(Square.SQUARE_4_B, Stone.WHITE);
    board.setStone(Square.SQUARE_5_B, Stone.WHITE);
    assertEquals(4, Tools.upReversibleCount(board, Square.SQUARE_6_B, Stone.BLACK));
    // take five
    board.init();
    board.setStone(Square.SQUARE_1_B, Stone.BLACK);
    board.setStone(Square.SQUARE_2_B, Stone.WHITE);
    board.setStone(Square.SQUARE_3_B, Stone.WHITE);
    board.setStone(Square.SQUARE_4_B, Stone.WHITE);
    board.setStone(Square.SQUARE_5_B, Stone.WHITE);
    board.setStone(Square.SQUARE_6_B, Stone.WHITE);
    assertEquals(5, Tools.upReversibleCount(board, Square.SQUARE_7_B, Stone.BLACK));
    // take six
    board.init();
    board.setStone(Square.SQUARE_1_B, Stone.BLACK);
    board.setStone(Square.SQUARE_2_B, Stone.WHITE);
    board.setStone(Square.SQUARE_3_B, Stone.WHITE);
    board.setStone(Square.SQUARE_4_B, Stone.WHITE);
    board.setStone(Square.SQUARE_5_B, Stone.WHITE);
    board.setStone(Square.SQUARE_6_B, Stone.WHITE);
    board.setStone(Square.SQUARE_7_B, Stone.WHITE);
    assertEquals(6, Tools.upReversibleCount(board, Square.SQUARE_8_B, Stone.BLACK));
  }


}
