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
      board.setDisk(sq, idx < 2 ? Disk.values()[idx] : null);
    }
    return board;
  }

  @Test
  void testClear() {
    Board board = new Board();
    board.clear();
    for (Square sq : Square.values()) {
      assertTrue(board.getDisk(sq).isEmpty());
    }
  }

  @Test
  void testInit() {
    for (long seed = 0L; seed < 5L; seed++) {
      Board board = randomBoard(seed);
      board.init();
      for (Square sq : Square.values()) {
        if (sq.equals(Square.SQUARE_4_D) || sq.equals(Square.SQUARE_5_E)) {
          Optional<Disk> stone = board.getDisk(sq);
          assertTrue(stone.isPresent());
          assertEquals(Disk.WHITE, stone.get());
        } else if (sq.equals(Square.SQUARE_4_E) || sq.equals(Square.SQUARE_5_D)) {
          Optional<Disk> stone = board.getDisk(sq);
          assertTrue(stone.isPresent());
          assertEquals(Disk.BLACK, stone.get());
        } else {
          Optional<Disk> stone = board.getDisk(sq);
          assertTrue(stone.isEmpty());
        }
      }
    }
  }

  @Test
  void testSetDisk() {
    Random rand = new Random(12345);
    Board board = new Board();
    for (Square square : Square.values()) {
      Row row = square.row();
      Col col = square.col();
      int idx1 = rand.nextInt(3);
      int idx2 = rand.nextInt(3);
      int idx3 = rand.nextInt(3);
      int idx4 = rand.nextInt(3);
      Disk disk1 = idx1 < 2 ? Disk.values()[idx1] : null;
      Disk disk2 = idx2 < 2 ? Disk.values()[idx2] : null;
      Disk disk3 = idx3 < 2 ? Disk.values()[idx3] : null;
      Disk disk4 = idx4 < 2 ? Disk.values()[idx4] : null;

      board.setDisk(square, disk1);
      assertEquals(disk1, board.getDisk(square).orElse(null));
      board.setDisk(square, disk2);
      assertEquals(disk2, board.getDisk(row, col).orElse(null));
      board.setDisk(row, col, disk3);
      assertEquals(disk3, board.getDisk(square).orElse(null));
      board.setDisk(row, col, disk4);
      assertEquals(disk4, board.getDisk(row, col).orElse(null));
    }
  }

  @Test
  void testClone() {
    Board original = randomBoard(12345L);
    original.setDisk(Square.SQUARE_1_A, Disk.BLACK);
    Board clone = original.clone();
    for (Square sq : Square.values()) {
      assertEquals(original.getDisk(sq).orElse(null), clone.getDisk(sq).orElse(null));
    }
    // not same object.
    clone.setDisk(Square.SQUARE_1_A, Disk.WHITE);
    assertEquals(Disk.BLACK, original.getDisk(Square.SQUARE_1_A).orElse(null));
  }

  @Test
  void testEquals() {
    Board board = randomBoard(12345L);
    board.setDisk(Square.SQUARE_1_A, null);
    Board clone = board.clone();
    assertEquals(board, clone);
    clone.setDisk(Square.SQUARE_1_A, Disk.BLACK);
    assertNotEquals(board, clone);
    clone.setDisk(Square.SQUARE_1_A, Disk.WHITE);
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
