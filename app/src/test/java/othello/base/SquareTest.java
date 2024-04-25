package othello.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.Test;

public class SquareTest {

  @Test
  void col() {
    for (Square sq : arrayA) {
      assertEquals(Col.COL_A, sq.col());
    }
    for (Square sq : arrayB) {
      assertEquals(Col.COL_B, sq.col());
    }
    for (Square sq : arrayC) {
      assertEquals(Col.COL_C, sq.col());
    }
    for (Square sq : arrayD) {
      assertEquals(Col.COL_D, sq.col());
    }
    for (Square sq : arrayE) {
      assertEquals(Col.COL_E, sq.col());
    }
    for (Square sq : arrayF) {
      assertEquals(Col.COL_F, sq.col());
    }
    for (Square sq : arrayG) {
      assertEquals(Col.COL_G, sq.col());
    }
    for (Square sq : arrayH) {
      assertEquals(Col.COL_H, sq.col());
    }
  }

  @Test
  void row() {
    for (Square sq : array1) {
      assertEquals(Row.ROW_1, sq.row());
    }
    for (Square sq : array2) {
      assertEquals(Row.ROW_2, sq.row());
    }
    for (Square sq : array3) {
      assertEquals(Row.ROW_3, sq.row());
    }
    for (Square sq : array4) {
      assertEquals(Row.ROW_4, sq.row());
    }
    for (Square sq : array5) {
      assertEquals(Row.ROW_5, sq.row());
    }
    for (Square sq : array6) {
      assertEquals(Row.ROW_6, sq.row());
    }
    for (Square sq : array7) {
      assertEquals(Row.ROW_7, sq.row());
    }
    for (Square sq : array8) {
      assertEquals(Row.ROW_8, sq.row());
    }
  }

  @Test
  void set() {
    for (Square sq : Square.values()) {
      sq.setStone(Stone.BLACK);
      Optional<Stone> stone1 = sq.getStone();
      assertTrue(stone1.isPresent());
      assertEquals(Stone.BLACK, stone1.get());

      sq.setStone(Stone.WHITE);
      Optional<Stone> stone2 = sq.getStone();
      assertTrue(stone2.isPresent());
      assertEquals(Stone.WHITE, stone2.get());

      sq.setStone(null);
      Optional<Stone> stone3 = sq.getStone();
      assertTrue(stone3.isEmpty());
    }
  }


  @Test
  void up() {
    for (int col = 0; col < 8; col++) {
      assertNull(array1[col].up().orElse(null));
      assertEquals(array1[col], array2[col].up().orElse(null));
      assertEquals(array2[col], array3[col].up().orElse(null));
      assertEquals(array3[col], array4[col].up().orElse(null));
      assertEquals(array4[col], array5[col].up().orElse(null));
      assertEquals(array5[col], array6[col].up().orElse(null));
      assertEquals(array6[col], array7[col].up().orElse(null));
      assertEquals(array7[col], array8[col].up().orElse(null));
    }
  }

  @Test
  void down() {
    for (int col = 0; col < 8; col++) {
      assertEquals(array2[col], array1[col].down().orElse(null));
      assertEquals(array3[col], array2[col].down().orElse(null));
      assertEquals(array4[col], array3[col].down().orElse(null));
      assertEquals(array5[col], array4[col].down().orElse(null));
      assertEquals(array6[col], array5[col].down().orElse(null));
      assertEquals(array7[col], array6[col].down().orElse(null));
      assertEquals(array8[col], array7[col].down().orElse(null));
      assertNull(array8[col].down().orElse(null));
    }
  }

  @Test
  void left() {
    for (int row = 0; row < 8; row++) {
      assertNull(arrayA[row].left().orElse(null));
      assertEquals(arrayA[row], arrayB[row].left().orElse(null));
      assertEquals(arrayB[row], arrayC[row].left().orElse(null));
      assertEquals(arrayC[row], arrayD[row].left().orElse(null));
      assertEquals(arrayD[row], arrayE[row].left().orElse(null));
      assertEquals(arrayE[row], arrayF[row].left().orElse(null));
      assertEquals(arrayF[row], arrayG[row].left().orElse(null));
      assertEquals(arrayG[row], arrayH[row].left().orElse(null));
    }
  }

  @Test
  void right() {
    for (int row = 0; row < 8; row++) {
      assertEquals(arrayB[row], arrayA[row].right().orElse(null));
      assertEquals(arrayC[row], arrayB[row].right().orElse(null));
      assertEquals(arrayD[row], arrayC[row].right().orElse(null));
      assertEquals(arrayE[row], arrayD[row].right().orElse(null));
      assertEquals(arrayF[row], arrayE[row].right().orElse(null));
      assertEquals(arrayG[row], arrayF[row].right().orElse(null));
      assertEquals(arrayH[row], arrayG[row].right().orElse(null));
      assertNull(arrayH[row].right().orElse(null));
    }
  }

