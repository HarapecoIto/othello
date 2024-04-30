package othello;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

public class ToolsTest {

  @Test
  void testCountDisks() {
    Board board = new Board();
    board.clear();
    for (Col col : Col.values()) {
      board.setDisk(Row.ROW_1, col, Disk.BLACK);
      board.setDisk(Row.ROW_2, col, Disk.WHITE);
    }
    board.setDisk(Row.ROW_3, Col.COL_A, Disk.BLACK);
    board.setDisk(Row.ROW_4, Col.COL_D, Disk.BLACK);
    board.setDisk(Row.ROW_5, Col.COL_F, Disk.BLACK);
    board.setDisk(Row.ROW_8, Col.COL_H, Disk.BLACK);
    board.setDisk(Row.ROW_6, Col.COL_D, Disk.WHITE);
    board.setDisk(Row.ROW_7, Col.COL_F, Disk.WHITE);
    assertEquals(12, Tools.countDisks(board, Disk.BLACK));
    assertEquals(10, Tools.countDisks(board, Disk.WHITE));
  }

  @Test
  void testCountReversibleDisks() {
    Board board = new Board();
    board.init();
    assertEquals(1, Tools.countReversibleDisks(board, Square.SQUARE_6_E, Disk.BLACK));
    board.setDisk(Square.SQUARE_6_E, Disk.BLACK);
    board.setDisk(Square.SQUARE_5_E, Disk.BLACK);
    assertEquals(1, Tools.countReversibleDisks(board, Square.SQUARE_6_F, Disk.WHITE));
    board.setDisk(Square.SQUARE_6_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_E, Disk.WHITE);
    assertEquals(1, Tools.countReversibleDisks(board, Square.SQUARE_5_F, Disk.BLACK));
    board.setDisk(Square.SQUARE_5_E, Disk.BLACK);
    board.setDisk(Square.SQUARE_5_E, Disk.BLACK);
  }

