package net.yoursweetest.othello.citrus;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

public class SetokaPlayer extends CitrusPlayer {

  private static final class Position {

    private final Board board;
    private final Disk turn;
    private final int step;
    private final List<Integer> myDisks;
    private final List<Integer> yourDisks;

    Position(@NotNull Board board, @NotNull Disk turn, int step) {
      this.board = board;
      this.turn = turn;
      this.step = step;
      this.myDisks = Collections.synchronizedList(new ArrayList<>());
      this.yourDisks = Collections.synchronizedList(new ArrayList<>());
      Arrays.stream(Square.values()).forEach(sq -> {
        this.myDisks.add(0);
        this.yourDisks.add(0);
      });
    }

    public Board getBoard() {
      return this.board.clone();
    }

    public Disk getTurn() {
      return this.turn;
    }

    public int getStep() {
      return this.step;
    }

    public void setMyDisks(int idx, int disks) {
      if (this.myDisks.size() > idx) {
        this.myDisks.set(idx, disks);
      }
    }

    public void setYourDisks(int idx, int disks) {
      if (this.yourDisks.size() > idx) {
        this.yourDisks.set(idx, disks);
      }
    }

    public List<Integer> getMyDisks() {
      return Collections.unmodifiableList(this.myDisks);
    }

    public List<Integer> getYourDisks() {
      return Collections.unmodifiableList(this.yourDisks);
    }
  }

  private final int MAX_STEP;
  private ExecutorService service;

  public SetokaPlayer(String name, long seed, int maxStep) {
    super(name, seed);
    this.MAX_STEP = maxStep;
    this.service = null;
  }

  @Override
  public void init(@NotNull Disk myDisk) {
    super.init(myDisk);
    this.service = Executors.newFixedThreadPool(16);
  }

  @Override
  List<Square> moveCandidates(@NotNull Board board, Square moved) {
    // assert
    if (this.myDisk.isEmpty()) {
      // not initialized
      throw new OthelloException();
    }
    // multi-thread exec
    Position position = new Position(board, this.myDisk.get(), 0);
    this.explore(position);
    int max = position.getMyDisks().stream().max(Comparator.naturalOrder()).orElse(0);
    List<Square> squares = Arrays.stream(Square.values())
        .filter(sq -> position.getMyDisks().get(sq.getIndex()) == max)
        .toList();
    return max > 0 ? squares : new ArrayList<>();
  }

  private void explore(@NotNull Position position1) {
    if (position1.getStep() <= MAX_STEP) {
      Score score = Tools.countReversibleDisks(position1.getBoard(), position1.getTurn());
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
              new Position(work, position1.getTurn().reverse(), position1.getStep() + 1);
          // exec explore
          this.explore(position2);
          // count max
          int myMax = position2.getMyDisks().stream()
              .max(Comparator.naturalOrder()).orElse(0);
          int yourMax = position2.getYourDisks().stream()
              .max(Comparator.naturalOrder()).orElse(0);
          position1.setMyDisks(sq.getIndex(), myMax);
          position1.setYourDisks(sq.getIndex(), yourMax);
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
      position1.setMyDisks(sq.getIndex(),
          Tools.countDisks(position1.getBoard(), position1.getTurn()));
      position1.setYourDisks(sq.getIndex(),
          Tools.countDisks(position1.getBoard(), position1.getTurn()));
    });
  }

  @Override
  public void shutdown() {
    this.service.shutdownNow();
    super.shutdown();
  }
}
