package net.yoursweetest.othello.citrus;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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
 * This player is a multi-thread version of {@code MikanPlayer}. This player saves the search
 * results of the previous turn and reuses them to reduce amount of calculation. All public methods
 * return the same result as {@code LemonPlayer}. In particular, given the same random seed as
 * {@code LemonPlayer}, they return the same result for each seed.
 */
public class ZabonPlayer extends CitrusPlayer {

  private static final int THREAD_NUMBER = 16;

  /**
   * Position is the state of the disk (BLACK, WHITE, or empty) on the squares at specified time.
   */
  private static final class Position {

    private final Board board;
    private final Optional<Square> moved;
    private final Disk turn;
    private int step;
    private int myDiskCount;
    private int yourDiskCount;
    private List<Position> children;

    /**
     * Constructor of Position.
     *
     * @param board The board that Position issues.
     * @param moved {@code Optional} of the square object that another player move disk at previous
     *              turn. If this is the first move, or another player passed at previous turn, it
     *              is empty.
     * @param turn  The disk represent who's turn is this.
     * @param step  {@code MAX_STEP} what this player explores.
     */
    Position(@NotNull Board board, @NotNull Optional<Square> moved, @NotNull Disk turn, int step) {
      this.board = board;
      this.moved = moved;
      this.turn = turn;
      this.step = step;
      this.myDiskCount = 0;
      this.yourDiskCount = 0;
      this.children = null;
    }

    public Board getBoard() {
      return this.board;
    }

    public Disk getTurn() {
      return this.turn;
    }

    public void setStep(int step) {
      this.step = step;
    }

    public int getStep() {
      return this.step;
    }

    public Optional<Square> getMoved() {
      return this.moved;
    }

    public void setMyDiskCount(int count) {
      this.myDiskCount = count;
    }

    public int getMyDiskCount() {
      return this.myDiskCount;
    }

    public void setYourDiskCount(int count) {
      this.yourDiskCount = count;
    }

    public int getYourDiskCount() {
      return this.yourDiskCount;
    }

    /**
     * Set list of child Positions. These child positions are explored on the step following this
     * position.
     *
     * @param children List of child of this position. Un-modifiable-list is recommended.
     */
    public void setChildren(@NotNull List<Position> children) {
      this.children = children;
    }

    /**
     * Get list of child positions.
     *
     * @return List of child position of this position.
     */
    public List<Position> getChildren() {
      return this.isExplored() ? this.children : new ArrayList<>();
    }

    /**
     * Get whether this position already explored or not.
     *
     * @return {@code true} if this position id already explored at previous step or this step.
     */
    public boolean isExplored() {
      return this.children != null;
    }

    /**
     * Get whether this position is the last to be explored at this step or not.
     *
     * @return {@code true} if reached the max step, passed twice, or end of game.
     */
    public boolean isLeaf() {
      return this.children != null && this.children.isEmpty();
    }

  }

  private final int MAX_STEP;
  private Optional<Position> root;

  /**
   * Thread pool.
   */
  private final ExecutorService service;

  /**
   * Constructor of Zabon player.
   *
   * @param name    Player's name.
   * @param seed    Random seed.
   * @param maxStep Maximum step what this player explores to move.
   */
  public ZabonPlayer(@NotNull String name, long seed, int maxStep) {
    super(name, seed);
    this.MAX_STEP = maxStep;
    this.root = Optional.empty();
    this.service = Executors.newFixedThreadPool(THREAD_NUMBER);
  }

  /**
   * Search root position to explore at this turn.
   *
   * @param board The board that Position issues. This method should not edit this board.
   * @return Root position to explore at this turn.
   * @throws OthelloException if this player is not initialized.
   */
  private Position searchNewRoot(@NotNull Board board) {
    // assert
    if (this.myDisk.isEmpty()) {
      throw new OthelloException();
    }
    // root is already explored -> search children
    if (this.root.isPresent()) {
      for (Position p1 : this.root.get().getChildren()) {
        for (Position p2 : p1.getChildren()) {
          if (p2.getBoard().equals(board)) {
            p2.setStep(0);
            return p2;
          }
        }
      }
    }
    return new Position(board, Optional.empty(), this.myDisk.get().turnOver(), 0);
  }

