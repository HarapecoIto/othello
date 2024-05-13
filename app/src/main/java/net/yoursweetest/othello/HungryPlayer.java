package net.yoursweetest.othello;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import othello.OthelloException;
import othello.base.Board;
import othello.base.Disk;
import othello.base.Square;
import othello.player.Player;
import othello.util.Score;
import othello.util.Tools;

public class HungryPlayer implements Player {

  private final String name;
  private final Random rand;
  private Optional<Disk> myDisk;

  public HungryPlayer(@NotNull String name, long seed) {
    this.name = name;
    this.rand = new Random(seed);
    this.myDisk = Optional.empty();
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void init(@NotNull Disk myDisk) {
    this.myDisk = Optional.of(myDisk);
  }

  @Override
  public Optional<Disk> getMyDisk() {
    return this.myDisk;
  }

  @Override
  public Optional<Square> move(@NotNull Board board, Square moved) {
    List<Square> squares = moveCandidates(board, moved);
    return !squares.isEmpty()
        ? Optional.of(squares.get(rand.nextInt(squares.size())))
        : Optional.empty();
  }

  List<Square> moveCandidates(@NotNull Board board, Square moved) {
    // assert
    if (this.myDisk.isEmpty()) {
      // not initialized
      throw new OthelloException();
    }
    Score score = Tools.countTurnoverableDisks(board, this.myDisk.get());
    int max = Arrays.stream(Square.values())
        .map(score::getScore).max(Comparator.naturalOrder()).orElse(0);
    List<Square> squares = Arrays.stream(Square.values())
        .filter(sq -> score.getScore(sq) == max)
        .toList();
    return max > 0 && !squares.isEmpty()
        ? squares
        : new ArrayList<>();
  }

  @Override
  public void shutdown() {
  }

}
