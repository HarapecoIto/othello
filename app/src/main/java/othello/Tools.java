package othello;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
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
      return Optional.empty();
    }
    List<Square> list = new ArrayList<>();
    listOfList.forEach(
        l -> list.addAll(l.orElse(new ArrayList<>()))
    );
    if (list.isEmpty()) {
      return Optional.of(list);
    }
    // assert
    if (Tools.countDisks(work, mine) != Tools.countDisks(board, mine) + list.size()) {
      return Optional.empty();
    }
    // ok -> place the disk.
    work.setDisk(square, mine);
    // write back work -> board.
    Arrays.stream(Square.values()).forEach(sq -> board.setDisk(sq, work.getDisk(sq).orElse(null)));
    return Optional.of(list);
  }

  private static Optional<List<Square>> moveEngine(
      @NotNull Function<Square, Optional<Square>> next,
      @NotNull Board board, @NotNull Square square, @NotNull Disk mine) {
    int count = countReversibleDisksEngine(next, board, square, mine);
    if (count < 0) {
      return Optional.empty();
    }
    List<Square> list = new ArrayList<>();
    Square nextSquare = square;
    for (int i = 0; i < count; i++) {
      nextSquare = next.apply(nextSquare).orElse(null);
      board.setDisk(nextSquare, mine);
      list.add(nextSquare);
    }
    return Optional.of(list);
  }

  public static int countReversibleDisks(
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
    if (Arrays.stream(count).filter(c -> c < 0).count() != 0L) {
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
    Optional<Square> nextSquare = next.apply(square);
    do {
      // out of board.
      if (nextSquare.isEmpty()) {
        return 0;
      }
      // next disk.
      Optional<Disk> disk = board.getDisk(nextSquare.get());
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
      nextSquare = next.apply(nextSquare.orElse(null));
    } while (true);
  }
  
}
