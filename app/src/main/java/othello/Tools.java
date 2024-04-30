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

  public static int countDisks(Board board, Disk disk) {
    return (int) Arrays.stream(Square.values())
        .filter(sq -> disk.equals(board.getDisk(sq).orElse(null))).count();
  }

  private static int countToTakeEngine(
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
      // next stone.
      Optional<Disk> stone = board.getDisk(nextSquare.get());
      // if empty, ng.
      if (stone.isEmpty()) {
        return 0;
      }
      // if mine, ok.
      if (mine.equals(stone.get())) {
        return count;
      }
      // count yours.
      if (yours.equals(stone.get())) {
        count++;
      }
      nextSquare = next.apply(nextSquare.orElse(null));
    } while (true);
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
    // ok -> place the stone.
    work.setDisk(square, mine);
    // wright back work -> board.
    for (Square sq : Square.values()) {
      board.setDisk(sq, work.getDisk(sq).orElse(null));
    }
    return Optional.of(list);
  }

  private static Optional<List<Square>> moveEngine(
      @NotNull Function<Square, Optional<Square>> next,
      @NotNull Board board, @NotNull Square square, @NotNull Disk mine) {
    int count = countToTakeEngine(next, board, square, mine);
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

  public static int countToTake(@NotNull Board board, @NotNull Square square, @NotNull Disk mine) {
    int[] count = new int[8];
    count[0] = countToTakeEngine(Square::up, board, square, mine);
    count[1] = countToTakeEngine(Square::down, board, square, mine);
    count[2] = countToTakeEngine(Square::left, board, square, mine);
    count[3] = countToTakeEngine(Square::right, board, square, mine);
    count[4] = countToTakeEngine(Square::upLeft, board, square, mine);
    count[5] = countToTakeEngine(Square::upRight, board, square, mine);
    count[6] = countToTakeEngine(Square::downLeft, board, square, mine);
    count[7] = countToTakeEngine(Square::downRight, board, square, mine);
    if (Arrays.stream(count).filter(c -> c < 0).count() != 0L) {
      return -1;
    }
    return Arrays.stream(count).sum();
  }

  public static int upCountToTake(@NotNull Board board, @NotNull Square square,
      @NotNull Disk mine) {
    return countToTakeEngine(Square::up, board, square, mine);
  }

  public static int downCountToTake(@NotNull Board board, @NotNull Square square,
      @NotNull Disk mine) {
    return countToTakeEngine(Square::down, board, square, mine);
  }

  public static int leftCountToTake(@NotNull Board board, @NotNull Square square,
      @NotNull Disk mine) {
    return countToTakeEngine(Square::left, board, square, mine);
  }

  public static int rightCountToTake(@NotNull Board board, @NotNull Square square,
      @NotNull Disk mine) {
    return countToTakeEngine(Square::right, board, square, mine);
  }

  public static int upLeftCountToTake(@NotNull Board board, @NotNull Square square,
      @NotNull Disk mine) {
    return countToTakeEngine(Square::upLeft, board, square, mine);
  }

  public static int upRightCountToTake(@NotNull Board board, @NotNull Square square,
      @NotNull Disk mine) {
    return countToTakeEngine(Square::upRight, board, square, mine);
  }

  public static int downLeftCountToTake(@NotNull Board board, @NotNull Square square,
      @NotNull Disk mine) {
    return countToTakeEngine(Square::downLeft, board, square, mine);
  }

  public static int downRightCountToTake(@NotNull Board board, @NotNull Square square,
      @NotNull Disk mine) {
    return countToTakeEngine(Square::downRight, board, square, mine);
  }

}
