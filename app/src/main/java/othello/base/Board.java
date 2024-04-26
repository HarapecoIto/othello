package othello.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Board implements Cloneable {

  private final List<Optional<Stone>> stones;

  public Board() {
    this.stones = new ArrayList<>();
    for (int i = 0; i < 64; i++) {
      this.stones.add(Optional.empty());
    }
    this.init();
  }

  public void init() {
    for (int i = 0; i < 64; i++) {
      this.setStone(Square.values()[i], null);
    }
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
    Board board = null;
    try {
      board = (Board) super.clone();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return board;
  }

}
