package net.yoursweetest.othello.citrus;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import othello.OthelloException;
import othello.base.Board;
import othello.base.Disk;
import othello.base.Square;
import othello.util.Score;
import othello.util.Tools;

/**
 * This player is a multi-thread version of {@code LemonPlayer}. All public methods return the same
 * result as {@code LemonPlayer}. In particular, given the same random seed as {@code LemonPlayer},
 * they return the same result for each seed.
 */
public class SetokaPlayer extends CitrusPlayer {

  /**
   * Position is the state of the disk (BLACK, WHITE, or empty) on the squares at specified time.
   */
  private static final class Position {

    private final Board board;
    private final Disk turn;
    private final int step;
    private final Score myDisks;
    private final Score yourDisks;

    /**
     * Constructor of Position.
     *
     * @param board The board that Position issues.
     * @param turn  The disk represent who's turn is this.
     * @param step  {@code MAX_STEP} what this player explores.
     */
    Position(@NotNull Board board, @NotNull Disk turn, int step) {
      this.board = board;
      this.turn = turn;
      this.step = step;
      this.myDisks = new Score();
      this.yourDisks = new Score();
    }

    public Board getBoard() {
      return this.board;
    }

    public Disk getTurn() {
      return this.turn;
    }

    public int getStep() {
      return this.step;
    }

    public void setMyDisks(Square square, int disks) {
      this.myDisks.setScore(square, disks);
    }

    public void setYourDisks(Square square, int disks) {
      this.yourDisks.setScore(square, disks);
    }

    public Score getMyDisks() {
      return this.myDisks.clone();
    }

    public Score getYourDisks() {
      return this.yourDisks.clone();
    }
  }

  /**
   * Maximum step what this player explores to move.
   */
  private final int MAX_STEP;

  /**
   * Thread pool.
   */
  private final ExecutorService service;

  /**
   * Constructor of Setoka player.
   *
   * @param name    Player's name.
   * @param seed    Random seed.
   * @param maxStep Maximum step what this player explores to move.
   */
  public SetokaPlayer(@NotNull String name, long seed, int maxStep) {
    super(name, seed);
    this.MAX_STEP = maxStep;
    this.service = Executors.newFixedThreadPool(16);
  }

  @Override
  public void init(@NotNull Disk myDisk) {
    super.init(myDisk);
  }

  @Override
  List<Square> calculateCandidates(@NotNull Board board, Square moved) {
    // assert
    if (this.myDisk.isEmpty()) {
      // not initialized
      throw new OthelloException();
    }
    // multi-thread exec
    Position position = new Position(board, this.myDisk.get(), 0);
    this.explore(position);
    Score score = position.getMyDisks();
    int max = Arrays.stream(Square.values()).map(score::getScore)
        .max(Comparator.naturalOrder()).orElse(0);
    List<Square> squares = Arrays.stream(Square.values())
        .filter(sq -> score.getScore(sq) == max)
        .toList();
    return max > 0 ? squares : new ArrayList<>();
  }

  private void explore(@NotNull Position position1) {
    if (position1.getStep() <= MAX_STEP) {
      Score score = Tools.countTurnoverableDisks(position1.getBoard(), position1.getTurn());
      List<Square> movable = Arrays.stream(Square.values())
          .filter(
              sq -> score.getScore(sq) > 0
          ).toList();
      if (!movable.isEmpty()) {
        Function<Square, Boolean> proc = (sq) -> {
          // move
          Board work = position1.getBoard().clone();
          Tools.move(work, sq, position1.getTurn());
          // next position
          Position position2 =
              new Position(work, position1.getTurn().turnOver(), position1.getStep() + 1);
          // exec explore
          this.explore(position2);
          // count max
          int myMax = Arrays.stream(Square.values())
              .map(position2.getMyDisks()::getScore)
              .max(Comparator.naturalOrder()).orElse(0);
          int yourMax = Arrays.stream(Square.values())
              .map(position2.getYourDisks()::getScore)
              .max(Comparator.naturalOrder()).orElse(0);
          position1.setMyDisks(sq, myMax);
          position1.setYourDisks(sq, yourMax);
          return true;
        };
        if (position1.getStep() == 0) {
          List<Callable<Boolean>> tasks = new ArrayList<>();
          for (Square sq : movable) {
            tasks.add(() -> proc.apply(sq));
          }
          List<Future<Boolean>> futures = new ArrayList<>();
          for (Callable<Boolean> task : tasks) {
            futures.add(this.service.submit(task));
          }
          for (Future<Boolean> f : futures) {
            try {
              f.get();
            } catch (InterruptedException | ExecutionException e) {
              e.printStackTrace();
            }
          }
        } else {
          movable.forEach(proc::apply);
        }
        return;
      }
    }
    // max step or pass or end of game
    Arrays.stream(Square.values()).forEach(sq -> {
      position1.setMyDisks(sq,
          Tools.countDisks(position1.getBoard(), position1.getTurn()));
      position1.setYourDisks(sq,
          Tools.countDisks(position1.getBoard(), position1.getTurn()));
    });
  }

  @Override
  public void shutdown() {
    this.service.shutdownNow();
    super.shutdown();
  }
}
