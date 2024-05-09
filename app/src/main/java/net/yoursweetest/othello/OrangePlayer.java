package net.yoursweetest.othello;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import othello.base.Board;
import othello.base.Square;

public class OrangePlayer extends CitrusPlayer {

  public OrangePlayer(String name, long seed) {
    super(name, seed);
  }

  @Override
  public Optional<Square> moveDisk(
      @NotNull Board board, Optional<Square> moved) {
    // assert
    if (this.myDisk.isEmpty()) {
      // not initialized
      return Optional.empty();
    }
    List<Square> list = getMaximumSquares(board, this.myDisk.get());
    return !list.isEmpty()
        ? Optional.of(list.get(this.rand.nextInt(list.size())))
        : Optional.empty();
  }

}
