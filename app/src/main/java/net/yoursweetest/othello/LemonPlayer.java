package net.yoursweetest.othello;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import othello.Tools;
import othello.base.Board;
import othello.base.Disk;
import othello.base.Square;

public class LemonPlayer extends CitrusPlayer {

  private final int MAX_STEP;

  public LemonPlayer(String name, long seed, int maxStep) {
    super(name, seed);
    this.MAX_STEP = maxStep;
  }

  @Override
  public Optional<Square> moveDisk(@NotNull Board board) {
    // assert
    if (this.myDisk.isEmpty()) {
      // not initialized
      return Optional.empty();
    }
    List<Integer> disks = explore(board, this.myDisk.get(), 0);
    Optional<Integer> max = Arrays.stream(Square.values())
        .filter(sq -> Tools.countReversibleDisks(board, sq, this.myDisk.get()) > 0)
        .map(sq -> disks.get(sq.getIndex()))
        .max(Comparator.naturalOrder());
    if (max.isPresent() && max.get() > 0) {
      List<Square> squares = Arrays.stream(Square.values())
          .filter(sq -> disks.get(sq.getIndex()).equals(max.get())).toList();
      return Optional.ofNullable(squares.get(this.rand.nextInt(squares.size())));
    }
    return Optional.empty();
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
          Optional<Integer> max = explore(work, turn.reverse(), nextStep).stream()
              .max(Comparator.naturalOrder());
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

