package net.yoursweetest.othello;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import othello.base.Board;
import othello.base.Col;
import othello.base.Disk;
import othello.base.Row;
import othello.base.Square;
import othello.player.Player;
import othello.util.Tools;

public class HungryPlayerTest {

  @BeforeAll
  public static void setUp() {
    Tools.init();
  }

  @AfterAll
  public static void shutdown() {
    Tools.shutdown();
  }

  @Test
  @DisplayName("Test 1 of hungryPlayer.move();")
  void testMove1() {
    Board board = new Board();
    board.init();
    List<Square> expects = Arrays.asList(
        Square.SQUARE_3_E, Square.SQUARE_4_F, Square.SQUARE_5_C, Square.SQUARE_6_D
    );
    for (long seed = 0; seed < 10; seed++) {
      Player player = new HungryPlayer("Hungry Player", seed);
      player.init(Disk.WHITE);
      Optional<Square> square = player.move(board.clone(), null);
      assertThat(square).isPresent();
      assertThat(square.get()).isIn(expects);
      player.shutdown();
    }
  }

  @Test
  @DisplayName("Test 2 of hungryPlayer.move();")
  void testMove2() {
    Board board = new Board();
    board.init();
    List<Square> expects = Arrays.asList(
        Square.SQUARE_3_D, Square.SQUARE_4_C, Square.SQUARE_5_F, Square.SQUARE_6_E
    );
    for (long seed = 0; seed < 10; seed++) {
      Player player = new HungryPlayer("Hungry Player", seed);
      player.init(Disk.BLACK);
      Optional<Square> square = player.move(board.clone(), null);
      assertThat(square).isPresent();
      assertThat(square.get()).isIn(expects);
      player.shutdown();
    }
  }

  @Test
  @DisplayName("Test 3 of hungryPlayer.move();")
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
    Player player = new HungryPlayer("Hungry Player", 13L);
    player.init(Disk.WHITE);
    Optional<Square> square = player.move(board.clone(), Square.SQUARE_6_F);
    assertThat(square).isEmpty();
    player.shutdown();
  }

  @Test
  @DisplayName("Test 4 of hungryPlayer.move();")
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
    for (long seed = 0; seed < 10; seed++) {
      Player player = new HungryPlayer("Hungry Player", seed);
      player.init(Disk.WHITE);
      Optional<Square> square = player.move(board.clone(), Square.SQUARE_6_F);
      assertThat(square).isPresent();
      assertThat(square.get()).isIn(expects);
      player.shutdown();
    }
  }
}
