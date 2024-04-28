package othello.base;

import jakarta.validation.constraints.NotNull;
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

  public void setStone(@NotNull Square square, Stone stone) {
    this.stones.set(square.getIndex(), Optional.ofNullable(stone));
  }

  public Optional<Stone> getStone(@NotNull Square square) {
    return this.stones.get(square.getIndex());
  }

  public void setStone(@NotNull Row row, @NotNull Col col, Stone stone) {
    Optional<Square> square = Square.getSquare(row, col);
    square.ifPresent(sq -> this.stones.set(sq.getIndex(), Optional.ofNullable(stone)));
  }


  public Optional<Stone> getStone(@NotNull Row row, @NotNull Col col) {
    Optional<Square> square = Square.getSquare(row, col);
    return square.isPresent() ? this.stones.get(square.get().getIndex()) : Optional.empty();
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
