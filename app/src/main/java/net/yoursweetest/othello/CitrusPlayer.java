package net.yoursweetest.othello;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import othello.Tools;
import othello.base.Board;
import othello.base.Disk;
import othello.base.Square;
import othello.player.Player;

public abstract class CitrusPlayer implements Player {

  protected final String name;
  protected final long seed;
  protected final Random rand;
  protected Optional<Disk> myDisk;

  protected CitrusPlayer(@NotNull String name, long seed) {
    this.name = name;
    this.seed = seed;
    this.rand = new Random(this.seed);
    this.myDisk = Optional.of(Disk.BLACK);
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
  public abstract Optional<Square> moveDisk(@NotNull Board board);

  @Override
  public void shutdown() {
  }

  protected List<Square> getMaximumSquares(@NotNull Board board, @NotNull Disk disk) {
    Board clone = board.clone();
    List<Integer> count = Arrays.stream(Square.values())
        .map(sq -> Tools.countReversibleDisks(clone, sq, disk)).toList();
    Optional<Integer> max = Arrays.stream(Square.values())
        .map(sq -> count.get(sq.getIndex()))
        .max(Comparator.naturalOrder());
    if (max.isEmpty() || max.get() <= 0) {
      return new ArrayList<>();
    }
    return Arrays.stream(Square.values())
        .filter(sq -> count.get(sq.getIndex()).equals(max.get())).toList();
  }

  protected List<Square> getMinimumSquares(@NotNull Board board, @NotNull Disk disk) {
    Board clone = board.clone();
    List<Integer> count = Arrays.stream(Square.values())
        .map(sq -> Tools.countReversibleDisks(clone, sq, disk)).toList();
    Optional<Integer> min = Arrays.stream(Square.values())
        .map(sq -> count.get(sq.getIndex()))
        .filter(c -> c > 0)
        .min(Comparator.naturalOrder());
    if (min.isEmpty() || min.get() <= 0) {
      return new ArrayList<>();
    }
    return Arrays.stream(Square.values())
        .filter(sq -> count.get(sq.getIndex()).equals(min.get())).toList();
  }

}
