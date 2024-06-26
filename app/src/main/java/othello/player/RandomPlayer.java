package othello.player;

import jakarta.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import othello.OthelloException;
import othello.base.Board;
import othello.base.Disk;
import othello.base.Square;
import othello.util.Score;
import othello.util.Tools;

/**
 * Random player is a computer player which moving disk to random square.
 */
public class RandomPlayer implements Player {

  private final String name;
  private Optional<Disk> myDisk;
  private final Random rand;

  /**
   * Constructor of RandomPlayer.
   *
   * @param name Player's name.
   * @param seed Random number seed.
   */
  public RandomPlayer(@NotNull String name, long seed) {
    this.myDisk = Optional.empty();
    this.rand = new Random(seed);
    this.name = name;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public Optional<Disk> getMyDisk() {
    return this.myDisk;
  }

  @Override
  public void init(@NotNull Disk myDisk) {
    this.myDisk = Optional.of(myDisk);
  }

  @Override
  public Optional<Square> move(@NotNull Board board, @NotNull Square moved) {
    // assert
    if (this.myDisk.isEmpty()) {
      // not initialized
      throw new OthelloException();
    }
    Board clone = board.clone();
    Score score = Tools.countTurnoverableDisks(clone, this.myDisk.get());
    List<Square> squares = Arrays.stream(Square.values())
        .filter(sq -> score.getScore(sq) > 0).toList();
    return !squares.isEmpty()
        ? Optional.of(squares.get(this.rand.nextInt(squares.size())))
        : Optional.empty();
  }

  @Override
  public void shutdown() {
  }
}
