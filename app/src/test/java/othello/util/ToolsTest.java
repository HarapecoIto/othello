package othello.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import othello.OthelloException;
import othello.base.Board;
import othello.base.Col;
import othello.base.Disk;
import othello.base.Row;
import othello.base.Square;

public class ToolsTest {

  @BeforeAll
  public static void setUp() {
    Tools.init();
  }

  @AfterAll
  public static void shutdown() {
    Tools.shutdown();
  }

  @Test
  @DisplayName("Test Tools.countDisk();")
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
    assertThat(Tools.countDisks(board, Disk.BLACK)).isEqualTo(12);
    assertThat(Tools.countDisks(board, Disk.WHITE)).isEqualTo(10);
  }

  //  @Test
  @DisplayName("Test total of Tools.countTurnoverableDisk();")
  void testTotalCountTurnoverableDisks() {
    Board board = new Board();
    board.init();
    assertThat(Tools.countTurnoverableDisks(board, Square.SQUARE_6_E, Disk.BLACK)).isEqualTo(1);
    board.setDisk(Square.SQUARE_6_E, Disk.BLACK);
    board.setDisk(Square.SQUARE_5_E, Disk.BLACK);
    assertThat(Tools.countTurnoverableDisks(board, Square.SQUARE_6_F, Disk.WHITE)).isEqualTo(1);
    board.setDisk(Square.SQUARE_6_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_E, Disk.WHITE);
    assertThat(Tools.countTurnoverableDisks(board, Square.SQUARE_5_F, Disk.BLACK)).isEqualTo(1);
    board.setDisk(Square.SQUARE_5_E, Disk.BLACK);
    board.setDisk(Square.SQUARE_5_E, Disk.BLACK);
  }

  private static int countUpTurnoverableDisks
      (@NotNull Board board, @NotNull Square square, @NotNull Disk mine) {
    Optional<List<Square>> opt
        = Tools.countTurnoverableDisksOfLineEngine(Square::up, board, square, mine);
    return opt.map(List::size).orElse(-1);
  }

  private static int countDownTurnoverableDisks
      (@NotNull Board board, @NotNull Square square, @NotNull Disk mine) {
    Optional<List<Square>> opt
        = Tools.countTurnoverableDisksOfLineEngine(Square::down, board, square, mine);
    return opt.map(List::size).orElse(-1);
  }

  private static int countLeftTurnoverableDisks
      (@NotNull Board board, @NotNull Square square, @NotNull Disk mine) {
    Optional<List<Square>> opt
        = Tools.countTurnoverableDisksOfLineEngine(Square::left, board, square, mine);
    return opt.map(List::size).orElse(-1);
  }

  private static int countRightTurnoverableDisks
      (@NotNull Board board, @NotNull Square square, @NotNull Disk mine) {
    Optional<List<Square>> opt
        = Tools.countTurnoverableDisksOfLineEngine(Square::right, board, square, mine);
    return opt.map(List::size).orElse(-1);
  }

  private static int countUpLeftTurnoverableDisks
      (@NotNull Board board, @NotNull Square square, @NotNull Disk mine) {
    Optional<List<Square>> opt
        = Tools.countTurnoverableDisksOfLineEngine(Square::upLeft, board, square, mine);
    return opt.map(List::size).orElse(-1);
  }

  private static int countUpRightTurnoverableDisks
      (@NotNull Board board, @NotNull Square square, @NotNull Disk mine) {
    Optional<List<Square>> opt
        = Tools.countTurnoverableDisksOfLineEngine(Square::upRight, board, square, mine);
    return opt.map(List::size).orElse(-1);
  }

  private static int countDownLeftTurnoverableDisks
      (@NotNull Board board, @NotNull Square square, @NotNull Disk mine) {
    Optional<List<Square>> opt
        = Tools.countTurnoverableDisksOfLineEngine(Square::downLeft, board, square, mine);
    return opt.map(List::size).orElse(-1);
  }

  private static int countDownRightTurnoverableDisks
      (@NotNull Board board, @NotNull Square square, @NotNull Disk mine) {
    Optional<List<Square>> opt
        = Tools.countTurnoverableDisksOfLineEngine(Square::downRight, board, square, mine);
    return opt.map(List::size).orElse(-1);
  }

  @Test
  @DisplayName("Test up side of Tools.countUpTurnoverableDisks();")
  void testUpCountTurnoverableDisks() {
    Board board = new Board();
    board.init();
    // on the another disk -> error.
    assertThat(ToolsTest.countUpTurnoverableDisks(board, Square.SQUARE_4_D, Disk.BLACK))
        .isEqualTo(-1);
    assertThat(ToolsTest.countUpTurnoverableDisks(board, Square.SQUARE_4_D, Disk.WHITE))
        .isEqualTo(-1);
    // next square is out of board -> 0.
    assertThat(ToolsTest.countUpTurnoverableDisks(board, Square.SQUARE_1_D, Disk.BLACK))
        .isEqualTo(0);
    assertThat(ToolsTest.countUpTurnoverableDisks(board, Square.SQUARE_1_D, Disk.WHITE))
        .isEqualTo(0);
    // take zero
    board.clear();
    board.setDisk(Square.SQUARE_1_B, Disk.BLACK);
    assertThat(ToolsTest.countUpTurnoverableDisks(board, Square.SQUARE_2_B, Disk.BLACK))
        .isEqualTo(0);
    // take one
    board.clear();
    board.setDisk(Square.SQUARE_1_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    assertThat(ToolsTest.countUpTurnoverableDisks(board, Square.SQUARE_3_B, Disk.BLACK))
        .isEqualTo(1);
    // take two
    board.clear();
    board.setDisk(Square.SQUARE_1_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_B, Disk.WHITE);
    assertThat(ToolsTest.countUpTurnoverableDisks(board, Square.SQUARE_4_B, Disk.BLACK))
        .isEqualTo(2);
    // take three
    board.clear();
    board.setDisk(Square.SQUARE_1_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_B, Disk.WHITE);
    assertThat(ToolsTest.countUpTurnoverableDisks(board, Square.SQUARE_5_B, Disk.BLACK))
        .isEqualTo(3);
    // take four
    board.clear();
    board.setDisk(Square.SQUARE_1_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_B, Disk.WHITE);
    assertThat(ToolsTest.countUpTurnoverableDisks(board, Square.SQUARE_6_B, Disk.BLACK))
        .isEqualTo(4);
    // take five
    board.clear();
    board.setDisk(Square.SQUARE_1_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_B, Disk.WHITE);
    assertThat(ToolsTest.countUpTurnoverableDisks(board, Square.SQUARE_7_B, Disk.BLACK))
        .isEqualTo(5);
    // take six
    board.clear();
    board.setDisk(Square.SQUARE_1_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    assertThat(ToolsTest.countUpTurnoverableDisks(board, Square.SQUARE_8_B, Disk.BLACK))
        .isEqualTo(6);
    // change black <-> white
    board.clear();
    board.setDisk(Square.SQUARE_1_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_2_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_3_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_4_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_5_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_6_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.BLACK);
    assertThat(ToolsTest.countUpTurnoverableDisks(board, Square.SQUARE_8_B, Disk.WHITE))
        .isEqualTo(6);
    // terminated by empty square -> 0
    board.clear();
    board.setDisk(Square.SQUARE_1_B, null);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    assertThat(ToolsTest.countUpTurnoverableDisks(board, Square.SQUARE_8_B, Disk.BLACK))
        .isEqualTo(0);
  }

  @Test
  @DisplayName("Test down side of Tools.countUpTurnoverableDisks();")
  void testDownCountTurnoverableDisks() {
    Board board = new Board();
    board.init();
    // on the another disk -> error.
    assertThat(ToolsTest.countDownTurnoverableDisks(board, Square.SQUARE_5_D, Disk.BLACK))
        .isEqualTo(-1);
    assertThat(ToolsTest.countDownTurnoverableDisks(board, Square.SQUARE_5_D, Disk.WHITE))
        .isEqualTo(-1);
    // next square is out of board -> 0.
    assertThat(ToolsTest.countDownTurnoverableDisks(board, Square.SQUARE_8_D, Disk.BLACK))
        .isEqualTo(0);
    assertThat(ToolsTest.countDownTurnoverableDisks(board, Square.SQUARE_8_D, Disk.WHITE))
        .isEqualTo(0);
    // next disk is mine -> 0
    assertThat(ToolsTest.countDownTurnoverableDisks(board, Square.SQUARE_3_E, Disk.BLACK))
        .isEqualTo(0);
    assertThat(ToolsTest.countDownTurnoverableDisks(board, Square.SQUARE_3_D, Disk.WHITE))
        .isEqualTo(0);
    // take one
    board.clear();
    board.setDisk(Square.SQUARE_8_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    assertThat(ToolsTest.countDownTurnoverableDisks(board, Square.SQUARE_6_B, Disk.BLACK))
        .isEqualTo(1);
    // take two
    board.clear();
    board.setDisk(Square.SQUARE_8_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_B, Disk.WHITE);
    assertThat(ToolsTest.countDownTurnoverableDisks(board, Square.SQUARE_5_B, Disk.BLACK))
        .isEqualTo(2);
    // take three
    board.clear();
    board.setDisk(Square.SQUARE_8_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_B, Disk.WHITE);
    assertThat(ToolsTest.countDownTurnoverableDisks(board, Square.SQUARE_4_B, Disk.BLACK))
        .isEqualTo(3);
    // take four
    board.clear();
    board.setDisk(Square.SQUARE_8_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_B, Disk.WHITE);
    assertThat(ToolsTest.countDownTurnoverableDisks(board, Square.SQUARE_3_B, Disk.BLACK))
        .isEqualTo(4);
    // take five
    board.clear();
    board.setDisk(Square.SQUARE_8_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_B, Disk.WHITE);
    assertThat(ToolsTest.countDownTurnoverableDisks(board, Square.SQUARE_2_B, Disk.BLACK))
        .isEqualTo(5);
    // take six
    board.clear();
    board.setDisk(Square.SQUARE_8_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    assertThat(ToolsTest.countDownTurnoverableDisks(board, Square.SQUARE_1_B, Disk.BLACK))
        .isEqualTo(6);
    // change black <-> white
    board.clear();
    board.setDisk(Square.SQUARE_8_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_6_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_5_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_4_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_3_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_B, Disk.BLACK);
    assertThat(ToolsTest.countDownTurnoverableDisks(board, Square.SQUARE_1_B, Disk.WHITE))
        .isEqualTo(6);
    // terminated by empty square -> 0
    board.clear();
    board.setDisk(Square.SQUARE_8_B, null);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_B, Disk.WHITE);
  }

  @Test
  @DisplayName("Test left side of Tools.countUpTurnoverableDisks();")
  void testLeftCountTurnoverableDisks() {
    Board board = new Board();
    board.init();
    // on the another disk -> error.
    assertThat(ToolsTest.countLeftTurnoverableDisks(board, Square.SQUARE_4_D, Disk.BLACK))
        .isEqualTo(-1);
    assertThat(ToolsTest.countLeftTurnoverableDisks(board, Square.SQUARE_4_D, Disk.WHITE))
        .isEqualTo(-1);
    // next square is out of board -> 0.
    assertThat(ToolsTest.countLeftTurnoverableDisks(board, Square.SQUARE_1_A, Disk.BLACK))
        .isEqualTo(0);
    assertThat(ToolsTest.countLeftTurnoverableDisks(board, Square.SQUARE_1_A, Disk.WHITE))
        .isEqualTo(0);
    // take zero
    board.clear();
    board.setDisk(Square.SQUARE_7_A, Disk.BLACK);
    assertThat(ToolsTest.countLeftTurnoverableDisks(board, Square.SQUARE_7_B, Disk.BLACK))
        .isEqualTo(0);
    // take one
    board.clear();
    board.setDisk(Square.SQUARE_7_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    assertThat(ToolsTest.countLeftTurnoverableDisks(board, Square.SQUARE_7_C, Disk.BLACK))
        .isEqualTo(1);
    // take two
    board.clear();
    board.setDisk(Square.SQUARE_7_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_C, Disk.WHITE);
    assertThat(ToolsTest.countLeftTurnoverableDisks(board, Square.SQUARE_7_D, Disk.BLACK))
        .isEqualTo(2);
    // take three
    board.clear();
    board.setDisk(Square.SQUARE_7_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_D, Disk.WHITE);
    assertThat(ToolsTest.countLeftTurnoverableDisks(board, Square.SQUARE_7_E, Disk.BLACK))
        .isEqualTo(3);
    // take four
    board.clear();
    board.setDisk(Square.SQUARE_7_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_E, Disk.WHITE);
    assertThat(ToolsTest.countLeftTurnoverableDisks(board, Square.SQUARE_7_F, Disk.BLACK))
        .isEqualTo(4);
    // take five
    board.clear();
    board.setDisk(Square.SQUARE_7_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_F, Disk.WHITE);
    assertThat(ToolsTest.countLeftTurnoverableDisks(board, Square.SQUARE_7_G, Disk.BLACK))
        .isEqualTo(5);
    // take six
    board.clear();
    board.setDisk(Square.SQUARE_7_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    assertThat(ToolsTest.countLeftTurnoverableDisks(board, Square.SQUARE_7_H, Disk.BLACK))
        .isEqualTo(6);
    // change black <-> white
    board.clear();
    board.setDisk(Square.SQUARE_7_A, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_C, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_D, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_E, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_F, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_G, Disk.BLACK);
    assertThat(ToolsTest.countLeftTurnoverableDisks(board, Square.SQUARE_7_H, Disk.WHITE))
        .isEqualTo(6);
    // terminated by empty square -> 0
    board.clear();
    board.setDisk(Square.SQUARE_7_A, null);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    assertThat(ToolsTest.countLeftTurnoverableDisks(board, Square.SQUARE_7_H, Disk.BLACK))
        .isEqualTo(0);
  }

  @Test
  @DisplayName("Test right side of Tools.countUpTurnoverableDisks();")
  void testRightCountTurnoverableDisks() {
    Board board = new Board();
    board.init();
    // on the another disk -> error.
    assertThat(ToolsTest.countRightTurnoverableDisks(board, Square.SQUARE_4_E, Disk.BLACK))
        .isEqualTo(-1);
    assertThat(ToolsTest.countRightTurnoverableDisks(board, Square.SQUARE_4_E, Disk.WHITE))
        .isEqualTo(-1);
    // next square is out of board -> 0.
    assertThat(ToolsTest.countRightTurnoverableDisks(board, Square.SQUARE_1_H, Disk.BLACK))
        .isEqualTo(0);
    assertThat(ToolsTest.countRightTurnoverableDisks(board, Square.SQUARE_1_H, Disk.WHITE))
        .isEqualTo(0);
    // take zero
    board.clear();
    board.setDisk(Square.SQUARE_7_H, Disk.BLACK);
    assertThat(ToolsTest.countRightTurnoverableDisks(board, Square.SQUARE_7_G, Disk.BLACK))
        .isEqualTo(0);
    // take one
    board.clear();
    board.setDisk(Square.SQUARE_7_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    assertThat(ToolsTest.countRightTurnoverableDisks(board, Square.SQUARE_7_F, Disk.BLACK))
        .isEqualTo(1);
    // take two
    board.clear();
    board.setDisk(Square.SQUARE_7_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_F, Disk.WHITE);
    assertThat(ToolsTest.countRightTurnoverableDisks(board, Square.SQUARE_7_E, Disk.BLACK))
        .isEqualTo(2);
    // take three
    board.clear();
    board.setDisk(Square.SQUARE_7_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_E, Disk.WHITE);
    assertThat(ToolsTest.countRightTurnoverableDisks(board, Square.SQUARE_7_D, Disk.BLACK))
        .isEqualTo(3);
    // take four
    board.clear();
    board.setDisk(Square.SQUARE_7_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_D, Disk.WHITE);
    assertThat(ToolsTest.countRightTurnoverableDisks(board, Square.SQUARE_7_C, Disk.BLACK))
        .isEqualTo(4);
    // take five
    board.clear();
    board.setDisk(Square.SQUARE_7_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_C, Disk.WHITE);
    assertThat(ToolsTest.countRightTurnoverableDisks(board, Square.SQUARE_7_B, Disk.BLACK))
        .isEqualTo(5);
    // take six
    board.clear();
    board.setDisk(Square.SQUARE_7_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    assertThat(ToolsTest.countRightTurnoverableDisks(board, Square.SQUARE_7_A, Disk.BLACK))
        .isEqualTo(6);
    // change black <-> white
    board.clear();
    board.setDisk(Square.SQUARE_7_H, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_G, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_F, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_E, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_D, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_C, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.BLACK);
    assertThat(ToolsTest.countRightTurnoverableDisks(board, Square.SQUARE_7_A, Disk.WHITE))
        .isEqualTo(6);
    // terminated by empty square -> 0
    board.clear();
    board.setDisk(Square.SQUARE_7_H, null);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    assertThat(ToolsTest.countRightTurnoverableDisks(board, Square.SQUARE_7_A, Disk.BLACK))
        .isEqualTo(0);
  }

  @Test
  @DisplayName("Test up left side of Tools.countUpTurnoverableDisks();")
  void testUpLeftCountTurnoverableDisks() {
    Board board = new Board();
    board.init();
    board.setDisk(Square.SQUARE_3_C, null);
    board.setDisk(Square.SQUARE_3_D, null);
    board.setDisk(Square.SQUARE_4_C, null);
    board.setDisk(Square.SQUARE_5_D, null);
    // on the another disk -> error.
    assertThat(ToolsTest.countUpLeftTurnoverableDisks(board, Square.SQUARE_4_D, Disk.BLACK))
        .isEqualTo(-1);
    assertThat(ToolsTest.countUpLeftTurnoverableDisks(board, Square.SQUARE_4_D, Disk.WHITE))
        .isEqualTo(-1);
    // next square is out of board -> 0.
    assertThat(ToolsTest.countUpLeftTurnoverableDisks(board, Square.SQUARE_1_A, Disk.BLACK))
        .isEqualTo(0);
    assertThat(ToolsTest.countUpLeftTurnoverableDisks(board, Square.SQUARE_1_A, Disk.WHITE))
        .isEqualTo(0);
    // take zero
    board.clear();
    board.setDisk(Square.SQUARE_1_A, Disk.BLACK);
    assertThat(ToolsTest.countUpLeftTurnoverableDisks(board, Square.SQUARE_2_B, Disk.BLACK))
        .isEqualTo(0);
    // take one
    board.clear();
    board.setDisk(Square.SQUARE_1_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    assertThat(ToolsTest.countUpLeftTurnoverableDisks(board, Square.SQUARE_3_C, Disk.BLACK))
        .isEqualTo(1);
    // take two
    board.clear();
    board.setDisk(Square.SQUARE_1_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_C, Disk.WHITE);
    assertThat(ToolsTest.countUpLeftTurnoverableDisks(board, Square.SQUARE_4_D, Disk.BLACK))
        .isEqualTo(2);
    // take three
    board.clear();
    board.setDisk(Square.SQUARE_1_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_D, Disk.WHITE);
    assertThat(ToolsTest.countUpLeftTurnoverableDisks(board, Square.SQUARE_5_E, Disk.BLACK))
        .isEqualTo(3);
    // take four
    board.clear();
    board.setDisk(Square.SQUARE_1_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_E, Disk.WHITE);
    assertThat(ToolsTest.countUpLeftTurnoverableDisks(board, Square.SQUARE_6_F, Disk.BLACK))
        .isEqualTo(4);
    // take five
    board.clear();
    board.setDisk(Square.SQUARE_1_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_F, Disk.WHITE);
    assertThat(ToolsTest.countUpLeftTurnoverableDisks(board, Square.SQUARE_7_G, Disk.BLACK))
        .isEqualTo(5);
    // take six
    board.clear();
    board.setDisk(Square.SQUARE_1_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    assertThat(ToolsTest.countUpLeftTurnoverableDisks(board, Square.SQUARE_8_H, Disk.BLACK))
        .isEqualTo(6);
    // change black <-> white
    board.clear();
    board.setDisk(Square.SQUARE_1_A, Disk.WHITE);
    board.setDisk(Square.SQUARE_2_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_3_C, Disk.BLACK);
    board.setDisk(Square.SQUARE_4_D, Disk.BLACK);
    board.setDisk(Square.SQUARE_5_E, Disk.BLACK);
    board.setDisk(Square.SQUARE_6_F, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_G, Disk.BLACK);
    assertThat(ToolsTest.countUpLeftTurnoverableDisks(board, Square.SQUARE_8_H, Disk.WHITE))
        .isEqualTo(6);
    // terminated by empty square -> 0
    board.clear();
    board.setDisk(Square.SQUARE_1_A, null);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    assertThat(ToolsTest.countUpLeftTurnoverableDisks(board, Square.SQUARE_8_H, Disk.BLACK))
        .isEqualTo(0);
  }

  @Test
  @DisplayName("Test up right side of Tools.countUpTurnoverableDisks();")
  void testUpRightCountTurnoverableDisks() {
    Board board = new Board();
    board.init();
    board.setDisk(Square.SQUARE_3_C, null);
    board.setDisk(Square.SQUARE_3_D, null);
    board.setDisk(Square.SQUARE_4_C, null);
    board.setDisk(Square.SQUARE_5_D, null);
    // on the another disk -> error.
    assertThat(ToolsTest.countUpRightTurnoverableDisks(board, Square.SQUARE_4_D, Disk.BLACK))
        .isEqualTo(-1);
    assertThat(ToolsTest.countUpRightTurnoverableDisks(board, Square.SQUARE_4_D, Disk.WHITE))
        .isEqualTo(-1);
    // next square is out of board -> 0.
    assertThat(ToolsTest.countUpRightTurnoverableDisks(board, Square.SQUARE_1_A, Disk.BLACK))
        .isEqualTo(0);
    assertThat(ToolsTest.countUpRightTurnoverableDisks(board, Square.SQUARE_1_A, Disk.WHITE))
        .isEqualTo(0);
    // take zero
    board.clear();
    board.setDisk(Square.SQUARE_1_H, Disk.BLACK);
    assertThat(ToolsTest.countUpRightTurnoverableDisks(board, Square.SQUARE_2_G, Disk.BLACK))
        .isEqualTo(0);
    // take one
    board.clear();
    board.setDisk(Square.SQUARE_1_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_G, Disk.WHITE);
    assertThat(ToolsTest.countUpRightTurnoverableDisks(board, Square.SQUARE_3_F, Disk.BLACK))
        .isEqualTo(1);
    // take two
    board.clear();
    board.setDisk(Square.SQUARE_1_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_F, Disk.WHITE);
    assertThat(ToolsTest.countUpRightTurnoverableDisks(board, Square.SQUARE_4_E, Disk.BLACK))
        .isEqualTo(2);
    // take three
    board.clear();
    board.setDisk(Square.SQUARE_1_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_E, Disk.WHITE);
    assertThat(ToolsTest.countUpRightTurnoverableDisks(board, Square.SQUARE_5_D, Disk.BLACK))
        .isEqualTo(3);
    // take four
    board.clear();
    board.setDisk(Square.SQUARE_1_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_D, Disk.WHITE);
    assertThat(ToolsTest.countUpRightTurnoverableDisks(board, Square.SQUARE_6_C, Disk.BLACK))
        .isEqualTo(4);
    // take five
    board.clear();
    board.setDisk(Square.SQUARE_1_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_C, Disk.WHITE);
    assertThat(ToolsTest.countUpRightTurnoverableDisks(board, Square.SQUARE_7_B, Disk.BLACK))
        .isEqualTo(5);
    // take six
    board.clear();
    board.setDisk(Square.SQUARE_1_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    assertThat(ToolsTest.countUpRightTurnoverableDisks(board, Square.SQUARE_8_A, Disk.BLACK))
        .isEqualTo(6);
    // change black <-> white
    board.clear();
    board.setDisk(Square.SQUARE_1_H, Disk.WHITE);
    board.setDisk(Square.SQUARE_2_G, Disk.BLACK);
    board.setDisk(Square.SQUARE_3_F, Disk.BLACK);
    board.setDisk(Square.SQUARE_4_E, Disk.BLACK);
    board.setDisk(Square.SQUARE_5_D, Disk.BLACK);
    board.setDisk(Square.SQUARE_6_C, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.BLACK);
    assertThat(ToolsTest.countUpRightTurnoverableDisks(board, Square.SQUARE_8_A, Disk.WHITE))
        .isEqualTo(6);
    // terminated by empty square -> 0
    board.clear();
    board.setDisk(Square.SQUARE_1_H, null);
    board.setDisk(Square.SQUARE_2_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    assertThat(
        ToolsTest.countUpRightTurnoverableDisks(board, Square.SQUARE_8_A, Disk.BLACK))
        .isEqualTo(0);
  }

  @Test
  @DisplayName("Test down left side of Tools.countUpTurnoverableDisks();")
  void testDownLeftCountTurnoverableDisks() {
    Board board = new Board();
    board.init();
    board.setDisk(Square.SQUARE_3_C, null);
    board.setDisk(Square.SQUARE_3_D, null);
    board.setDisk(Square.SQUARE_4_C, null);
    board.setDisk(Square.SQUARE_5_D, null);
    // on the another disk -> error.
    assertThat(ToolsTest.countDownLeftTurnoverableDisks(board, Square.SQUARE_4_D, Disk.BLACK))
        .isEqualTo(-1);
    assertThat(ToolsTest.countDownLeftTurnoverableDisks(board, Square.SQUARE_4_D, Disk.WHITE))
        .isEqualTo(-1);
    // next square is out of board -> 0.
    assertThat(ToolsTest.countDownLeftTurnoverableDisks(board, Square.SQUARE_1_A, Disk.BLACK))
        .isEqualTo(0);
    assertThat(ToolsTest.countDownLeftTurnoverableDisks(board, Square.SQUARE_1_A, Disk.WHITE))
        .isEqualTo(0);
    // take zero
    board.clear();
    board.setDisk(Square.SQUARE_8_A, Disk.BLACK);
    assertThat(ToolsTest.countDownLeftTurnoverableDisks(board, Square.SQUARE_7_B, Disk.BLACK))
        .isEqualTo(0);
    // take one
    board.clear();
    board.setDisk(Square.SQUARE_8_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    assertThat(ToolsTest.countDownLeftTurnoverableDisks(board, Square.SQUARE_6_C, Disk.BLACK))
        .isEqualTo(1);
    // take two
    board.clear();
    board.setDisk(Square.SQUARE_8_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_C, Disk.WHITE);
    assertThat(ToolsTest.countDownLeftTurnoverableDisks(board, Square.SQUARE_5_D, Disk.BLACK))
        .isEqualTo(2);
    // take three
    board.clear();
    board.setDisk(Square.SQUARE_8_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_D, Disk.WHITE);
    assertThat(ToolsTest.countDownLeftTurnoverableDisks(board, Square.SQUARE_4_E, Disk.BLACK))
        .isEqualTo(3);
    // take four
    board.clear();
    board.setDisk(Square.SQUARE_8_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_E, Disk.WHITE);
    assertThat(ToolsTest.countDownLeftTurnoverableDisks(board, Square.SQUARE_3_F, Disk.BLACK))
        .isEqualTo(4);
    // take five
    board.clear();
    board.setDisk(Square.SQUARE_8_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_F, Disk.WHITE);
    assertThat(ToolsTest.countDownLeftTurnoverableDisks(board, Square.SQUARE_2_G, Disk.BLACK))
        .isEqualTo(5);
    // take six
    board.clear();
    board.setDisk(Square.SQUARE_8_A, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_2_G, Disk.WHITE);
    assertThat(ToolsTest.countDownLeftTurnoverableDisks(board, Square.SQUARE_1_H, Disk.BLACK))
        .isEqualTo(6);
    // change black <-> white
    board.clear();
    board.setDisk(Square.SQUARE_8_A, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_B, Disk.BLACK);
    board.setDisk(Square.SQUARE_6_C, Disk.BLACK);
    board.setDisk(Square.SQUARE_5_D, Disk.BLACK);
    board.setDisk(Square.SQUARE_4_E, Disk.BLACK);
    board.setDisk(Square.SQUARE_3_F, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_G, Disk.BLACK);
    assertThat(ToolsTest.countDownLeftTurnoverableDisks(board, Square.SQUARE_1_H, Disk.WHITE))
        .isEqualTo(6);
    // terminated by empty square -> 0
    board.clear();
    board.setDisk(Square.SQUARE_8_A, null);
    board.setDisk(Square.SQUARE_7_B, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_2_G, Disk.WHITE);
    assertThat(ToolsTest.countDownLeftTurnoverableDisks(board, Square.SQUARE_1_H, Disk.BLACK))
        .isEqualTo(0);
  }

  @Test
  @DisplayName("Test down right side of Tools.countUpTurnoverableDisks();")
  void testDownRightCountTurnoverableDisks() {
    Board board = new Board();
    board.init();
    board.setDisk(Square.SQUARE_3_C, null);
    board.setDisk(Square.SQUARE_3_D, null);
    board.setDisk(Square.SQUARE_4_C, null);
    board.setDisk(Square.SQUARE_5_D, null);
    // on the another disk -> error.
    assertThat(ToolsTest.countDownRightTurnoverableDisks(board, Square.SQUARE_4_D, Disk.BLACK))
        .isEqualTo(-1);
    assertThat(ToolsTest.countDownRightTurnoverableDisks(board, Square.SQUARE_4_D, Disk.WHITE))
        .isEqualTo(-1);
    // next square is out of board -> 0.
    assertThat(ToolsTest.countDownRightTurnoverableDisks(board, Square.SQUARE_1_H, Disk.BLACK))
        .isEqualTo(0);
    assertThat(ToolsTest.countDownRightTurnoverableDisks(board, Square.SQUARE_1_H, Disk.WHITE))
        .isEqualTo(0);
    // take zero
    board.clear();
    board.setDisk(Square.SQUARE_8_H, Disk.BLACK);
    assertThat(ToolsTest.countDownRightTurnoverableDisks(board, Square.SQUARE_7_G, Disk.BLACK))
        .isEqualTo(0);
    // take one
    board.clear();
    board.setDisk(Square.SQUARE_8_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    assertThat(ToolsTest.countDownRightTurnoverableDisks(board, Square.SQUARE_6_F, Disk.BLACK))
        .isEqualTo(1);
    // take two
    board.clear();
    board.setDisk(Square.SQUARE_8_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_F, Disk.WHITE);
    assertThat(ToolsTest.countDownRightTurnoverableDisks(board, Square.SQUARE_5_E, Disk.BLACK))
        .isEqualTo(2);
    // take three
    board.clear();
    board.setDisk(Square.SQUARE_8_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_E, Disk.WHITE);
    assertThat(ToolsTest.countDownRightTurnoverableDisks(board, Square.SQUARE_4_D, Disk.BLACK))
        .isEqualTo(3);
    // take four
    board.clear();
    board.setDisk(Square.SQUARE_8_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_D, Disk.WHITE);
    assertThat(ToolsTest.countDownRightTurnoverableDisks(board, Square.SQUARE_3_C, Disk.BLACK))
        .isEqualTo(4);
    // take five
    board.clear();
    board.setDisk(Square.SQUARE_8_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_C, Disk.WHITE);
    assertThat(ToolsTest.countDownRightTurnoverableDisks(board, Square.SQUARE_2_B, Disk.BLACK))
        .isEqualTo(5);
    // take six
    board.clear();
    board.setDisk(Square.SQUARE_8_H, Disk.BLACK);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    assertThat(ToolsTest.countDownRightTurnoverableDisks(board, Square.SQUARE_1_A, Disk.BLACK))
        .isEqualTo(6);
    // change black <-> white
    board.clear();
    board.setDisk(Square.SQUARE_8_H, Disk.WHITE);
    board.setDisk(Square.SQUARE_7_G, Disk.BLACK);
    board.setDisk(Square.SQUARE_6_F, Disk.BLACK);
    board.setDisk(Square.SQUARE_5_E, Disk.BLACK);
    board.setDisk(Square.SQUARE_4_D, Disk.BLACK);
    board.setDisk(Square.SQUARE_3_C, Disk.BLACK);
    board.setDisk(Square.SQUARE_2_B, Disk.BLACK);
    assertThat(ToolsTest.countDownRightTurnoverableDisks(board, Square.SQUARE_1_A, Disk.WHITE))
        .isEqualTo(6);
    // terminated by empty square -> 0
    board.clear();
    board.setDisk(Square.SQUARE_8_H, null);
    board.setDisk(Square.SQUARE_7_G, Disk.WHITE);
    board.setDisk(Square.SQUARE_6_F, Disk.WHITE);
    board.setDisk(Square.SQUARE_5_E, Disk.WHITE);
    board.setDisk(Square.SQUARE_4_D, Disk.WHITE);
    board.setDisk(Square.SQUARE_3_C, Disk.WHITE);
    board.setDisk(Square.SQUARE_2_B, Disk.WHITE);
    assertThat(ToolsTest.countDownRightTurnoverableDisks(board, Square.SQUARE_1_A, Disk.BLACK))
        .isEqualTo(0);
  }

  @Test
  @DisplayName("Test of Tools.countTurnoverableDisks();")
  void testCountTurnoverableDisks() {
    Board board = new Board();
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

    // ㊚㊚㊚㊚㊚㊚＿＿
    // ㊚㊚㊚㊚㊛㊚＿＿
    // ㊚㊚㊚㊚㊚㊚＿＿
    // ㊚㊚㊚㊚㊛㊚＿＿
    // ㊚㊛㊚㊛㊚㊚＿＿
    // ㊚㊚㊚㊚㊚㊚＿＿
    // ＿＿＿＿＿＿＿＿
    // ＿＿＿＿＿＿＿＿
    Tools.move(board, Square.SQUARE_3_C, Disk.BLACK);

    Board dummy = new Board();
    dummy.clear();
    Score expects = new Score();
    Arrays.stream(Square.values()).filter(
        sq -> sq.row().index() < 6 && sq.col().index() < 6
    ).forEach(
        sq -> expects.setScore(sq, -1)
    );
    expects.setScore(Square.SQUARE_2_G, 2);
    expects.setScore(Square.SQUARE_4_G, 2);
    expects.setScore(Square.SQUARE_5_G, 2);
    expects.setScore(Square.SQUARE_6_G, 1);
    expects.setScore(Square.SQUARE_7_B, 2);
    expects.setScore(Square.SQUARE_7_D, 2);
    expects.setScore(Square.SQUARE_7_E, 2);
    expects.setScore(Square.SQUARE_7_F, 1);

    Score score = Tools.countTurnoverableDisks(board, Disk.WHITE);
    dumpBoard(dummy, expects);
    dumpBoard(dummy, score);
    assertThat(score).isEqualTo(expects);
  }

  @Test
  @DisplayName("Test exception of move();")
  void testMoveException() {
    Board board = new Board();
    board.init();
    Board clone = board.clone();
    assertThatThrownBy(() -> Tools.move(clone, Square.SQUARE_4_D, Disk.BLACK))
        .isInstanceOf(OthelloException.class);
  }

  @Test
  @DisplayName("Test Tools.move();")
  void testMove() {

    // ＿＿＿＿＿＿＿＿
    // ＿＿＿＿＿＿＿＿
    // ＿＿＿＿＿＿＿＿
    // ＿＿＿㊛㊚＿＿＿
    // ＿＿＿㊚㊚＿＿＿
    // ＿＿＿＿㊚＿＿＿
    // ＿＿＿＿＿＿＿＿
    // ＿＿＿＿＿＿＿＿
    Board board = new Board();
    board.init();
    Board clone = board.clone();
    Optional<List<Square>> opt = Tools.move(clone, Square.SQUARE_6_E, Disk.BLACK);
    assertThat(opt).isPresent();
    assertThat(opt.get()).hasSize(1).contains(Square.SQUARE_5_E);
    board.setDisk(Square.SQUARE_5_E, Disk.BLACK);
    board.setDisk(Square.SQUARE_6_E, Disk.BLACK);
    dumpBoard(board, new Score());
    dumpBoard(clone, new Score());
    assertThat(clone).isEqualTo(board);

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
      for (Col col : new Col[]{Col.COL_A, Col.COL_B, Col.COL_C, Col.COL_D, Col.COL_E,
          Col.COL_F}) {
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
    assertThat(result).isPresent();
    assertThat(result.get()).hasSize(11);
    Square[] taken = new Square[]{
        Square.SQUARE_2_B, Square.SQUARE_2_C, Square.SQUARE_2_D, Square.SQUARE_3_B,
        Square.SQUARE_3_D, Square.SQUARE_3_E, Square.SQUARE_4_B, Square.SQUARE_4_C,
        Square.SQUARE_4_D, Square.SQUARE_5_C, Square.SQUARE_5_E
    };
    assertThat(Arrays.stream(taken).filter(sq -> !result.get().contains(sq)).count())
        .isEqualTo(0);

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
      for (Col col : new Col[]{Col.COL_A, Col.COL_B, Col.COL_C, Col.COL_D, Col.COL_E,
          Col.COL_F}) {
        expects.setDisk(row, col, Disk.BLACK);
      }
    }
    expects.setDisk(Square.SQUARE_5_B, Disk.WHITE);
    expects.setDisk(Square.SQUARE_5_D, Disk.WHITE);
    expects.setDisk(Square.SQUARE_2_E, Disk.WHITE);
    expects.setDisk(Square.SQUARE_4_E, Disk.WHITE);
    assertThat(board).isEqualTo(expects);
  }

  public static void dumpBoard(
      @NotNull Board board, @NotNull Score score) {
    int blackDisks = countDisk(board, Disk.BLACK);
    int whiteDisks = countDisk(board, Disk.WHITE);
    System.err.printf("%s: %d%n", diskCharacter(Disk.BLACK), blackDisks);
    System.err.printf("%s: %d%n", diskCharacter(Disk.WHITE), whiteDisks);
    System.err.println("    A  B  C  D  E  F  G  H");
    System.err.println("  ┌──┬──┬──┬──┬──┬──┬──┬──┐");
    for (Row row : Row.values()) {
      StringBuilder builder = new StringBuilder(String.format("%d |", row.index() + 1));
      Arrays.stream(Square.values()).filter(sq -> sq.row().equals(row))
          .map(square -> board.getDisk(square).isPresent()
              ? diskCharacter(board.getDisk(square).get())
              : String.format("%2d", score.getScore(square)))
          .forEach(string -> {
            builder.append(string);
            builder.append("|");
          });
      System.err.println(builder);
      if (!row.equals(Row.ROW_8)) {
        System.err.println("  ├──┼──┼──┼──┼──┼──┼──┼──┤");
      } else {
        System.err.println("  └──┴──┴──┴──┴──┴──┴──┴──┘");
      }
    }
  }

  private static int countDisk(@NotNull Board board, @NotNull Disk disk) {
    return (int) Arrays.stream(Square.values()).filter(
        sq -> {
          Optional<Disk> opt = board.getDisk(sq);
          return opt.isPresent() && opt.get().equals(disk);
        }).count();
  }

  private static String diskCharacter(Disk disk) {
    if (disk == null) {
      return "　";
    } else {
      if (disk.equals(Disk.BLACK)) {
        return "㊚";
      } else if (disk.equals(Disk.WHITE)) {
        return "㊛";
      }
    }
    return "";
  }
}