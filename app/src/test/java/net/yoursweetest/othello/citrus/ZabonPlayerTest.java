package net.yoursweetest.othello.citrus;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import othello.base.Board;
import othello.base.Col;
import othello.base.Disk;
import othello.base.Row;
import othello.base.Square;
import othello.player.Player;

public class ZabonPlayerTest {

  static final int MAX_STEP = 4;

  @Test
  @DisplayName("Test 1 of lemonPlayer.move();")
  void testMove1() {
    Board board = new Board();
    board.init();
    List<Square> expects = Arrays.asList(
        Square.SQUARE_3_E, Square.SQUARE_4_F, Square.SQUARE_5_C, Square.SQUARE_6_D
    );
    for (long seed = 0; seed < 5; seed++) {
      Player player = new ZabonPlayer("Zabon Player", seed, MAX_STEP);
      player.init(Disk.WHITE);
      Optional<Square> square = player.move(board.clone(), null);
      assertThat(square).isPresent();
      assertThat(square.get()).isIn(expects);
      player.shutdown();
    }
  }

  @Test
  @DisplayName("Test 2 of ZabonPlayer.move();")
  void testMove2() {
    Board board = new Board();
    board.init();
    List<Square> expects = Arrays.asList(
        Square.SQUARE_3_D, Square.SQUARE_4_C, Square.SQUARE_5_F, Square.SQUARE_6_E
    );
    for (long seed = 0; seed < 5; seed++) {
      Player player = new ZabonPlayer("Zabon Player", seed, MAX_STEP);
      player.init(Disk.BLACK);
      Optional<Square> square = player.move(board.clone(), null);
      assertThat(square).isPresent();
      assertThat(square.get()).isIn(expects);
      player.shutdown();
    }
  }

  @Test
  @DisplayName("Test 3 of lemonPlayer.move();")
  void testMove3() {
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
    Player player = new ZabonPlayer("Zabon Player", 13L, MAX_STEP);
    player.init(Disk.WHITE);
    Optional<Square> square = player.move(board.clone(), Square.SQUARE_6_F);
    assertThat(square).isEmpty();
    player.shutdown();
  }

  @Test
  @DisplayName("Test 4 of lemonPlayer.move();")
  void testMove4() {
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
    for (long seed = 0; seed < 5; seed++) {
      Player player = new ZabonPlayer("Zabon Player", seed, MAX_STEP);
      player.init(Disk.WHITE);
      Optional<Square> square = player.move(board.clone(), Square.SQUARE_6_F);
      assertThat(square).isPresent();
      assertThat(square.get()).isIn(expects);
      player.shutdown();
    }
  }

  @Test
  @DisplayName("Compare ZabonPlayer to LemonPlayer")
  void compareMovedWith() {
    for (long seed = 0; seed < 1; seed++) {
      CitrusPlayer player1 = new ZabonPlayer("Zabon", seed, 4);
      CitrusPlayer player2 = new LemonPlayer("Lemon", seed, 4);
      CitrusPlayerTest.compareMovedWith(player1, player2);
    }
    for (long seed = 10; seed < 11; seed++) {
      CitrusPlayer player1 = new ZabonPlayer("Zabon", seed, 5);
      CitrusPlayer player2 = new LemonPlayer("Lemon", seed, 5);
      CitrusPlayerTest.compareMovedWith(player1, player2);
    }
  }
}
