package othello.base;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SquareTest {

  @Test
  @DisplayName("Test square.col();")
  void testCol() {
    for (Square sq : arrayA) {
      assertThat(sq.col()).isEqualTo(Col.COL_A);
    }
    for (Square sq : arrayB) {
      assertThat(sq.col()).isEqualTo(Col.COL_B);
    }
    for (Square sq : arrayC) {
      assertThat(sq.col()).isEqualTo(Col.COL_C);
    }
    for (Square sq : arrayD) {
      assertThat(sq.col()).isEqualTo(Col.COL_D);
    }
    for (Square sq : arrayE) {
      assertThat(sq.col()).isEqualTo(Col.COL_E);
    }
    for (Square sq : arrayF) {
      assertThat(sq.col()).isEqualTo(Col.COL_F);
    }
    for (Square sq : arrayG) {
      assertThat(sq.col()).isEqualTo(Col.COL_G);
    }
    for (Square sq : arrayH) {
      assertThat(sq.col()).isEqualTo(Col.COL_H);
    }

  }

  @Test
  @DisplayName("Test square.row();")
  void testRow() {
    for (Square sq : array1) {
      assertThat(sq.row()).isEqualTo(Row.ROW_1);
    }
    for (Square sq : array2) {
      assertThat(sq.row()).isEqualTo(Row.ROW_2);
    }
    for (Square sq : array3) {
      assertThat(sq.row()).isEqualTo(Row.ROW_3);
    }
    for (Square sq : array4) {
      assertThat(sq.row()).isEqualTo(Row.ROW_4);
    }
    for (Square sq : array5) {
      assertThat(sq.row()).isEqualTo(Row.ROW_5);
    }
    for (Square sq : array6) {
      assertThat(sq.row()).isEqualTo(Row.ROW_6);
    }
    for (Square sq : array7) {
      assertThat(sq.row()).isEqualTo(Row.ROW_7);
    }
    for (Square sq : array8) {
      assertThat(sq.row()).isEqualTo(Row.ROW_8);
    }
  }

  @Test
  @DisplayName("Test square.getIndex();")
  void testGetIndex() {
    for (int i = 0; i < 64; i++) {
      assertThat(Square.values()[i].getIndex()).isEqualTo(i);
    }
  }

  @Test
  @DisplayName("Test square.up();")
  void testUp() {
    for (int col = 0; col < 8; col++) {
      assertThat(array1[col].up().orElse(null)).isNull();
      assertThat(array2[col].up().orElse(null)).isEqualTo(array1[col]);
      assertThat(array3[col].up().orElse(null)).isEqualTo(array2[col]);
      assertThat(array4[col].up().orElse(null)).isEqualTo(array3[col]);
      assertThat(array5[col].up().orElse(null)).isEqualTo(array4[col]);
      assertThat(array6[col].up().orElse(null)).isEqualTo(array5[col]);
      assertThat(array7[col].up().orElse(null)).isEqualTo(array6[col]);
      assertThat(array8[col].up().orElse(null)).isEqualTo(array7[col]);
    }
  }

  @Test
  @DisplayName("Test square.down();")
  void testDown() {
    for (int col = 0; col < 8; col++) {
      assertThat(array1[col].down().orElse(null)).isEqualTo(array2[col]);
      assertThat(array2[col].down().orElse(null)).isEqualTo(array3[col]);
      assertThat(array3[col].down().orElse(null)).isEqualTo(array4[col]);
      assertThat(array4[col].down().orElse(null)).isEqualTo(array5[col]);
      assertThat(array5[col].down().orElse(null)).isEqualTo(array6[col]);
      assertThat(array6[col].down().orElse(null)).isEqualTo(array7[col]);
      assertThat(array7[col].down().orElse(null)).isEqualTo(array8[col]);
      assertThat(array8[col].down().orElse(null)).isNull();
    }
  }

  @Test
  @DisplayName("Test square.left();")
  void testLeft() {
    for (int row = 0; row < 8; row++) {
      assertThat(arrayA[row].left().orElse(null)).isNull();
      assertThat(arrayB[row].left().orElse(null)).isEqualTo(arrayA[row]);
      assertThat(arrayC[row].left().orElse(null)).isEqualTo(arrayB[row]);
      assertThat(arrayD[row].left().orElse(null)).isEqualTo(arrayC[row]);
      assertThat(arrayE[row].left().orElse(null)).isEqualTo(arrayD[row]);
      assertThat(arrayF[row].left().orElse(null)).isEqualTo(arrayE[row]);
      assertThat(arrayG[row].left().orElse(null)).isEqualTo(arrayF[row]);
      assertThat(arrayH[row].left().orElse(null)).isEqualTo(arrayG[row]);
    }
  }

  @Test
  @DisplayName("Test square.right();")
  void testRight() {
    for (int row = 0; row < 8; row++) {
      assertThat(arrayA[row].right().orElse(null)).isEqualTo(arrayB[row]);
      assertThat(arrayB[row].right().orElse(null)).isEqualTo(arrayC[row]);
      assertThat(arrayC[row].right().orElse(null)).isEqualTo(arrayD[row]);
      assertThat(arrayD[row].right().orElse(null)).isEqualTo(arrayE[row]);
      assertThat(arrayE[row].right().orElse(null)).isEqualTo(arrayF[row]);
      assertThat(arrayF[row].right().orElse(null)).isEqualTo(arrayG[row]);
      assertThat(arrayG[row].right().orElse(null)).isEqualTo(arrayH[row]);
      assertThat(arrayH[row].right().orElse(null)).isNull();
    }
  }

  @Test
  @DisplayName("Test square.upLeft();")
  void testUpLeft() {
    assertThat(Square.SQUARE_1_A.upLeft().orElse(null)).isNull();
    assertThat(Square.SQUARE_2_A.upLeft().orElse(null)).isNull();
    assertThat(Square.SQUARE_3_A.upLeft().orElse(null)).isNull();
    assertThat(Square.SQUARE_4_A.upLeft().orElse(null)).isNull();
    assertThat(Square.SQUARE_5_A.upLeft().orElse(null)).isNull();
    assertThat(Square.SQUARE_6_A.upLeft().orElse(null)).isNull();
    assertThat(Square.SQUARE_7_A.upLeft().orElse(null)).isNull();
    assertThat(Square.SQUARE_8_A.upLeft().orElse(null)).isNull();
    for (int col = 1; col < 8; col++) {
      assertThat(array1[col].upLeft().orElse(null)).isNull();
      assertThat(array2[col].upLeft().orElse(null)).isEqualTo(array1[col - 1]);
      assertThat(array3[col].upLeft().orElse(null)).isEqualTo(array2[col - 1]);
      assertThat(array4[col].upLeft().orElse(null)).isEqualTo(array3[col - 1]);
      assertThat(array5[col].upLeft().orElse(null)).isEqualTo(array4[col - 1]);
      assertThat(array6[col].upLeft().orElse(null)).isEqualTo(array5[col - 1]);
      assertThat(array7[col].upLeft().orElse(null)).isEqualTo(array6[col - 1]);
      assertThat(array8[col].upLeft().orElse(null)).isEqualTo(array7[col - 1]);
    }

  }

  @Test
  @DisplayName("Test square.upRight();")
  void testUpRight() {
    assertThat(Square.SQUARE_1_H.upRight().orElse(null)).isNull();
    assertThat(Square.SQUARE_2_H.upRight().orElse(null)).isNull();
    assertThat(Square.SQUARE_3_H.upRight().orElse(null)).isNull();
    assertThat(Square.SQUARE_4_H.upRight().orElse(null)).isNull();
    assertThat(Square.SQUARE_5_H.upRight().orElse(null)).isNull();
    assertThat(Square.SQUARE_6_H.upRight().orElse(null)).isNull();
    assertThat(Square.SQUARE_7_H.upRight().orElse(null)).isNull();
    assertThat(Square.SQUARE_8_H.upRight().orElse(null)).isNull();

    for (int col = 0; col < 7; col++) {
      assertThat(array1[col].upRight().orElse(null)).isNull();
      assertThat(array2[col].upRight().orElse(null)).isEqualTo(array1[col + 1]);
      assertThat(array3[col].upRight().orElse(null)).isEqualTo(array2[col + 1]);
      assertThat(array4[col].upRight().orElse(null)).isEqualTo(array3[col + 1]);
      assertThat(array5[col].upRight().orElse(null)).isEqualTo(array4[col + 1]);
      assertThat(array6[col].upRight().orElse(null)).isEqualTo(array5[col + 1]);
      assertThat(array7[col].upRight().orElse(null)).isEqualTo(array6[col + 1]);
      assertThat(array8[col].upRight().orElse(null)).isEqualTo(array7[col + 1]);
    }
  }

  @Test
  @DisplayName("Test square.downLeft();")
  void testDownLeft() {
    assertThat(Square.SQUARE_1_A.downLeft().orElse(null)).isNull();
    assertThat(Square.SQUARE_2_A.downLeft().orElse(null)).isNull();
    assertThat(Square.SQUARE_3_A.downLeft().orElse(null)).isNull();
    assertThat(Square.SQUARE_4_A.downLeft().orElse(null)).isNull();
    assertThat(Square.SQUARE_5_A.downLeft().orElse(null)).isNull();
    assertThat(Square.SQUARE_6_A.downLeft().orElse(null)).isNull();
    assertThat(Square.SQUARE_7_A.downLeft().orElse(null)).isNull();
    assertThat(Square.SQUARE_8_A.downLeft().orElse(null)).isNull();

    for (int col = 1; col < 8; col++) {
      assertThat(array1[col].downLeft().orElse(null)).isEqualTo(array2[col - 1]);
      assertThat(array2[col].downLeft().orElse(null)).isEqualTo(array3[col - 1]);
      assertThat(array3[col].downLeft().orElse(null)).isEqualTo(array4[col - 1]);
      assertThat(array4[col].downLeft().orElse(null)).isEqualTo(array5[col - 1]);
      assertThat(array5[col].downLeft().orElse(null)).isEqualTo(array6[col - 1]);
      assertThat(array6[col].downLeft().orElse(null)).isEqualTo(array7[col - 1]);
      assertThat(array7[col].downLeft().orElse(null)).isEqualTo(array8[col - 1]);
      assertThat(array8[col].downLeft().orElse(null)).isNull();
    }
  }

  @Test
  @DisplayName("Test square.downRight();")
  void testDownRight() {
    assertThat(Square.SQUARE_1_H.downRight().orElse(null)).isNull();
    assertThat(Square.SQUARE_2_H.downRight().orElse(null)).isNull();
    assertThat(Square.SQUARE_3_H.downRight().orElse(null)).isNull();
    assertThat(Square.SQUARE_4_H.downRight().orElse(null)).isNull();
    assertThat(Square.SQUARE_5_H.downRight().orElse(null)).isNull();
    assertThat(Square.SQUARE_6_H.downRight().orElse(null)).isNull();
    assertThat(Square.SQUARE_7_H.downRight().orElse(null)).isNull();
    assertThat(Square.SQUARE_8_H.downRight().orElse(null)).isNull();
    for (int col = 0; col < 7; col++) {
      assertThat(array1[col].downRight().orElse(null)).isEqualTo(array2[col + 1]);
      assertThat(array2[col].downRight().orElse(null)).isEqualTo(array3[col + 1]);
      assertThat(array3[col].downRight().orElse(null)).isEqualTo(array4[col + 1]);
      assertThat(array4[col].downRight().orElse(null)).isEqualTo(array5[col + 1]);
      assertThat(array5[col].downRight().orElse(null)).isEqualTo(array6[col + 1]);
      assertThat(array6[col].downRight().orElse(null)).isEqualTo(array7[col + 1]);
      assertThat(array7[col].downRight().orElse(null)).isEqualTo(array8[col + 1]);
      assertThat(array8[col].downRight().orElse(null)).isNull();
    }
  }

  @Test
  @DisplayName("Test Square.getSquare(row, col);")
  void testGetSquare() {
    assertThat(Square.getSquare(Row.ROW_1, Col.COL_A).orElse(null)).isEqualTo(Square.SQUARE_1_A);
    assertThat(Square.getSquare(Row.ROW_1, Col.COL_B).orElse(null)).isEqualTo(Square.SQUARE_1_B);
    assertThat(Square.getSquare(Row.ROW_1, Col.COL_C).orElse(null)).isEqualTo(Square.SQUARE_1_C);
    assertThat(Square.getSquare(Row.ROW_1, Col.COL_D).orElse(null)).isEqualTo(Square.SQUARE_1_D);
    assertThat(Square.getSquare(Row.ROW_1, Col.COL_E).orElse(null)).isEqualTo(Square.SQUARE_1_E);
    assertThat(Square.getSquare(Row.ROW_1, Col.COL_F).orElse(null)).isEqualTo(Square.SQUARE_1_F);
    assertThat(Square.getSquare(Row.ROW_1, Col.COL_G).orElse(null)).isEqualTo(Square.SQUARE_1_G);
    assertThat(Square.getSquare(Row.ROW_1, Col.COL_H).orElse(null)).isEqualTo(Square.SQUARE_1_H);

    assertThat(Square.getSquare(Row.ROW_2, Col.COL_A).orElse(null)).isEqualTo(Square.SQUARE_2_A);
    assertThat(Square.getSquare(Row.ROW_2, Col.COL_B).orElse(null)).isEqualTo(Square.SQUARE_2_B);
    assertThat(Square.getSquare(Row.ROW_2, Col.COL_C).orElse(null)).isEqualTo(Square.SQUARE_2_C);
    assertThat(Square.getSquare(Row.ROW_2, Col.COL_D).orElse(null)).isEqualTo(Square.SQUARE_2_D);
    assertThat(Square.getSquare(Row.ROW_2, Col.COL_E).orElse(null)).isEqualTo(Square.SQUARE_2_E);
    assertThat(Square.getSquare(Row.ROW_2, Col.COL_F).orElse(null)).isEqualTo(Square.SQUARE_2_F);
    assertThat(Square.getSquare(Row.ROW_2, Col.COL_G).orElse(null)).isEqualTo(Square.SQUARE_2_G);
    assertThat(Square.getSquare(Row.ROW_2, Col.COL_H).orElse(null)).isEqualTo(Square.SQUARE_2_H);

    assertThat(Square.getSquare(Row.ROW_3, Col.COL_A).orElse(null)).isEqualTo(Square.SQUARE_3_A);
    assertThat(Square.getSquare(Row.ROW_3, Col.COL_B).orElse(null)).isEqualTo(Square.SQUARE_3_B);
    assertThat(Square.getSquare(Row.ROW_3, Col.COL_C).orElse(null)).isEqualTo(Square.SQUARE_3_C);
    assertThat(Square.getSquare(Row.ROW_3, Col.COL_D).orElse(null)).isEqualTo(Square.SQUARE_3_D);
    assertThat(Square.getSquare(Row.ROW_3, Col.COL_E).orElse(null)).isEqualTo(Square.SQUARE_3_E);
    assertThat(Square.getSquare(Row.ROW_3, Col.COL_F).orElse(null)).isEqualTo(Square.SQUARE_3_F);
    assertThat(Square.getSquare(Row.ROW_3, Col.COL_G).orElse(null)).isEqualTo(Square.SQUARE_3_G);
    assertThat(Square.getSquare(Row.ROW_3, Col.COL_H).orElse(null)).isEqualTo(Square.SQUARE_3_H);

    assertThat(Square.getSquare(Row.ROW_4, Col.COL_A).orElse(null)).isEqualTo(Square.SQUARE_4_A);
    assertThat(Square.getSquare(Row.ROW_4, Col.COL_B).orElse(null)).isEqualTo(Square.SQUARE_4_B);
    assertThat(Square.getSquare(Row.ROW_4, Col.COL_C).orElse(null)).isEqualTo(Square.SQUARE_4_C);
    assertThat(Square.getSquare(Row.ROW_4, Col.COL_D).orElse(null)).isEqualTo(Square.SQUARE_4_D);
    assertThat(Square.getSquare(Row.ROW_4, Col.COL_E).orElse(null)).isEqualTo(Square.SQUARE_4_E);
    assertThat(Square.getSquare(Row.ROW_4, Col.COL_F).orElse(null)).isEqualTo(Square.SQUARE_4_F);
    assertThat(Square.getSquare(Row.ROW_4, Col.COL_G).orElse(null)).isEqualTo(Square.SQUARE_4_G);
    assertThat(Square.getSquare(Row.ROW_4, Col.COL_H).orElse(null)).isEqualTo(Square.SQUARE_4_H);

    assertThat(Square.getSquare(Row.ROW_5, Col.COL_A).orElse(null)).isEqualTo(Square.SQUARE_5_A);
    assertThat(Square.getSquare(Row.ROW_5, Col.COL_B).orElse(null)).isEqualTo(Square.SQUARE_5_B);
    assertThat(Square.getSquare(Row.ROW_5, Col.COL_C).orElse(null)).isEqualTo(Square.SQUARE_5_C);
    assertThat(Square.getSquare(Row.ROW_5, Col.COL_D).orElse(null)).isEqualTo(Square.SQUARE_5_D);
    assertThat(Square.getSquare(Row.ROW_5, Col.COL_E).orElse(null)).isEqualTo(Square.SQUARE_5_E);
    assertThat(Square.getSquare(Row.ROW_5, Col.COL_F).orElse(null)).isEqualTo(Square.SQUARE_5_F);
    assertThat(Square.getSquare(Row.ROW_5, Col.COL_G).orElse(null)).isEqualTo(Square.SQUARE_5_G);
    assertThat(Square.getSquare(Row.ROW_5, Col.COL_H).orElse(null)).isEqualTo(Square.SQUARE_5_H);

    assertThat(Square.getSquare(Row.ROW_6, Col.COL_A).orElse(null)).isEqualTo(Square.SQUARE_6_A);
    assertThat(Square.getSquare(Row.ROW_6, Col.COL_B).orElse(null)).isEqualTo(Square.SQUARE_6_B);
    assertThat(Square.getSquare(Row.ROW_6, Col.COL_C).orElse(null)).isEqualTo(Square.SQUARE_6_C);
    assertThat(Square.getSquare(Row.ROW_6, Col.COL_D).orElse(null)).isEqualTo(Square.SQUARE_6_D);
    assertThat(Square.getSquare(Row.ROW_6, Col.COL_E).orElse(null)).isEqualTo(Square.SQUARE_6_E);
    assertThat(Square.getSquare(Row.ROW_6, Col.COL_F).orElse(null)).isEqualTo(Square.SQUARE_6_F);
    assertThat(Square.getSquare(Row.ROW_6, Col.COL_G).orElse(null)).isEqualTo(Square.SQUARE_6_G);
    assertThat(Square.getSquare(Row.ROW_6, Col.COL_H).orElse(null)).isEqualTo(Square.SQUARE_6_H);

    assertThat(Square.getSquare(Row.ROW_7, Col.COL_A).orElse(null)).isEqualTo(Square.SQUARE_7_A);
    assertThat(Square.getSquare(Row.ROW_7, Col.COL_B).orElse(null)).isEqualTo(Square.SQUARE_7_B);
    assertThat(Square.getSquare(Row.ROW_7, Col.COL_C).orElse(null)).isEqualTo(Square.SQUARE_7_C);
    assertThat(Square.getSquare(Row.ROW_7, Col.COL_D).orElse(null)).isEqualTo(Square.SQUARE_7_D);
    assertThat(Square.getSquare(Row.ROW_7, Col.COL_E).orElse(null)).isEqualTo(Square.SQUARE_7_E);
    assertThat(Square.getSquare(Row.ROW_7, Col.COL_F).orElse(null)).isEqualTo(Square.SQUARE_7_F);
    assertThat(Square.getSquare(Row.ROW_7, Col.COL_G).orElse(null)).isEqualTo(Square.SQUARE_7_G);
    assertThat(Square.getSquare(Row.ROW_7, Col.COL_H).orElse(null)).isEqualTo(Square.SQUARE_7_H);

    assertThat(Square.getSquare(Row.ROW_8, Col.COL_A).orElse(null)).isEqualTo(Square.SQUARE_8_A);
    assertThat(Square.getSquare(Row.ROW_8, Col.COL_B).orElse(null)).isEqualTo(Square.SQUARE_8_B);
    assertThat(Square.getSquare(Row.ROW_8, Col.COL_C).orElse(null)).isEqualTo(Square.SQUARE_8_C);
    assertThat(Square.getSquare(Row.ROW_8, Col.COL_D).orElse(null)).isEqualTo(Square.SQUARE_8_D);
    assertThat(Square.getSquare(Row.ROW_8, Col.COL_E).orElse(null)).isEqualTo(Square.SQUARE_8_E);
    assertThat(Square.getSquare(Row.ROW_8, Col.COL_F).orElse(null)).isEqualTo(Square.SQUARE_8_F);
    assertThat(Square.getSquare(Row.ROW_8, Col.COL_G).orElse(null)).isEqualTo(Square.SQUARE_8_G);
    assertThat(Square.getSquare(Row.ROW_8, Col.COL_H).orElse(null)).isEqualTo(Square.SQUARE_8_H);
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
