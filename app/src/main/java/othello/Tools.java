package othello;

import jakarta.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import othello.base.Board;
import othello.base.Square;
import othello.base.Stone;

public class Tools {

  public static int countStones(Board board, Stone stone) {
    return (int) Arrays.stream(Square.values())
        .filter(sq -> stone.equals(board.getStone(sq).orElse(null))).count();
  }

  private static int countToTakeEngine(
      @NotNull Function<Square, Optional<Square>> next,
      @NotNull Board board, @NotNull Square square, @NotNull Stone mine) {
    // make sure that the square is empty.
    if (board.getStone(square).isPresent()) {
      return -1;
    }
    final Stone yours = mine.reverse();
    int count = 0;
    Optional<Square> nextSquare = next.apply(square);
    do {
      // out of board.
      if (nextSquare.isEmpty()) {
        return 0;
      }
      // next stone.
      Optional<Stone> stone = board.getStone(nextSquare.get());
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

  public static boolean move(@NotNull Board board, @NotNull Square square, @NotNull Stone mine) {
    int count = countToTake(board, square, mine);
    if (count < 1) {
      return false;
    }
    Board work = board.clone();
    int[] counts = new int[8];
    counts[0] = moveEngine(Square::up, work, square, mine);
    counts[1] = moveEngine(Square::down, work, square, mine);
    counts[2] = moveEngine(Square::left, work, square, mine);
    counts[3] = moveEngine(Square::right, work, square, mine);
    counts[4] = moveEngine(Square::upLeft, work, square, mine);
    counts[5] = moveEngine(Square::upRight, work, square, mine);
    counts[6] = moveEngine(Square::downLeft, work, square, mine);
    counts[7] = moveEngine(Square::downRight, work, square, mine);
    if (Arrays.stream(counts).filter(c -> c < 0).count() > 0) {
      return false;
    }
    work.setStone(square, mine);

    // assert
    if (Tools.countStones(work, mine) != Tools.countStones(board, mine) + count + 1) {
      return false;
    }
    for (Square sq : Square.values()) {
      board.setStone(sq, work.getStone(sq).orElse(null));
    }
    return true;
  }

  private static int moveEngine(
      @NotNull Function<Square, Optional<Square>> next,
      @NotNull Board board, @NotNull Square square, @NotNull Stone mine) {
    int count = countToTakeEngine(next, board, square, mine);
    if (count < 0) {
      return -1;
    }
    Square nextSquare = square;
    for (int i = 0; i < count; i++) {
      nextSquare = next.apply(nextSquare).orElse(null);
      board.setStone(nextSquare, mine);
    }
    return count;
  }

  public static int countToTake(@NotNull Board board, @NotNull Square square, @NotNull Stone mine) {
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
      @NotNull Stone mine) {
    return countToTakeEngine(Square::up, board, square, mine);
  }

  public static int downCountToTake(@NotNull Board board, @NotNull Square square,
      @NotNull Stone mine) {
    return countToTakeEngine(Square::down, board, square, mine);
  }

  public static int leftCountToTake(@NotNull Board board, @NotNull Square square,
      @NotNull Stone mine) {
    return countToTakeEngine(Square::left, board, square, mine);
  }

  public static int rightCountToTake(@NotNull Board board, @NotNull Square square,
      @NotNull Stone mine) {
    return countToTakeEngine(Square::right, board, square, mine);
  }

  public static int upLeftCountToTake(@NotNull Board board, @NotNull Square square,
      @NotNull Stone mine) {
    return countToTakeEngine(Square::upLeft, board, square, mine);
  }

  public static int upRightCountToTake(@NotNull Board board, @NotNull Square square,
      @NotNull Stone mine) {
    return countToTakeEngine(Square::upRight, board, square, mine);
  }

  public static int downLeftCountToTake(@NotNull Board board, @NotNull Square square,
      @NotNull Stone mine) {
    return countToTakeEngine(Square::downLeft, board, square, mine);
  }

  public static int downRightCountToTake(@NotNull Board board, @NotNull Square square,
      @NotNull Stone mine) {
    return countToTakeEngine(Square::downRight, board, square, mine);
  }

}
