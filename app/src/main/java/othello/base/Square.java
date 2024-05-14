package othello.base;

import jakarta.validation.constraints.NotNull;
import java.util.Optional;

/**
 * A "square" represents 64 8x8 squares on the board. Each square can contain a black or white disk,
 * and there are also empty squares.
 */
public enum Square {

  /**
   * {@code SQUARE_1_A} is the square represented by row 1 and column A. The same applies to the
   * other enumerated squares.
   */
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

  /**
   * Get the {@code Row}.
   *
   * @return The {@code Row} on which this square is placed.
   */
  public final Row row() {
    return this.row;
  }

  /**
   * Get the {@code Col}.
   *
   * @return The {@code Col} on which this square is placed.
   */
  public final Col col() {
    return this.col;
  }

  /**
   * Get the index of each square.
   *
   * @return For example, {@code 0} for {@code SQUARE_1_A},...,{@code 7} for {@code SQUARE_1_H},
   * {@code 8} for {@code SQUARE_2_A},...,{@code 63} for {@code SQUARE_8_H}.
   */
  public int index() {
    return row.index() * 8 + col.index();
  }

  /**
   * Get {@code Optional} of one square up.
   *
   * @return For example: {@code Optional.(SQUARE_4_D)} for {@code SQUARE_5_D},
   * {@code Optional.(SQUARE_6_F)} for {@code SQUARE_7_F}. return {@code Optional.empty()} for
   * undefined square. For example, {@code SQUARE_1_E}.
   */
  public Optional<Square> up() {
    Optional<Row> row = this.row.up();
    Optional<Col> col = Optional.of(this.col);
    return row.map(value -> Square.values()[value.index() * 8 + col.get().index()]);
  }

  /**
   * Get {@code Optional} of one square down.
   *
   * @return For example: {@code Optional.(SQUARE_6_D)} for {@code SQUARE_5_D},
   * {@code Optional.(SQUARE_8_F)} for {@code SQUARE_7_F}. return {@code Optional.empty()} for
   * undefined square. For example, {@code SQUARE_8_E}.
   */
  public Optional<Square> down() {
    Optional<Row> row = this.row.down();
    Optional<Col> col = Optional.of(this.col);
    return row.map(value -> Square.values()[value.index() * 8 + col.get().index()]);
  }

  /**
   * Get {@code Optional} of one square left.
   *
   * @return For example: {@code Optional.(SQUARE_5_C)} for {@code SQUARE_5_D},
   * {@code Optional.(SQUARE_7_E)} for {@code SQUARE_7_F}. return {@code Optional.empty()} for
   * undefined square. For example, {@code SQUARE_3_A}.
   */
  public Optional<Square> left() {
    Optional<Row> row = Optional.of(this.row);
    Optional<Col> col = this.col.left();
    return col.map(value -> Square.values()[row.get().index() * 8 + value.index()]);
  }

  /**
   * Get {@code Optional} of one square right.
   *
   * @return For example: {@code Optional.(SQUARE_5_E)} for {@code SQUARE_5_D},
   * {@code Optional.(SQUARE_7_G)} for {@code SQUARE_7_F}. return {@code Optional.empty()} for
   * undefined square. For example, {@code SQUARE_3_H}.
   */
  public Optional<Square> right() {
    Optional<Row> row = Optional.of(this.row);
    Optional<Col> col = this.col.right();
    return col.map(value -> Square.values()[row.get().index() * 8 + value.index()]);
  }

  /**
   * Get {@code Optional} of one upper left square.
   *
   * @return For example: {@code Optional.(SQUARE_4_C)} for {@code SQUARE_5_D},
   * {@code Optional.(SQUARE_6_E)} for {@code SQUARE_7_F}. return {@code Optional.empty()} for
   * undefined square. For example, {@code SQUARE_1_E}, {@code SQUARE_3_A}.
   */
  public Optional<Square> upLeft() {
    Optional<Row> row = this.row.up();
    Optional<Col> col = this.col.left();
    if (row.isPresent() && col.isPresent()) {
      return Optional.of(Square.values()[row.get().index() * 8 + col.get().index()]);
    }
    return Optional.empty();
  }

  /**
   * Get {@code Optional} of one upper right square.
   *
   * @return For example: {@code Optional.(SQUARE_2_E)} for {@code SQUARE_3_D},
   * {@code Optional.(SQUARE_6_G)} for {@code SQUARE_7_F}. return {@code Optional.empty()} for
   * undefined square. For example, {@code SQUARE_1_E}, {@code SQUARE_3_H}.
   */
  public Optional<Square> upRight() {
    Optional<Row> row = this.row.up();
    Optional<Col> col = this.col.right();
    if (row.isPresent() && col.isPresent()) {
      return Optional.of(Square.values()[row.get().index() * 8 + col.get().index()]);
    }
    return Optional.empty();
  }

  /**
   * Get {@code Optional} of one lower left square.
   *
   * @return For example: {@code Optional.(SQUARE_4_C)} for {@code SQUARE_3_D},
   * {@code Optional.(SQUARE_8_E)} for {@code SQUARE_7_F}. return {@code Optional.empty()} for
   * undefined square. For example, {@code SQUARE_8_E}, {@code SQUARE_3_A}.
   */
  public Optional<Square> downLeft() {
    Optional<Row> row = this.row.down();
    Optional<Col> col = this.col.left();
    if (row.isPresent() && col.isPresent()) {
      return Optional.of(Square.values()[row.get().index() * 8 + col.get().index()]);
    }
    return Optional.empty();
  }


  /**
   * Get {@code Optional} of one lower right square.
   *
   * @return For example: {@code Optional.(SQUARE_4_E)} for {@code SQUARE_3_D},
   * {@code Optional.(SQUARE_8_G)} for {@code SQUARE_7_F}. return {@code Optional.empty()} for
   * undefined square. For example, {@code SQUARE_8_E}, {@code SQUARE_3_H}.
   */
  public Optional<Square> downRight() {
    Optional<Row> row = this.row.down();
    Optional<Col> col = this.col.right();
    if (row.isPresent() && col.isPresent()) {
      return Optional.of(Square.values()[row.get().index() * 8 + col.get().index()]);
    }
    return Optional.empty();
  }

  /**
   * Get square of specific {@code Row} and {@code Col}.
   *
   * @param row {@code Row} which is square placed.
   * @param col {@code Col} which is square placed.
   * @return square which is specified by given {@code row} and {@code col}.
   */
  public
  static Optional<Square> getSquare(@NotNull Row row, @NotNull Col col) {
    return Optional.of(Square.values()[row.index() * 8 + col.index()]);
  }

}
