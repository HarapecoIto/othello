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

/**
 * This player is super class that the type of player who explores a certain number of moves. All
 * subclasses are verified one's behaviour by comparing consistency of each other.
 */
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
    List<Square> candidates = sortCandidates(board, moved);
    if (candidates.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(candidates.get(this.rand.nextInt(candidates.size())));
  }

  /**
   * Get list of candidate squares to move disk. This list is sorted in ascending order by
   * {@code square.index()}. Every this method of subclasses, they should be return the same list.
   *
   * @param board The board that Position issues. This method should not edit this board.
   * @param moved The square what another player move his disk at previous turn.
   * @return List of candidate squares to be moved the disk.
   */
  public List<Square> sortCandidates(@NotNull Board board, Square moved) {
    return calculateCandidates(board, moved)
        .stream().sorted(Comparator.comparing(Square::index))
        .toList();
  }

  /**
   * Get list of candidate squares to move disk. This list may is not sorted.
   *
   * @param board The board that Position issues. This method should not edit this board.
   * @param moved The square what another player move his disk at previous turn.
   * @return List of candidate squares to be moved the disk.
   */
  abstract List<Square> calculateCandidates(@NotNull Board board, Square moved);

  @Override
  public void shutdown() {
  }

}
