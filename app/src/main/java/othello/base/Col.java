package othello.base;

import java.util.Optional;

/**
 * "Col" represents the vertical lines on the board. Each "Col" is alphabetized from left to right,
 * with the left-most column being A and the right-most column being H. Each column contains eight
 * squares.
 */
public enum Col {

  /**
   * Left-most column.
   */
  COL_A(0),

  /**
   * Second column from the left.
   */
  COL_B(1),

  /**
   * Third column from the left.
   */
  COL_C(2),

  /**
   * Forth column from the left.
   */
  COL_D(3),

  /**
   * Fifth column from the left.
   */
  COL_E(4),

  /**
   * Sixth column from the left.
   */
  COL_F(5),

  /**
   * Seventh column from the left.
   */
  COL_G(6),

  /**
   * Right-most column.
   */
  COL_H(7);

  private final int index;

  private Col(int index) {
    this.index = index;
  }

  /**
   * Get column index of each row.
   *
   * @return {@code 0} for {@code COL_A}, {@code 1} for {@code COL_B}, ..., {@code 7} for
   * {@code COL_H}.
   */
  public int index() {
    return this.index;
  }

  /**
   * Get {@code Optional} of one column left.
   *
   * @return {@code Optional.(COL_A)} for {@code COL_B}, {@code Optional.(COL_B)} for {@code COL_C},
   * ..., {@code Optional.(COL_G)} for {@code COL_H}, and {@code Optional.empty()} for
   * {@code ROW_A}.
   */
  public Optional<Col> left() {
    return Optional.ofNullable(this.index > 0 ? Col.values()[this.index - 1] : null);
  }

  /**
   * Get {@code Optional} of one col right.
   *
   * @return {@code Optional.(COL_B)} for {@code COL_A}, {@code Optional.(COL_C)} for {@code COL_B},
   * ..., {@code Optional.(COL_H)} for {@code COL_G}, and {@code Optional.empty()} for
   * {@code ROW_H}.
   */
  public Optional<Col> right() {
    return Optional.ofNullable(this.index < 7 ? Col.values()[this.index + 1] : null);
  }

}
