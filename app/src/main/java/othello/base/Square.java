package othello.base;

import java.util.Optional;

public enum Square {
  SQUARE_1_A(Row.ROW_1, Col.COL_A), SQUARE_1_B(Row.ROW_1, Col.COL_B),
  SQUARE_1_C(Row.ROW_1, Col.COL_C), SQUARE_1_D(Row.ROW_1, Col.COL_D),
  SQUARE_1_E(Row.ROW_1, Col.COL_E), SQUARE_1_F(Row.ROW_1, Col.COL_F),
  SQUARE_1_G(Row.ROW_1, Col.COL_G), SQUARE_1_H(Row.ROW_1, Col.COL_H),

  SQUARE_2_A(Row.ROW_2, Col.COL_A), SQUARE_2_B(Row.ROW_2, Col.COL_B),
  SQUARE_2_C(Row.ROW_2, Col.COL_C), SQUARE_2_D(Row.ROW_2, Col.COL_D),
  SQUARE_2_E(Row.ROW_2, Col.COL_E), SQUARE_2_F(Row.ROW_2, Col.COL_F),
  SQUARE_2_G(Row.ROW_2, Col.COL_G), SQUARE_2_H(Row.ROW_2, Col.COL_H),

  SQUARE_3_A(Row.ROW_3, Col.COL_A), SQUARE_3_B(Row.ROW_3, Col.COL_B),
  SQUARE_3_C(Row.ROW_3, Col.COL_C), SQUARE_3_D(Row.ROW_3, Col.COL_D),
  SQUARE_3_E(Row.ROW_3, Col.COL_E), SQUARE_3_F(Row.ROW_3, Col.COL_F),
  SQUARE_3_G(Row.ROW_3, Col.COL_G), SQUARE_3_H(Row.ROW_3, Col.COL_H),

  SQUARE_4_A(Row.ROW_4, Col.COL_A), SQUARE_4_B(Row.ROW_4, Col.COL_B),
  SQUARE_4_C(Row.ROW_4, Col.COL_C), SQUARE_4_D(Row.ROW_4, Col.COL_D),
  SQUARE_4_E(Row.ROW_4, Col.COL_E), SQUARE_4_F(Row.ROW_4, Col.COL_F),
  SQUARE_4_G(Row.ROW_4, Col.COL_G), SQUARE_4_H(Row.ROW_4, Col.COL_H),

  SQUARE_5_A(Row.ROW_5, Col.COL_A), SQUARE_5_B(Row.ROW_5, Col.COL_B),
  SQUARE_5_C(Row.ROW_5, Col.COL_C), SQUARE_5_D(Row.ROW_5, Col.COL_D),
  SQUARE_5_E(Row.ROW_5, Col.COL_E), SQUARE_5_F(Row.ROW_5, Col.COL_F),
  SQUARE_5_G(Row.ROW_5, Col.COL_G), SQUARE_5_H(Row.ROW_5, Col.COL_H),

  SQUARE_6_A(Row.ROW_6, Col.COL_A), SQUARE_6_B(Row.ROW_6, Col.COL_B),
  SQUARE_6_C(Row.ROW_6, Col.COL_C), SQUARE_6_D(Row.ROW_6, Col.COL_D),
  SQUARE_6_E(Row.ROW_6, Col.COL_E), SQUARE_6_F(Row.ROW_6, Col.COL_F),
  SQUARE_6_G(Row.ROW_6, Col.COL_G), SQUARE_6_H(Row.ROW_6, Col.COL_H),

  SQUARE_7_A(Row.ROW_7, Col.COL_A), SQUARE_7_B(Row.ROW_7, Col.COL_B),
  SQUARE_7_C(Row.ROW_7, Col.COL_C), SQUARE_7_D(Row.ROW_7, Col.COL_D),
  SQUARE_7_E(Row.ROW_7, Col.COL_E), SQUARE_7_F(Row.ROW_7, Col.COL_F),
  SQUARE_7_G(Row.ROW_7, Col.COL_G), SQUARE_7_H(Row.ROW_7, Col.COL_H),

  SQUARE_8_A(Row.ROW_8, Col.COL_A), SQUARE_8_B(Row.ROW_8, Col.COL_B),
  SQUARE_8_C(Row.ROW_8, Col.COL_C), SQUARE_8_D(Row.ROW_8, Col.COL_D),
  SQUARE_8_E(Row.ROW_8, Col.COL_E), SQUARE_8_F(Row.ROW_8, Col.COL_F),
  SQUARE_8_G(Row.ROW_8, Col.COL_G), SQUARE_8_H(Row.ROW_8, Col.COL_H);

  private final Row row;
  private final Col col;

  private Square(Row row, Col col) {
    this.row = row;
    this.col = col;
  }

  public final Row row() {
    return this.row;
  }

  public final Col col() {
    return this.col;
  }

  public Optional<Square> up() {
    Optional<Row> row = this.row.up();
    Optional<Col> col = Optional.of(this.col);
    if (row.isPresent()) {
      return Optional.of(Square.values()[row.get().getIndex() * 8 + col.get().getIndex()]);
    }
    return Optional.empty();
  }

  public Optional<Square> down() {
    Optional<Row> row = this.row.down();
    Optional<Col> col = Optional.of(this.col);
    if (row.isPresent()) {
      return Optional.of(Square.values()[row.get().getIndex() * 8 + col.get().getIndex()]);
    }
    return Optional.empty();
  }

  public Optional<Square> left() {
    Optional<Row> row = Optional.of(this.row);
    Optional<Col> col = this.col.left();
    if (col.isPresent()) {
      return Optional.of(Square.values()[row.get().getIndex() * 8 + col.get().getIndex()]);
    }
    return Optional.empty();
  }

  public Optional<Square> right() {
    Optional<Row> row = Optional.of(this.row);
    Optional<Col> col = this.col.right();
    if (col.isPresent()) {
      return Optional.of(Square.values()[row.get().getIndex() * 8 + col.get().getIndex()]);
    }
    return Optional.empty();
  }

}
