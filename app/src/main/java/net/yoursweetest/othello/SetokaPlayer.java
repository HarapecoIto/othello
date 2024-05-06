package net.yoursweetest.othello;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import othello.Tools;
import othello.base.Board;
import othello.base.Disk;
import othello.base.Square;

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
      this.myDisks = new ArrayList<>();
      this.yourDisks = new ArrayList<>();
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
  public Optional<Square> moveDisk(@NotNull Board board) {
    // assert
    if (this.myDisk.isEmpty()) {
      // not initialized
      return Optional.empty();
    }
    // multi-thread exec
    Position position = new Position(board, this.myDisk.get(), 0);
    this.explore(position);
    int max = position.getMyDisks().stream().max(Comparator.naturalOrder()).orElse(0);
    List<Square> squares = Arrays.stream(Square.values())
        .filter(sq -> position.getMyDisks().get(sq.getIndex()) == max)
        .toList();
    if (max > 0 && !squares.isEmpty()) {
      return Optional.of(squares.get(this.rand.nextInt(squares.size())));
    }
    return Optional.empty();
  }

  private void explore(@NotNull Position position1) {
    if (position1.getStep() < MAX_STEP) {
      List<Square> movable = Arrays.stream(Square.values())
          .filter(
              sq -> Tools.countReversibleDisks(position1.getBoard(), sq, position1.getTurn()) > 0
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
          System.out.println("aaa");
          List<Callable<Boolean>> tasks = new ArrayList<>();
          for (Square sq : movable) {
            tasks.add(() -> proc.apply(sq));
          }
          System.out.println("iii");
          List<Future<Boolean>> futures = new ArrayList<>();
          for (Callable<Boolean> task : tasks) {
            futures.add(this.service.submit(task));
          }
          System.out.println("uuu");
          futures.forEach(f -> {
            try {
              f.get();
            } catch (InterruptedException | ExecutionException e) {
              e.printStackTrace();
            }
          });
          System.out.println("ooo");
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
