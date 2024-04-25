package othello.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.Random;
import org.junit.jupiter.api.Test;

public class BoardTest {

  @Test
  void init() {
    Board board = new Board();
    for (Square sq : Square.values()) {
      if (sq.equals(Square.SQUARE_4_D) || sq.equals(Square.SQUARE_5_E)) {
        Optional<Stone> stone = board.getStone(sq);
        assertTrue(stone.isPresent());
        assertEquals(Stone.WHITE, stone.get());
      } else if (sq.equals(Square.SQUARE_4_E) || sq.equals(Square.SQUARE_5_D)) {
        Optional<Stone> stone = board.getStone(sq);
        assertTrue(stone.isPresent());
        assertEquals(Stone.BLACK, stone.get());
      } else {
        Optional<Stone> stone = board.getStone(sq);
        assertTrue(stone.isEmpty());
      }
    }

    Random rand = new Random(12345);
    Stone[] stones = new Stone[]{Stone.BLACK, Stone.WHITE, null};
    for (Square sq : Square.values()) {
      board.setStone(sq, stones[rand.nextInt(3)]);
    }

    board.init();
    for (Square sq : Square.values()) {
      if (sq.equals(Square.SQUARE_4_D) || sq.equals(Square.SQUARE_5_E)) {
        Optional<Stone> stone = board.getStone(sq);
        assertTrue(stone.isPresent());
        assertEquals(Stone.WHITE, stone.get());
      } else if (sq.equals(Square.SQUARE_4_E) || sq.equals(Square.SQUARE_5_D)) {
        Optional<Stone> stone = board.getStone(sq);
        assertTrue(stone.isPresent());
        assertEquals(Stone.BLACK, stone.get());
      } else {
        Optional<Stone> stone = board.getStone(sq);
        assertTrue(stone.isEmpty());
      }
    }
  }

}
