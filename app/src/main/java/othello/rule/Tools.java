package othello.rule;

import java.util.Optional;
import othello.base.Board;
import othello.base.Square;
import othello.base.Stone;

public class Tools {

  static int upReversibleCount(Board board, Square square, Stone mine) {
    if (board == null || square == null || mine == null) {
      throw new IllegalArgumentException();
    }
    // make sure that the square is empty.
    if (board.getStone(square).isPresent()) {
      return -1;
    }
    final Stone yours = mine.reverse();
    int count = 0;
    Optional<Square> next = square.up();
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
      next = next.get().up();
    } while (true);
  }
}
