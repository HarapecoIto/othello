package net.yoursweetest.othello;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
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
  List<Square> moveCandidates(@NotNull Board board, Square moved) {
    // assert
    if (this.myDisk.isEmpty()) {
      // not initialized
      throw new OthelloException();
    }
    Score score = Tools.countReversibleDisks(board, this.myDisk.get());
    int max = Arrays.stream(Square.values())
        .map(score::getScore).max(Comparator.naturalOrder()).orElse(0);
    List<Square> squares = Arrays.stream(Square.values())
        .filter(sq -> score.getScore(sq) == max)
        .toList();
    return max > 0 && !squares.isEmpty()
        ? squares
        : new ArrayList<>();

  }
}
