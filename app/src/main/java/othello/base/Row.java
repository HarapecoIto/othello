package othello.base;

import java.util.Optional;

/**
 * "Row" represents a horizontal line on the board. Each "Row" is numbered from the top to the
 * bottom, with the top row being 1 and the bottom row being 8. Each "Row" contains eight squares.
 */
public enum Row {

  /**
   * Top row.
   */
  ROW_1(0),

  /**
   * Second row from the top.
   */
  ROW_2(1),

  /**
   * Third row from the top.
   */
  ROW_3(2),

  /**
   * Fourth row from the top.
   */
  ROW_4(3),

  /**
   * Fifth row from the top.
   */
  ROW_5(4),

  /**
   * Sixth row from the top.
   */
  ROW_6(5),

  /**
   * Seventh row from the top.
   */
  ROW_7(6),

  /**
   * Bottom row.
   */
  ROW_8(7);

  private final int index;

  private Row(int index) {
    this.index = index;
  }

  /**
   * Get row index of each row.
   *
   * @return {@code 0} for {@code ROW_1}, {@code 1} for {@code ROW_2}, ..., {@code 7} for
   * {@code ROW_8}.
   */
  public int index() {
    return this.index;
  }

  /**
   * Get {@code Optional} of one row up.
   *
   * @return {@code Optional.(ROW_1)} for {@code ROW_2}, {@code Optional.(ROW_2)} for {@code ROW_3},
   * ..., {@code Optional.(ROW_7)} for {@code ROW_8}, and {@code Optional.empty()} for
   * {@code ROW_1}.
   */
  public final Optional<Row> up() {
    return Optional.ofNullable(!this.equals(ROW_1) ? Row.values()[this.index - 1] : null);
  }

  /**
   * Get {@code Optional} of one row down.
   *
   * @return {@code Optional.(ROW_2)} for {@code ROW_1}, {@code Optional.(ROW_3)} for {@code ROW_2},
   * ..., {@code Optional.(ROW_8)} for {@code ROW_7}, and {@code Optional.empty()} for
   * {@code ROW_8}.
   */
  public final Optional<Row> down() {
    return Optional.ofNullable(!this.equals(ROW_8) ? Row.values()[this.index + 1] : null);
  }

}