  @Override
  List<Square> calculateCandidates(@NotNull Board board, Square moved) {
    // assert
    if (this.myDisk.isEmpty()) {
      throw new OthelloException();
    }
    // new root
    Position newRoot = this.searchNewRoot(board);
    this.explore(newRoot, this.root.isPresent() && this.root.get().isLeaf());
    this.root = Optional.of(newRoot);
    // best move
    if (newRoot.isExplored()) {
      int max = newRoot.getChildren().stream()
          .map(Position::getMyDiskCount)
          .max(Comparator.naturalOrder()).orElse(0);
      List<Square> squares = newRoot.getChildren().stream()
          .filter(p -> p.getMyDiskCount() == max)
          .filter(p -> p.getMoved().isPresent())
          .map(p -> p.getMoved().get()).toList();
      if (max >= 0) {
        return squares;
      }
    }
    return new ArrayList<>();
  }

  private boolean explore(@NotNull Position position1, boolean passed) {
    if (this.myDisk.isEmpty()) {
      throw new OthelloException();
    }
    position1.setMyDiskCount(0);
    position1.setYourDiskCount(0);
    if (!stopExploration(position1)) {
      // there exists data of previous step
      if (position1.isExplored()) {
        Function<Position, Boolean> proc = (p) -> {
          p.setStep(position1.getStep() + 1);
          return this.explore(p, position1.isLeaf());
        };
        if (position1.getStep() == 0) {
          // multi-thread exec.
          List<Callable<Boolean>> tasks = new ArrayList<>();
          for (Position p : position1.getChildren()) {
            tasks.add(() -> proc.apply(p));
          }
          List<Future<Boolean>> futures = new ArrayList<>();
          tasks.forEach(task -> futures.add(this.service.submit(task)));
          futures.forEach(f -> {
            try {
              f.get();
            } catch (InterruptedException | ExecutionException e) {
              e.printStackTrace();
              throw new OthelloException();
            }
          });
        } else {
          // just update step
          position1.getChildren().forEach(proc::apply);
        }
      } else {
        // unexplored -> (normal move)
        Function<Square, Position> proc = (sq) -> {
          // move
          Board work = position1.getBoard().clone();
          Tools.move(work, sq, position1.getTurn().turnOver());
          // next position
          Position position2 =
              new Position(
                  work, Optional.of(sq), position1.getTurn().turnOver(),
                  position1.getStep() + 1);
          // exec explore
          this.explore(position2, position1.getChildren().isEmpty());
          return position2;
        };
        Score score = Tools.countTurnoverableDisks(position1.getBoard(),
            position1.getTurn().turnOver());
        List<Square> squares = Arrays.stream(Square.values())
            .filter(sq -> score.getScore(sq) > 0).toList();
        List<Position> children = squares.stream().map(proc::apply).toList();
        position1.setChildren(children);
      }
      // not (pass -> pass)
      if (!passed || !position1.isLeaf()) {
        mergeMaxDisksOfChildren(position1);
        return true;
      }
    }
    // (max step) or (pass -> pass) or (end of game)
    countDisksOfLeaf(position1);
    return true;
  }

  @Override
  public void shutdown() {
    super.shutdown();
    this.service.shutdownNow();
  }

  private boolean stopExploration(@NotNull Position position) {
    // asset
    if (this.myDisk.isEmpty()) {
      throw new OthelloException();
    }
    int blackDisks = Tools.countDisks(position.getBoard(), Disk.BLACK);
    int whiteDisks = Tools.countDisks(position.getBoard(), Disk.WHITE);
    return position.getStep() > this.MAX_STEP || blackDisks + whiteDisks >= 64;
  }

  private void mergeMaxDisksOfChildren(Position position) {
    int myMax = position.getChildren().stream().map(Position::getMyDiskCount)
        .max(Comparator.naturalOrder()).orElse(0);
    int yourMax = position.getChildren().stream().map(Position::getYourDiskCount)
        .max(Comparator.naturalOrder()).orElse(0);
    position.setMyDiskCount(myMax);
    position.setYourDiskCount(yourMax);
  }

  private void countDisksOfLeaf(Position position) {
    position.setMyDiskCount(
        Tools.countDisks(position.getBoard(), position.getTurn().turnOver()));
    position.setYourDiskCount(
        Tools.countDisks(position.getBoard(), position.getTurn()));
  }

}
