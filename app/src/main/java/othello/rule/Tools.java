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

  public static int leftCountToTake(Board board, Square square, Stone mine) {
    return countToTake(Square::left, board, square, mine);
  }

  public static int rightCountToTake(Board board, Square square, Stone mine) {
    return countToTake(Square::right, board, square, mine);
  }

  public static int upLeftCountToTake(Board board, Square square, Stone mine) {
    return countToTake(Square::upLeft, board, square, mine);
  }

  public static int upRightCountToTake(Board board, Square square, Stone mine) {
    return countToTake(Square::upRight, board, square, mine);
  }

  public static int downLeftCountToTake(Board board, Square square, Stone mine) {
    return countToTake(Square::downLeft, board, square, mine);
  }

  public static int downRightCountToTake(Board board, Square square, Stone mine) {
    return countToTake(Square::downRight, board, square, mine);
  }

  private static int countToTake(
      Function<Square, Optional<Square>> next,
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
}
