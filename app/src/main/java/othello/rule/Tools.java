package othello.rule;

import java.util.Optional;
import java.util.function.Function;
import othello.base.Board;
import othello.base.Square;
import othello.base.Stone;

public class Tools {

  public static int upCountToTake(Board board, Square square, Stone mine) {
    return countToTake(Square::up, board, square, mine);
  }

  public static int downCountToTake(Board board, Square square, Stone mine) {
    return countToTake(Square::down, board, square, mine);
  }

  public static int leftCountToGet(Board board, Square square, Stone mine) {
    return countToTake(Square::left, board, square, mine);
  }

  public static int rightCountToGet(Board board, Square square, Stone mine) {
    return countToTake(Square::right, board, square, mine);
  }

  private static int countToTake(
      Function<Square, Optional<Square>> nextSquare,
      Board board, Square square, Stone mine) {
    if (board == null || square == null || mine == null) {
      throw new IllegalArgumentException();
    }
    // make sure that the square is empty.
    if (board.getStone(square).isPresent()) {
      return -1;
    }
    final Stone yours = mine.reverse();
    int count = 0;
    Optional<Square> next = nextSquare.apply(square);
    do {
      // out of board.
      if (next.isEmpty()) {
        return 0;
      }
      // next stone.
      Optional<Stone> stone = board.getStone(next.get());
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
      next = nextSquare.apply(next.orElse(null));
    } while (true);
  }
}
