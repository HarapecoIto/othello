package othello.base;

import jakarta.validation.constraints.NotNull;
import java.util.Optional;

/**
 * "Board" is the board on which the game is played and consists of 64 (8 x 8) squares. In each
 * square you can place a black or white disk.
 */
public final class Board implements Cloneable {

  static final String CLEAN_DISKS = "EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE";
  static final String INITIAL_DISKS = "EEEEEEEEEEEEEEEEEEEEEEEEEEEWBEEEEEEBWEEEEEEEEEEEEEEEEEEEEEEEEEEE";

  private char[] disks;

  public Board() {
    this.disks = INITIAL_DISKS.toCharArray();
  }

  /**
   * Make empty for each square on the board.
   */
  public void clear() {
    this.disks = CLEAN_DISKS.toCharArray();
  }

  /**
   * Make initial board for the game. Black disk for {@code SQUARE_4_E}, {@code SQUARE_5_D}, White
   * disk for {@code SQUARE_4_D}, {@code SQUARE_5_E}, empty for other squares.
   */
  public void init() {
    this.disks = INITIAL_DISKS.toCharArray();
  }

  /**
   * Set disk on the specific square on the board.
   *
   * @param square Square where the disk will be placed.
   * @param disk   Disk to place. {@code null} to empty cell.
   */
  public void setDisk(@NotNull Square square, Disk disk) {
    this.disks[square.index()] = disk == null ? 'E' : disk.equals(Disk.BLACK) ? 'B' : 'W';
  }

  /**
   * Get {@code Optional} of specified square on the board.
   *
   * @param square Target square to get disk.
   * @return {@code Optional.of(Disk.BLACK)}, {@code Optional.of(Disk.WHITE)}, or
   * {@code Optional.empty()}, responding to the given square.
   */
  public Optional<Disk> getDisk(@NotNull Square square) {
    if (this.disks[square.index()] == 'B') {
      return Optional.of(Disk.BLACK);
    } else if (this.disks[square.index()] == 'W') {
      return Optional.of(Disk.WHITE);
    }
    return Optional.empty();
  }

  /**
   * Set disk on the specific square on the board.
   *
   * @param row  Row of target square to set disk.
   * @param col  Col of target square to set disk.
   * @param disk Disk to place. {@code null} to empty cell.
   */
  public void setDisk(@NotNull Row row, @NotNull Col col, Disk disk) {
    this.setDisk(Square.values()[row.index() * 8 + col.index()], disk);
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
    return this.getDisk(Square.values()[row.index() * 8 + col.index()]);
  }

  /**
   * Clone board. Create new board which is same disk for each square.
   *
   * @return Cloned board.
   */
  @Override
  public Board clone() {
    Board clone = new Board();
    clone.disks = this.disks.clone();
    return clone;
  }

  @Override
  public String toString() {
    return new String(this.disks);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Board)) {
      return false;
    }
    return ((Board) obj).toString().equals(this.toString());
  }

  @Override
  public int hashCode() {
    return this.toString().hashCode();
  }
}
