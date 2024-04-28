package othello.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class Board implements Cloneable {

  private final List<Optional<Stone>> stones;

  public Board() {
    this.stones = new ArrayList<>();
    for (int i = 0; i < 64; i++) {
      this.stones.add(Optional.empty());
    }
    this.init();
  }

  public void clear() {
    for (int i = 0; i < 64; i++) {
      this.setStone(Square.values()[i], null);
    }
  }

  public void init() {
    this.clear();
    this.setStone(Square.SQUARE_4_D, Stone.WHITE);
    this.setStone(Square.SQUARE_4_E, Stone.BLACK);
    this.setStone(Square.SQUARE_5_D, Stone.BLACK);
    this.setStone(Square.SQUARE_5_E, Stone.WHITE);
  }

  public void setStone(Square square, Stone stone) {
    if (square != null) {
      this.stones.set(square.getIndex(), Optional.ofNullable(stone));
    } else {
      throw new IllegalArgumentException();
    }
  }

  public Optional<Stone> getStone(Square square) {
    if (square != null) {
      return this.stones.get(square.getIndex());
    } else {
      throw new IllegalArgumentException();
    }
  }

  public void setStone(Row row, Col col, Stone stone) {
    if (row != null && col != null) {
      this.setStone(Square.getSquare(row, col).orElse(null), stone);
    } else {
      throw new IllegalArgumentException();
    }
  }

  public Optional<Stone> getStone(Row row, Col col) {
    if (row != null && col != null) {
      return this.getStone(Square.getSquare(row, col).orElse(null));
    } else {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public Board clone() {
    Board clone = new Board();
    for (int i = 0; i < 64; i++) {
      clone.setStone(Square.values()[i], this.stones.get(i).orElse(null));
    }
    return clone;
  }

  private String stonesToString() {
    StringBuilder builder = new StringBuilder();
    this.stones.stream().map(
        stone -> {
          if (stone.isEmpty()) {
            return "empty";
          } else if (stone.get().equals(Stone.BLACK)) {
            return "black";
          } else {
            return "white";
          }
        }
    ).forEach(builder::append);
    return builder.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Board)) {
      return false;
    }
    return ((Board) obj).stonesToString().equals(this.stonesToString());
  }

  @Override
  public int hashCode() {
    return this.stonesToString().hashCode();
  }
}