  @Test
  void testUpCountReversibleDisks() {
    Board board = new Board();
    board.init();
    // on the another stone -> error.
    assertEquals(-1, Tools.countUpReversibleDisks(board, Square.SQUARE_4_D, Disk.BLACK));
    assertEquals(-1, Tools.countUpReversibleDisks(board, Square.SQUARE_4_D, Disk.WHITE));
    // next square is out of board -> 0.
    assertEquals(0, Tools.countUpReversibleDisks(board, Square.SQUARE_1_D, Disk.BLACK));
    assertEquals(0, Tools.countUpReversibleDisks(board, Square.SQUARE_1_D, Disk.WHITE));
    // take zero
    board.clear();
    board.setDisk(Square.SQUARE_1_B, Disk.BLACK);
    assertEquals(0, Tools.countUpReversibleDisks(board, Square.SQUARE_2_B, Disk.BLACK));
    // take one
    board.clear();
    board.setDisk(Square.SQUARE_1_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    assertEquals(1, Tools.countUpReversibleDisks(board, Square.SQUARE_3_B, Disk.BLACK));
    // take two
    board.clear();
    board.setDisk(Square.SQUARE_1_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_B, Disk.WHITE);
    assertEquals(2, Tools.countUpReversibleDisks(board, Square.SQUARE_4_B, Disk.BLACK));
    // take three
    board.clear();
    board.setDisk(Square.SQUARE_1_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_B, Disk.WHITE);
    assertEquals(3, Tools.countUpReversibleDisks(board, Square.SQUARE_5_B, Disk.BLACK));
    // take four
    board.clear();
    board.setDisk(Square.SQUARE_1_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_B, Disk.WHITE);
    assertEquals(4, Tools.countUpReversibleDisks(board, Square.SQUARE_6_B, Disk.BLACK));
    // take five
    board.clear();
    board.setDisk(Square.SQUARE_1_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_B, Disk.WHITE);
    assertEquals(5, Tools.countUpReversibleDisks(board, Square.SQUARE_7_B, Disk.BLACK));
    // take six
    board.clear();
    board.setDisk(Square.SQUARE_1_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    assertEquals(6, Tools.countUpReversibleDisks(board, Square.SQUARE_8_B, Disk.BLACK));
    // change black <-> white
    board.clear();
    board.setDisk(Square.SQUARE_1_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_2_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_3_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_4_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_5_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_6_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.BLACK);
    assertEquals(6, Tools.countUpReversibleDisks(board, Square.SQUARE_8_B, Disk.WHITE));
    // terminated by empty square -> 0
    board.clear();
    board.setDisk(Square.SQUARE_1_B, null);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    assertEquals(0, Tools.countUpReversibleDisks(board, Square.SQUARE_8_B, Disk.BLACK));

  }

  @Test
  void testDownCountReversibleDisks() {
    Board board = new Board();
    board.init();
    // on the another stone -> error.
    assertEquals(-1, Tools.countDownReversibleDisks(board, Square.SQUARE_5_D, Disk.BLACK));
    assertEquals(-1, Tools.countDownReversibleDisks(board, Square.SQUARE_5_D, Disk.WHITE));
    // next square is out of board -> 0.
    assertEquals(0, Tools.countDownReversibleDisks(board, Square.SQUARE_8_D, Disk.BLACK));
    assertEquals(0, Tools.countDownReversibleDisks(board, Square.SQUARE_8_D, Disk.WHITE));
    // next stone is mine -> 0
    assertEquals(0, Tools.countDownReversibleDisks(board, Square.SQUARE_3_E, Disk.BLACK));
    assertEquals(0, Tools.countDownReversibleDisks(board, Square.SQUARE_3_D, Disk.WHITE));
    // take one
    board.clear();
    board.setDisk(Square.SQUARE_8_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    assertEquals(1, Tools.countDownReversibleDisks(board, Square.SQUARE_6_B, Disk.BLACK));
    // take two
    board.clear();
    board.setDisk(Square.SQUARE_8_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_B, Disk.WHITE);
    assertEquals(2, Tools.countDownReversibleDisks(board, Square.SQUARE_5_B, Disk.BLACK));
    // take three
    board.clear();
    board.setDisk(Square.SQUARE_8_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_B, Disk.WHITE);
    assertEquals(3, Tools.countDownReversibleDisks(board, Square.SQUARE_4_B, Disk.BLACK));
    // take four
    board.clear();
    board.setDisk(Square.SQUARE_8_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_B, Disk.WHITE);
    assertEquals(4, Tools.countDownReversibleDisks(board, Square.SQUARE_3_B, Disk.BLACK));
    // take five
    board.clear();
    board.setDisk(Square.SQUARE_8_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_B, Disk.WHITE);
    assertEquals(5, Tools.countDownReversibleDisks(board, Square.SQUARE_2_B, Disk.BLACK));
    // take six
    board.clear();
    board.setDisk(Square.SQUARE_8_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    assertEquals(6, Tools.countDownReversibleDisks(board, Square.SQUARE_1_B, Disk.BLACK));
    // change black <-> white
    board.clear();
    board.setDisk(Square.SQUARE_8_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_6_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_5_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_4_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_3_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_B, Disk.BLACK);
    assertEquals(6, Tools.countDownReversibleDisks(board, Square.SQUARE_1_B, Disk.WHITE));
    // terminated by empty square -> 0
    board.clear();
    board.setDisk(Square.SQUARE_8_B, null);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    assertEquals(0, Tools.countDownReversibleDisks(board, Square.SQUARE_1_B, Disk.BLACK));
  }

  @Test
  void testLeftCountReversibleDisks() {
    Board board = new Board();
    board.init();
    // on the another stone -> error.
    assertEquals(-1, Tools.countLeftReversibleDisks(board, Square.SQUARE_4_D, Disk.BLACK));
    assertEquals(-1, Tools.countLeftReversibleDisks(board, Square.SQUARE_4_D, Disk.WHITE));
    // next square is out of board -> 0.
    assertEquals(0, Tools.countLeftReversibleDisks(board, Square.SQUARE_1_A, Disk.BLACK));
    assertEquals(0, Tools.countLeftReversibleDisks(board, Square.SQUARE_1_A, Disk.WHITE));
    // take zero
    board.clear();
    board.setDisk(Square.SQUARE_7_A, Disk.BLACK);
    assertEquals(0, Tools.countLeftReversibleDisks(board, Square.SQUARE_7_B, Disk.BLACK));
    // take one
    board.clear();
    board.setDisk(Square.SQUARE_7_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    assertEquals(1, Tools.countLeftReversibleDisks(board, Square.SQUARE_7_C, Disk.BLACK));
    // take two
    board.clear();
    board.setDisk(Square.SQUARE_7_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_C, Disk.WHITE);
    assertEquals(2, Tools.countLeftReversibleDisks(board, Square.SQUARE_7_D, Disk.BLACK));
    // take three
    board.clear();
    board.setDisk(Square.SQUARE_7_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_D, Disk.WHITE);
    assertEquals(3, Tools.countLeftReversibleDisks(board, Square.SQUARE_7_E, Disk.BLACK));
    // take four
    board.clear();
    board.setDisk(Square.SQUARE_7_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_E, Disk.WHITE);
    assertEquals(4, Tools.countLeftReversibleDisks(board, Square.SQUARE_7_F, Disk.BLACK));
    // take five
    board.clear();
    board.setDisk(Square.SQUARE_7_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_F, Disk.WHITE);
    assertEquals(5, Tools.countLeftReversibleDisks(board, Square.SQUARE_7_G, Disk.BLACK));
    // take six
    board.clear();
    board.setDisk(Square.SQUARE_7_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    assertEquals(6, Tools.countLeftReversibleDisks(board, Square.SQUARE_7_H, Disk.BLACK));
    // change black <-> white
    board.clear();
    board.setDisk(Square.SQUARE_7_A, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_C, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_D, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_E, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_F, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_G, Disk.BLACK);
    assertEquals(6, Tools.countLeftReversibleDisks(board, Square.SQUARE_7_H, Disk.WHITE));
    // terminated by empty square -> 0
    board.clear();
    board.setDisk(Square.SQUARE_7_A, null);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    assertEquals(0, Tools.countLeftReversibleDisks(board, Square.SQUARE_7_H, Disk.BLACK));
  }

  @Test
  void testRightCountReversibleDisks() {
    Board board = new Board();
    board.init();
    // on the another stone -> error.
    assertEquals(-1, Tools.countRightReversibleDisks(board, Square.SQUARE_4_E, Disk.BLACK));
    assertEquals(-1, Tools.countRightReversibleDisks(board, Square.SQUARE_4_E, Disk.WHITE));
    // next square is out of board -> 0.
    assertEquals(0, Tools.countRightReversibleDisks(board, Square.SQUARE_1_H, Disk.BLACK));
    assertEquals(0, Tools.countRightReversibleDisks(board, Square.SQUARE_1_H, Disk.WHITE));
    // take zero
    board.clear();
    board.setDisk(Square.SQUARE_7_H, Disk.BLACK);
    assertEquals(0, Tools.countRightReversibleDisks(board, Square.SQUARE_7_G, Disk.BLACK));
    // take one
    board.clear();
    board.setDisk(Square.SQUARE_7_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    assertEquals(1, Tools.countRightReversibleDisks(board, Square.SQUARE_7_F, Disk.BLACK));
    // take two
    board.clear();
    board.setDisk(Square.SQUARE_7_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_F, Disk.WHITE);
    assertEquals(2, Tools.countRightReversibleDisks(board, Square.SQUARE_7_E, Disk.BLACK));
    // take three
    board.clear();
    board.setDisk(Square.SQUARE_7_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_E, Disk.WHITE);
    assertEquals(3, Tools.countRightReversibleDisks(board, Square.SQUARE_7_D, Disk.BLACK));
    // take four
    board.clear();
    board.setDisk(Square.SQUARE_7_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_D, Disk.WHITE);
    assertEquals(4, Tools.countRightReversibleDisks(board, Square.SQUARE_7_C, Disk.BLACK));
    // take five
    board.clear();
    board.setDisk(Square.SQUARE_7_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_C, Disk.WHITE);
    assertEquals(5, Tools.countRightReversibleDisks(board, Square.SQUARE_7_B, Disk.BLACK));
    // take six
    board.clear();
    board.setDisk(Square.SQUARE_7_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    assertEquals(6, Tools.countRightReversibleDisks(board, Square.SQUARE_7_A, Disk.BLACK));
    // change black <-> white
    board.clear();
    board.setDisk(Square.SQUARE_7_H, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_G, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_F, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_E, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_D, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_C, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.BLACK);
    assertEquals(6, Tools.countRightReversibleDisks(board, Square.SQUARE_7_A, Disk.WHITE));
    // terminated by empty square -> 0
    board.clear();
    board.setDisk(Square.SQUARE_7_H, null);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    assertEquals(0, Tools.countRightReversibleDisks(board, Square.SQUARE_7_A, Disk.BLACK));
  }

  @Test
  void testUpLeftCountReversibleDisks() {
    Board board = new Board();
    board.init();
    board.setDisk(Square.SQUARE_3_C, null);
    board.setDisk(Square.SQUARE_3_D, null);
    board.setDisk(Square.SQUARE_4_C, null);
    board.setDisk(Square.SQUARE_5_D, null);
    // on the another stone -> error.
    assertEquals(-1, Tools.countUpLeftReversibleDisk(board, Square.SQUARE_4_D, Disk.BLACK));
    assertEquals(-1, Tools.countUpLeftReversibleDisk(board, Square.SQUARE_4_D, Disk.WHITE));
    // next square is out of board -> 0.
    assertEquals(0, Tools.countUpLeftReversibleDisk(board, Square.SQUARE_1_A, Disk.BLACK));
    assertEquals(0, Tools.countUpLeftReversibleDisk(board, Square.SQUARE_1_A, Disk.WHITE));
    // take zero
    board.clear();
    board.setDisk(Square.SQUARE_1_A, Disk.BLACK);
    assertEquals(0, Tools.countUpLeftReversibleDisk(board, Square.SQUARE_2_B, Disk.BLACK));
    // take one
    board.clear();
    board.setDisk(Square.SQUARE_1_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    assertEquals(1, Tools.countUpLeftReversibleDisk(board, Square.SQUARE_3_C, Disk.BLACK));
    // take two
    board.clear();
    board.setDisk(Square.SQUARE_1_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_C, Disk.WHITE);
    assertEquals(2, Tools.countUpLeftReversibleDisk(board, Square.SQUARE_4_D, Disk.BLACK));
    // take three
    board.clear();
    board.setDisk(Square.SQUARE_1_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_D, Disk.WHITE);
    assertEquals(3, Tools.countUpLeftReversibleDisk(board, Square.SQUARE_5_E, Disk.BLACK));
    // take four
    board.clear();
    board.setDisk(Square.SQUARE_1_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_E, Disk.WHITE);
    assertEquals(4, Tools.countUpLeftReversibleDisk(board, Square.SQUARE_6_F, Disk.BLACK));
    // take five
    board.clear();
    board.setDisk(Square.SQUARE_1_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_F, Disk.WHITE);
    assertEquals(5, Tools.countUpLeftReversibleDisk(board, Square.SQUARE_7_G, Disk.BLACK));
    // take six
    board.clear();
    board.setDisk(Square.SQUARE_1_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    assertEquals(6, Tools.countUpLeftReversibleDisk(board, Square.SQUARE_8_H, Disk.BLACK));
    // change black <-> white
    board.clear();
    board.setDisk(Square.SQUARE_1_A, Disk.WHITE);
    board.setDisk(Square.SQUARE_2_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_3_C, Disk.BLACK);
    board.setDisk(Square.SQUARE_4_D, Disk.BLACK);
    board.setDisk(Square.SQUARE_5_E, Disk.BLACK);
    board.setDisk(Square.SQUARE_6_F, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_G, Disk.BLACK);
    assertEquals(6, Tools.countUpLeftReversibleDisk(board, Square.SQUARE_8_H, Disk.WHITE));
    // terminated by empty square -> 0
    board.clear();
    board.setDisk(Square.SQUARE_1_A, null);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    assertEquals(0, Tools.countUpLeftReversibleDisk(board, Square.SQUARE_8_H, Disk.BLACK));
  }

  @Test
  void testUpRightCountReversibleDisks() {
    Board board = new Board();
    board.init();
    board.setDisk(Square.SQUARE_3_C, null);
    board.setDisk(Square.SQUARE_3_D, null);
    board.setDisk(Square.SQUARE_4_C, null);
    board.setDisk(Square.SQUARE_5_D, null);
    // on the another stone -> error.
    assertEquals(-1, Tools.countUpRightReversibleDisks(board, Square.SQUARE_4_D, Disk.BLACK));
    assertEquals(-1, Tools.countUpRightReversibleDisks(board, Square.SQUARE_4_D, Disk.WHITE));
    // next square is out of board -> 0.
    assertEquals(0, Tools.countUpRightReversibleDisks(board, Square.SQUARE_1_A, Disk.BLACK));
    assertEquals(0, Tools.countUpRightReversibleDisks(board, Square.SQUARE_1_A, Disk.WHITE));
    // take zero
    board.clear();
    board.setDisk(Square.SQUARE_1_H, Disk.BLACK);
    assertEquals(0, Tools.countUpRightReversibleDisks(board, Square.SQUARE_2_G, Disk.BLACK));
    // take one
    board.clear();
    board.setDisk(Square.SQUARE_1_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_G, Disk.WHITE);
    assertEquals(1, Tools.countUpRightReversibleDisks(board, Square.SQUARE_3_F, Disk.BLACK));
    // take two
    board.clear();
    board.setDisk(Square.SQUARE_1_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_F, Disk.WHITE);
    assertEquals(2, Tools.countUpRightReversibleDisks(board, Square.SQUARE_4_E, Disk.BLACK));
    // take three
    board.clear();
    board.setDisk(Square.SQUARE_1_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_E, Disk.WHITE);
    assertEquals(3, Tools.countUpRightReversibleDisks(board, Square.SQUARE_5_D, Disk.BLACK));
    // take four
    board.clear();
    board.setDisk(Square.SQUARE_1_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_D, Disk.WHITE);
    assertEquals(4, Tools.countUpRightReversibleDisks(board, Square.SQUARE_6_C, Disk.BLACK));
    // take five
    board.clear();
    board.setDisk(Square.SQUARE_1_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_C, Disk.WHITE);
    assertEquals(5, Tools.countUpRightReversibleDisks(board, Square.SQUARE_7_B, Disk.BLACK));
    // take six
    board.clear();
    board.setDisk(Square.SQUARE_1_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    assertEquals(6, Tools.countUpRightReversibleDisks(board, Square.SQUARE_8_A, Disk.BLACK));
    // change black <-> white
    board.clear();
    board.setDisk(Square.SQUARE_1_H, Disk.WHITE);
    board.setDisk(Square.SQUARE_2_G, Disk.BLACK);
    board.setDisk(Square.SQUARE_3_F, Disk.BLACK);
    board.setDisk(Square.SQUARE_4_E, Disk.BLACK);
    board.setDisk(Square.SQUARE_5_D, Disk.BLACK);
    board.setDisk(Square.SQUARE_6_C, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.BLACK);
    assertEquals(6, Tools.countUpRightReversibleDisks(board, Square.SQUARE_8_A, Disk.WHITE));
    // terminated by empty square -> 0
    board.clear();
    board.setDisk(Square.SQUARE_1_H, null);
    board.setDisk(Square.SQUARE_2_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    assertEquals(0, Tools.countUpRightReversibleDisks(board, Square.SQUARE_8_A, Disk.BLACK));
  }

  @Test
  void testDownLeftCountReversibleDisks() {
    Board board = new Board();
    board.init();
    board.setDisk(Square.SQUARE_3_C, null);
    board.setDisk(Square.SQUARE_3_D, null);
    board.setDisk(Square.SQUARE_4_C, null);
    board.setDisk(Square.SQUARE_5_D, null);
    // on the another stone -> error.
    assertEquals(-1, Tools.countDownLeftReversibleDisks(board, Square.SQUARE_4_D, Disk.BLACK));
    assertEquals(-1, Tools.countDownLeftReversibleDisks(board, Square.SQUARE_4_D, Disk.WHITE));
    // next square is out of board -> 0.
    assertEquals(0, Tools.countDownLeftReversibleDisks(board, Square.SQUARE_1_A, Disk.BLACK));
    assertEquals(0, Tools.countDownLeftReversibleDisks(board, Square.SQUARE_1_A, Disk.WHITE));
    // take zero
    board.clear();
    board.setDisk(Square.SQUARE_8_A, Disk.BLACK);
    assertEquals(0, Tools.countDownLeftReversibleDisks(board, Square.SQUARE_7_B, Disk.BLACK));
    // take one
    board.clear();
    board.setDisk(Square.SQUARE_8_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    assertEquals(1, Tools.countDownLeftReversibleDisks(board, Square.SQUARE_6_C, Disk.BLACK));
    // take two
    board.clear();
    board.setDisk(Square.SQUARE_8_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_C, Disk.WHITE);
    assertEquals(2, Tools.countDownLeftReversibleDisks(board, Square.SQUARE_5_D, Disk.BLACK));
    // take three
    board.clear();
    board.setDisk(Square.SQUARE_8_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_D, Disk.WHITE);
    assertEquals(3, Tools.countDownLeftReversibleDisks(board, Square.SQUARE_4_E, Disk.BLACK));
    // take four
    board.clear();
    board.setDisk(Square.SQUARE_8_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_E, Disk.WHITE);
    assertEquals(4, Tools.countDownLeftReversibleDisks(board, Square.SQUARE_3_F, Disk.BLACK));
    // take five
    board.clear();
    board.setDisk(Square.SQUARE_8_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_F, Disk.WHITE);
    assertEquals(5, Tools.countDownLeftReversibleDisks(board, Square.SQUARE_2_G, Disk.BLACK));
    // take six
    board.clear();
    board.setDisk(Square.SQUARE_8_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_2_G, Disk.WHITE);
    assertEquals(6, Tools.countDownLeftReversibleDisks(board, Square.SQUARE_1_H, Disk.BLACK));
    // change black <-> white
    board.clear();
    board.setDisk(Square.SQUARE_8_A, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_6_C, Disk.BLACK);
    board.setDisk(Square.SQUARE_5_D, Disk.BLACK);
    board.setDisk(Square.SQUARE_4_E, Disk.BLACK);
    board.setDisk(Square.SQUARE_3_F, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_G, Disk.BLACK);
    assertEquals(6, Tools.countDownLeftReversibleDisks(board, Square.SQUARE_1_H, Disk.WHITE));
    // terminated by empty square -> 0
    board.clear();
    board.setDisk(Square.SQUARE_8_A, null);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_2_G, Disk.WHITE);
    assertEquals(0, Tools.countDownLeftReversibleDisks(board, Square.SQUARE_1_H, Disk.BLACK));
  }

  @Test
  void testDownRightCountReversibleDisks() {
    Board board = new Board();
    board.init();
    board.setDisk(Square.SQUARE_3_C, null);
    board.setDisk(Square.SQUARE_3_D, null);
    board.setDisk(Square.SQUARE_4_C, null);
    board.setDisk(Square.SQUARE_5_D, null);
    // on the another stone -> error.
    assertEquals(-1, Tools.countDownRightReversibleDisks(board, Square.SQUARE_4_D, Disk.BLACK));
    assertEquals(-1, Tools.countDownRightReversibleDisks(board, Square.SQUARE_4_D, Disk.WHITE));
    // next square is out of board -> 0.
    assertEquals(0, Tools.countDownRightReversibleDisks(board, Square.SQUARE_1_A, Disk.BLACK));
    assertEquals(0, Tools.countDownRightReversibleDisks(board, Square.SQUARE_1_A, Disk.WHITE));
    // take zero
    board.clear();
    board.setDisk(Square.SQUARE_8_H, Disk.BLACK);
    assertEquals(0, Tools.countDownRightReversibleDisks(board, Square.SQUARE_7_G, Disk.BLACK));
    // take one
    board.clear();
    board.setDisk(Square.SQUARE_8_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    assertEquals(1, Tools.countDownRightReversibleDisks(board, Square.SQUARE_6_F, Disk.BLACK));
    // take two
    board.clear();
    board.setDisk(Square.SQUARE_8_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_F, Disk.WHITE);
    assertEquals(2, Tools.countDownRightReversibleDisks(board, Square.SQUARE_5_E, Disk.BLACK));
    // take three
    board.clear();
    board.setDisk(Square.SQUARE_8_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_E, Disk.WHITE);
    assertEquals(3, Tools.countDownRightReversibleDisks(board, Square.SQUARE_4_D, Disk.BLACK));
    // take four
    board.clear();
    board.setDisk(Square.SQUARE_8_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_D, Disk.WHITE);
    assertEquals(4, Tools.countDownRightReversibleDisks(board, Square.SQUARE_3_C, Disk.BLACK));
    // take five
    board.clear();
    board.setDisk(Square.SQUARE_8_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_C, Disk.WHITE);
    assertEquals(5, Tools.countDownRightReversibleDisks(board, Square.SQUARE_2_B, Disk.BLACK));
    // take six
    board.clear();
    board.setDisk(Square.SQUARE_8_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    assertEquals(6, Tools.countDownRightReversibleDisks(board, Square.SQUARE_1_A, Disk.BLACK));
    // change black <-> white
    board.clear();
    board.setDisk(Square.SQUARE_8_H, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_G, Disk.BLACK);
    board.setDisk(Square.SQUARE_6_F, Disk.BLACK);
    board.setDisk(Square.SQUARE_5_E, Disk.BLACK);
    board.setDisk(Square.SQUARE_4_D, Disk.BLACK);
    board.setDisk(Square.SQUARE_3_C, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_B, Disk.BLACK);
    assertEquals(6, Tools.countDownRightReversibleDisks(board, Square.SQUARE_1_A, Disk.WHITE));
    // terminated by empty square -> 0
    board.clear();
    board.setDisk(Square.SQUARE_8_H, null);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    assertEquals(0, Tools.countDownRightReversibleDisks(board, Square.SQUARE_1_A, Disk.BLACK));
  }

  @Test
  void testMove() {
    Board board = new Board();
    board.init();
    Board clone = board.clone();
    Optional<List<Square>> opt = Tools.move(clone, Square.SQUARE_4_D, Disk.BLACK);
    assertTrue(opt.isEmpty());
    assertEquals(board, clone);

    board.init();
    clone = board.clone();
    opt = Tools.move(clone, Square.SQUARE_6_D, Disk.BLACK);
    assertTrue(opt.isPresent());
    assertEquals(0, opt.get().size());
    assertEquals(board, clone);

    board.init();
    clone = board.clone();
    opt = Tools.move(clone, Square.SQUARE_6_E, Disk.BLACK);
    assertTrue(opt.isPresent());
    assertEquals(1, opt.get().size());
    assertEquals(Square.SQUARE_5_E, opt.get().get(0));
    board.setDisk(Square.SQUARE_5_E, Disk.BLACK);
    board.setDisk(Square.SQUARE_6_E, Disk.BLACK);
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
        board.setDisk(row, col, Disk.BLACK);
      }
    }
    for (Row row : new Row[]{Row.ROW_2, Row.ROW_3, Row.ROW_4, Row.ROW_5}) {
      for (Col col : new Col[]{Col.COL_B, Col.COL_C, Col.COL_D, Col.COL_E}) {
        board.setDisk(row, col, Disk.WHITE);
      }
    }
    board.setDisk(Square.SQUARE_3_C, null);

    // move
    Optional<List<Square>> result = Tools.move(board, Square.SQUARE_3_C, Disk.BLACK);
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
        expects.setDisk(row, col, Disk.BLACK);
      }
    }
    expects.setDisk(Square.SQUARE_5_B, Disk.WHITE);
    expects.setDisk(Square.SQUARE_5_D, Disk.WHITE);
    expects.setDisk(Square.SQUARE_2_E, Disk.WHITE);
    expects.setDisk(Square.SQUARE_4_E, Disk.WHITE);
    assertEquals(expects, board);
  }

}
