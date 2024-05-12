package othello.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SquareTest {

  @Test
  @DisplayName("Test square.col();")
  void testCol() {
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
  @DisplayName("Test square.row();")
  void testRow() {
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
  @DisplayName("Test square.getIndex();")
  void testGetIndex() {
    for (int i = 0; i < 64; i++) {
      assertEquals(i, Square.values()[i].getIndex());
    }
  }

  @Test
  @DisplayName("Test square.up();")
  void testUp() {
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
  @DisplayName("Test square.down();")
  void testDown() {
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
  @DisplayName("Test square.left();")
  void testLeft() {
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
  @DisplayName("Test square.right();")
  void testRight() {
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

  @Test
  @DisplayName("Test square.upLeft();")
  void testUpLeft() {
    assertNull(Square.SQUARE_1_A.upLeft().orElse(null));
    assertNull(Square.SQUARE_2_A.upLeft().orElse(null));
    assertNull(Square.SQUARE_3_A.upLeft().orElse(null));
    assertNull(Square.SQUARE_4_A.upLeft().orElse(null));
    assertNull(Square.SQUARE_5_A.upLeft().orElse(null));
    assertNull(Square.SQUARE_6_A.upLeft().orElse(null));
    assertNull(Square.SQUARE_7_A.upLeft().orElse(null));
    assertNull(Square.SQUARE_8_A.upLeft().orElse(null));
    for (int col = 1; col < 8; col++) {
      assertNull(array1[col].upLeft().orElse(null));
      assertEquals(array1[col - 1], array2[col].upLeft().orElse(null));
      assertEquals(array2[col - 1], array3[col].upLeft().orElse(null));
      assertEquals(array3[col - 1], array4[col].upLeft().orElse(null));
      assertEquals(array4[col - 1], array5[col].upLeft().orElse(null));
      assertEquals(array5[col - 1], array6[col].upLeft().orElse(null));
      assertEquals(array6[col - 1], array7[col].upLeft().orElse(null));
      assertEquals(array7[col - 1], array8[col].upLeft().orElse(null));
    }
  }

  @Test
  @DisplayName("Test square.upRight();")
  void testUpRight() {
    assertNull(Square.SQUARE_1_H.upRight().orElse(null));
    assertNull(Square.SQUARE_2_H.upRight().orElse(null));
    assertNull(Square.SQUARE_3_H.upRight().orElse(null));
    assertNull(Square.SQUARE_4_H.upRight().orElse(null));
    assertNull(Square.SQUARE_5_H.upRight().orElse(null));
    assertNull(Square.SQUARE_6_H.upRight().orElse(null));
    assertNull(Square.SQUARE_7_H.upRight().orElse(null));
    assertNull(Square.SQUARE_8_H.upRight().orElse(null));
    for (int col = 0; col < 7; col++) {
      assertNull(array1[col].upRight().orElse(null));
      assertEquals(array1[col + 1], array2[col].upRight().orElse(null));
      assertEquals(array2[col + 1], array3[col].upRight().orElse(null));
      assertEquals(array3[col + 1], array4[col].upRight().orElse(null));
      assertEquals(array4[col + 1], array5[col].upRight().orElse(null));
      assertEquals(array5[col + 1], array6[col].upRight().orElse(null));
      assertEquals(array6[col + 1], array7[col].upRight().orElse(null));
      assertEquals(array7[col + 1], array8[col].upRight().orElse(null));
    }
  }

  @Test
  @DisplayName("Test square.downLeft();")
  void testDownLeft() {
    assertNull(Square.SQUARE_1_A.downLeft().orElse(null));
    assertNull(Square.SQUARE_2_A.downLeft().orElse(null));
    assertNull(Square.SQUARE_3_A.downLeft().orElse(null));
    assertNull(Square.SQUARE_4_A.downLeft().orElse(null));
    assertNull(Square.SQUARE_5_A.downLeft().orElse(null));
    assertNull(Square.SQUARE_6_A.downLeft().orElse(null));
    assertNull(Square.SQUARE_7_A.downLeft().orElse(null));
    assertNull(Square.SQUARE_8_A.downLeft().orElse(null));
    for (int col = 1; col < 8; col++) {
      assertEquals(array2[col - 1], array1[col].downLeft().orElse(null));
      assertEquals(array3[col - 1], array2[col].downLeft().orElse(null));
      assertEquals(array4[col - 1], array3[col].downLeft().orElse(null));
      assertEquals(array5[col - 1], array4[col].downLeft().orElse(null));
      assertEquals(array6[col - 1], array5[col].downLeft().orElse(null));
      assertEquals(array7[col - 1], array6[col].downLeft().orElse(null));
      assertEquals(array8[col - 1], array7[col].downLeft().orElse(null));
      assertNull(array8[col].downLeft().orElse(null));
    }
  }

  @Test
  @DisplayName("Test square.downRight();")
  void testDownRight() {
    assertNull(Square.SQUARE_1_H.downRight().orElse(null));
    assertNull(Square.SQUARE_2_H.downRight().orElse(null));
    assertNull(Square.SQUARE_3_H.downRight().orElse(null));
    assertNull(Square.SQUARE_4_H.downRight().orElse(null));
    assertNull(Square.SQUARE_5_H.downRight().orElse(null));
    assertNull(Square.SQUARE_6_H.downRight().orElse(null));
    assertNull(Square.SQUARE_7_H.downRight().orElse(null));
    assertNull(Square.SQUARE_8_H.downRight().orElse(null));
    for (int col = 0; col < 7; col++) {
      assertEquals(array2[col + 1], array1[col].downRight().orElse(null));
      assertEquals(array3[col + 1], array2[col].downRight().orElse(null));
      assertEquals(array4[col + 1], array3[col].downRight().orElse(null));
      assertEquals(array5[col + 1], array4[col].downRight().orElse(null));
      assertEquals(array6[col + 1], array5[col].downRight().orElse(null));
      assertEquals(array7[col + 1], array6[col].downRight().orElse(null));
      assertEquals(array8[col + 1], array7[col].downRight().orElse(null));
      assertNull(array8[col].downRight().orElse(null));
    }
  }

  @Test
  @DisplayName("Test Square.getSquare(row, col);")
  void testGetSquare() {
    assertEquals(Square.SQUARE_1_A, Square.getSquare(Row.ROW_1, Col.COL_A).orElse(null));
    assertEquals(Square.SQUARE_1_B, Square.getSquare(Row.ROW_1, Col.COL_B).orElse(null));
    assertEquals(Square.SQUARE_1_C, Square.getSquare(Row.ROW_1, Col.COL_C).orElse(null));
    assertEquals(Square.SQUARE_1_D, Square.getSquare(Row.ROW_1, Col.COL_D).orElse(null));
    assertEquals(Square.SQUARE_1_E, Square.getSquare(Row.ROW_1, Col.COL_E).orElse(null));
    assertEquals(Square.SQUARE_1_F, Square.getSquare(Row.ROW_1, Col.COL_F).orElse(null));
    assertEquals(Square.SQUARE_1_G, Square.getSquare(Row.ROW_1, Col.COL_G).orElse(null));
    assertEquals(Square.SQUARE_1_H, Square.getSquare(Row.ROW_1, Col.COL_H).orElse(null));

    assertEquals(Square.SQUARE_2_A, Square.getSquare(Row.ROW_2, Col.COL_A).orElse(null));
    assertEquals(Square.SQUARE_2_B, Square.getSquare(Row.ROW_2, Col.COL_B).orElse(null));
    assertEquals(Square.SQUARE_2_C, Square.getSquare(Row.ROW_2, Col.COL_C).orElse(null));
    assertEquals(Square.SQUARE_2_D, Square.getSquare(Row.ROW_2, Col.COL_D).orElse(null));
    assertEquals(Square.SQUARE_2_E, Square.getSquare(Row.ROW_2, Col.COL_E).orElse(null));
    assertEquals(Square.SQUARE_2_F, Square.getSquare(Row.ROW_2, Col.COL_F).orElse(null));
    assertEquals(Square.SQUARE_2_G, Square.getSquare(Row.ROW_2, Col.COL_G).orElse(null));
    assertEquals(Square.SQUARE_2_H, Square.getSquare(Row.ROW_2, Col.COL_H).orElse(null));

    assertEquals(Square.SQUARE_3_A, Square.getSquare(Row.ROW_3, Col.COL_A).orElse(null));
    assertEquals(Square.SQUARE_3_B, Square.getSquare(Row.ROW_3, Col.COL_B).orElse(null));
    assertEquals(Square.SQUARE_3_C, Square.getSquare(Row.ROW_3, Col.COL_C).orElse(null));
    assertEquals(Square.SQUARE_3_D, Square.getSquare(Row.ROW_3, Col.COL_D).orElse(null));
    assertEquals(Square.SQUARE_3_E, Square.getSquare(Row.ROW_3, Col.COL_E).orElse(null));
    assertEquals(Square.SQUARE_3_F, Square.getSquare(Row.ROW_3, Col.COL_F).orElse(null));
    assertEquals(Square.SQUARE_3_G, Square.getSquare(Row.ROW_3, Col.COL_G).orElse(null));
    assertEquals(Square.SQUARE_3_H, Square.getSquare(Row.ROW_3, Col.COL_H).orElse(null));

    assertEquals(Square.SQUARE_4_A, Square.getSquare(Row.ROW_4, Col.COL_A).orElse(null));
    assertEquals(Square.SQUARE_4_B, Square.getSquare(Row.ROW_4, Col.COL_B).orElse(null));
    assertEquals(Square.SQUARE_4_C, Square.getSquare(Row.ROW_4, Col.COL_C).orElse(null));
    assertEquals(Square.SQUARE_4_D, Square.getSquare(Row.ROW_4, Col.COL_D).orElse(null));
    assertEquals(Square.SQUARE_4_E, Square.getSquare(Row.ROW_4, Col.COL_E).orElse(null));
    assertEquals(Square.SQUARE_4_F, Square.getSquare(Row.ROW_4, Col.COL_F).orElse(null));
    assertEquals(Square.SQUARE_4_G, Square.getSquare(Row.ROW_4, Col.COL_G).orElse(null));
    assertEquals(Square.SQUARE_4_H, Square.getSquare(Row.ROW_4, Col.COL_H).orElse(null));

    assertEquals(Square.SQUARE_5_A, Square.getSquare(Row.ROW_5, Col.COL_A).orElse(null));
    assertEquals(Square.SQUARE_5_B, Square.getSquare(Row.ROW_5, Col.COL_B).orElse(null));
    assertEquals(Square.SQUARE_5_C, Square.getSquare(Row.ROW_5, Col.COL_C).orElse(null));
    assertEquals(Square.SQUARE_5_D, Square.getSquare(Row.ROW_5, Col.COL_D).orElse(null));
    assertEquals(Square.SQUARE_5_E, Square.getSquare(Row.ROW_5, Col.COL_E).orElse(null));
    assertEquals(Square.SQUARE_5_F, Square.getSquare(Row.ROW_5, Col.COL_F).orElse(null));
    assertEquals(Square.SQUARE_5_G, Square.getSquare(Row.ROW_5, Col.COL_G).orElse(null));
    assertEquals(Square.SQUARE_5_H, Square.getSquare(Row.ROW_5, Col.COL_H).orElse(null));

    assertEquals(Square.SQUARE_6_A, Square.getSquare(Row.ROW_6, Col.COL_A).orElse(null));
    assertEquals(Square.SQUARE_6_B, Square.getSquare(Row.ROW_6, Col.COL_B).orElse(null));
    assertEquals(Square.SQUARE_6_C, Square.getSquare(Row.ROW_6, Col.COL_C).orElse(null));
    assertEquals(Square.SQUARE_6_D, Square.getSquare(Row.ROW_6, Col.COL_D).orElse(null));
    assertEquals(Square.SQUARE_6_E, Square.getSquare(Row.ROW_6, Col.COL_E).orElse(null));
    assertEquals(Square.SQUARE_6_F, Square.getSquare(Row.ROW_6, Col.COL_F).orElse(null));
    assertEquals(Square.SQUARE_6_G, Square.getSquare(Row.ROW_6, Col.COL_G).orElse(null));
    assertEquals(Square.SQUARE_6_H, Square.getSquare(Row.ROW_6, Col.COL_H).orElse(null));

    assertEquals(Square.SQUARE_7_A, Square.getSquare(Row.ROW_7, Col.COL_A).orElse(null));
    assertEquals(Square.SQUARE_7_B, Square.getSquare(Row.ROW_7, Col.COL_B).orElse(null));
    assertEquals(Square.SQUARE_7_C, Square.getSquare(Row.ROW_7, Col.COL_C).orElse(null));
    assertEquals(Square.SQUARE_7_D, Square.getSquare(Row.ROW_7, Col.COL_D).orElse(null));
    assertEquals(Square.SQUARE_7_E, Square.getSquare(Row.ROW_7, Col.COL_E).orElse(null));
    assertEquals(Square.SQUARE_7_F, Square.getSquare(Row.ROW_7, Col.COL_F).orElse(null));
    assertEquals(Square.SQUARE_7_G, Square.getSquare(Row.ROW_7, Col.COL_G).orElse(null));
    assertEquals(Square.SQUARE_7_H, Square.getSquare(Row.ROW_7, Col.COL_H).orElse(null));

    assertEquals(Square.SQUARE_8_A, Square.getSquare(Row.ROW_8, Col.COL_A).orElse(null));
    assertEquals(Square.SQUARE_8_B, Square.getSquare(Row.ROW_8, Col.COL_B).orElse(null));
    assertEquals(Square.SQUARE_8_C, Square.getSquare(Row.ROW_8, Col.COL_C).orElse(null));
    assertEquals(Square.SQUARE_8_D, Square.getSquare(Row.ROW_8, Col.COL_D).orElse(null));
    assertEquals(Square.SQUARE_8_E, Square.getSquare(Row.ROW_8, Col.COL_E).orElse(null));
    assertEquals(Square.SQUARE_8_F, Square.getSquare(Row.ROW_8, Col.COL_F).orElse(null));
    assertEquals(Square.SQUARE_8_G, Square.getSquare(Row.ROW_8, Col.COL_G).orElse(null));
    assertEquals(Square.SQUARE_8_H, Square.getSquare(Row.ROW_8, Col.COL_H).orElse(null));
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
