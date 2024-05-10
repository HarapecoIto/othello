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

public class Tools {

  public static int countDisks(@NotNull Board board, @NotNull Disk disk) {
    return (int) Arrays.stream(Square.values())
        .filter(sq -> disk.equals(board.getDisk(sq).orElse(null))).count();
  }

  public static Optional<List<Square>> move(@NotNull Board board, @NotNull Square square,
      @NotNull Disk mine) {
    Board work = board.clone();
    List<Optional<List<Square>>> listOfList = new ArrayList<>();
    listOfList.add(moveEngine(Square::up, work, square, mine));
    listOfList.add(moveEngine(Square::down, work, square, mine));
    listOfList.add(moveEngine(Square::left, work, square, mine));
    listOfList.add(moveEngine(Square::right, work, square, mine));
    listOfList.add(moveEngine(Square::upLeft, work, square, mine));
    listOfList.add(moveEngine(Square::upRight, work, square, mine));
    listOfList.add(moveEngine(Square::downLeft, work, square, mine));
    listOfList.add(moveEngine(Square::downRight, work, square, mine));
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
    if (Tools.countDisks(work, mine) != Tools.countDisks(board, mine) + reversed.size()) {
      throw new OthelloException();
    }
    // ok -> place the disk.
    work.setDisk(square, mine);
    // write back work -> board.
    Arrays.stream(Square.values()).forEach(sq -> board.setDisk(sq, work.getDisk(sq).orElse(null)));
    return Optional.of(reversed);
  }

  private static Optional<List<Square>> moveEngine(
      @NotNull Function<Square, Optional<Square>> next,
      @NotNull Board board, @NotNull Square square, @NotNull Disk mine) {
    int count = countReversibleDisksEngine(next, board, square, mine);
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
      board.setDisk(opt.get(), mine);
      list.add(opt.get());
    }
    return Optional.of(list);
  }

  public static Score countReversibleDisks(@NotNull Board board, @NotNull Disk mine) {
    Score score = new Score();
    Arrays.stream(Square.values())
        .forEach(sq -> score.setScore(sq, countReversibleDisks(board, sq, mine)));
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
