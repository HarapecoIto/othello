package othello.base;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SquareTest {

  @Test
  void col() {
    Square[] arrayA = new Square[]{
        Square.SQUARE_1_A, Square.SQUARE_2_A, Square.SQUARE_3_A, Square.SQUARE_4_A,
        Square.SQUARE_5_A, Square.SQUARE_6_A, Square.SQUARE_7_A, Square.SQUARE_8_A};
    for (Square sq : arrayA) {
      assertEquals(Col.COL_A, sq.col());
    }

    Square[] arrayB = new Square[]{
        Square.SQUARE_1_B, Square.SQUARE_2_B, Square.SQUARE_3_B, Square.SQUARE_4_B,
        Square.SQUARE_5_B, Square.SQUARE_6_B, Square.SQUARE_7_B, Square.SQUARE_8_B};
    for (Square sq : arrayB) {
      assertEquals(Col.COL_B, sq.col());
    }

    Square[] arrayC = new Square[]{
        Square.SQUARE_1_C, Square.SQUARE_2_C, Square.SQUARE_3_C, Square.SQUARE_4_C,
        Square.SQUARE_5_C, Square.SQUARE_6_C, Square.SQUARE_7_C, Square.SQUARE_8_C};
    for (Square sq : arrayC) {
      assertEquals(Col.COL_C, sq.col());
    }

    Square[] arrayD = new Square[]{
        Square.SQUARE_1_D, Square.SQUARE_2_D, Square.SQUARE_3_D, Square.SQUARE_4_D,
        Square.SQUARE_5_D, Square.SQUARE_6_D, Square.SQUARE_7_D, Square.SQUARE_8_D};
    for (Square sq : arrayD) {
      assertEquals(Col.COL_D, sq.col());
    }

    Square[] arrayE = new Square[]{
        Square.SQUARE_1_E, Square.SQUARE_2_E, Square.SQUARE_3_E, Square.SQUARE_4_E,
        Square.SQUARE_5_E, Square.SQUARE_6_E, Square.SQUARE_7_E, Square.SQUARE_8_E};
    for (Square sq : arrayE) {
      assertEquals(Col.COL_E, sq.col());
    }

    Square[] arrayF = new Square[]{
        Square.SQUARE_1_F, Square.SQUARE_2_F, Square.SQUARE_3_F, Square.SQUARE_4_F,
        Square.SQUARE_5_F, Square.SQUARE_6_F, Square.SQUARE_7_F, Square.SQUARE_8_F};
    for (Square sq : arrayF) {
      assertEquals(Col.COL_F, sq.col());
    }

    Square[] arrayG = new Square[]{
        Square.SQUARE_1_G, Square.SQUARE_2_G, Square.SQUARE_3_G, Square.SQUARE_4_G,
        Square.SQUARE_5_G, Square.SQUARE_6_G, Square.SQUARE_7_G, Square.SQUARE_8_G};
    for (Square sq : arrayG) {
      assertEquals(Col.COL_G, sq.col());
    }

    Square[] arrayH = new Square[]{
        Square.SQUARE_1_H, Square.SQUARE_2_H, Square.SQUARE_3_H, Square.SQUARE_4_H,
        Square.SQUARE_5_H, Square.SQUARE_6_H, Square.SQUARE_7_H, Square.SQUARE_8_H};
    for (Square sq : arrayH) {
      assertEquals(Col.COL_H, sq.col());
    }
  }

  @Test
  void row() {
    Square[] array1 = new Square[]{
        Square.SQUARE_1_A, Square.SQUARE_1_B, Square.SQUARE_1_C, Square.SQUARE_1_D,
        Square.SQUARE_1_E, Square.SQUARE_1_F, Square.SQUARE_1_G, Square.SQUARE_1_H};
    for (Square sq : array1) {
      assertEquals(Row.ROW_1, sq.row());
    }

    Square[] array2 = new Square[]{
        Square.SQUARE_2_A, Square.SQUARE_2_B, Square.SQUARE_2_C, Square.SQUARE_2_D,
        Square.SQUARE_2_E, Square.SQUARE_2_F, Square.SQUARE_2_G, Square.SQUARE_2_H};
    for (Square sq : array2) {
      assertEquals(Row.ROW_2, sq.row());
    }

    Square[] array3 = new Square[]{
        Square.SQUARE_3_A, Square.SQUARE_3_B, Square.SQUARE_3_C, Square.SQUARE_3_D,
        Square.SQUARE_3_E, Square.SQUARE_3_F, Square.SQUARE_3_G, Square.SQUARE_3_H};
    for (Square sq : array3) {
      assertEquals(Row.ROW_3, sq.row());
    }

    Square[] array4 = new Square[]{
        Square.SQUARE_4_A, Square.SQUARE_4_B, Square.SQUARE_4_C, Square.SQUARE_4_D,
        Square.SQUARE_4_E, Square.SQUARE_4_F, Square.SQUARE_4_G, Square.SQUARE_4_H};
    for (Square sq : array4) {
      assertEquals(Row.ROW_4, sq.row());
    }

    Square[] array5 = new Square[]{
        Square.SQUARE_5_A, Square.SQUARE_5_B, Square.SQUARE_5_C, Square.SQUARE_5_D,
        Square.SQUARE_5_E, Square.SQUARE_5_F, Square.SQUARE_5_G, Square.SQUARE_5_H};
    for (Square sq : array5) {
      assertEquals(Row.ROW_5, sq.row());
    }

    Square[] array6 = new Square[]{
        Square.SQUARE_6_A, Square.SQUARE_6_B, Square.SQUARE_6_C, Square.SQUARE_6_D,
        Square.SQUARE_6_E, Square.SQUARE_6_F, Square.SQUARE_6_G, Square.SQUARE_6_H};
    for (Square sq : array6) {
      assertEquals(Row.ROW_6, sq.row());
    }
    
    Square[] array7 = new Square[]{
        Square.SQUARE_7_A, Square.SQUARE_7_B, Square.SQUARE_7_C, Square.SQUARE_7_D,
        Square.SQUARE_7_E, Square.SQUARE_7_F, Square.SQUARE_7_G, Square.SQUARE_7_H};
    for (Square sq : array7) {
      assertEquals(Row.ROW_7, sq.row());
    }

    Square[] array8 = new Square[]{
        Square.SQUARE_8_A, Square.SQUARE_8_B, Square.SQUARE_8_C, Square.SQUARE_8_D,
        Square.SQUARE_8_E, Square.SQUARE_8_F, Square.SQUARE_8_G, Square.SQUARE_8_H};
    for (Square sq : array8) {
      assertEquals(Row.ROW_8, sq.row());
    }

  }
}
