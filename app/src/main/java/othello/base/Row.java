package othello.base;

import java.util.Optional;

public enum Row {
  ROW_1(0), ROW_2(1), ROW_3(2), ROW_4(3), ROW_5(4), ROW_6(5), ROW_7(6), ROW_8(7);

  private final int index;

  private Row(int index) {
    this.index = index;
  }

  public int getIndex() {
    return this.index;
  }

  public final Optional<Row> down() {
    return Optional.ofNullable(!this.equals(ROW_8) ? Row.values()[this.index + 1] : null);
  }

  public final Optional<Row> up() {
    return Optional.ofNullable(!this.equals(ROW_1) ? Row.values()[this.index - 1] : null);
  }

}
