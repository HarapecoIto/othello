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

  @Test
  void setStone() {
    Random rand = new Random(12345);
    Board board = new Board();
    for (Square square : Square.values()) {
      Row row = square.row();
      Col col = square.col();
      int idx1 = rand.nextInt(3);
      int idx2 = rand.nextInt(3);
      int idx3 = rand.nextInt(3);
      int idx4 = rand.nextInt(3);
      Stone stone1 = idx1 < 2 ? Stone.values()[idx1] : null;
      Stone stone2 = idx2 < 2 ? Stone.values()[idx2] : null;
      Stone stone3 = idx3 < 2 ? Stone.values()[idx3] : null;
      Stone stone4 = idx4 < 2 ? Stone.values()[idx4] : null;

      board.setStone(square, stone1);
      assertEquals(stone1, board.getStone(square).orElse(null));
      board.setStone(square, stone2);
      assertEquals(stone2, board.getStone(row, col).orElse(null));
      board.setStone(row, col, stone3);
      assertEquals(stone3, board.getStone(square).orElse(null));
      board.setStone(row, col, stone4);
      assertEquals(stone4, board.getStone(row, col).orElse(null));
    }
  }

  @Test
  void testClone() {
    Board board = new Board();
    Random rand = new Random(77777);
    Stone[] stones = new Stone[64];
    for (int i = 0; i < 64; i++) {
      int idx = rand.nextInt(3);
      stones[i] = idx < 2 ? Stone.values()[idx] : null;
    }
    for (Square sq : Square.values()) {
      board.setStone(sq, stones[sq.getIndex()]);
    }
    Board cloned = board.clone();
    for (Square sq : Square.values()) {
      assertEquals(stones[sq.getIndex()], cloned.getStone(sq).orElse(null));
    }
  }
}
