package net.yoursweetest.othello.citrus;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import othello.base.Board;
import othello.base.Disk;
import othello.base.Square;
import othello.player.Player;
import othello.util.Tools;

public class MikanPlayerTest {

  static private final Square[][] squares = {
      {Square.SQUARE_1_A, Square.SQUARE_1_B, Square.SQUARE_1_C, Square.SQUARE_1_D,
          Square.SQUARE_1_E, Square.SQUARE_1_F, Square.SQUARE_1_G, Square.SQUARE_1_H},
      {Square.SQUARE_2_A, Square.SQUARE_2_B, Square.SQUARE_2_C, Square.SQUARE_2_D,
          Square.SQUARE_2_E, Square.SQUARE_2_F, Square.SQUARE_2_G, Square.SQUARE_2_H},
      {Square.SQUARE_3_A, Square.SQUARE_3_B, Square.SQUARE_3_C, Square.SQUARE_3_D,
          Square.SQUARE_3_E, Square.SQUARE_3_F, Square.SQUARE_3_G, Square.SQUARE_3_H},
      {Square.SQUARE_4_A, Square.SQUARE_4_B, Square.SQUARE_4_C, Square.SQUARE_4_D,
          Square.SQUARE_4_E, Square.SQUARE_4_F, Square.SQUARE_4_G, Square.SQUARE_4_H},
      {Square.SQUARE_5_A, Square.SQUARE_5_B, Square.SQUARE_5_C, Square.SQUARE_5_D,
          Square.SQUARE_5_E, Square.SQUARE_5_F, Square.SQUARE_5_G, Square.SQUARE_5_H},
      {Square.SQUARE_6_A, Square.SQUARE_6_B, Square.SQUARE_6_C, Square.SQUARE_6_D,
          Square.SQUARE_6_E, Square.SQUARE_6_F, Square.SQUARE_6_G, Square.SQUARE_6_H},
      {Square.SQUARE_7_A, Square.SQUARE_7_B, Square.SQUARE_7_C, Square.SQUARE_7_D,
          Square.SQUARE_7_E, Square.SQUARE_7_F, Square.SQUARE_7_G, Square.SQUARE_7_H},
      {Square.SQUARE_8_A, Square.SQUARE_8_B, Square.SQUARE_8_C, Square.SQUARE_8_D,
          Square.SQUARE_8_E, Square.SQUARE_8_F, Square.SQUARE_8_G, Square.SQUARE_8_H}
  };

  private static final String[][] queueStrings = {
      {"1A", "1B", "1C", "1D", "1E", "1F", "1G", "1H"},
      {"2A", "2B", "2C", "2D", "2E", "2F", "2G", "2H"},
      {"3A", "3B", "3C", "3D", "3E", "3F", "3G", "3H"},
      {"4A", "4B", "4C", "4D", "4E", "4F", "4G", "4H"},
      {"5A", "5B", "5C", "5D", "5E", "5F", "5G", "5H"},
      {"6A", "6B", "6C", "6D", "6E", "6F", "6G", "6H"},
      {"7A", "7B", "7C", "7D", "7E", "7F", "7G", "7H"},
      {"8A", "8B", "8C", "8D", "8E", "8F", "8G", "8H"}
  };

  @Test
  @DisplayName("test of serializeSquare()")
  public void testSerializeSquare() {
    assertThat(MikanPlayer.serializeSquare(null)).isEqualTo("@@");
    for (int row = 0; row < squares.length; row++) {
      for (int col = 0; col < squares[row].length; col++) {
        assertThat(MikanPlayer.serializeSquare(squares[row][col]))
            .isEqualTo(queueStrings[row][col]);
      }
    }
  }

