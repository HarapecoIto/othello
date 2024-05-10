package net.yoursweetest.othello;

import jakarta.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import othello.OthelloException;
import othello.base.Board;
import othello.base.Square;
import othello.util.Score;
import othello.util.Tools;

public class OrangePlayer extends CitrusPlayer {

  public OrangePlayer(String name, long seed) {
    super(name, seed);
  }

  @Override
  public Optional<Square> moveDisk(@NotNull Board board, Square moved) {
    // assert
    if (this.myDisk.isEmpty()) {
      // not initialized
      throw new OthelloException();
    }
    Score score = Tools.countReversibleDisks(board, this.myDisk.get());
    int max = score.getMaximum();
    List<Square> list = Arrays.stream(Square.values())
        .filter(sq -> score.getScore(sq) == max)
        .toList();
    return max > 0 && !list.isEmpty()
        ? Optional.of(list.get(this.rand.nextInt(list.size())))
        : Optional.empty();
  }

}
