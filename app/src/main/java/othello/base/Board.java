package othello.base;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * "Board" is the board on which the game is played and consists of 64 (8 x 8) squares. In each
 * square you can place a black or white disk.
 */
public final class Board implements Cloneable {

  private final List<Optional<Disk>> disks;

  public Board() {
    this.disks = new ArrayList<>();
    for (int i = 0; i < 64; i++) {
      this.disks.add(Optional.empty());
    }
    this.init();
  }

  /**
   * Make empty for each square on the board.
   */
  public void clear() {
    for (int i = 0; i < 64; i++) {
      this.setDisk(Square.values()[i], null);
    }
  }

  /**
   * Make initial board for the game. Black disk for {@code SQUARE_4_E}, {@code SQUARE_5_D}, White
   * disk for {@code SQUARE_4_D}, {@code SQUARE_5_E}, empty for other squares.
   */
  public void init() {
    this.clear();
    this.setDisk(Square.SQUARE_4_D, Disk.WHITE);
    this.setDisk(Square.SQUARE_4_E, Disk.BLACK);
    this.setDisk(Square.SQUARE_5_D, Disk.BLACK);
    this.setDisk(Square.SQUARE_5_E, Disk.WHITE);
  }

  /**
   * Set disk on the specific square on the board.
   *
   * @param square Square where the disk will be placed.
   * @param disk   Disk to place. {@code null} to empty cell.
   */
  public void setDisk(@NotNull Square square, Disk disk) {
    this.disks.set(square.getIndex(), Optional.ofNullable(disk));
  }

  /**
   * Get {@code Optional} of specified square on the board.
   *
   * @param square Target square to get disk.
   * @return {@code Optional.of(Disk.BLACK)}, {@code Optional.of(Disk.WHITE)}, or
   * {@code Optional.empty()}, responding to the given square.
   */
  public Optional<Disk> getDisk(@NotNull Square square) {
    return this.disks.get(square.getIndex());
  }

  /**
   * Set disk on the specific square on the board.
   *
   * @param row  Row of target square to set disk.
   * @param col  Col of target square to set disk.
   * @param disk Disk to place. {@code null} to empty cell.
   */
  public void setDisk(@NotNull Row row, @NotNull Col col, Disk disk) {
    Optional<Square> square = Square.getSquare(row, col);
    square.ifPresent(sq -> this.disks.set(sq.getIndex(), Optional.ofNullable(disk)));
  }

  /**
   * Get {@code Optional} of specified square on the board.
   *
   * @param row Row of target square to get disk.
   * @param col Col of target square to get disk.
   * @return {@code Optional.of(Disk.BLACK)}, {@code Optional.of(Disk.WHITE)}, or *
   * {@code Optional.empty()}, responding to the given square.
   */
  public Optional<Disk> getDisk(@NotNull Row row, @NotNull Col col) {
    Optional<Square> square = Square.getSquare(row, col);
    return square.isPresent() ? this.disks.get(square.get().getIndex()) : Optional.empty();
  }

  /**
   * Clone board. Create new board which is same disk for each square.
   *
   * @return Cloned board.
   */
  @Override
  public Board clone() {
    Board clone = new Board();
    for (int i = 0; i < 64; i++) {
      clone.setDisk(Square.values()[i], this.disks.get(i).orElse(null));
    }
    return clone;
  }

  private String diskToString() {
    StringBuilder builder = new StringBuilder();
    this.disks.stream().map(
        disk -> {
          if (disk.isEmpty()) {
            return "empty";
          } else if (disk.get().equals(Disk.BLACK)) {
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
    return ((Board) obj).diskToString().equals(this.diskToString());
  }

  @Override
  public int hashCode() {
    return this.diskToString().hashCode();
  }
}