  @Test
  @DisplayName("test of deserializeSquare()")
  public void testDeserializeSquare() {
    assertThat(MikanPlayer.deserializeSquare("0A")).isEmpty();
    assertThat(MikanPlayer.deserializeSquare("1J")).isEmpty();
    assertThat(MikanPlayer.deserializeSquare("@@")).isEmpty();
    for (int row = 0; row < squares.length; row++) {
      for (int col = 0; col < squares[row].length; col++) {
        String string = queueStrings[row][col];
        Optional<Square> opt = MikanPlayer.deserializeSquare(string);
        Square expects = Square.values()[row * 8 + col];
        assertThat(opt).isPresent();
        assertThat(opt.get()).isEqualTo(expects);
      }
    }
  }

  private static final String BOARD_STRING_1 = ""
      + "EBEWEBEW"
      + "BEWEBEWE"
      + "EWEBEWEB"
      + "WEBEWEBE"
      + "EBEWEBEW"
      + "BEWEBEWE"
      + "EWEBEWEB"
      + "WEBEWEBE";

  @Test
  @DisplayName("test of serialize / deserialize Board")
  public void testSerializeBoard() {
    Board board = new Board();
    Arrays.stream(Square.values()).forEach(
        sq -> {
          Disk disk = null;
          if ((sq.row().index() + sq.col().index()) % 4 == 3) {
            disk = Disk.WHITE;
          } else if ((sq.row().index() + sq.col().index()) % 4 == 1) {
            disk = Disk.BLACK;
          }
          board.setDisk(sq, disk);
        }
    );
    // serialize
    assertThat(MikanPlayer.serializeBoard(board)).isEqualTo(BOARD_STRING_1);
    // deserialize
    Optional<Board> opt = MikanPlayer.deserializeBoard(BOARD_STRING_1);
    assertThat(opt).isPresent();
    assertThat(opt.get()).isEqualTo(board);
    opt = MikanPlayer.deserializeBoard(BOARD_STRING_1.substring(0, 63));
    assertThat(opt).isEmpty();
    opt = MikanPlayer.deserializeBoard(BOARD_STRING_1.substring(0, 63) + "A");
    assertThat(opt).isEmpty();
    opt = MikanPlayer.deserializeBoard(BOARD_STRING_1 + "E");
    assertThat(opt).isEmpty();
  }

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
      Player player = new MikanPlayer("Mikan Player", seed, MAX_STEP);
      player.init(Disk.WHITE);
      Optional<Square> square = player.move(board.clone(), null);
      assertThat(square).isPresent();
      assertThat(square.get()).isIn(expects);
      player.shutdown();
    }
  }

  @Test
  @DisplayName("Test 2 of mikanPlayer.move();")
  void testMove2() {
    // ＿＿＿＿＿＿＿＿
    // ＿＿＿＿＿＿＿＿
    // ＿＿＿＿＿＿＿＿
    // ＿＿＿㊛㊚＿＿＿
    // ＿＿＿㊛㊛＿＿＿
    // ＿＿＿㊛＿＿＿＿
    // ＿＿＿＿＿＿＿＿
    // ＿＿＿＿＿＿＿＿
    Board board = new Board();
    board.init();
    Tools.move(board, Square.SQUARE_6_D, Disk.WHITE);
    List<Square> expects = Arrays.asList(
        Square.SQUARE_4_C, Square.SQUARE_6_C, Square.SQUARE_6_E
    );
    for (long seed = 0; seed < 5; seed++) {
      Player player = new MikanPlayer("Mikan Player", seed, MAX_STEP);
      player.init(Disk.BLACK);
      Optional<Square> square = player.move(board.clone(), Square.SQUARE_6_D);
      assertThat(square).isPresent();
      assertThat(square.get()).isIn(expects);
      player.shutdown();
    }
  }

  @Test
  @DisplayName("Compare MikanPlayer to LemonPlayer")
  void compareMovedWith() {
    for (long seed = 0; seed < 1; seed++) {
      CitrusPlayer player1 = new MikanPlayer("Mikan", seed, 5);
      CitrusPlayer player2 = new LemonPlayer("Lemon", seed, 5);
      CitrusPlayerTest.compareMovedWith(player1, player2);
    }
  }
}
