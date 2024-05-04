package net.yoursweetest.othello;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import othello.Tools;
import othello.base.Board;
import othello.base.Disk;
import othello.base.Square;

public class SetokaPlayer extends CitrusPlayer {

  private final int MAX_STEP;
  private ExecutorService service;

  public SetokaPlayer(String name, long seed, int maxStep) {
    super(name, seed);
    this.MAX_STEP = maxStep;
    this.service = null;
  }

  @Override
  public Optional<Square> moveDisk(@NotNull Board board) {
    // assert
    if (this.myDisk.isEmpty()) {
      // not initialized
      return Optional.empty();
    }
    this.service = Executors.newFixedThreadPool(16);
    List<Integer> disks = explore(board, this.myDisk.get(), 0);
    Optional<Integer> max = Arrays.stream(Square.values())
        .filter(sq -> Tools.countReversibleDisks(board, sq, this.myDisk.get()) > 0)
        .map(sq -> disks.get(sq.getIndex()))
        .max(Comparator.naturalOrder());
    Optional<Square> result = Optional.empty();
    if (max.isPresent() && max.get() > 0) {
      List<Square> squares = Arrays.stream(Square.values())
          .filter(sq -> disks.get(sq.getIndex()).equals(max.get())).toList();
      result = Optional.ofNullable(squares.get(this.rand.nextInt(squares.size())));
    }
    this.service.shutdownNow();
    return result;
  }

  private List<Integer> exploreWrapper(@NotNull Board board, @NotNull Disk turn, int step) {
    List<Integer> list = new ArrayList<>();
    try {
      Future<List<Integer>> future = this.service.submit(() -> explore(board, turn, step));
      list = future.get();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  private List<Integer> explore(@NotNull Board board, @NotNull Disk turn, int step) {
    // assert
    if (this.myDisk.isEmpty()) {
      return new ArrayList<>();
    }
    if (step < MAX_STEP) {
      int nextStep = step + 1;
      return Arrays.stream(Square.values()).map(sq -> {
        Board work = board.clone();
        if (Tools.countReversibleDisks(work, sq, turn) > 0) {
          Tools.move(work, sq, turn);
          List<Integer> disks =
              step == 2
                  ? exploreWrapper(work, turn.reverse(), nextStep)
                  : explore(work, turn.reverse(), nextStep);
          Optional<Integer> max = disks.stream().max(Comparator.naturalOrder());
          return max.orElse(0);
        }
        return Tools.countDisks(board, this.myDisk.get());
      }).toList();
    }
    return Arrays.stream(Square.values())
        .map(sq -> Tools.countDisks(board, this.myDisk.get()))
        .toList();
  }
}

