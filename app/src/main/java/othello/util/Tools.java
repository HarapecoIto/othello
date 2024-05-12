package othello.util;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import othello.OthelloException;
import othello.base.Board;
import othello.base.Disk;
import othello.base.Square;

/**
 * You can use these tools to check the board status. Built-in classes are also use these tools.
 */
public class Tools {

  /**
   * Count disks on the board.
   *
   * @param board Target board to count disks.
   * @param disk  The disk color to be counted. {@code Disk.BLACK} or {@code Disk.WHITE}.
   * @return Number of disks specified by disk parameter.
   */
  public static int countDisks(@NotNull Board board, @NotNull Disk disk) {
    return (int) Arrays.stream(Square.values())
        .filter(sq -> disk.equals(board.getDisk(sq).orElse(null))).count();
  }

  /**
   * Move new disk to the board.
   *
   * @param board  The board to move the disk.
   * @param square Target square to move the disk.
   * @param myDisk Disk color to be moved. {@code Disk.BLACK} or {@code Disk.WHITE}.
   * @return {@code Optional} of List containing turned over disks.
   * @throws OthelloException This square cannot be placed due to the rules.
   */
  public static Optional<List<Square>> move(@NotNull Board board, @NotNull Square square,
      @NotNull Disk myDisk) {
    Board work = board.clone();
    List<Optional<List<Square>>> listOfList = new ArrayList<>();
    listOfList.add(moveEngine(Square::up, work, square, myDisk));
    listOfList.add(moveEngine(Square::down, work, square, myDisk));
    listOfList.add(moveEngine(Square::left, work, square, myDisk));
    listOfList.add(moveEngine(Square::right, work, square, myDisk));
    listOfList.add(moveEngine(Square::upLeft, work, square, myDisk));
    listOfList.add(moveEngine(Square::upRight, work, square, myDisk));
    listOfList.add(moveEngine(Square::downLeft, work, square, myDisk));
    listOfList.add(moveEngine(Square::downRight, work, square, myDisk));
    if (listOfList.stream().anyMatch(Optional::isEmpty)) {
      throw new OthelloException();
    }
    List<Square> reversed = listOfList.stream()
        .flatMap(opt -> opt.orElse(new ArrayList<>()).stream())
        .toList();
    if (reversed.isEmpty()) {
      return Optional.of(reversed);
    }
    // assert
    if (Tools.countDisks(work, myDisk) != Tools.countDisks(board, myDisk) + reversed.size()) {
      throw new OthelloException();
    }
    // ok -> place the disk.
    work.setDisk(square, myDisk);
    // write back work -> board.
    Arrays.stream(Square.values()).forEach(sq -> board.setDisk(sq, work.getDisk(sq).orElse(null)));
    return Optional.of(reversed);
  }

  private static Optional<List<Square>> moveEngine(
      @NotNull Function<Square, Optional<Square>> next,
      @NotNull Board board, @NotNull Square square, @NotNull Disk myDisk) {
    int count = countReversibleDisksEngine(next, board, square, myDisk);
    if (count < 0) {
      return Optional.empty();
    }
    List<Square> list = new ArrayList<>();
    Optional<Square> opt = Optional.of(square);
    for (int i = 0; i < count; i++) {
      opt = next.apply(opt.get());
      if (opt.isEmpty()) {
        // assertion failed.
        throw new OthelloException();
      }
      board.setDisk(opt.get(), myDisk);
      list.add(opt.get());
    }
    return Optional.of(list);
  }

  /**
   * Count how many another player's disk to be turned over, if you move the disk on these squares
   * correspond to each square. Note: No actual moves are made.
   *
   * @param board   Target board which to be moved the disk.
   * @param myStone Disk color to be moved. {@code Disk.BLACK} or {@code Disk.WHITE}.
   * @return Score object containing the number of disks to be turned over corresponding to each
   * square.
   */
  public static Score countReversibleDisks(@NotNull Board board, @NotNull Disk myStone) {
    Score score = new Score();
    Arrays.stream(Square.values())
        .forEach(sq -> score.setScore(sq, countReversibleDisks(board, sq, myStone)));
    return score;
  }

  static int countReversibleDisks(
      @NotNull Board board, @NotNull Square square, @NotNull Disk mine) {
    int[] count = new int[8];
    count[0] = countReversibleDisksEngine(Square::up, board, square, mine);
    count[1] = countReversibleDisksEngine(Square::down, board, square, mine);
    count[2] = countReversibleDisksEngine(Square::left, board, square, mine);
    count[3] = countReversibleDisksEngine(Square::right, board, square, mine);
    count[4] = countReversibleDisksEngine(Square::upLeft, board, square, mine);
    count[5] = countReversibleDisksEngine(Square::upRight, board, square, mine);
    count[6] = countReversibleDisksEngine(Square::downLeft, board, square, mine);
    count[7] = countReversibleDisksEngine(Square::downRight, board, square, mine);
    if (Arrays.stream(count).anyMatch(c -> c < 0)) {
      return -1;
    }
    return Arrays.stream(count).sum();
  }

  static int countReversibleDisksEngine(
      @NotNull Function<Square, Optional<Square>> next,
      @NotNull Board board, @NotNull Square square, @NotNull Disk mine) {
    // make sure that the square is empty.
    if (board.getDisk(square).isPresent()) {
      return -1;
    }
    final Disk yours = mine.reverse();
    int count = 0;
    Optional<Square> opt = next.apply(square);
    do {
      // out of board.
      if (opt.isEmpty()) {
        return 0;
      }
      // next disk.
      Optional<Disk> disk = board.getDisk(opt.get());
      // if empty, ng.
      if (disk.isEmpty()) {
        return 0;
      }
      // if mine, ok.
      if (mine.equals(disk.get())) {
        return count;
      }
      // count yours.
      if (yours.equals(disk.get())) {
        count++;
      }
      opt = next.apply(opt.orElse(null));
    } while (true);
  }

}
