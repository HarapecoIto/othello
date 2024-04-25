package othello.base;

import java.util.Optional;

public enum Col {
  COL_A(0), COL_B(1), COL_C(2), COL_D(3), COL_E(4), COL_F(5), COL_G(6), COL_H(7);

  private final int index;

  private Col(int index) {
    this.index = index;
  }

  public int getIndex() {
    return this.index;
  }

  public Optional<Col> left() {
    return Optional.ofNullable(this.index > 0 ? Col.values()[this.index - 1] : null);
  }

  public Optional<Col> right() {
    return Optional.ofNullable(this.index < 7 ? Col.values()[this.index + 1] : null);
  }

}
