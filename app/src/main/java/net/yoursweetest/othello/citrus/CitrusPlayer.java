package net.yoursweetest.othello.citrus;

import jakarta.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import othello.base.Board;
import othello.base.Disk;
import othello.base.Square;
import othello.player.Player;

public abstract class CitrusPlayer implements Player {

  protected final String name;
  private final long seed;
  private final Random rand;
  protected Optional<Disk> myDisk;

  protected CitrusPlayer(@NotNull String name, long seed) {
    this.name = name;
    this.seed = seed;
    this.rand = new Random(this.seed);
    this.myDisk = Optional.of(Disk.BLACK);
  }

  private static final Comparator<Square> squareComparator = Comparator.comparing(Square::getIndex);

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
  public final Optional<Square> moveDisk(@NotNull Board board, Square moved) {
    List<Square> candidates = allCandidates(board, moved);
    if (candidates.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(candidates.get(this.rand.nextInt(candidates.size())));
  }

  public List<Square> allCandidates(@NotNull Board board, Square moved) {
    return moveCandidates(board, moved)
        .stream().sorted(squareComparator)
        .toList();
  }

  abstract List<Square> moveCandidates(@NotNull Board board, Square moved);

  @Override
  public void shutdown() {
  }

}