  static Square[] array1 = new Square[]{
      Square.SQUARE_1_A, Square.SQUARE_1_B, Square.SQUARE_1_C, Square.SQUARE_1_D,
      Square.SQUARE_1_E, Square.SQUARE_1_F, Square.SQUARE_1_G, Square.SQUARE_1_H};
  static Square[] array2 = new Square[]{
      Square.SQUARE_2_A, Square.SQUARE_2_B, Square.SQUARE_2_C, Square.SQUARE_2_D,
      Square.SQUARE_2_E, Square.SQUARE_2_F, Square.SQUARE_2_G, Square.SQUARE_2_H};
  static Square[] array3 = new Square[]{
      Square.SQUARE_3_A, Square.SQUARE_3_B, Square.SQUARE_3_C, Square.SQUARE_3_D,
      Square.SQUARE_3_E, Square.SQUARE_3_F, Square.SQUARE_3_G, Square.SQUARE_3_H};
  static Square[] array4 = new Square[]{
      Square.SQUARE_4_A, Square.SQUARE_4_B, Square.SQUARE_4_C, Square.SQUARE_4_D,
      Square.SQUARE_4_E, Square.SQUARE_4_F, Square.SQUARE_4_G, Square.SQUARE_4_H};
  static Square[] array5 = new Square[]{
      Square.SQUARE_5_A, Square.SQUARE_5_B, Square.SQUARE_5_C, Square.SQUARE_5_D,
      Square.SQUARE_5_E, Square.SQUARE_5_F, Square.SQUARE_5_G, Square.SQUARE_5_H};
  static Square[] array6 = new Square[]{
      Square.SQUARE_6_A, Square.SQUARE_6_B, Square.SQUARE_6_C, Square.SQUARE_6_D,
      Square.SQUARE_6_E, Square.SQUARE_6_F, Square.SQUARE_6_G, Square.SQUARE_6_H};
  static Square[] array7 = new Square[]{
      Square.SQUARE_7_A, Square.SQUARE_7_B, Square.SQUARE_7_C, Square.SQUARE_7_D,
      Square.SQUARE_7_E, Square.SQUARE_7_F, Square.SQUARE_7_G, Square.SQUARE_7_H};
  static Square[] array8 = new Square[]{
      Square.SQUARE_8_A, Square.SQUARE_8_B, Square.SQUARE_8_C, Square.SQUARE_8_D,
      Square.SQUARE_8_E, Square.SQUARE_8_F, Square.SQUARE_8_G, Square.SQUARE_8_H};

  static Square[] arrayA = new Square[]{
      Square.SQUARE_1_A, Square.SQUARE_2_A, Square.SQUARE_3_A, Square.SQUARE_4_A,
      Square.SQUARE_5_A, Square.SQUARE_6_A, Square.SQUARE_7_A, Square.SQUARE_8_A};
  static Square[] arrayB = new Square[]{
      Square.SQUARE_1_B, Square.SQUARE_2_B, Square.SQUARE_3_B, Square.SQUARE_4_B,
      Square.SQUARE_5_B, Square.SQUARE_6_B, Square.SQUARE_7_B, Square.SQUARE_8_B};
  static Square[] arrayC = new Square[]{
      Square.SQUARE_1_C, Square.SQUARE_2_C, Square.SQUARE_3_C, Square.SQUARE_4_C,
      Square.SQUARE_5_C, Square.SQUARE_6_C, Square.SQUARE_7_C, Square.SQUARE_8_C};
  static Square[] arrayD = new Square[]{
      Square.SQUARE_1_D, Square.SQUARE_2_D, Square.SQUARE_3_D, Square.SQUARE_4_D,
      Square.SQUARE_5_D, Square.SQUARE_6_D, Square.SQUARE_7_D, Square.SQUARE_8_D};
  static Square[] arrayE = new Square[]{
      Square.SQUARE_1_E, Square.SQUARE_2_E, Square.SQUARE_3_E, Square.SQUARE_4_E,
      Square.SQUARE_5_E, Square.SQUARE_6_E, Square.SQUARE_7_E, Square.SQUARE_8_E};
  static Square[] arrayF = new Square[]{
      Square.SQUARE_1_F, Square.SQUARE_2_F, Square.SQUARE_3_F, Square.SQUARE_4_F,
      Square.SQUARE_5_F, Square.SQUARE_6_F, Square.SQUARE_7_F, Square.SQUARE_8_F};
  static Square[] arrayG = new Square[]{
      Square.SQUARE_1_G, Square.SQUARE_2_G, Square.SQUARE_3_G, Square.SQUARE_4_G,
      Square.SQUARE_5_G, Square.SQUARE_6_G, Square.SQUARE_7_G, Square.SQUARE_8_G};
  static Square[] arrayH = new Square[]{
      Square.SQUARE_1_H, Square.SQUARE_2_H, Square.SQUARE_3_H, Square.SQUARE_4_H,
      Square.SQUARE_5_H, Square.SQUARE_6_H, Square.SQUARE_7_H, Square.SQUARE_8_H};
}
