package othello.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.Random;
import org.junit.jupiter.api.Test;

public class BoardTest {

  static private Board randomBoard(long seed) {
    Board board = new Board();
    Random rand = new Random(seed);
    for (Square sq : Square.values()) {
      int idx = rand.nextInt(3);
      board.setStone(sq, idx < 2 ? Stone.values()[idx] : null);
    }
    return board;
  }

  @Test
  void testClear() {
    Board board = new Board();
    board.clear();
    for (Square sq : Square.values()) {
      assertTrue(board.getStone(sq).isEmpty());
    }
  }

  @Test
  void testInit() {
    for (long seed = 0L; seed < 5L; seed++) {
      Board board = randomBoard(seed);
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

  @Test
  void testSetStone() {
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
    Board original = randomBoard(12345L);
    original.setStone(Square.SQUARE_1_A, Stone.BLACK);
    Board clone = original.clone();
    for (Square sq : Square.values()) {
      assertEquals(original.getStone(sq).orElse(null), clone.getStone(sq).orElse(null));
    }
    // not same object.
    clone.setStone(Square.SQUARE_1_A, Stone.WHITE);
    assertEquals(Stone.BLACK, original.getStone(Square.SQUARE_1_A).orElse(null));
  }

  @Test
  void testEquals() {
    Board board = randomBoard(12345L);
    board.setStone(Square.SQUARE_1_A, null);
    Board clone = board.clone();
    assertEquals(board, clone);
    clone.setStone(Square.SQUARE_1_A, Stone.BLACK);
    assertNotEquals(board, clone);
    clone.setStone(Square.SQUARE_1_A, Stone.WHITE);
    assertNotEquals(board, clone);
  }

  @Test
  void testHashCode() {
    for (int i = 0; i < 5; i++) {
      Board board = randomBoard(12345L);
      Board copy = randomBoard(12345L);
      assertEquals(board.hashCode(), copy.hashCode());
    }
    Board original = randomBoard(123L);
    for (int i = 0; i < 5; i++) {
      Board other = randomBoard(i);
      assertNotEquals(original.hashCode(), other.hashCode());
    }
  }

}
